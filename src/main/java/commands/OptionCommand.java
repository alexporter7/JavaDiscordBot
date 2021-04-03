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
        String[] args = commandEvent.getArgs().split(" ");
        DiscordBot.optionHandler.deserializeOptions();
        switch(args[0]) {
            case "view":
                viewAllOptions(commandEvent);
                break;
            case "edit":
                editOption(commandEvent, args);
                break;
            default:
                commandEvent.reply("Incorrect syntax. $option [mode]");
        }
        DiscordBot.optionHandler.serializeOptions();
    }

    private void viewAllOptions(CommandEvent commandEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Options\n")
                .append("```");
        for(BotOption botOption : DiscordBot.optionHandler.getOptions()) {
            stringBuilder
                    .append(botOption.getName())
                    .append(" : ")
                    .append(botOption.getValue())
                    .append("\n");
        }
        stringBuilder.append("```");
        commandEvent.reply(stringBuilder.toString());
    }

    private void editOption(CommandEvent commandEvent, String[] args) {
        if(!isValidOption(args[1])) {
            commandEvent.reply(String.format("[%s] is not a valid option", args[1]));
            return;
        }
        if(args.length != 2) {
            commandEvent.reply("Invalid length" +
                    "$option edit <option_name> <new_value>");
        }
        for(BotOption option : DiscordBot.optionHandler.getOptions()) {
            if(option.getName().equals(args[1])) {
                commandEvent.reply(
                        String.format("Set [%s] from [%s] to [%s]",
                                args[1], option.getValue(), args[2]));
                option.setValue(args[2]);
            }
        }
    }

    private boolean isValidOption(String optionName) {
        return DiscordBot.validOptions.contains(optionName);
    }
}
