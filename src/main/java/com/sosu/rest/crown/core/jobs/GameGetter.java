package com.sosu.rest.crown.core.jobs;


import com.sosu.rest.crown.core.service.ImageUploader;
import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.repo.mongo.CategoryRepository;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@DependsOn("imageUploader")
@Slf4j
public class GameGetter {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ImageUploader imageUploader;

    private static final List<String> LINKS = new ArrayList<>() {
        {
            add("https://www.metacritic.com/browse/games/release-date/new-releases/ps4/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/xboxone/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/switch/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/pc/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/ios/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/xbox-series-x/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/ps5/date");
            add("https://www.metacritic.com/browse/games/release-date/new-releases/stadia/date");
        }
    };

    private static final List<String> CATEGORY_IDS = new ArrayList<>() {
        {
            add("5eebb7587ea2d9497f4d7636");
            add("5eebb75f7ea2d9497f4d7682");
            add("5eebb7697ea2d9497f4d76f4");
            add("5eebb7707ea2d9497f4d7740");
            add("5eebb76d7ea2d9497f4d771a");
            add("5eebb7637ea2d9497f4d76a8");
            add("5eebb75c7ea2d9497f4d765c");
            add("5eebb7667ea2d9497f4d76ce");
        }
    };

    private static final List<String> CATEGORIES = new ArrayList<>() {
        {
            add("2D");
            add("Action");
            add("Adventure");
            add("Compilation");
            add("Fighting");
            add("First-Person");
            add("Flight");
            add("General");
            add("Miscellaneous");
            add("Party");
            add("Platformer");
            add("Puzzle");
            add("Racing");
            add("Real-Time");
            add("Role-Playing");
            add("Simulation");
            add("Sports");
            add("Strategy");
            add("Third-Person");
            add("Turn-Based");
            add("War");
            add("Wrestling");
        }
    };

    @Scheduled(cron = " 0 0 0 ? * * ")
    private void startTask() {
        log.info("Started game update service");
        for (int i = 0; i < LINKS.size(); i++) {
            String consoleLink = LINKS.get(i);
            String category = CATEGORY_IDS.get(i);
            HashMap<String, String> genres = new HashMap<>();
            String parentId = CATEGORY_IDS.get(i);
            List<Category> categoryList = categoryRepository.findByParentId(parentId, Sort.by(Sort.Direction.ASC, "name"));
            for (int j = 0; j < categoryList.size(); j++) {
                genres.put(CATEGORIES.get(j), categoryList.get(j).getDefaultCategory());
            }
            (new GameGetterThread(gameRepository, imageUploader, consoleLink, category, genres)).start();
        }
    }

}