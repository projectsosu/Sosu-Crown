/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.service;

import com.sosu.rest.crown.enums.ProductType;
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


    /**
     * Configuration of imagekit io api
     */
    @PostConstruct
    private void configure() {
        imageKit = ImageKit.getInstance();
        Configuration config = new Configuration();
        config.setPrivateKey(privateKey);
        config.setPublicKey(publicKey);
        config.setUrlEndpoint(urlEndpoint);
        imageKit.setConfig(config);
    }

    /**
     * Profile image uploader
     *
     * @param imageBytes byte array of image
     * @param userName   username of selected user
     * @return return url path of image
     */
    public String uploadProfileImage(byte[] imageBytes, String userName) {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(imageBytes, userName);
        fileCreateRequest.setFolder("sosu/profileImages");
        Result result = imageKit.upload(fileCreateRequest);
        return result.getUrl();
    }

    /**
     * Product or game image uploader
     *
     * @param url         external url of product or image
     * @param productName name of product or game
     * @param productType type of product or game
     * @return return url path of product or game
     */
    public String uploadImage(String url, String productName, ProductType productType) {
        FileCreateRequest fileCreateRequest = new FileCreateRequest(url, productName);
        if (productType == ProductType.GAME) {
            fileCreateRequest.setFolder("sosu/game");
        } else if (productType == ProductType.PRODUCT) {
            fileCreateRequest.setFolder("sosu/product");
        }
        fileCreateRequest.setUseUniqueFileName(true);
        Result result = imageKit.upload(fileCreateRequest);
        return result.getUrl();
    }


}
