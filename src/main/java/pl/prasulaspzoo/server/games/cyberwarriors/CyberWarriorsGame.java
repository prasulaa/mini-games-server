package pl.prasulaspzoo.server.games.cyberwarriors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import pl.prasulaspzoo.server.games.common.Game;
import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.HandlerNotRegisteredException;
import pl.prasulaspzoo.server.games.cyberwarriors.handler.MessageHandlerRepository;
import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.queue.QueueDispatcher;
import pl.prasulaspzoo.server.queue.QueueMsg;

import java.util.Objects;
import java.util.Queue;

@Slf4j
public class CyberWarriorsGame extends Game {

    private final ObjectMapper mapper;
    private final ObjectReader msgReader;
    private MessageHandlerRepository handlerRepository;
    private CyberWarriorsGameInfo gameInfo;

    public CyberWarriorsGame(ServerInfo serverInfo, QueueDispatcher queueDispatcher) {
        super(serverInfo, queueDispatcher);
        mapper = new ObjectMapper();
        msgReader = mapper.readerFor(GeneralMsg.class);
    }

    @Override
    protected void init() {
        gameInfo = new CyberWarriorsGameInfo();
        handlerRepository = new MessageHandlerRepository(serverInfo, gameInfo);

        log.info(serverInfo.getServerId() + " - Cyber Warriors 2115 game initialized");
    }

    @Override
    public void run() {
        Queue<QueueMsg> queue = serverInfo.getQueue();

        while (!queue.isEmpty()) {
            QueueMsg queueMsg = queue.poll();

            try {
                GeneralMsg msg = msgReader.readValue(queueMsg.getMsg());
                handlerRepository.messageHandler(msg).handle(msg, queueMsg.getSession());
            } catch (JsonProcessingException e) {
                log.error(serverInfo.getServerId() + " error occurred", e);
            } catch (HandlerNotRegisteredException e) {
                log.error(serverInfo.getServerId() + " - " + queueMsg.getSession().getId(), e);
            }

        }
    }
}
