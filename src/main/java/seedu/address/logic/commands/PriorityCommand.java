package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Marks a client as a priority
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("TRIAL");
    }
}