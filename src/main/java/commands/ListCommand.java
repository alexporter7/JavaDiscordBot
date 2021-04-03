package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import main.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import objects.lists.BotList;
import objects.lists.BotListItem;

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
                break;
            case "add":
                createList(commandEvent, command);
                break;
            case "addi":
                addItem(commandEvent, command);
                break;
            default:
                commandEvent.reply("Invalid command arguments, type $help for more info.");
        }
        DiscordBot.botListHandler.serializeList();
    }

    private void addItem(CommandEvent commandEvent, String[] args) {
        if(args.length < 3) {
            commandEvent.reply("Invalid arguments" +
                    "list addi <list_name> <item_name> [item_description]");
            return;
        } else if(args.length == 3) {
            addItem(commandEvent, args[1], args[2]);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 3; i < args.length; i++) {
            stringBuilder.append(args[i]);
            if(i + 1 != args.length)
                stringBuilder.append(" ");
        }
        addItem(commandEvent, args[1], args[2], stringBuilder.toString());

    }

    private void addItem(CommandEvent commandEvent, String listName, String itemName) {
        for(BotList list : DiscordBot.botListHandler.getLists()) {
            if(list.getListName().equals(listName))
                list.addItem(new BotListItem(itemName));
        }
        commandEvent.reply(String.format("Added [%s] to [%s]", itemName, listName));
    }

    private void addItem(CommandEvent commandEvent, String listName, String itemName, String itemDescription) {
        for(BotList list : DiscordBot.botListHandler.getLists()) {
            if(list.getListName().equals(listName))
                list.addItem(new BotListItem(itemName, itemDescription));
        }
        commandEvent.reply(
                String.format("Added [%s] to [%s] with description [%s]",
                        itemName, listName, itemDescription));
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
        for(BotList list : DiscordBot.botListHandler.getLists()) {
            if(list.getListName().equals(listName)) {
                EmbedBuilder embedBuilder =
                        new EmbedBuilder().setTitle(String.format("List: %s", listName));
                for(BotListItem item : list.getItems()) {
                    embedBuilder.addField("Item Name: ", item.getItemName(), true);
                    embedBuilder.addField("Description: ", item.hasDescription() ? item.getDescription() : "No Description", true);
                    embedBuilder.addBlankField(false);
                }
                commandEvent.reply(embedBuilder.build());
            }
        }
    }

    private void createList(CommandEvent commandEvent, String[] args) {
        if(args.length != 2) {
            commandEvent.reply("Invalid arguments" +
                    "list add <list_name>");
            return;
        }
        DiscordBot.botListHandler.addList(new BotList(args[1]));
        commandEvent.reply(String.format("Created list [%s]", args[1]));
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
        for(BotListItem botListObject : botList.getItems()) {
            sb.append(
                    String.format("Item Name: %s | Item Description: %s%n",
                            botListObject.getItemName(),
                            botListObject.hasDescription() ? botListObject.getDescription() : "None"));
        }
        sb.append("```");
        commandEvent.reply(sb.toString());
    }

}
