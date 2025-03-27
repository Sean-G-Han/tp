package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class DeletePolicyCommandParserTest {

    private DeletePolicyCommandParser parser = new DeletePolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        // Prepare the expected tags and client index
        Set<Tag> policiesToDelete = new HashSet<>();
        policiesToDelete.add(new Tag("Health"));
        policiesToDelete.add(new Tag("Life"));

        assertParseSuccess(parser, "1 " + PREFIX_TAG + "Health " + PREFIX_TAG + "Life",
                new DeletePolicyCommand(INDEX_FIRST_CLIENT, policiesToDelete));
    }

    @Test
    public void parse_multipleTags_success() throws ParseException {
        Set<Tag> policiesToDelete = new HashSet<>();
        policiesToDelete.add(new Tag("Health"));
        policiesToDelete.add(new Tag("Life"));
        policiesToDelete.add(new Tag("Travel"));

        assertParseSuccess(parser, "1 " + PREFIX_TAG + "Health " + PREFIX_TAG + "Life " + PREFIX_TAG + "Travel",
                new DeletePolicyCommand(INDEX_FIRST_CLIENT, policiesToDelete));
    }

    @Test
    public void parse_missingPolicy_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClientIndex_throwsParseException() {
        assertParseFailure(parser, "abc " + PREFIX_TAG + "Health",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, "ExtraText 1 " + PREFIX_TAG + "Health",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyCommand_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validClientWithSinglePolicy_success() throws ParseException {
        Set<Tag> policiesToDelete = new HashSet<>();
        policiesToDelete.add(new Tag("Health"));

        assertParseSuccess(parser, "2 " + PREFIX_TAG + "Health",
                new DeletePolicyCommand(INDEX_SECOND_CLIENT, policiesToDelete));
    }
}

