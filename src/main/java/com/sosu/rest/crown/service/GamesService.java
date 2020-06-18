package com.sosu.rest.crown.service;

import com.sosu.rest.crown.entity.postgres.Game;

public interface GamesService {

    Game getFromName(String name);

    void saveOrUpdate(Game game);

}
