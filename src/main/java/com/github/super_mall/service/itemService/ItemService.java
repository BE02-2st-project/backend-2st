package com.github.super_mall.service.itemService;

import com.github.super_mall.dto.itemDto.ItemRegisterDto;
import com.github.super_mall.entity.saleEntity.Sale;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.exceptions.NotFoundCategoryException;
import com.github.super_mall.repository.categoryRepository.CategoryRepository;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.saleRepository.SaleRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.categoryEntity.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    public List<Item> findByNameContaining(String nameKeyword) {
        return itemRepository.findItemByNameContaining(nameKeyword);
    }

    @Transactional
    public void addItem(ItemRegisterDto addItem, String email) {
        Category category = categoryRepository.findCategoryByCategory(addItem.getCategory())
                .orElseThrow(() -> new NotFoundCategoryException("카테고리가 존재하지 않습니다."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Item item = Item.toEntity(addItem, category);
        Sale sale = Sale.toEntity(item, user);
        itemRepository.save(item);
        saleRepository.save(sale);
    }

    @Transactional
    public void updateProduct(ItemRegisterDto itemRegisterDto) {
        Category category = categoryRepository.findCategoryByCategory(itemRegisterDto.getCategory())
                .orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));

        Item updateItem = Item.builder()
                .id(itemRegisterDto.getItemId())
                .category(category)
                .name(itemRegisterDto.getName())
                .stock(itemRegisterDto.getStock())
                .price(itemRegisterDto.getPrice())
                .description(itemRegisterDto.getDescription())
                .build();

        itemRepository.save(updateItem);

    }

    @Transactional
    public void deleteProduct(Integer itemId) {
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
//        itemEntity.setIsDelete(true);
    }
}
