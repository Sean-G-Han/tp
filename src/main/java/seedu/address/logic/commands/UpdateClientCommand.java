package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Phone;

/**
 * Updates contact information (phone, email, address) of an existing client in the address book.
 * Unlike EditCommand, UpdateClientCommand only allows updating contact information fields.
 */
public class UpdateClientCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the contact information of the client identified "
            + "by the index number used in the displayed client list. "
            + "Only phone, email, and address can be updated.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_UPDATE_CLIENT_SUCCESS = "Updated Client: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in WealthVault.";

    private final Index index;
    private final EditCommand.EditClientDescriptor updateClientDescriptor;

    /**
     * @param index of the client in the filtered client list to update
     * @param updateClientDescriptor details to update the client with
     */
    public UpdateClientCommand(Index index, EditCommand.EditClientDescriptor updateClientDescriptor) {
        requireNonNull(index);
        requireNonNull(updateClientDescriptor);

        this.index = index;
        this.updateClientDescriptor = new EditCommand.EditClientDescriptor(updateClientDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        // Check if any fields are being updated
        if (!isAnyContactFieldEdited(updateClientDescriptor)) {
            throw new CommandException(MESSAGE_NOT_UPDATED);
        }

        Client clientToUpdate = lastShownList.get(index.getZeroBased());
        Client updatedClient = createUpdatedClient(clientToUpdate, updateClientDescriptor);

        if (!clientToUpdate.isSameClient(updatedClient) && model.hasClient(updatedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.setClient(clientToUpdate, updatedClient);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
        return new CommandResult(String.format(MESSAGE_UPDATE_CLIENT_SUCCESS, Messages.format(updatedClient)));
    }

    /**
     * Creates and returns a {@code Client} with the contact details of {@code clientToUpdate}
     * updated with {@code updateClientDescriptor}.
     * Only phone, email, and address can be updated.
     */
    private static Client createUpdatedClient(Client clientToUpdate,
        EditCommand.EditClientDescriptor updateClientDescriptor) {
        assert clientToUpdate != null;

        // These fields can be updated
        Phone updatedPhone = updateClientDescriptor.getPhone().orElse(clientToUpdate.getPhone());
        Email updatedEmail = updateClientDescriptor.getEmail().orElse(clientToUpdate.getEmail());
        Address updatedAddress = updateClientDescriptor.getAddress().orElse(clientToUpdate.getAddress());

        // These fields are preserved (not updated)
        return new Client(
                clientToUpdate.getName(),
                updatedPhone,
                updatedEmail,
                updatedAddress,
                clientToUpdate.getTags()
        );
    }

    /**
     * Returns true if at least one contact field (phone, email, address) is edited.
     */
    private boolean isAnyContactFieldEdited(EditCommand.EditClientDescriptor updateClientDescriptor) {
        return updateClientDescriptor.getPhone().isPresent()
                || updateClientDescriptor.getEmail().isPresent()
                || updateClientDescriptor.getAddress().isPresent();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateClientCommand)) {
            return false;
        }

        UpdateClientCommand otherUpdateCommand = (UpdateClientCommand) other;
        return index.equals(otherUpdateCommand.index)
                && updateClientDescriptor.equals(otherUpdateCommand.updateClientDescriptor);
    }
}
