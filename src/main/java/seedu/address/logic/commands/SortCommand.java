package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts all clients on the GUI in case-insensitive alphabetical order for the user.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all clients in WealthVault by case-insensitive alphabetical order.";
    public static final String MESSAGE_SORTED_SUCCESS = "Clients sorted in alphabetical order.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortClients();
        return new CommandResult(MESSAGE_SORTED_SUCCESS);
    }
}
