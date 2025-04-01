package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.*;
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
    public void isValidName_invalidPunctuation() {
        assertFalse(Name.isValidName("John--Doe")); // consecutive hyphens
        assertFalse(Name.isValidName("Anne//Marie")); // consecutive slashes
        assertFalse(Name.isValidName("O''Brien")); // consecutive apostrophes
        assertFalse(Name.isValidName("John ,Doe")); // space before comma
        assertFalse(Name.isValidName("John.  Doe")); // multiple spaces after period
    }

    @Test
    public void isValidName_validSlashUsage() {
        assertTrue(Name.isValidName("s/o John"));
        assertTrue(Name.isValidName("d/o Jane"));
        assertFalse(Name.isValidName("ss/oo")); // not allowed
        assertFalse(Name.isValidName("x/y")); // not allowed
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

    @Test
    public void formatName_handlesExact120Characters() {
        String exact120 = "x".repeat(120);
        Name name = new Name(exact120);
        assertEquals(exact120, name.toString()); // No new line needed
    }

    @Test
    public void formatName_handlesLongWords() {
        String longWord = "x".repeat(130);
        Name name = new Name(longWord);
        String formattedName = name.toString();
        assertTrue(formattedName.contains("-\n")); // Ensure hyphen is added for long words
    }

    @Test
    public void normalizeName_handlesSpacingAndSpecialCharacters() {
        Name name1 = new Name("John   Doe");
        assertEquals("John Doe", name1.toString()); // Normalize spaces

        Name name2 = new Name("John,Jane");
        assertEquals("John, Jane", name2.toString()); // Ensure comma spacing

        Name name3 = new Name("John@Doe");
        assertEquals("John @ Doe", name3.toString()); // Ensure @ spacing
    }
}
