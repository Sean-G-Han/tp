package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.DUPLICATE_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityCommand;

public class PriorityCommandParserTest {
    private PriorityCommandParser parser = new PriorityCommandParser();

    @Test
    public void parse_validArgs_returnsPriorityClientCommand() {
        Index i1 = Index.fromOneBased(1);
        Index i2 = Index.fromOneBased(2);

        // One input
        assertParseSuccess(parser, "1", new PriorityCommand(List.of(i1)));

        // Same list
        assertParseSuccess(parser, "1 2", new PriorityCommand(List.of(i1, i2)));

        // Reversed order
        assertParseSuccess(parser, "2 1", new PriorityCommand(List.of(i1, i2)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Non-integer value
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));

        // Zero number
        assertParseFailure(parser, "0", MESSAGE_INVALID_INDEX
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));

        // Duplicate values
        assertParseFailure(parser, "1 1", DUPLICATE_INDEX
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }
}
