package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPolicyCommand object.
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPolicyCommand
     * and returns an AddPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        try {
            // Parse the index of the client
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

            // Parse policies
            Set<Tag> policies = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            // Check if policies are provided
            if (policies.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
            }

            // Return the AddPolicyCommand object
            return new AddPolicyCommand(index, policies);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

