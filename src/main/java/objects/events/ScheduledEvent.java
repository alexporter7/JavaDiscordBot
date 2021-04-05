package objects.events;


import java.util.concurrent.TimeUnit;

public class ScheduledEvent {

    private final String name;
    private final String description;
    private final Runnable command;
    private long initialDelay;
    private long delay;
    private TimeUnit timeUnit;

    public ScheduledEvent(String name, String description, Runnable command, long initalDelay, long delay, TimeUnit timeUnit) {
        this.name = name;
        this.description = description;
        this.command = command;
        this.initialDelay = initalDelay;
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Runnable getCommand() {
        return this.command;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

}
