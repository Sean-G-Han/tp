package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.Tag;

/**
 * Deletes policies from an existing client by their index.
 */
public class DeletePolicyCommand extends Command {

    public static final String COMMAND_WORD = "delp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes policies from an existing client identified by the user-inputted index number. "
            + "\n         The index number must be based on the displayed client list.\n"
            + "Parameters: INDEX (MUST BE A POSITIVE INTEGER) POLICY [MORE_POLICIES]...\n"
            + "Example: " + COMMAND_WORD + " 1 t/Life Insurance t/Health Insurance";

    public static final String MESSAGE_SUCCESS = "Updated Policy Information: %1$s";
    public static final String MESSAGE_CLIENT_NOT_FOUND = "Client with the given index does not exist!";
    public static final String MESSAGE_POLICY_NOT_FOUND = "One or more specified policies do not exist for the client!";
    public static final String MESSAGE_USE_PRIORITY_COMMAND =
            "\nt/Priority, if included, is not deleted. Please use priority command to toggle priority.\n"
                    + "Duplicates are skipped.";
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
            throw new CommandException(MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE));
        }

        Client clientToEdit = model.getFilteredClientList().get(clientIndex.getZeroBased());
        Set<Tag> updatedPolicies = new HashSet<>(clientToEdit.getTags());

        Set<Tag> policiesToDelete2 = new HashSet<>();
        for (Tag t : policiesToDelete) {
            if (!(t instanceof PriorityTag)) {
                policiesToDelete2.add(t);
            }
        }

        if (!updatedPolicies.containsAll(policiesToDelete)) {
            throw new CommandException(MESSAGE_POLICY_NOT_FOUND);
        }

        updatedPolicies.removeAll(policiesToDelete2);

        Client updatedClient = new Client(
                clientToEdit.getName(),
                clientToEdit.getPhone(),
                clientToEdit.getEmail(),
                clientToEdit.getAddress(),
                updatedPolicies
        );

        model.setClient(clientToEdit, updatedClient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedClient))
                + MESSAGE_USE_PRIORITY_COMMAND);
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
