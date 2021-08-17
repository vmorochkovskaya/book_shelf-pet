package org.example.web.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class CompanyController {
    @GetMapping("/about")
    public String faq() {
        return "faq";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
}
