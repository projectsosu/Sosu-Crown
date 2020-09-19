/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.controller.impl;

import com.sosu.rest.crown.controller.ProductController;
import com.sosu.rest.crown.enums.ProductType;
import com.sosu.rest.crown.model.CommonProductDetailDTO;
import com.sosu.rest.crown.model.CommonProductDTO;
import com.sosu.rest.crown.model.request.ProductByCategorySearchRequest;
import com.sosu.rest.crown.service.product.GamesService;
import com.sosu.rest.crown.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private GamesService gamesService;

    /**
     * Getting products
     *
     * @param request of product
     * @return selected product list with condition
     */
    @Override
    public ResponseEntity<List<CommonProductDTO>> getProductByCategory(ProductByCategorySearchRequest request) {
        if (request.getProductType() == ProductType.GAME) {
            return ResponseEntity.ok(gamesService.getProductByCategory(request));
        } else {
            return ResponseEntity.ok(productService.getProductByCategory(request));
        }
    }

    /**
     * Random game and products for initial page
     *
     * @param page page number
     * @return max 10 random products and games
     */
    @Override
    public ResponseEntity<List<CommonProductDTO>> getRandomProducts(Integer page) {
        List<CommonProductDTO> commonProductDTOS = new ArrayList<>();
        commonProductDTOS.addAll(productService.findRandomProduct(page == null || page < 0 ? 0 : page));
        commonProductDTOS.addAll(gamesService.findRandomGame(page == null || page < 0 ? 0 : page));
        commonProductDTOS.sort(Comparator.comparing(CommonProductDTO::getName));
        if (commonProductDTOS.size() > 10) {
            return ResponseEntity.ok(commonProductDTOS.subList(0, 10));
        }
        return ResponseEntity.ok(commonProductDTOS);
    }

    /**
     * Get product detail
     *
     * @param productId   id of product
     * @param productType type of product
     * @return product detail
     */
    @Override
    public ResponseEntity<CommonProductDetailDTO> getProductDetail(Long productId, ProductType productType) {
        if (ProductType.GAME.equals(productType)) {
            return ResponseEntity.ok(gamesService.findGame(productId));
        } else {
            return ResponseEntity.ok(productService.findProduct(productId));
        }
    }
}
