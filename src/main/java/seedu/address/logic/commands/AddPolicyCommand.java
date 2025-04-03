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
 * Adds policies to an existing client by their index.
 */
public class AddPolicyCommand extends Command {

    public static final String COMMAND_WORD = "addp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds policies to an existing client identified by the user-inputted index number.\n"
            + "          The index number must be based on the displayed client list.\n"
            + "Parameters: INDEX (MUST BE A POSITIVE INTEGER) POLICY [MORE_POLICIES]...\n"
            + "Example: " + COMMAND_WORD + " 1 t/Life Insurance t/Health Insurance";

    public static final String MESSAGE_SUCCESS = "Updated Policy Information: %1$s";
    public static final String MESSAGE_USE_PRIORITY_COMMAND =
            "\nt/Priority, if included, is not added. Please use priority command to toggle priority.\n"
            + "Duplicates are skipped.";

    private final Index clientIndex;
    private final Set<Tag> policiesToAdd;

    /**
     * Creates an AddPolicyCommand to add the specified policies to a client by index.
     */
    public AddPolicyCommand(Index clientIndex, Set<Tag> policies) {
        requireNonNull(clientIndex);
        requireNonNull(policies);
        this.clientIndex = clientIndex;
        this.policiesToAdd = new HashSet<>(policies);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if the index is valid
        if (clientIndex.getZeroBased() >= model.getFilteredClientList().size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        Client clientToEdit = model.getFilteredClientList().get(clientIndex.getZeroBased());
        Set<Tag> updatedPolicies = new HashSet<>(clientToEdit.getTags());

        Set<Tag> policiesToAdd2 = new HashSet<>();
        for (Tag t : policiesToAdd) {
            if (!(t instanceof PriorityTag)) {
                policiesToAdd2.add(t);
            }
        }

        updatedPolicies.addAll(policiesToAdd2);

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

        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand otherCommand = (AddPolicyCommand) other;
        return clientIndex.equals(otherCommand.clientIndex) && policiesToAdd.equals(otherCommand.policiesToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("policiesToAdd", policiesToAdd)
                .toString();
    }
}


