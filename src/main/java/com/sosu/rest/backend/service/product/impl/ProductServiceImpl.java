/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.product.impl;

import com.sosu.rest.backend.mapper.CommonProductMapper;
import com.sosu.rest.backend.repo.postgres.ProductRepository;
import com.sosu.rest.backend.core.exception.SoSuException;
import com.sosu.rest.backend.entity.postgres.Product;
import com.sosu.rest.backend.model.CommonProductDTO;
import com.sosu.rest.backend.model.CommonProductDetailDTO;
import com.sosu.rest.backend.model.request.ProductByCategorySearchRequest;
import com.sosu.rest.backend.service.CategoryService;
import com.sosu.rest.backend.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Product processes
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CommonProductMapper commonProductMapper;

    /**
     * Get products by selected conditions
     *
     * @param request search params
     * @return found product list
     */
    @Override
    public List<CommonProductDTO> getProductByCategory(ProductByCategorySearchRequest request) {
        if (request.getDesc()) {
            return commonProductMapper.productsToCommon(repository.getProductByCategory(request.getCategoryId(),
                    PageRequest.of(request.getPage() - 1, request.getPageSize(), Sort.by(request.getSortBy().label).descending())),
                    categoryService);
        } else {
            return commonProductMapper.productsToCommon(repository.getProductByCategory(request.getCategoryId(),
                    PageRequest.of(request.getPage() - 1, request.getPageSize(), Sort.by(request.getSortBy().label).ascending())),
                    categoryService);
        }
    }

    /**
     * Saves product
     *
     * @param product product entity
     */
    @Override
    public void saveOrUpdate(Product product) {
        repository.save(product);
    }

    /**
     * Gets random products for initial page
     *
     * @param page page number
     * @return random 10 products
     */
    @Override
    public List<CommonProductDTO> findRandomProduct(Integer page) {
        return commonProductMapper.productsToCommon(repository.findRandomProduct(PageRequest.of(page, 10, Sort.by("name"))), categoryService);
    }

    /**
     * Get product detail
     *
     * @param id of product
     * @return of product detail
     */
    @Override
    public CommonProductDetailDTO findProduct(Long id) {
        return commonProductMapper.productToCommon(repository.findById(id).orElseThrow(() ->
                new SoSuException(HttpStatus.BAD_REQUEST, "Product can not find", "PRODUCT_NOT_FOUND")
        ), categoryService);
    }
}
