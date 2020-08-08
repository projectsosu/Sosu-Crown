package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.entity.postgres.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findByImdbId(String imdbId);

    List<Product> findByYear(Integer year);

    @Query("SELECT p FROM Product p WHERE p.categoryId like %:category% or p.mainCategoryId like %:category% ")
    List<Product> getProductByCategory(
            @Param("category") String category,
            Pageable pageable);

    List<Product> findTop10ByAndNameContains(@Param("name") String name);

    @Query("SELECT p FROM Product p order by function('RAND')")
    List<Product> findRandomProduct(Pageable pageable);

    List<Product> findByTmdbId(Integer imdbId);

    List<Product> findByCategoryId(String categoryId);

    Integer countByCategoryIdContaining(String categoryId);

    Integer countByMainCategoryIdContaining(String categoryId);
}
