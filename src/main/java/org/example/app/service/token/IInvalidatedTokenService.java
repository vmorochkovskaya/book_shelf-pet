package org.example.app.service.token;

import org.example.app.entity.InvalidatedToken;

import java.util.List;


public interface IInvalidatedTokenService {
    void addToBlackList(InvalidatedToken token);

    List<InvalidatedToken> getAllTokens();

}
