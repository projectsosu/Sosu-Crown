package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GamesServiceImpl implements GamesService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game getFromName(String name) {
        return gameRepository.getFromName(name);
    }

    @Override
    public void saveOrUpdate(Game game) {
        gameRepository.save(game);
    }
}
