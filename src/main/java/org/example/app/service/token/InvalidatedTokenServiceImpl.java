package org.example.app.service.token;

import org.example.app.entity.InvalidatedToken;
import org.example.app.repository.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvalidatedTokenServiceImpl implements IInvalidatedTokenService {
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    public InvalidatedTokenServiceImpl(InvalidatedTokenRepository invalidatedTokenRepository) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    @Override
    public void addToBlackList(InvalidatedToken token) {
        System.out.println("ieoiepoqwe");
        invalidatedTokenRepository.save(token);
        System.out.println(invalidatedTokenRepository.findAll().get(0).getId());

    }
}
