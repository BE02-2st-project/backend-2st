package com.github.super_mall.repository.itemRepository;

import com.github.super_mall.entity.itemEntity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {

    List<ItemImage> findByItemId(Integer itemId);
}
