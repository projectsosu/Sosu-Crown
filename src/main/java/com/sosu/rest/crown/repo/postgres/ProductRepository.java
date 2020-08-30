/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
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

    @Query(value = "SELECT * FROM product p WHERE ?1 like any(p.category_id_list) or ?1 like any(p.main_category_id_list)", nativeQuery = true)
    List<Product> getProductByCategory(String category, Pageable pageable);

    List<Product> findTop10ByAndNameContains(@Param("name") String name);

    @Query("SELECT p FROM Product p order by function('RAND')")
    List<Product> findRandomProduct(Pageable pageable);

    List<Product> findByTmdbId(Integer imdbId);

    @Query(value = "SELECT count(*) FROM product p WHERE ?1 like any(p.category_id_list)", nativeQuery = true)
    Integer countByCategoryIdListContaining(String categoryId);

    @Query(value = "SELECT count(*) FROM product p WHERE ?1 like any(p.main_category_id_list)", nativeQuery = true)
    Integer countByMainCategoryIdListContaining(String categoryId);

    @Query(value = "SELECT * FROM product p WHERE category_id_list = '{}')", nativeQuery = true)
    List<Product> findEmptyCategories();

}
