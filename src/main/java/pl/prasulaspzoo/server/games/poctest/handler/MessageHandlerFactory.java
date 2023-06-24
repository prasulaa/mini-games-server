package pl.prasulaspzoo.server.games.poctest.handler;

import pl.prasulaspzoo.server.games.common.GeneralMsg;
import pl.prasulaspzoo.server.games.common.HandlerNotRegisteredException;
import pl.prasulaspzoo.server.games.common.MessageHandler;
import pl.prasulaspzoo.server.games.poctest.messages.*;
import pl.prasulaspzoo.server.manager.ServerInfo;
import pl.prasulaspzoo.server.games.poctest.PoCTestGameInfo;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerFactory {

    private final Map<String, MessageHandler> handlers;

    public MessageHandlerFactory(ServerInfo serverInfo, PoCTestGameInfo gameInfo) {
        this.handlers = new HashMap<>();
        handlers.put(ConnectionRequest.NAME, new ConnectionRequestHandler(serverInfo, gameInfo));
        handlers.put(Disconnect.NAME, new DisconnectHandler(serverInfo, gameInfo));
        handlers.put(Move.NAME, new MoveHandler(serverInfo, gameInfo));
    }

    public MessageHandler messageHandler(GeneralMsg msg) {
        MessageHandler handler = handlers.get(msg.getMsgType());

        if (handler == null) {
            throw new HandlerNotRegisteredException("Handler for message type " + msg.getMsgType() + " not registered");
        }

        return handler;
    }

}
