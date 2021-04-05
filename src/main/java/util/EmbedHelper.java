package util;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedHelper {

    public static EmbedBuilder generateSimpleEmbed(CommandEvent commandEvent, String description) {
        return new EmbedBuilder()
                    .setTitle(String.format("Command from [%s]", commandEvent.getAuthor().getName()))
                    .addField("Description", description, true);
    }

}
