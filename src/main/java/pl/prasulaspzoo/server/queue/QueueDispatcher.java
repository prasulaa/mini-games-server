package pl.prasulaspzoo.server.queue;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class QueueDispatcher {

    private final Map<String, Queue<QueueMsg>> gameQueues;

    public QueueDispatcher() {
        gameQueues = new HashMap<>();
    }

    public Queue<QueueMsg> getQueue(String serverId) {
        return gameQueues.get(serverId);
    }

    public Queue<QueueMsg> createGameQueue(String serverId) {
        Queue<QueueMsg> queue = new ConcurrentLinkedQueue<>();
        gameQueues.put(serverId, queue);
        return queue;
    }

    public void disableQueue(String serverId) {
        gameQueues.remove(serverId);
    }

}
