package com.github.super_mall.service.itemService;

import com.github.super_mall.dto.itemDto.ItemRegisterDto;
import com.github.super_mall.entity.itemEntity.ItemImage;
import com.github.super_mall.entity.saleEntity.Sale;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.exceptions.NotFoundCategoryException;
import com.github.super_mall.repository.categoryRepository.CategoryRepository;
import com.github.super_mall.repository.itemRepository.ItemImageRepository;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import com.github.super_mall.repository.saleRepository.SaleRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.categoryEntity.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private final ItemImageRepository itemImageRepository;

    public Page<Item> findWithPaging(Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");

        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return itemRepository.findAll(pageableWithSort);
    }

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    public List<Item> findByNameContaining(String nameKeyword) {
        return itemRepository.findItemByNameContaining(nameKeyword).stream()
                .filter(item -> !item.isDelete())
                .collect(Collectors.toList());
    }

    public Page<Item> findByNameContainingWithPage(String nameKeyword, Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");

        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Item> itemPage = itemRepository.findAllByNameContaining(nameKeyword, pageableWithSort);

        List<Item> filteredItems = itemPage.getContent().stream()
                .filter(item -> !item.isDelete())
                .filter(item -> item.getName().contains(nameKeyword))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredItems, pageable, itemPage.getTotalElements());
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

        addItem.getImgURLs().forEach(image -> {
            itemImageRepository.save(ItemImage.toEntity(image, item));
        });

        determineMainImage(item);
    }

    private void determineMainImage(Item item) {
        ItemImage regImg = itemImageRepository
                .findByItemId(item.getId())
                .stream()
                .min(ItemImage::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("이미지가 비어 있습니다."));

        regImg.setRepImgURL("Y");
    }

    @Transactional
    public void updateItem(ItemRegisterDto itemRegisterDto) {
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
    public void deleteItem(Integer itemId) {
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));
        if (itemEntity.isDelete()) throw new RuntimeException("이미 삭제된 상품입니다.");
        itemEntity.deleteItem();
    }
}
