package com.github.super_mall.entity.itemEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Item {
    /**
     * `item_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     * `category_id` INT NOT NULL,
     * `item_name` VARCHAR(255) NOT NULL,
     * `img` VARCHAR(255) NOT NULL,
     * `price` INT NOT NULL,
     * `stock` INT NOT NULL,
     * `item_description` TEXT NOT NULL,
     * `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
