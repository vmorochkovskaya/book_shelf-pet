package org.example.app.service.token;

import org.example.app.entity.InvalidatedToken;


public interface IInvalidatedTokenService {
    void addToBlackList(InvalidatedToken token);
}
