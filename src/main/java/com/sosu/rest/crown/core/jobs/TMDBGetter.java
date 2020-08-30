/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.jobs;

import com.sosu.rest.crown.core.model.Genre;
import com.sosu.rest.crown.core.model.NewMovies;
import com.sosu.rest.crown.core.model.ProductDetail;
import com.sosu.rest.crown.core.model.TMDBResult;
import com.sosu.rest.crown.core.service.ImageUploader;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Add new products, checks for invalid products
 */
@Component
@Slf4j
public class TMDBGetter {

    @Autowired
    private DataSource dataSource;

    private static final Map<Integer, String> genresMovie = new HashMap<>() {
        {
            put(28, "5edd81c27d85f9caded1c5bc");
            put(12, "5ee73fd92ace0520f01af3ca");
            put(16, "5ee73fda2ace0520f01af3cb");
            put(35, "5ee73fdb2ace0520f01af3cd");
            put(80, "5ee73fdb2ace0520f01af3ce");
            put(99, "5ee73fdb2ace0520f01af3cf");
            put(18, "5ee73fdb2ace0520f01af3d0");
            put(10751, "5ee73fdb2ace0520f01af3d1");
            put(14, "5ee73fdb2ace0520f01af3d2");
            put(36, "5ee73fdb2ace0520f01af3d4");
            put(27, "5ee73fdb2ace0520f01af3d5");
            put(10402, "5ee7722177a9074146eabad4");
            put(9648, "5ee73fdb2ace0520f01af3d6");
            put(10749, "5ee73fdb2ace0520f01af3d7");
            put(878, "5ee73fdb2ace0520f01af3d8");
            put(10770, "5ee73fdb2ace0520f01af3d9");
            put(53, "5ee73fdb2ace0520f01af3dc");
            put(10752, "5ee73fdc2ace0520f01af3dd");
            put(37, "5ee73fdc2ace0520f01af3de");
        }
    };

    private static final Map<Integer, String> genresTv = new HashMap<>() {
        {
            put(28, "5ee9eeb8c106085337e8ee77");
            put(12, "5ee9eeb9c106085337e8ee79");
            put(16, "5ee9eeb9c106085337e8ee7b");
            put(35, "5ee9eeb9c106085337e8ee7f");
            put(80, "5ee9eeb9c106085337e8ee81");
            put(99, "5ee9eeb9c106085337e8ee83");
            put(18, "5ee9eeb9c106085337e8ee85");
            put(10751, "5ee9eebac106085337e8ee87");
            put(14, "5ee9eebac106085337e8ee89");
            put(36, "5ee9eebac106085337e8ee8d");
            put(27, "5ee9eebac106085337e8ee8f");
            put(10402, "5ee9eebac106085337e8ee91");
            put(9648, "5ee9eebac106085337e8ee95");
            put(10749, "5ee9eebbc106085337e8ee9b");
            put(878, "5ee9eebbc106085337e8ee9d");
            put(10770, "5ee73fdb2ace0520f01af3d9");
            put(53, "5ee9eebbc106085337e8eea5");
            put(10752, "5ee9eebcc106085337e8eea7");
            put(37, "5ee9eebcc106085337e8eea9");
        }
    };

    private static String getterLink = "https://api.themoviedb.org/3/discover/[TYPE]?api_key=2c77bdb30d44c27312cb203cd5491210&sort_by=release_date.desc&page={0}";
    private static String detailLink = "https://api.themoviedb.org/3/{0}/{1}?api_key=2c77bdb30d44c27312cb203cd5491210&language=en-US";
    private static String imageUrl = "http://image.tmdb.org/t/p/w185{0}";
    private static final List<String> GENERAL_ID = new ArrayList<>() {
        {
            add("5edd816e7d85f9caded1c5bb");
            add("5ee9e7eedee64f1214047c03");
        }
    };
    private static final String TV_ID = "5ee9e7eedee64f1214047c03";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUploader imageUploader;

