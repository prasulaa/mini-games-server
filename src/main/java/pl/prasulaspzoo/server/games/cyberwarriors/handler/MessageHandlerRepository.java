package pl.prasulaspzoo.server.games.cyberwarriors.handler;

import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.HandlerNotRegisteredException;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.common.message.ConnectionRequest;
import pl.prasulaspzoo.server.games.common.message.Disconnect;
import pl.prasulaspzoo.server.games.cyberwarriors.CyberWarriorsGameInfo;
import pl.prasulaspzoo.server.games.cyberwarriors.dto.MoveDTO;
import pl.prasulaspzoo.server.manager.ServerInfo;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerRepository {

    private final Map<String, MessageHandler> handlers;

    public MessageHandlerRepository(ServerInfo serverInfo, CyberWarriorsGameInfo gameInfo) {
        this.handlers = new HashMap<>();
        handlers.put(ConnectionRequest.NAME, new ConnectionRequestHandler(serverInfo, gameInfo));
        handlers.put(MoveDTO.NAME, new MoveHandler(gameInfo));
        handlers.put(Disconnect.NAME, new DisconnectHandler(serverInfo, gameInfo));
    }

    public MessageHandler messageHandler(GeneralMsg msg) {
        MessageHandler handler = handlers.get(msg.getMsgType());

        if (handler == null) {
            throw new HandlerNotRegisteredException("Handler for message type " + msg.getMsgType() + " not registered");
        }

        return handler;
    }

}
