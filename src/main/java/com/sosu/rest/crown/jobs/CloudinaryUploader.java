package com.sosu.rest.crown.jobs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class CloudinaryUploader {

    Logger logger = LogManager.getLogger(CloudinaryUploader.class);

    @Autowired
    private ProductService productService;

    @Value("${cloudinary.cloud.name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api.key}")
    private String API_KEY;

    @Value("${cloudinary.api.secret}")
    private String API_SECRET;

    private static Map<String, String> optionsMap = new HashMap<>();

    static {
        optionsMap.put("folder", "sosu/");
    }

    @Scheduled(cron = "0 3 0 16 * ?")
    private void uploadImages() {
        logger.info("Cloudinary job started");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET));
        List<Product> productList = productService.getNotUploaded();
        productList.parallelStream().forEach(item -> {
            try {
                Map result = cloudinary.uploader().upload(item.getImage(), optionsMap);
                item.setImage((String) result.get("secure_url"));
                productService.saveOrUpdate(item);
            } catch (IOException e) {
                logger.info("Cloudinary job error: {}", e.getMessage());
            }
        });
    }

}
