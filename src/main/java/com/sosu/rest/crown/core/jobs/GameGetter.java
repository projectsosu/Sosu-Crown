package com.sosu.rest.crown.core.jobs;


import com.sosu.rest.crown.core.service.ImageUploader;
import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.repo.mongo.CategoryRepository;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    private static final String gameGenre = "5eebb7587ea2d9497f4d7634";
    private static final String metaCritic = "https://www.metacritic.com";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    private static List<String> links = new ArrayList<>() {
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

    private static List<String> categoryIds = new ArrayList<>() {
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

    private static List<String> categories = new ArrayList<>() {
        {
            add("Action");
            add("Adventure");
            add("Fighting");
            add("First-Person");
            add("Flight");
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
            add("Miscellaneous");
            add("Compilation");
            add("General");
        }
    };

    @Scheduled(cron = " 0 0 0 ? * * ")
    private void startTask() {
        log.info("Started game update service");
        for (int i = 0; i < links.size(); i++) {
            String consoleLink = links.get(i);
            String category = categoryIds.get(i);
            run(consoleLink, generateMap(i), category);
        }
        log.info("Finished game update service");
    }

    private Map<String, String> generateMap(Integer index) {
        HashMap<String, String> genres = new HashMap<>();
        String parentId = categoryIds.get(index);
        List<Category> categoryList = categoryRepository.findByParentId(parentId, Sort.by(Sort.Direction.ASC, "id"));
        for (int i = 0; i < categoryList.size(); i++) {
            genres.put(categories.get(i), categoryList.get(i).getDefaultCategory());
        }
        return genres;
    }

    public void run(String consoleLink, Map genre, String category) {
        String selected = consoleLink;
        boolean error = false;
        while (!error) {
            try {
                Document document = Jsoup.connect(selected).get();
                Elements link = document.getElementsByClass("clamp-list");
                for (Element item : link) {
                    for (Element gameItem : item.getElementsByTag("tr")) {
                        String imageUrl = "https://2img.net/i/default.png";
                        if (CollectionUtils.isNotEmpty(gameItem.getElementsByClass("clamp-image-wrap"))) {
                            imageUrl = gameItem.getElementsByClass("clamp-image-wrap").get(0).getElementsByTag("a").get(0)
                                    .getElementsByTag("img").get(0).attr("src");
                        }
                        if (CollectionUtils.isNotEmpty(gameItem.getElementsByClass("clamp-summary-wrap"))) {
                            Elements detail = gameItem.getElementsByClass("clamp-summary-wrap").get(0).getElementsByTag("a");
                            String linkOfGame = detail.get(1).attr("href");
                            String summary = gameItem.getElementsByClass("summary").get(0).html();
                            String gameName = detail.get(1).getElementsByTag("h3").html();
                            Document documentGame = Jsoup.connect(metaCritic + linkOfGame).get();
                            if (gameRepository.findByName(gameName) != null) {
                                Game game = gameRepository.findByName(gameName);
                                if (!game.getCategoryId().contains(category)) {
                                    game.setCategoryId(game.getCategoryId() + ";" + category);
                                    gameRepository.save(game);
                                }
                            } else {
                                Game game = new Game();
                                game.setName(gameName);
                                game.setMainCategoryId(gameGenre);
                                game.setConsoleCategoryId(category);
                                game.setDescription(summary);
                                gameDetail(documentGame, genre, game, imageUrl);
                            }
                        }
                    }
                }
                Element next = document.getElementsByClass("flipper next").get(0);
                Elements actions = next.getElementsByClass("action");
                if (CollectionUtils.isNotEmpty(actions) && StringUtils.isNotBlank(actions.get(0).attr("href"))) {
                    selected = metaCritic + actions.get(0).attr("href");
                } else {
                    error = true;
                }
            } catch (Exception e) {
                log.error("Error getting game: {}", consoleLink);
            }
        }
    }

    public void gameDetail(Document documentGame, Map genres, Game game, String imageUrl) {
        StringBuilder platforms = new StringBuilder();
        String publisher = null;
        if (CollectionUtils.isNotEmpty(documentGame.getElementsByClass("summary_detail publisher"))) {
            publisher = documentGame.getElementsByClass("summary_detail publisher").get(0).getElementsByClass("data")
                    .get(0).getElementsByTag("a").html();
        }
        documentGame.getElementsByClass("summary_details");
        Elements details = documentGame.getElementsByClass("summary_details").select("li");
        String developer = null;
        if (CollectionUtils.isNotEmpty(documentGame.getElementsByClass("summary_detail developer"))) {
            developer = documentGame.getElementsByClass("summary_detail developer").get(0).getElementsByClass("data")
                    .html();
        }
        if (StringUtils.isNotBlank(developer)) {
            developer = publisher;
        }
        if (StringUtils.isNotBlank(publisher)) {
            publisher = developer;
        }
        String releaseDate = documentGame.getElementsByClass("summary_detail release_data").get(0).getElementsByClass("data")
                .html();
        LocalDate formattedDate = LocalDate.parse(releaseDate, formatter);

        for (Element element : documentGame.getElementsByClass("summary_detail product_genre").get(0).getElementsByClass("data")) {
            if (genres.containsKey(element.html())) {
                platforms.append(genres.get(element.html()));
                platforms.append(";");
            }
        }
        if (StringUtils.isBlank(platforms.toString())) {
            platforms.append(genres.get("General"));
        } else {
            platforms.deleteCharAt(platforms.length() - 1);
        }
        String genre = platforms.toString();

        String rating = "E";
        if (CollectionUtils.isNotEmpty(documentGame.getElementsByClass("summary_detail product_rating"))) {
            rating = documentGame.getElementsByClass("summary_detail product_rating").get(0).getElementsByClass("data")
                    .html();
        }
        game.setCert(rating);
        game.setDeveloper(developer);
        game.setPublisher(publisher);
        game.setPublishDate(formattedDate);
        game.setCategoryId(genre);
        game.setImage(imageUploader.uploadImage(imageUrl, game.getName()));
        gameRepository.save(game);
    }
}