package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("a".repeat(151))); // longer than 150 characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("a".repeat(150))); // exactly 150 characters
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void constructor_validName_normalizesName() {
        // Test normalization
        Name name1 = new Name("  John   Doe  ");
        assertEquals("John Doe", name1.fullName);

        Name name2 = new Name("John,Doe");
        assertEquals("John, Doe", name2.fullName);

        Name name4 = new Name("John s/o Doe");
        assertEquals("John s/o Doe", name4.fullName);

        Name name5 = new Name("John d/o Doe");
        assertEquals("John d/o Doe", name5.fullName);
    }

    @Test
    public void toString_returnsFullName() {
        Name name = new Name("Test Name");
        assertEquals("Test Name", name.toString());
    }

    @Test
    public void hashCode_returnsHashCodeOfFullName() {
        Name name1 = new Name("Test Name");
        Name name2 = new Name("Test Name");
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    public void constructor_longName_throwsIllegalArgumentException() {
        String longName = "a".repeat(151);
        assertThrows(IllegalArgumentException.class, () -> new Name(longName));
    }
}
