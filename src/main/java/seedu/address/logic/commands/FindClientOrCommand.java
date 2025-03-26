package seedu.address.logic.commands;

import seedu.address.model.client.ContainsKeywordsPredicate;

/**
 * Finds and lists all clients in address book whose name or tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindClientOrCommand extends AbstractFindClientCommand {
    public static final String COMMAND_WORD = "findany";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients whose names or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob Priority";

    public FindClientOrCommand(ContainsKeywordsPredicate predicate) {
        super(predicate);
    }
}
