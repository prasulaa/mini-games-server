package pl.prasulaspzoo.server.manager;

import org.springframework.stereotype.Service;

@Service
public class ServerInfoMapper {

    public ServerInfoDTO map(ServerInfo serverInfo) {
        return new ServerInfoDTO(serverInfo.getServerId());
    }

}
