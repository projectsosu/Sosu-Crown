package com.sosu.rest.crown.repo;

import com.sosu.rest.crown.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = :name and p.year = :year")
    Product getProductByNameAndYear(
            @Param("name") String name,
            @Param("year") int year);

}
