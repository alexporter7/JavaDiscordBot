package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import main.DiscordBot;
import objects.lists.BotList;
import objects.lists.BotListObject;

import java.util.ArrayList;

/**
 * Manages lists within the discord bot
 */
public class ListCommand extends Command {

    private final EventWaiter eventWaiter;

    public ListCommand (EventWaiter eventWaiter) {
        this.eventWaiter = eventWaiter;
        this.name = "list";
        this.help = "Create, modify, and edit lists";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getArgs().split(" ");
        switch(command[0]) {
            case "view":
                viewList(commandEvent, command);
        }
    }

    /**
     * Prints all lists
     * @param commandEvent - passed when command is called
     */
    private void viewList(CommandEvent commandEvent, String[] args) {

        ArrayList<BotList> botLists = DiscordBot.botListHandler.getLists();
        if(args.length == 1) {
            printAllLists(commandEvent, botLists);
            return;
        }

        String listName = args[1];
    }

    private void printAllLists(CommandEvent commandEvent, ArrayList<BotList> botLists) {
        for (BotList list : botLists) {
            printList(commandEvent, list);
        }
    }

    /**
     * Prints a single list
     * @param commandEvent - Command Event
     * @param botList - List to print out
     */
    private void printList(CommandEvent commandEvent, BotList botList) {
        StringBuilder sb = new StringBuilder();
        sb.append("```");
        sb.append("List Name: ");
        sb.append(botList.getListName());
        sb.append("\n");
        for(BotListObject botListObject : botList.getItems()) {
            sb.append(
                    String.format("Item Name: %s | Item Description: %s%n",
                            botListObject.getItemName(),
                            botListObject.hasDescription() ? botListObject.getDescription() : "None"));
        }
        sb.append("```");
        commandEvent.reply(sb.toString());
    }

}
