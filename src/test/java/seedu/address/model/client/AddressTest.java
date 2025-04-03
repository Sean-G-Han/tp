package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("   ")); // multiple spaces only
        assertFalse(Address.isValidAddress("\t")); // tab only
        assertFalse(Address.isValidAddress("\n")); // newline only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
        assertTrue(Address.isValidAddress("Address with leading space but "
                + "not only space")); // leading space but not only space
        assertTrue(Address.isValidAddress("Address with trailing space but "
                + "not only space ")); // trailing space but not only space
        assertTrue(Address.isValidAddress(" Address with leading and trailing space ")); // leading and trailing space
        assertTrue(Address.isValidAddress("Address with tab\tin it")); // address with tab in it.
        assertTrue(Address.isValidAddress("A" + " ".repeat(150) + "O")); // address with 150 spaces in it.
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }

    @Test
    public void toString_returnsCorrectValue() {
        Address address = new Address("123 Main St");
        assertEquals("123 Main St", address.toString());
    }

    @Test
    public void constructor_addressWithExtraSpaces_normalizeAddress() {
        Address address = new Address("  123  Main   St  ");
        assertEquals("123 Main St", address.value);
    }

    @Test
    public void constructor_addressWithLeadingAndTrailingSpaces_normalizeAddress() {
        Address address = new Address("  123 Main St  ");
        assertEquals("123 Main St", address.value);
    }
}
