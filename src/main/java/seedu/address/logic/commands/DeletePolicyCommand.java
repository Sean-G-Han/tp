package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;

/**
 * Deletes policies from an existing client by their index.
 */
public class DeletePolicyCommand extends Command {

    public static final String COMMAND_WORD = "deletepolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes policies from an existing client identified by the user-inputted index number. "
            + "The index number must be based on the displayed client list. "
            + "Parameters: INDEX (MUST BE A POSITIVE INTEGER) POLICY [MORE_POLICIES]...\n"
            + "Example: " + COMMAND_WORD + " 1 t/Life Insurance t/Health Insurance";

    public static final String MESSAGE_SUCCESS = "Policies deleted from client: %1$s";
    public static final String MESSAGE_CLIENT_NOT_FOUND = "Client with the given index does not exist.";
    public static final String MESSAGE_POLICY_NOT_FOUND = "One or more specified policies do not exist for the client.";

    private final Index clientIndex;
    private final Set<Tag> policiesToDelete;

    /**
     * Creates a DeletePolicyCommand to remove the specified policies from a client by index.
     */
    public DeletePolicyCommand(Index clientIndex, Set<Tag> policies) {
        requireNonNull(clientIndex);
        requireNonNull(policies);
        this.clientIndex = clientIndex;
        this.policiesToDelete = new HashSet<>(policies);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if the index is valid
        if (clientIndex.getZeroBased() >= model.getFilteredClientList().size()) {
            throw new CommandException(MESSAGE_CLIENT_NOT_FOUND);
        }

        Client clientToEdit = model.getFilteredClientList().get(clientIndex.getZeroBased());
        Set<Tag> updatedPolicies = new HashSet<>(clientToEdit.getTags());

        // Check if all policies to delete exist
        if (!updatedPolicies.containsAll(policiesToDelete)) {
            throw new CommandException(MESSAGE_POLICY_NOT_FOUND);
        }

        updatedPolicies.removeAll(policiesToDelete);

        Client updatedClient = new Client(
                clientToEdit.getName(),
                clientToEdit.getPhone(),
                clientToEdit.getEmail(),
                clientToEdit.getAddress(),
                updatedPolicies
        );

        model.setClient(clientToEdit, updatedClient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedClient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeletePolicyCommand)) {
            return false;
        }

        DeletePolicyCommand otherCommand = (DeletePolicyCommand) other;
        return clientIndex.equals(otherCommand.clientIndex) && policiesToDelete.equals(otherCommand.policiesToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("policiesToDelete", policiesToDelete)
                .toString();
    }
}
