package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import main.DiscordBot;
import objects.options.BotOption;

/**
 * @author Alex Porter
 * Option command for viewing and editing options defined during initilization
 */
public class OptionCommand extends Command {

    private final EventWaiter eventWaiter;

    public OptionCommand(EventWaiter eventWaiter) {
        this.eventWaiter = eventWaiter;
        this.name = "option";
        this.help = "Configure options within bot";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getArgs().split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        switch(command[0]) {
            case "view":
                stringBuilder
                        .append("Options\n")
                        .append("```");
                for(BotOption botOption : DiscordBot.optionHandler.getOptions()) {
                    stringBuilder
                            .append(botOption.getName())
                            .append(" : ")
                            .append(botOption.getValue() instanceof Boolean ?
                                    String.valueOf(botOption.getValue()) : botOption.getValue())
                            .append("\n");
                }
                stringBuilder.append("```");
        }
        commandEvent.reply(stringBuilder.toString());
    }
}
