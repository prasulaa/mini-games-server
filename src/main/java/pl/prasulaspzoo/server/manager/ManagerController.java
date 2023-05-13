package pl.prasulaspzoo.server.manager;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.prasulaspzoo.server.games.GameType;

@RestController
@RequestMapping("servers")
@AllArgsConstructor
public class ManagerController {

    private final GameManager gameManager;
    private final ServerInfoMapper serverInfoMapper;

    @PostMapping
    public ResponseEntity<ServerInfoDTO> createServer(@RequestParam("type") GameType gameType) {
        ServerInfo serverInfo = gameManager.createServer(gameType);
        return new ResponseEntity<>(serverInfoMapper.map(serverInfo), HttpStatus.CREATED);
    }

}
