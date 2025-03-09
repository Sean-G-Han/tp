package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Marks a client as a priority
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person identified by the index number used in the displayed person list as a priority.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to mark as priority
     */
    public PriorityCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityCommand)) {
            return false;
        }

        PriorityCommand e = (PriorityCommand) other;
        return index.equals(e.index);
    }
}
