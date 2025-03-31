package seedu.address.model.client;

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
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName("     ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("John,, Doe")); // consecutive commas
        assertFalse(Name.isValidName("John.. Doe")); // consecutive periods
        assertFalse(Name.isValidName("John@@Doe")); // consecutive @ symbols
        assertFalse(Name.isValidName("John#Doe")); // contains non-alphanumeric characters

        // valid names
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("John, Jane")); // valid with comma
        assertTrue(Name.isValidName("John. Jane")); // valid with period
        assertTrue(Name.isValidName("John @ Doe")); // valid with @ symbol
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
    public void formatName_breaksLongNamesIntoLines() {
        String longName = "John Doe " + "x".repeat(130);  // 130 characters long, should break into new lines
        Name name = new Name(longName);
        String formattedName = name.toString();

        // Check that the formatted name is split correctly
        String[] lines = formattedName.split("\n");
        assertTrue(lines.length > 1);  // Ensures that line breaks occur
    }
}
