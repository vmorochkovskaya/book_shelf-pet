package org.example.app.service.cookie;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CookieType {
    CART("cartContents"),
    POSTPONED("postponedContents");

    private final String value;

    public String getValue(){
        return value;
    }
}
