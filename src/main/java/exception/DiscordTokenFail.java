package exception;

public class DiscordTokenFail extends Exception {

    public DiscordTokenFail() {
        super("Java Discord API was unable to build object. Invalid token or service failure");
    }

    public DiscordTokenFail(String errorMessage) {
        super(errorMessage);
    }

}
