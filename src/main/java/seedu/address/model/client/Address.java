package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddClientCommand;

/**
 * Represents a Client's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS =
            "The address given is invalid!\n"
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "The address given is either blank or purely whitespace.\n\n")
                    + AddClientCommand.MESSAGE_USAGE;

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\s*\\S.*\\S?\\s*|\\S+";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        String normalizedAddress = normalizeAddress(address);
        checkArgument(isValidAddress(normalizedAddress), MESSAGE_CONSTRAINTS);
        value = normalizedAddress;
    }

    private String normalizeAddress(String name) {
        name = name.replaceAll("\\s+", " ").trim();
        return name.trim();
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

