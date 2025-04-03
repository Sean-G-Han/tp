package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddClientCommand;

/**
 * Represents a Client's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";

    public static final String MESSAGE_CONSTRAINTS =
            "The email address given is invalid!\n"
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Emails should be of the format [local-part]@[domain].\n"
                    + "Standard domain names like gmail.com are definitely fine.\n"
                    + "Do not include unnecessary whitespace.\n"
                    + "Only leading and trailing whitespaces will be removed.\n\n"
                    + AddClientCommand.MESSAGE_USAGE
                    + "\n\n"
                    + "For more information on valid email formats, you may refer to the following:"
                    + "\nThe local-part should be alphanumeric, but can also contain "
                    + SPECIAL_CHARACTERS
                    + "\nThe local part may not start or end with any special characters.\n"
                    + "\nThe domain name must end with a domain label at least 2 characters long.\n"
                    + "Every domain label must start and end with alphanumeric characters.\n"
                    + "Every domain label must consist of alphanumeric characters.\n"
                    + "Every domain label can be separated only by hyphens, if any.");

    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)*" + DOMAIN_LAST_PART_REGEX;
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX) || test.equals("-");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
