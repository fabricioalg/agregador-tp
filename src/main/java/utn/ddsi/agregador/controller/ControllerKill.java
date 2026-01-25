package utn.ddsi.agregador.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerKill {

    @PostMapping("/kill")
    public void killApp() {
        System.exit(1);
    }
}
