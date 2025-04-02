package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindClientAndCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ContainsAllKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindClientOrCommand object
 */
public class FindClientAndCommandParser implements Parser<FindClientAndCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindClientCommand
     * and returns a FindClientAndCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindClientAndCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_FIELD
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientAndCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindClientAndCommand(new ContainsAllKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
