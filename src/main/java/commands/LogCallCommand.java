package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import main.DiscordBot;
import objects.calls.BotCallObject;

import java.util.Arrays;

public class LogCallCommand extends Command {

    private final EventWaiter eventWaiter;

    public LogCallCommand(EventWaiter eventWaiter) {
        this.eventWaiter = eventWaiter;
        this.name = "call";
        this.help = "Log a Call" +
                "call log <transferred> <description>" +
                "call view";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] args = commandEvent.getArgs().split(" ");
        switch(args[0]) {
            case "log":
                logCall(commandEvent, args);
                break;
            case "view":
                viewCalls(commandEvent);
                break;
            default:
                commandEvent.reply("Incorrect usage\n" + this.help);
                break;
        }
    }

    private void logCall(CommandEvent commandEvent, String[] args) {
        StringBuilder description = new StringBuilder();
        for(int i = 2; i < args.length; i++) {
            description.append(args[i]);
            if(i + 1 != args.length)
                description.append(" ");
        }
        try {
            boolean transferred = Boolean.parseBoolean(args[1]);
            DiscordBot.botCallHandler.addCall(
                    new BotCallObject(description.toString(), transferred));
            DiscordBot.botCallHandler.serializeData();
            DiscordBot.botCallHandler.deserializeData();
            commandEvent.reply("Call has been logged.");
        } catch (Exception e) {
            commandEvent.reply("Transferred field was not a true/false value");
            e.printStackTrace();
        }
    }

    private void viewCalls(CommandEvent commandEvent) {

    }

}
