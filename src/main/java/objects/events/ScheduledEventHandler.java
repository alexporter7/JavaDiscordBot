package objects.events;

import main.DiscordBot;
import util.Config;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledEventHandler {

    private boolean init;

    private ArrayList<ScheduledEvent> oneTimeEvents = new ArrayList<>();
    private ArrayList<ScheduledEvent> repeatingEvents = new ArrayList<>();

    public ScheduledEventHandler() {
        this.init = false;
    }

    public boolean hasInit() {
        return this.init;
    }

    public void initialize() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        for(ScheduledEvent event : this.repeatingEvents) {
            scheduledExecutorService.scheduleAtFixedRate(event.getCommand(), event.getInitialDelay(), event.getDelay(), event.getTimeUnit());
        }
        this.init = true;
    }

    public void registerRepeatingEvent(ScheduledEvent scheduledEvent) {
        repeatingEvents.add(scheduledEvent);
    }

}
