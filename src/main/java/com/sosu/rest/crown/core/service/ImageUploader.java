package com.sosu.rest.crown.core.service;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ImageUploader {

    @Value("${cloudinary.cloud.name}")
    private String urlEndpoint;

    @Value("${cloudinary.api.key}")
    private String privateKey;

    @Value("${cloudinary.api.secret}")
    private String publicKey;

    private ImageKit imageKit;


    @PostConstruct
    private void configure() {
        imageKit = ImageKit.getInstance();
        Configuration config = new Configuration();
        config.setPrivateKey(privateKey);
        config.setPublicKey(publicKey);
        config.setUrlEndpoint(urlEndpoint);
        imageKit.setConfig(config);
    }

    public String uploadImage(byte[] imageBytes, String userName) {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(imageBytes, userName);
        fileCreateRequest.setFolder("sosu/profileImages");
        Result result = imageKit.upload(fileCreateRequest);
        return result.getUrl();
    }

    public String uploadImage(String url, String productName) {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(url, productName);
        fileCreateRequest.setFolder("sosu");
        fileCreateRequest.setUseUniqueFileName(true);
        Result result = imageKit.upload(fileCreateRequest);
        return result.getUrl();
    }

}
