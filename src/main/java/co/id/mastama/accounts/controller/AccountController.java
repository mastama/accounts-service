package co.id.mastama.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    /*
     * test hello world!
     */
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }
}
