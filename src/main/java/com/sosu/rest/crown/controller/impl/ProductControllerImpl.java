package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.ProductController;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/java")
public class ProductControllerImpl implements ProductController {

    Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Override
    public Product getProducts(String name, Integer year) {
        return productService.getProductByNameAndYear(name, year);
    }

    @Override
    public List<Product> getProductByCategory(ProductByCategorySearchRequest request) {
        logger.info("Request for getting product: {}", request);
        return productService.getProductByCategory(request.getCategory_id(), request.getPageSize(), request.getSortBy().label, request.getDesc());
    }
}
