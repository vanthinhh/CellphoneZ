-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2023 at 01:52 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test01`
--

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`id`, `name`) VALUES
(1, 'iPhone'),
(2, 'samsung'),
(3, 'oppo'),
(4, 'xiaomi'),
(5, 'vivo'),
(6, 'hp'),
(7, 'lenovo'),
(8, 'dell'),
(9, 'MSi'),
(10, 'MacBook');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Laptop'),
(2, 'Điện thoại');

-- --------------------------------------------------------

--
-- Table structure for table `colors`
--

CREATE TABLE `colors` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `colors`
--

INSERT INTO `colors` (`id`, `name`) VALUES
(1, 'Đen'),
(2, 'Xanh da trời'),
(3, 'Nâu'),
(4, 'Xám'),
(5, 'Xanh lá cây'),
(6, 'Cam'),
(7, 'Hồng'),
(8, 'Tím'),
(9, 'Đỏ'),
(10, 'Trắng'),
(11, 'Vàng'),
(12, 'Xanh nước biển');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `create_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total` double NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `address`, `create_at`, `name`, `phone`, `status`, `total`, `user_id`, `payment_method`) VALUES
(1, 'Cổng 7 Kí Túc Xá ĐH Tôn Đức Thắng, Phường Bình Hưng Hoà A, Quận Bình Tân, Thành phố Hồ Chí Minh', '2023-08-27T17:05:20', 'Biên Nguyễn', '0333091697', 'Đang giao', 14990000, 1, NULL),
(2, 'Cổng 7 Kí Túc Xá ĐH Tôn Đức Thắng, Phường Tân Phong, Quận 7, Thành phố Hồ Chí Minh', '2023-08-28T00:48:36', 'Biên Nguyễn', '0333091697', 'Đã giao', 26790000, 1, NULL),
(3, 'Cổng 7 Kí Túc Xá ĐH Tôn Đức Thắng, Phường Tân Phong, Quận 7, Thành phố Hồ Chí Minh', '2023-08-28T01:11:48', 'Biên Nguyễn', '0333091697', 'Đang giao', 14990000, 1, NULL),
(4, 'KTX TDT, Phường Tân Phong, Quận 7, Thành phố Hồ Chí Minh', '2023-08-28T01:32:34', 'Biên Nguyễn', '0333091697', 'Đang giao', 71360000, 1, NULL),
(5, 'KTX TDT, Phường Tân Phong, Quận 7, Thành phố Hồ Chí Minh', '2023-08-28T01:48:47', 'Biên Nguyễn', '0333091697', 'Đang giao', 267050000, 1, NULL),
(6, 'HAI BA TRUNG, Xã Tiền Tiến, Thành phố Hải Dương, Tỉnh Hải Dương', '2023-08-31T17:02:08', 'TRAN DINH QUAN', '0813340655', 'Đang chờ', 299999, 1, 'VNPay');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `purchase_price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `purchase_price`, `quantity`, `order_id`, `product_id`) VALUES
(1, 14990000, 1, 1, 23),
(2, 26790000, 1, 2, 15),
(3, 14990000, 1, 3, 23),
(4, 24790000, 2, 4, 16),
(5, 10890000, 2, 4, 17),
(6, 24790000, 4, 5, 16),
(7, 15490000, 6, 5, 22),
(8, 14990000, 5, 5, 23),
(9, 299999, 1, 6, 28);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `cate_id` bigint(20) DEFAULT NULL,
  `color_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `description`, `image`, `name`, `price`, `quantity`, `brand_id`, `cate_id`, `color_id`, `user_id`) VALUES
(15, 'Điện thoại xịn', 'iphone-14-pro-max-tim-thumb-600x600.jpg', 'iPhone 14 Pro Max', 26790000, 99, 1, 2, 1, 1),
(16, 'ngon', 'iphone-14-pro-vang-thumb-600x600.jpg', 'iPhone 14 Pro', 24790000, 800, 1, 2, 11, 1),
(17, 'ngoo', 'iphone-11-trang-600x600.jpg', 'iPhone 11', 10890000, 50, 1, 2, 10, 1),
(18, 'ngoo', 'iphone-14-128gb-vang-thumbnew-600x600.jpeg', 'iPhone 14', 19190000, 11, 1, 2, 11, 1),
(19, 'iPhone 14 Plus', 'iPhone-14-plus-thumb-den-600x600.jpg', 'iPhone 14 Plus', 21790000, 123, 1, 2, 1, 1),
(20, 'iPhone 13 mini 128GB', 'iphone-13-mini-pink-1-600x600.jpg', 'iPhone 13 mini 128GB', 15990000, 321, 1, 2, 7, 1),
(21, 'iPhone 13', 'iphone-13-xanh-la-thumb-new-600x600.jpg', 'iPhone 13', 16990000, 33, 1, 2, 5, 1),
(22, 'iPhone 12', 'iphone-12-tim-1-600x600.jpg', 'iPhone 12', 15490000, 55, 1, 2, 8, 1),
(23, 'MSI Gaming GF63 Thin 11SC i5 11400H (664VN)', 'msi-gaming-gf63-thin-11sc-i5-664vn-glr-thumb-600x600.jpg', 'MSI Gaming GF63 Thin 11SC i5 11400H (664VN)', 14990000, 33, 9, 1, 1, 1),
(28, 'mota', 'canh-dep-sapa-mua-xuan-13.jpg', 'test', 299999, 11, NULL, 1, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `gender`, `name`, `password`) VALUES
(1, 'nvb221003@gmail.com', 'nam', 'Nguyễn Văn Biên', '$2a$10$ZmOUTPRkOAYoeZnxQR9LKuJTEVa7CF4iS40LWJSeKotVYFW6sFa/q'),
(13, 'admin1122@ad.ot', 'Nam', 'Phan Ngoc', '$2a$10$q/1rzwjFbK4NAr0GjMDerORsCLXFyJ4HFIhD6lGPoG8zkFQcLsI4W'),
(17, 'admin1@ad.ot', 'nam', 'Phan Ngoc1', '$2a$10$ZmOUTPRkOAYoeZnxQR9LKuJTEVa7CF4iS40LWJSeKotVYFW6sFa/q'),
(18, 'admin22@ad.ot', 'nam', 'Phan Ngoc1', '$2a$10$xkaJKWgSl86EaAOTVufimOcZppeRQhsYyKG4wQqdAkFMN2cCCrUbe'),
(19, 'test@gmail.com', 'nam', 'vippro', '$2a$10$GYX5XZAVQpdEyPnBwEyCf.FeZGN.s7ezucHPiSS9F7SQwpwC1W8e.'),
(20, 'abc@gmail.com', 'nữ', 'Nguyễn Văn Biên', '$2a$10$1I3RZebe2ogWqI0MBAklle73CoBVa2TAsCvmqxFRf.po22Xlm0Wzy'),
(21, 'bienne@gmail.com', 'nam', 'Cam Ly', '$2a$10$/5LBNZs6XCmZjESYdRTmEO7eFws4pG9/vNuw76pXvrGzaUtwoR45m'),
(22, 'final@gmail.com', 'nam', 'Bien Bien', '$2a$10$UFdcvhNdsDPyjGAcVLEu0Oo3FpMSYxz5HgJP3NX8sOtRinzMO8D4e');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(21, 1),
(22, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vnpay`
--

CREATE TABLE `vnpay` (
  `id` bigint(20) NOT NULL,
  `payment_amount` int(11) NOT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `transaction_code` varchar(255) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `colors`
--
ALTER TABLE `colors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  ADD KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  ADD KEY `FKmj3byw2biiru52u2cok719rye` (`cate_id`),
  ADD KEY `FKebociq5k3b2tkcxh3q5dg4eed` (`color_id`),
  ADD KEY `FKdb050tk37qryv15hd932626th` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- Indexes for table `vnpay`
--
ALTER TABLE `vnpay`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKna1we5j06uapu0k9crbgxlt94` (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `colors`
--
ALTER TABLE `colors`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `vnpay`
--
ALTER TABLE `vnpay`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  ADD CONSTRAINT `FKdb050tk37qryv15hd932626th` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKebociq5k3b2tkcxh3q5dg4eed` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`),
  ADD CONSTRAINT `FKmj3byw2biiru52u2cok719rye` FOREIGN KEY (`cate_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `vnpay`
--
ALTER TABLE `vnpay`
  ADD CONSTRAINT `FKna1we5j06uapu0k9crbgxlt94` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
