package com.github.super_mall.repository.categoryRepository;

import com.github.super_mall.entity.categoryEntity.Category;
import com.github.super_mall.entity.itemEntity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategory(Category category);
}
