package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, periods (.), commas (,)" +
                    "'at' symbol (@), s/o or d/o!";

    public static final String VALIDATION_REGEX = "^(?:(s/o|d/o)|[\\p{Alnum},.@])[\\p{Alnum},.@ ]*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(normalizeName(name)), MESSAGE_CONSTRAINTS);
        fullName = formatName(normalizeName(name));
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private String normalizeName(String name) {
        name = name.replaceAll("\\s+", " ").trim();
        name = name.replaceAll("([,.@])(?!\\s)", "$1 ");
        name = name.replaceAll("(?<!\\s)@"," @");
        name = name.replaceAll("(?i)s/o", "s/o");
        name = name.replaceAll("(?i)d/o", "d/o");

        return name.trim();
    }

    /**
     * Formats the name to ensure that if it exceeds 120 characters, it breaks into a new line,
     * adding a hyphen (-) if a word is split across lines, ensuring all lines end exactly at 120 characters.
     */
    private String formatName(String name) {
        int maxLineLength = 120;
        if (name.length() <= maxLineLength) {
            return name;
        }

        StringBuilder formattedName = new StringBuilder();
        int lineLength = 0;

        String[] words = name.split(" ");
        for (String word : words) {
            if (lineLength + word.length() > maxLineLength) {
                // If adding this word exceeds maxLineLength, go to a new line
                while (lineLength < maxLineLength) {
                    formattedName.append(" "); // Pad with spaces
                    lineLength++;
                }
                formattedName.append("\n");
                lineLength = 0;
            }

            if (lineLength > 0) {
                formattedName.append(" ");
                lineLength++;
            }

            if (word.length() > maxLineLength) {
                // Break the long word with a hyphen
                for (int i = 0; i < word.length(); i++) {
                    if (lineLength >= maxLineLength) {
                        formattedName.append("-\n");
                        lineLength = 0;
                    }
                    formattedName.append(word.charAt(i));
                    lineLength++;
                }
            } else {
                formattedName.append(word);
                lineLength += word.length();
            }
        }
        // Ensure the last line also reaches exactly maxLineLength
        while (lineLength < maxLineLength) {
            formattedName.append(" ");
            lineLength++;
        }
        return formattedName.toString().trim();
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}