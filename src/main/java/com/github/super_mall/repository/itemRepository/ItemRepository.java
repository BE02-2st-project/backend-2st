package com.github.super_mall.repository.itemRepository;

import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.itemEntity.ItemImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findItemByNameContaining(String keyword);

    Page<Item> findAllByNameContaining(String keyword, Pageable pageable);
}
