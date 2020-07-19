package com.sosu.rest.crown.jobs;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.repo.postgres.GameRepository;
import com.sosu.rest.crown.service.ProductService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

//@Component
//@Slf4j
public class CloudinaryUploader {

//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private GameRepository gameRepository;
//
//    @Value("${cloudinary.cloud.name}")
//    private String urlEndpoint;
//
//    @Value("${cloudinary.api.key}")
//    private String privateKey;
//
//    @Value("${cloudinary.api.secret}")
//    private String publicKey;
//
//    @Scheduled(cron = " 0 30 0 ? * * ")
//    @PostConstruct
//    public void uploadImages() throws IOException {
//        log.info("ImageKit job started");
//        ImageKit imageKit = ImageKit.getInstance();
//        Configuration config = new Configuration();
//        config.setPrivateKey(privateKey);
//        config.setPublicKey(publicKey);
//        config.setUrlEndpoint(urlEndpoint);
//        imageKit.setConfig(config);
////        new Thread(() -> updateGames(imageKit)).start();
//        new Thread(() -> updateProducts(imageKit)).start();
//    }
//
////    public void updateGames(ImageKit imageKit) {
////        List<Game> gamesList = gameRepository.getNotUploaded();
////        gamesList.parallelStream().forEach(item -> {
////            try {
////                FileCreateRequest fileCreateRequest = new FileCreateRequest(item.getImage(), item.getName());
////                fileCreateRequest.setFolder("sosu");
////                fileCreateRequest.setUseUniqueFileName(true);
////                Result result = imageKit.upload(fileCreateRequest);
////                if (result.isSuccessful()) {
////                    item.setImage(result.getUrl());
////                    gameRepository.save(item);
////                } else {
////                    fileCreateRequest = new FileCreateRequest("https://increasify.com.au/wp-content/uploads/2016/08/default-image.png", item.getName());
////                    fileCreateRequest.setFolder("sosu");
////                    fileCreateRequest.setUseUniqueFileName(true);
////                    result = imageKit.upload(fileCreateRequest);
////                    if (result.isSuccessful()) {
////                        item.setImage(result.getUrl());
////                        gameRepository.save(item);
////                    }
////                }
////            } catch (Exception e) {
////                log.error("ImageKit job error: {}", e.getMessage());
////            }
////        });
////    }
//
//    public void updateProducts(ImageKit imageKit) {
//        List<Product> productList = productService.getNotUploaded();
//        productList.parallelStream().forEach(item -> {
//            try {
//                FileCreateRequest fileCreateRequest = new FileCreateRequest(item.getImage(), item.getName());
//                fileCreateRequest.setFolder("sosu");
//                fileCreateRequest.setUseUniqueFileName(true);
//                Result result = imageKit.upload(fileCreateRequest);
//                item.setImage(result.getUrl());
//                if (result.isSuccessful()) {
//                    item.setImage(result.getUrl());
//                    productService.saveOrUpdate(item);
//                } else {
//                    fileCreateRequest = new FileCreateRequest("https://increasify.com.au/wp-content/uploads/2016/08/default-image.png", item.getName());
//                    fileCreateRequest.setFolder("sosu");
//                    fileCreateRequest.setUseUniqueFileName(true);
//                    result = imageKit.upload(fileCreateRequest);
//                    if (result.isSuccessful()) {
//                        item.setImage(result.getUrl());
//                        productService.saveOrUpdate(item);
//                    }
//                }
//            } catch (Exception e) {
//                log.error("ImageKit job error: {}", e.getMessage());
//            }
//        });
//    }

}
