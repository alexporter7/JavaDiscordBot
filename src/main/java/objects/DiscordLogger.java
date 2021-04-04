package objects;

import main.DiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscordLogger {

    private JDA jda;
    private TextChannel channel;
    private boolean enabled;

    public DiscordLogger() {
        this.jda = DiscordBot.jda;
        this.channel = DiscordBot.jda.getTextChannelsByName(
                        DiscordBot.optionHandler.getOption("loggingChannel").getValue(), true)
                        .get(0);
        this.enabled = DiscordBot.optionHandler.getOption("enableLogging").getValue().equals("true");
        this.logMessage("Discord Logger has been initialized");
    }

    public void logMessage(String message) {
        if(!this.enabled)
            return;
        this.channel.sendMessage(message).queue();
    }

    public void logMessage(MessageEmbed messageEmbed) {
        if(!this.enabled)
            return;
        this.channel.sendMessage(messageEmbed).queue();
    }

}
