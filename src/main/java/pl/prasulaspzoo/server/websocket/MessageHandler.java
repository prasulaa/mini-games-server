package pl.prasulaspzoo.server.websocket;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pl.prasulaspzoo.server.manager.GameManager;
import pl.prasulaspzoo.server.queue.QueueDispatcher;
import pl.prasulaspzoo.server.queue.QueueMsg;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;


@AllArgsConstructor
@Service
public class MessageHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);

    private final QueueDispatcher queueDispatcher;
    private final GameManager gameManager;
    private final Timer timer = new Timer(true);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String serverId = (String) session.getAttributes().get("serverId");
        gameManager.getGame(serverId).getServerInfo().connectionRequest(session);

        log.info("Connection request " + session.getId() + " to " + serverId);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        log.info("Received from " + session.getId() + ": " + message.getPayload());

        String serverId = (String) session.getAttributes().get("serverId");
        Queue<QueueMsg> queue = queueDispatcher.getQueue(serverId);
        if (queue != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    queue.add(new QueueMsg(session, message.getPayload()));
                }
            }, 100);

        } else {
            log.error("Queue not found user=" + session.getId() + " server=" + serverId);
            session.close(CloseStatus.BAD_DATA);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String serverId = (String) session.getAttributes().get("serverId");

        gameManager.getGame(serverId).getServerInfo().disconnect(session);

        log.info("Connection closed with " + session.getId() + " - " + status);
    }

}
