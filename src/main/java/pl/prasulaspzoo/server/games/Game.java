package pl.prasulaspzoo.server.games;

import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.queue.QueueDispatcher;

import java.util.concurrent.ScheduledFuture;

public abstract class Game implements Runnable {

    private ScheduledFuture<?> scheduledTask;
    private final QueueDispatcher queueDispatcher;
    protected final ServerInfo serverInfo;

    public Game(ServerInfo serverInfo, QueueDispatcher queueDispatcher) {
        this.serverInfo = serverInfo;
        this.queueDispatcher = queueDispatcher;
    }

    protected abstract void init();

    public void setScheduledTask(ScheduledFuture<?> scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    protected void shutdown() {
        queueDispatcher.disableQueue(serverInfo.getServerId());
        scheduledTask.cancel(true);
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

}
