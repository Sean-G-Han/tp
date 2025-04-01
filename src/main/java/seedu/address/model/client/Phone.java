package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should be in the format +[international code] [number], where the international code "
                    + "is 1-3 digits, and the number is at most 13 digits.\n"
                    + "If no international code is provided, +65 is assumed.\n"
                    + "Leave a space between [international code] and [number].";
    public static final String VALIDATION_REGEX = "^(\\+\\d{1,3} )?\\d{3,13}$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = processPhone(phone);
    }

    /**
     * Processes the phone number to ensure it has an international code.
     * If no international code is provided, it defaults to +65.
     *
     * @param phone The input phone number.
     * @return The processed phone number with an international code.
     */
    private String processPhone(String phone) {
        if (phone.startsWith("+")) {
            return phone;
        } else {
            return "+65 " + phone;
        }
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        String processedTest = test.startsWith("+") ? test : "+65 " + test;
        return processedTest.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
