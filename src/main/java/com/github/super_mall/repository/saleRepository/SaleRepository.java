package com.github.super_mall.repository.saleRepository;

import com.github.super_mall.entity.saleEntity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findSaleByUser_UserId(Long user_userId);
}
