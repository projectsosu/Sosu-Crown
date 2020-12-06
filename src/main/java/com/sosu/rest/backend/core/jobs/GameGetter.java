/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.jobs;

import com.sosu.rest.backend.core.service.ImageUploader;
import com.sosu.rest.backend.entity.mongo.Category;
import com.sosu.rest.backend.repo.mongo.CategoryRepository;
import com.sosu.rest.backend.repo.postgres.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gets new games and adds to db
 */
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

    private static final List<String> LINKS = new ArrayList<>();
    private static final List<String> CATEGORY_IDS = new ArrayList<>();
    private static final List<String> CATEGORIES = new ArrayList<>();
    private static final String METACRITIC_BASE_URL = "https://www.metacritic.com/browse/games/release-date/new-releases";

    @PostConstruct
    private void initialize() {
        LINKS.add(METACRITIC_BASE_URL + "/ps4/date");
        LINKS.add(METACRITIC_BASE_URL + "/xboxone/date");
        LINKS.add(METACRITIC_BASE_URL + "/switch/date");
        LINKS.add(METACRITIC_BASE_URL + "/pc/date");
        LINKS.add(METACRITIC_BASE_URL + "/ios/date");
        LINKS.add(METACRITIC_BASE_URL + "/xbox-series-x/date");
        LINKS.add(METACRITIC_BASE_URL + "/ps5/date");
        LINKS.add(METACRITIC_BASE_URL + "/stadia/date");
        CATEGORY_IDS.add("5eebb7587ea2d9497f4d7636");
        CATEGORY_IDS.add("5eebb75f7ea2d9497f4d7682");
        CATEGORY_IDS.add("5eebb7697ea2d9497f4d76f4");
        CATEGORY_IDS.add("5eebb7707ea2d9497f4d7740");
        CATEGORY_IDS.add("5eebb76d7ea2d9497f4d771a");
        CATEGORY_IDS.add("5eebb7637ea2d9497f4d76a8");
        CATEGORY_IDS.add("5eebb75c7ea2d9497f4d765c");
        CATEGORY_IDS.add("5eebb7667ea2d9497f4d76ce");
        CATEGORIES.add("2D");
        CATEGORIES.add("Action");
        CATEGORIES.add("Adventure");
        CATEGORIES.add("Compilation");
        CATEGORIES.add("Fighting");
        CATEGORIES.add("First-Person");
        CATEGORIES.add("Flight");
        CATEGORIES.add("General");
        CATEGORIES.add("Miscellaneous");
        CATEGORIES.add("Party");
        CATEGORIES.add("Platformer");
        CATEGORIES.add("Puzzle");
        CATEGORIES.add("Racing");
        CATEGORIES.add("Real-Time");
        CATEGORIES.add("Role-Playing");
        CATEGORIES.add("Simulation");
        CATEGORIES.add("Sports");
        CATEGORIES.add("Strategy");
        CATEGORIES.add("Third-Person");
        CATEGORIES.add("Turn-Based");
        CATEGORIES.add("War");
        CATEGORIES.add("Wrestling");
    }

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