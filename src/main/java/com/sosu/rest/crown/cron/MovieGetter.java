//package com.sosu.rest.crown.cron;
//
//import com.sosu.rest.crown.entity.postgres.Product;
//import com.sosu.rest.crown.repo.postgres.ProductRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//
//@Component
//@Slf4j
//public class MovieGetter {
//
//    private static ArrayList<String> genres = new ArrayList<>() {
//        {
//            add("Action@5edd81c27d85f9caded1c5bc");
//            add("Adventure@5ee73fd92ace0520f01af3ca");
////            add("Animation@5ee73fda2ace0520f01af3cb");
//            add("Biography@5ee73fda2ace0520f01af3cc");
//            add("Comedy@5ee73fdb2ace0520f01af3cd");
//            add("Crime@5ee73fdb2ace0520f01af3ce");
//            add("Documentary@5ee73fdb2ace0520f01af3cf");
//            add("Drama@5ee73fdb2ace0520f01af3d0");
//            add("Family@5ee73fdb2ace0520f01af3d1");
//            add("Fantasy@5ee73fdb2ace0520f01af3d2");
//            add("Film Noir@5ee73fdb2ace0520f01af3d3");
//            add("History@5ee73fdb2ace0520f01af3d4");
//            add("Horror@5ee73fdb2ace0520f01af3d5");
//            add("Music@5ee7722177a9074146eabad4");
//            add("Musical@5ee7722777a9074146eabad5");
//            add("Mystery@5ee73fdb2ace0520f01af3d6");
//            add("Romance@5ee73fdb2ace0520f01af3d7");
//            add("Sci-Fi@5ee73fdb2ace0520f01af3d8");
//            add("Short@5ee73fdb2ace0520f01af3d9");
//            add("Sport@5ee73fdb2ace0520f01af3da");
//            add("superhero@5ee73fdb2ace0520f01af3db");
//            add("Thriller@5ee73fdb2ace0520f01af3dc");
//            add("War@5ee73fdc2ace0520f01af3dd");
//            add("Western@5ee73fdc2ace0520f01af3de");
//        }
//    };
//
////    @Autowired
////    private ProductRepository productRepository;
////
////    @PostConstruct
////    public void runThread() {
////        run(genres.get(20), null);
////    }
////
////    public void run(String genre, String firstLink) {
////        String selected = "https://www.imdb.com/search/keyword/?page=1&sort=release_date,desc&keywords=superhero&title_type=movie&mode=detail&ref_=kw_prv#main";
////        if (firstLink != null) {
////            selected = firstLink;
////        }
////        String[] formatted = genre.split("@");
////        log.info(selected);
////        boolean error = false;
////        while (!error) {
////            try {
////                Document document = Jsoup.connect(selected).get();
////                Elements link = document.getElementsByClass("lister-page-next next-page");
////                if (CollectionUtils.isEmpty(link)) {
////                    error = true;
////                } else {
////                    selected = "https://www.imdb.com/search/keyword/" + link.get(0).attr("href");
////                    log.info(selected);
////                }
////                Elements products = document.getElementsByClass("lister-item mode-detail");
////                for (Element movie : products) {
////                    Elements basics = movie.getElementsByClass("lister-item-content");
////                    String[] splitted = basics.get(0).getAllElements().get(3).attr("href").split("/");
////                    syncr(formatted[1], splitted[2]);
////                }
////            } catch (Exception e) {
////                error = true;
////                e.getStackTrace();
////            }
////        }
////    }
//
////    private synchronized void syncr(String formatted, String imdbId) {
////        try {
////            Product selectedProduct = productRepository.findProductByImdbId(imdbId);
////            if (selectedProduct != null && !selectedProduct.getCategoryId().contains(formatted)) {
////                selectedProduct.setCategoryId(selectedProduct.getCategoryId() + ";" + formatted);
////                productRepository.save(selectedProduct);
////            }
////        } catch (Exception e) {
////            e.getStackTrace();
////            log.error("Error imdbId: {}", imdbId);
////        }
////
////    }
//
//}
