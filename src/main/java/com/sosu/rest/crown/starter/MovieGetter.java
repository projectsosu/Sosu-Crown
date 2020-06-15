package com.sosu.rest.crown.starter;

import com.sosu.rest.crown.async.MovieThread;
import com.sosu.rest.crown.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
public class MovieGetter {

    private static ArrayList<String> genres = new ArrayList<>() {
        {
            add("Action@5edd81c27d85f9caded1c5bc");
            add("Adventure@5ee73fd92ace0520f01af3ca");
            add("Animation@5ee73fda2ace0520f01af3cb");
            add("Biography@5ee73fda2ace0520f01af3cc");
            add("Comedy@5ee73fdb2ace0520f01af3cd");
            add("Crime@5ee73fdb2ace0520f01af3ce");
            add("Documentary@5ee73fdb2ace0520f01af3cf");
            add("Drama@5ee73fdb2ace0520f01af3d0");
            add("Family@5ee73fdb2ace0520f01af3d1");
            add("Fantasy@5ee73fdb2ace0520f01af3d2");
            add("Film Noir@5ee73fdb2ace0520f01af3d3");
            add("History@5ee73fdb2ace0520f01af3d4");
            add("Horror@5ee73fdb2ace0520f01af3d5");
            add("Music@5ee7722177a9074146eabad4");
            add("Musical@5ee7722777a9074146eabad5");
            add("Mystery@5ee73fdb2ace0520f01af3d6");
            add("Romance@5ee73fdb2ace0520f01af3d7");
            add("Sci-Fi@5ee73fdb2ace0520f01af3d8");
            add("Short Film@5ee73fdb2ace0520f01af3d9");
            add("Sport@5ee73fdb2ace0520f01af3da");
            add("Superhero@5ee73fdb2ace0520f01af3db");
            add("Thriller@5ee73fdb2ace0520f01af3dc");
            add("War@5ee73fdc2ace0520f01af3dd");
            add("Western@5ee73fdc2ace0520f01af3de");
        }
    };

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void runThread() {
        for (String genre : genres) {
            MovieThread movieThread = new MovieThread(productService, genre);
            new Thread(movieThread).start();
        }
    }

}
