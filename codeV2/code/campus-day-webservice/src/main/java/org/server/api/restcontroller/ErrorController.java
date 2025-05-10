package org.server.api.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController extends AbstractController {

    // Standard, "" und "/"
    @RequestMapping("/")
    String index() {
        return ":::: Spring Boot Rest Server ::::";
    }
}
