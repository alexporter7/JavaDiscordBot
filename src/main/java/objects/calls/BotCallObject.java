package objects.calls;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class BotCallObject {

    @SerializedName("callTime")
    private LocalDateTime callTime;

    @SerializedName("description")
    private String description;

    @SerializedName("transferred")
    private boolean transferred;

    public BotCallObject(String description) {
        this(description, false);
    }

    public BotCallObject(String description, boolean transferred) {
        this.description = description;
        this.transferred = transferred;
        this.callTime = LocalDateTime.now();
    }

    public LocalDateTime getCallTime() {
        return callTime;
    }

    public void setCallTime(LocalDateTime callTime) {
        this.callTime = callTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTransferred() {
        return transferred;
    }

    public void setTransferred(boolean transferred) {
        this.transferred = transferred;
    }
}
