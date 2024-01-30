package com.example.demospringboot.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
    @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Home</title>
                </head>
                <body>
                    <h1>Homepage</h1>
                    <p>Welcome!</p>
                </body>
                </html>
                """;
    }
}
