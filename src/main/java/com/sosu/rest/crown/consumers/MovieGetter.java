package com.sosu.rest.crown.consumers;

import com.sosu.rest.crown.consumers.async.MovieThread;
import com.sosu.rest.crown.entity.mongo.Category;
import com.sosu.rest.crown.service.CategoryService;
import com.sosu.rest.crown.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MovieGetter {

    @Autowired
    private CategoryService categoryService;

    private static ArrayList<String> genres = new ArrayList<>() {
        {
            add("Action@5ee9eeb8c106085337e8ee77");
            add("Adventure@5ee9eeb9c106085337e8ee79");
            add("Animation@5ee9eeb9c106085337e8ee7b");
            add("Biography@5ee9eeb9c106085337e8ee7d");
            add("Comedy@5ee9eeb9c106085337e8ee7f");
            add("Crime@5ee9eeb9c106085337e8ee81");
            add("Documentary@5ee9eeb9c106085337e8ee83");
            add("Drama@5ee9eeb9c106085337e8ee85");
            add("Family@5ee9eebac106085337e8ee87");
            add("Fantasy@5ee9eebac106085337e8ee89");
            add("Game Show@5ee9eebac106085337e8ee8b");
            add("History@5ee9eebac106085337e8ee8d");
            add("Horror@5ee9eebac106085337e8ee8f");
            add("Music@5ee9eebac106085337e8ee91");
            add("Musical@5ee9eebac106085337e8ee93");
            add("Mystery@5ee9eebac106085337e8ee95");
            add("News@5ee9eebbc106085337e8ee97");
            add("Reality TV@5ee9eebbc106085337e8ee99");
            add("Romance@5ee9eebbc106085337e8ee9b");
            add("Sci-Fi@5ee9eebbc106085337e8ee9d");
            add("Sport@5ee9eebbc106085337e8ee9f");
            add("Superhero@5ee9eebbc106085337e8eea1");
            add("Talk Show@5ee9eebbc106085337e8eea3");
            add("Thriller@5ee9eebbc106085337e8eea5");
            add("War@5ee9eebcc106085337e8eea7");
            add("Western@5ee9eebcc106085337e8eea9");
        }
    };

    @Autowired
    private ProductService productService;

//    @PostConstruct
    public void runThread() {
        for (String genre : genres) {
            MovieThread movieThread = new MovieThread(productService, genre);
            new Thread(movieThread).start();
        }
    }


    //    @PostConstruct
    public void saveCategories() {
        for (String item : genres) {
            String[] genreList = item.split("@");
            Category categoryTr = new Category();
            categoryTr.setName(genreList[1]);
            categoryTr.setLang("tr_TR");
            categoryTr.setParent_id("5ee9e7ebdee64f1214047c02");
            Category categoryEnItem = new Category();
            categoryEnItem.setName(genreList[0]);
            categoryEnItem.setLang("en_US");
            categoryEnItem.setParent_id("5ee9e7eedee64f1214047c03");
            categoryService.saveOrUpdate(categoryTr);
            categoryService.saveOrUpdate(categoryEnItem);
        }
    }

}
