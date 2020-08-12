/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.impl;

import com.sosu.rest.crown.entity.postgres.Product;
import com.sosu.rest.crown.mapper.CommonProductMapper;
import com.sosu.rest.crown.model.CommonProductModel;
import com.sosu.rest.crown.model.ProductByCategorySearchRequest;
import com.sosu.rest.crown.repo.postgres.ProductRepository;
import com.sosu.rest.crown.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CommonProductMapper commonProductMapper;

    @Override
    public List<CommonProductModel> getProductByCategory(ProductByCategorySearchRequest request) {
        if (request.getDesc()) {
            return commonProductMapper.productsToCommon(repository.getProductByCategory(request.getCategoryId(),
                    PageRequest.of(request.getPage() - 1, request.getPageSize(), Sort.by(request.getSortBy().label).descending())));
        } else {
            return commonProductMapper.productsToCommon(repository.getProductByCategory(request.getCategoryId(),
                    PageRequest.of(request.getPage() - 1, request.getPageSize(), Sort.by(request.getSortBy().label).ascending())));
        }
    }

    @Override
    public void saveOrUpdate(Product product) {
        repository.save(product);
    }

    @Override
    public List<CommonProductModel> findRandomProduct(Integer page) {
        return commonProductMapper.productsToCommon(repository.findRandomProduct(PageRequest.of(page, 10, Sort.by("name"))));
    }
}