    private final RestTemplate restTemplate;

    public TMDBGetter(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(cron = " 0 0 3 ? * * ")
    public void setGenres() {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity request = new HttpEntity(headers);
        (new Thread(() -> {
            log.info("Getting Genres started");
            productRepository.findEmptyCategories().parallelStream().forEach(item -> {
                try {
                    ProductDetail productDetail = getProductDetail(request, item);
                    Set<String> categoryList = new HashSet<>();
                    if (item.getMainCategoryIdList().contains(TV_ID)) {
                        for (Genre genre : productDetail.getGenres()) {
                            String selected = genresTv.get(genre.getId());
                            if (selected != null) {
                                categoryList.add(selected);
                            }
                        }
                    } else {
                        for (Genre genre : productDetail.getGenres()) {
                            String selected = genresMovie.get(genre.getId());
                            if (selected != null) {
                                categoryList.add(selected);
                            }
                        }
                    }
                    item.setCategoryIdList(new ArrayList<>(categoryList));
                    productRepository.save(item);
                } catch (Exception e) {
                    log.error("Error link genre: {}", MessageFormat.format(detailLink, "tv", item.getTmdbId()));
                }
            });
            log.info("Getting Genres finished");
        })).start();
    }

    @Scheduled(cron = " 0 0 0 ? * * ")
    public void setImbdIds() {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity request = new HttpEntity(headers);
        (new Thread(() -> {
            log.info("Getting imdb started");
            productRepository.findByImdbId("").parallelStream().forEach(item -> {
                try {
                    ProductDetail productDetail = getProductDetail(request, item);
                    item.setImdbId(productDetail.getImdb_id() == null ? "" : productDetail.getImdb_id());
                    productRepository.save(item);
                } catch (Exception e) {
                    log.error("Error link imdb: {}", MessageFormat.format(detailLink, "tv", item.getTmdbId()));
                }
            });
            log.info("Getting imdb finished");
        })).start();
    }

    @Scheduled(cron = " 0 0 6 ? * * ")
    public void setReleaseDate() {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity request = new HttpEntity(headers);
        (new Thread(() -> {
            log.info("Getting year started");
            productRepository.findByYear(0).parallelStream().forEach(item -> {
                try {
                    ProductDetail productDetail = getProductDetail(request, item);
                    if (StringUtils.isNotBlank(productDetail.getRelease_date())) {
                        item.setYear(LocalDate.parse(productDetail.getRelease_date()).getYear());
                    } else if (StringUtils.isNotBlank(productDetail.getFirst_air_date())) {
                        item.setYear(LocalDate.parse(productDetail.getFirst_air_date()).getYear());
                    }
                    productRepository.save(item);
                } catch (Exception e) {
                    log.error("Error link release date: {}", MessageFormat.format(detailLink, "tv", item.getTmdbId()));
                }
            });
            log.info("Getting year finished");
        })).start();
    }

    public ProductDetail getProductDetail(HttpEntity request, Product item) {
        ProductDetail productDetail;
        if (item.getMainCategoryIdList().contains(TV_ID)) {
            productDetail = restTemplate.exchange(MessageFormat.format(detailLink, "tv", item.getTmdbId()), HttpMethod.GET,
                    request, ProductDetail.class).getBody();
        } else {
            productDetail = restTemplate.exchange(MessageFormat.format(detailLink, "movie", item.getTmdbId()), HttpMethod.GET,
                    request, ProductDetail.class).getBody();
        }
        return productDetail;
    }

    @Scheduled(cron = " 0 0 9 ? * * ")
    public void getNewMovies() {
        (new Thread(() -> {
            log.info("Getting new movies started");
            HttpHeaders headers = getHttpHeaders();
            HttpEntity request = new HttpEntity(headers);
            String typeLink = getterLink.replace("[TYPE]", "movie");
            for (int i = 1; i <= 500; i++) {
                NewMovies newMovies = restTemplate.exchange(MessageFormat.format(typeLink, i), HttpMethod.GET, request, NewMovies.class).getBody();
                newMovies.getResults().parallelStream().forEach(item -> {
                    try {
                        setMovieProduct(item);
                    } catch (Exception e) {
                        log.error("Getting new movie error ID: {}", item.getId());
                    }
                });
            }
            log.info("Getting new movies finished");
        })).start();

        (new Thread(() -> {
            log.info("Getting new tvs started");
            HttpHeaders headers = getHttpHeaders();
            HttpEntity request = new HttpEntity(headers);
            String typeLink = getterLink.replace("[TYPE]", "tv");
            for (int i = 1; i <= 500; i++) {
                NewMovies newMovies = restTemplate.exchange(MessageFormat.format(typeLink, i), HttpMethod.GET, request, NewMovies.class).getBody();
                newMovies.getResults().parallelStream().forEach(item -> {
                    try {
                        setTvProduct(item);
                    } catch (Exception e) {
                        log.error("Getting new tvs error ID: {}", item.getId());
                    }
                });
            }
            log.info("Getting new tvs finished");
        })).start();
    }

    private void setMovieProduct(TMDBResult tmdbResult) {
        Set<String> categoryList = new HashSet<>();
        if (CollectionUtils.isEmpty(productRepository.findByTmdbId(tmdbResult.getId()))) {
            Product product = new Product();
            product.setName(tmdbResult.getTitle());
            for (Integer genre : tmdbResult.getGenre_ids()) {
                String selected = genresMovie.get(genre);
                if (selected != null) {
                    categoryList.add(selected);
                }
            }
            if (categoryList.contains("5ee73fdb2ace0520f01af3cf")) {
                product.setMainCategoryIdList(GENERAL_ID);
                categoryList.add("5ee9eeb9c106085337e8ee83");
            } else {
                product.setMainCategoryIdList(Collections.singletonList("5ee9e7eedee64f1214047c03"));
            }
            product.setCategoryIdList(new ArrayList<>(categoryList));
            saveMovieWithDetail(tmdbResult, product);
        }
    }

    private void setTvProduct(TMDBResult tmdbResult) {
        Set<String> categoryList = new HashSet<>();
        if (productRepository.findByTmdbId(tmdbResult.getId()) == null) {
            Product product = new Product();
            product.setName(tmdbResult.getName());
            for (Integer genre : tmdbResult.getGenre_ids()) {
                String selected = genresTv.get(genre);
                if (selected != null) {
                    categoryList.add(selected);
                }
            }
            if (categoryList.contains("5ee9eeb9c106085337e8ee83")) {
                product.setMainCategoryIdList(GENERAL_ID);
            } else {
                product.setMainCategoryIdList(Collections.singletonList("5edd816e7d85f9caded1c5bb"));
            }
            product.setCategoryIdList(new ArrayList<>(categoryList));
            saveMovieWithDetail(tmdbResult, product);
        }
    }

    private void saveMovieWithDetail(TMDBResult tmdbResult, Product product) {
        product.setCert("Unrated");
        product.setDescription(tmdbResult.getOverview());
        product.setStatus("Announced");
        product.setTmdbId(tmdbResult.getId());
        product.setImdbId("");
        String url = "https://ik.imagekit.io/fbwk6udskh/sosu/default_beIuMGr9a7o.png";
        if (StringUtils.isNotBlank(tmdbResult.getPoster_path())) {
            url = imageUploader.uploadImage(MessageFormat.format(imageUrl, tmdbResult.getPoster_path()), product.getName());
        }
        product.setImage(url);
        if (tmdbResult.getRelease_date() != null) {
            product.setYear(LocalDate.parse(tmdbResult.getRelease_date()).getYear());
        } else {
            product.setYear(0);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
