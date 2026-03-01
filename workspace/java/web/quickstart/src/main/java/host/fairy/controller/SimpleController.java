/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-01 21:46:25 UTC+08:00
 ****************************************************/
package host.fairy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@RestController
@RequestMapping("/test")
public class SimpleController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World.";
    }
}
