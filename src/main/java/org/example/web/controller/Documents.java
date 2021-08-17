package org.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Documents {

    @GetMapping("/documents")
    public String documents(){
        return "documents/index";
    }
}
