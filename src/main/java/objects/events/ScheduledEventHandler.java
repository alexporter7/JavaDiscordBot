package objects.events;

import main.DiscordBot;
import objects.DiscordLogger;
import util.Config;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledEventHandler {

    private boolean init;

    private final ScheduledExecutorService scheduledExecutorService;

    private ArrayList<ScheduledEvent> oneTimeEvents = new ArrayList<>();
    private ArrayList<ScheduledEvent> repeatingEvents = new ArrayList<>();

    public ScheduledEventHandler() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        this.init = false;
    }

    public boolean hasInit() {
        return this.init;
    }

    public void initialize() {
        for(ScheduledEvent event : this.repeatingEvents) {
            this.scheduledExecutorService.scheduleAtFixedRate(event.getCommand(), event.getInitialDelay(), event.getDelay(), event.getTimeUnit());
        }
        for(ScheduledEvent event : this.oneTimeEvents) {
            this.scheduledExecutorService.schedule(event.getCommand(), event.getInitialDelay(), event.getTimeUnit());
        }
        this.init = true;
    }

    public ArrayList<ScheduledEvent> getRepeatingEvents() {
        return this.repeatingEvents;
    }

    public void disableScheduledEvents() {
        this.scheduledExecutorService.shutdown();
        this.init = false;
    }

    public void registerRepeatingEvent(ScheduledEvent scheduledEvent) {
        this.repeatingEvents.add(scheduledEvent);
    }

    public void registerOneTimeEvent(ScheduledEvent scheduledEvent) {
        this.oneTimeEvents.add(scheduledEvent);
    }

}
