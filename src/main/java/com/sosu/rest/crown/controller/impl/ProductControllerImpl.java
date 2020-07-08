package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.ProductController;
import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public Product getProducts(String name, Integer year) {
        return productService.getProductByNameAndYear(name, year);
    }

    @Override
    public List<Product> getProductByCategory(ProductByCategorySearchRequest request) {
        log.info("Request for getting product: {}", request);
        return productService.getProductByCategory(request);
    }
}
