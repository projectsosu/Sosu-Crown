package com.sosu.rest.crown.jobs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CloudinaryUploader {

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

    //    @Scheduled(cron = "0 3 0 16 * ?")
//    @PostConstruct
    private void uploadImages() {
        log.info("Cloudinary job started");
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
                log.error("Cloudinary job error: {}", e.getMessage());
            }
        });
    }

}
