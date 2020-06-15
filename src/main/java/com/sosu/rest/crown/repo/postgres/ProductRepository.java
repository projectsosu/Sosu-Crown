package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.entitiy.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = :name and p.year = :year")
    Product getProductByNameAndYear(
            @Param("name") String name,
            @Param("year") int year);

    @Query("SELECT p FROM Product p WHERE p.categoryId like %:category% ")
    List<Product> getProductByCategory(
            @Param("category") String category,
            Pageable pageable);

}
