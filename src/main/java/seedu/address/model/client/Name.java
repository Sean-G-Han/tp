package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "The name given is invalid. It either contains invalid symbols, or is more than 150 characters.";

    public static final String VALIDATION_REGEX = "[\\p{L}\\p{N}]+([ '-/@]+[\\p{L}\\p{N}]+)*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String normalizedName = normalizeName(name);
        checkArgument(isValidName(normalizedName), MESSAGE_CONSTRAINTS);
        fullName = normalizedName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 150;
    }

    private String normalizeName(String name) {
        name = name.replaceAll("\\s+", " ").trim();
        name = name.replaceAll("([,.@])(?!\\s)", "$1 ");
        name = name.replaceAll("(?<!\\s)@", " @");
        name = name.replaceAll("(?i)s/o", "s/o");
        name = name.replaceAll("(?i)d/o", "d/o");

        return name.trim();
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
