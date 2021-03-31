package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

public class ListCommand extends Command {

    private final EventWaiter eventWaiter;

    public ListCommand (EventWaiter eventWaiter) {
        this.eventWaiter = eventWaiter;
        this.name = "list";
        this.help = "Create, modify, and edit lists";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

    }
}
