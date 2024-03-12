package com.github.super_mall.repository.itemRepository;

import com.github.super_mall.entity.itemEntity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
