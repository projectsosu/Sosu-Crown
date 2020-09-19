/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.jobs;

import com.sosu.rest.crown.core.service.ImageUploader;
import com.sosu.rest.crown.entity.postgres.Game;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Thread for new games
 */
@Slf4j
public class GameGetterThread extends Thread {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
    private static final String GAME_GENRE = "5eebb7587ea2d9497f4d7634";
    private static final String METACRITIC_COM = "https://www.metacritic.com";
    private final GameRepository gameRepository;
    private final ImageUploader imageUploader;
    private final String consoleLink;
    private final String category;
    private final Map<String, String> genre;

    protected GameGetterThread(GameRepository gameRepository, ImageUploader imageUploader, String consoleLink, String category, Map<String, String> genre) {
        this.gameRepository = gameRepository;
        this.imageUploader = imageUploader;
        this.consoleLink = consoleLink;
        this.category = category;
        this.genre = genre;
    }

    @Override
    public void run() {
        getGame();
    }

    private void getGame() {
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
                            try {
                                Document documentGame = Jsoup.connect(METACRITIC_COM + linkOfGame).get();
                                if (gameRepository.findByName(gameName) != null) {
                                    Game game = gameRepository.findByName(gameName);
                                    if (!game.getConsoleCategoryIdList().contains(category)) {
                                        Set<String> categoryList = new HashSet<>(game.getCategoryIdList());
                                        categoryList.add(category);
                                        game.setConsoleCategoryIdList(new ArrayList<>(categoryList));
                                        gameRepository.save(game);
                                    }
                                } else {
                                    Game game = new Game();
                                    game.setName(gameName);
                                    game.setMainCategoryIdList(Collections.singletonList(GAME_GENRE));
                                    game.setConsoleCategoryIdList(Collections.singletonList(category));
                                    game.setDescription(summary);
                                    gameDetail(documentGame, genre, game, imageUrl);
                                }
                            } catch (Exception e) {
                                log.error("Error getting game: {} {}", METACRITIC_COM + linkOfGame, ExceptionUtils.getStackTrace(e));
                            }

                        }
                    }
                }
                if (!CollectionUtils.isEmpty(document.getElementsByClass("flipper next"))) {
                    Element next = document.getElementsByClass("flipper next").get(0);
                    Elements actions = next.getElementsByClass("action");
                    if (CollectionUtils.isNotEmpty(actions) && StringUtils.isNotBlank(actions.get(0).attr("href"))) {
                        selected = METACRITIC_COM + actions.get(0).attr("href");
                    } else {
                        error = true;
                    }
                } else {
                    error = true;
                }
            } catch (Exception e) {
                log.error("Error getting category of game: {} {}", consoleLink, ExceptionUtils.getStackTrace(e));
            }
        }
        log.info("Finished game update service for {}", consoleLink);
    }

    private void gameDetail(Document documentGame, Map<String, String> genres, Game game, String imageUrl) {
        String publisher = null;
        if (CollectionUtils.isNotEmpty(documentGame.getElementsByClass("summary_detail publisher"))) {
            publisher = documentGame.getElementsByClass("summary_detail publisher").get(0).getElementsByClass("data")
                    .get(0).getElementsByTag("a").html();
        }
        documentGame.getElementsByClass("summary_details");
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
        LocalDate formattedDate = LocalDate.parse(releaseDate, FORMATTER);

        Set<String> categoryList = new HashSet<>();
        for (Element element : documentGame.getElementsByClass("summary_detail product_genre").get(0).getElementsByClass("data")) {
            if (genres.containsKey(element.html())) {
                categoryList.add(genres.get(element.html()));
            }
        }
        if (CollectionUtils.isEmpty(categoryList)) {
            categoryList.add(genres.get("General"));
        }

        String rating = "E";
        if (CollectionUtils.isNotEmpty(documentGame.getElementsByClass("summary_detail product_rating"))) {
            rating = documentGame.getElementsByClass("summary_detail product_rating").get(0).getElementsByClass("data")
                    .html();
        }
        game.setCert(rating);
        game.setDeveloper(developer);
        game.setPublisher(publisher);
        game.setPublishDate(formattedDate);
        game.setCategoryIdList(new ArrayList<>(categoryList));
        game.setImage(imageUploader.uploadImage(imageUrl, game.getName(), ProductType.GAME));
        gameRepository.save(game);
    }
}
