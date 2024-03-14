package com.github.super_mall.service.saleService;

import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.saleEntity.Sale;
import com.github.super_mall.repository.saleRepository.SaleRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    public List<Sale> findSaleItem(Long userId) {
        return saleRepository.findSaleByUser_UserId(userId);
    }
}
