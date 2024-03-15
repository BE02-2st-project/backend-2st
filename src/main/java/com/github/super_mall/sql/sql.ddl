CREATE DATABASE super_mall_DB;

USE super_mall_DB;

CREATE TABLE `users` (
                         `user_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `user_name` VARCHAR(255),
                         `email` VARCHAR(255) NOT NULL,
                         `password` VARCHAR(255)  NULL,
                         `phone_number` VARCHAR(255)  NULL,
                         `address` VARCHAR(255)  NULL,
                         `gender` VARCHAR(255)  NULL,
                         `social_user_id` VARCHAR(255) NULL,
                         `social_name` VARCHAR(255) NULL,
                         `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `items` (
                         `item_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `category_id` INT NOT NULL,
                         `item_name` VARCHAR(255) NOT NULL,
                         `img` VARCHAR(255) NOT NULL,
                         `price` INT NOT NULL,
                         `stock` INT NOT NULL,
                         `item_description` TEXT NOT NULL,
                         `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `categorys` (
                             `category_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             `category_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `carts` (
                        `cart_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `user_id` INT NOT NULL
);

CREATE TABLE `cart_items` (
                            `cart_item_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            `cart_id` INT NOT NULL,
                            `item_id` INT NOT NULL,
                            `count` INT NOT NULL,
                            `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `orders` (
                          `order_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          `user_id` INT NOT NULL,
                          `total_price` INT NOT NULL,
                          `status` VARCHAR(20),
                          `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `order_item` (
                              `order_item_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              `order_id` INT NOT NULL,
                              `item_id` INT NOT NULL,
                              `price` INT NOT NULL,
                              `count` INT NOT NULL
);

CREATE TABLE `sales` (
                         `sale_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `user_id` INT NOT NULL,
                         `item_id` INT NOT NULL,
                         `stock` INT NOT NULL,
                         `create_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `roles` (
                         `role_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `role_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `user_roles` (
                              `user_role_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              `user_id` INT NOT NULL,
                              `role_id` INT NOT NULL
);

CREATE TABLE `refresh_tokens` (
                                  `refresh_token_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  `user_id` INT NOT NULL,
                                  `refresh_token` VARCHAR(255) NOT NULL
);

