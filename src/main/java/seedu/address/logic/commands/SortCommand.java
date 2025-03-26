package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts all clients in the address book to the user.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all clients in the address book by alphabetical order.";
    public static final String MESSAGE_SORTED_SUCCESS = "Clients sorted in alphabetical order.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortClients();
        return new CommandResult(MESSAGE_SORTED_SUCCESS);
    }
}
