package com.github.super_mall.repository.saleRepository;

import com.github.super_mall.entity.saleEntity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

}
