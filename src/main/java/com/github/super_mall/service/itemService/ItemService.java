package com.github.super_mall.service.itemService;

import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.repository.categoryRepository.CategoryRepository;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.categoryEntity.Category;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    public List<Item> findByNameContaining(String nameKeyword) {
        return itemRepository.findItemByNameContaining(nameKeyword);
    }

    public Item addItem(ItemAdditionalDto addItem, String email) {
        Category category = categoryRepository.findCategoryByCategory(addItem.getCategory())
                .orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Item item = Item.toEntity(addItem, category);
        return itemRepository.save(item);
    }
}
