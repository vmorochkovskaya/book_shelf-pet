package org.example.app.service.token;

import org.example.app.entity.InvalidatedToken;
import org.example.app.repository.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvalidatedTokenServiceImpl implements IInvalidatedTokenService {
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    public InvalidatedTokenServiceImpl(InvalidatedTokenRepository invalidatedTokenRepository) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    @Override
    public void addToBlackList(InvalidatedToken token) {
        invalidatedTokenRepository.save(token);
    }

    @Override
    public List<InvalidatedToken> getAllTokens() {
        return invalidatedTokenRepository.findAll();
    }
}
