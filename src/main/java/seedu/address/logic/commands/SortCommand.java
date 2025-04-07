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
    public static final String MESSAGE_EMPTY = "WealthVault is empty. No clients to sort.\n"
            + "To add a new client, use the addc command with the following format:\n"
            + "addc n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/POLICY_TAG]...\n"
            + "Example: addc n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getFilteredClientList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        model.sortClients();
        return new CommandResult(MESSAGE_SORTED_SUCCESS);
    }
}
