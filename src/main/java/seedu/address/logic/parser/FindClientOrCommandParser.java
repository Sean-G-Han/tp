package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindClientOrCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindClientOrCommand object
 */
public class FindClientOrCommandParser implements Parser<FindClientOrCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindClientCommand
     * and returns a FindClientOrCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindClientOrCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientOrCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindClientOrCommand(new ContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
