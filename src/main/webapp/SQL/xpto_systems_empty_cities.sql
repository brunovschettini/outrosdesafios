-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 13-Jun-2018 às 00:09
-- Versão do servidor: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `xpto_systems`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cities`
--

CREATE TABLE `cities` (
  `id` bigint(20) NOT NULL,
  `alternative_accents` varchar(150) NOT NULL,
  `capital` tinyint(1) DEFAULT '0',
  `ibge_id` bigint(20) NOT NULL,
  `lat` double DEFAULT NULL,
  `lon` double DEFAULT NULL,
  `meso_region` varchar(100) NOT NULL,
  `micro_region` varchar(100) NOT NULL,
  `name` varchar(150) NOT NULL,
  `no_accents` varchar(150) NOT NULL,
  `register_date` datetime NOT NULL,
  `uf` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `active` tinyint(1) DEFAULT '0',
  `confirm_code` varchar(255) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `nickname` varchar(150) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `register_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `users`
--

INSERT INTO `users` (`id`, `active`, `confirm_code`, `email`, `name`, `nickname`, `password`, `register_date`) VALUES
(1, 1, NULL, 'admin@xptosystems.com.br', 'Admin', '', '94b3a76e266ae19c7d96c994ec477a0f', '2018-06-10 10:38:00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `user_token`
--

CREATE TABLE `user_token` (
  `id` bigint(20) NOT NULL,
  `access` datetime DEFAULT NULL,
  `access_token` varchar(500) DEFAULT NULL,
  `expires` datetime DEFAULT NULL,
  `users` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `user_token`
--

INSERT INTO `user_token` (`id`, `access`, `access_token`, `expires`, `users`) VALUES
(1, '2018-06-10 16:22:46', 'caa63b218d67dfb8bbd2edc68a186f17', NULL, 1),
(2, '2018-06-10 16:24:53', 'd310cac070af124ae4490cde1377b73', NULL, 1),
(3, '2018-06-10 16:29:27', '47ec9215131a9545ed07f186ea710ac5', NULL, 1),
(4, '2018-06-10 16:29:58', 'fd7a655bd30ea3c212f76f21468e05a3', NULL, 1),
(5, '2018-06-10 16:30:41', 'ccf1130368d8c507908519e98b71589b', NULL, 1),
(6, '2018-06-10 16:32:47', 'e43870cc47c680958beb88f3d091105d', NULL, 1),
(7, '2018-06-10 16:36:42', 'f2d74b7b573463980b57198770627ccb', NULL, 1),
(8, '2018-06-10 16:37:12', '282ea9341f53e8003e2529c05d0e0a9e', NULL, 1),
(9, '2018-06-10 16:37:41', 'e698399708d261f5169dff8925ae21bf', NULL, 1),
(10, '2018-06-10 19:10:29', 'd82d99f9c9e899780123b2375ab28a1', NULL, 1),
(11, '2018-06-10 19:41:54', 'd39e85e796cd47bad61431ea4ea760f3', NULL, 1),
(12, '2018-06-10 19:58:16', '1a224692757f5afc15d9e130072f4110', NULL, 1),
(13, '2018-06-10 20:02:34', '1aefe0758c74a0452e7a785bd77e8ee6', NULL, 1),
(14, '2018-06-10 20:15:52', 'c682fad3e3e346af99282c7f192c92a1', NULL, 1),
(15, '2018-06-10 20:20:45', 'daae26158cb954b1299da79f760a52d4', NULL, 1),
(16, '2018-06-10 20:40:18', '73f54e937a32d53ef43284af26492ab6', NULL, 1),
(17, '2018-06-12 10:14:25', '76cd3e8ceb9d89cd7c52c32682111aed', NULL, 1),
(18, '2018-06-12 10:23:35', 'a495dea5fa55ede1409e9e04b4ca2a14', NULL, 1),
(19, '2018-06-12 11:09:24', '75c8e2e3b9280a1f2541ea64f28cc99', NULL, 1),
(20, '2018-06-12 11:13:02', 'f262351d5f0ac1ab192d777c9161393f', NULL, 1),
(21, '2018-06-12 11:39:16', '22a28cf6451b87d53318f23ddefdbf12', NULL, 1),
(22, '2018-06-12 11:42:33', '23157668785ffa7e42bcd58c0282abd2', NULL, 1),
(23, '2018-06-12 14:46:14', '4137028e76ac7864379e762049ab6f07', NULL, 1),
(24, '2018-06-12 14:49:07', '5547d931b17d721d2f1c6f48f26e049e', NULL, 1),
(25, '2018-06-12 14:55:25', '8f7274f937937da0a902d4882c942976', NULL, 1),
(26, '2018-06-12 15:05:05', '97f813faba68206fabcf52f1f7fc77c8', NULL, 1),
(27, '2018-06-12 15:31:05', 'c94d57234512a1dd0d5cd8a6539c368e', NULL, 1),
(28, '2018-06-12 15:44:05', '7324a78051da318c093d12778abd7e6', NULL, 1),
(29, '2018-06-12 16:05:24', 'f3e18abce5fb44feb22298047518c9fd', NULL, 1),
(30, '2018-06-12 16:35:17', '45b81d2737420af57c5f5b831f10ef70', NULL, 1),
(31, '2018-06-12 16:47:48', '4b3c6906811845855bd5d2951b33a8e2', NULL, 1),
(32, '2018-06-12 17:45:54', 'd8d59c3df5723228aa73fc1e5737c47', NULL, 1),
(33, '2018-06-12 17:47:41', 'da3f2020d1a18fa0c004fdfbe6d76591', NULL, 1),
(34, '2018-06-12 17:54:23', '5cb39362d64c618fa92a4b5ed28e2ce7', NULL, 1),
(35, '2018-06-12 18:22:29', '112e6b902755bdd2d382cf549beffe49', NULL, 1),
(36, '2018-06-12 18:23:50', '576666051d0076c4497434480a60dbc6', NULL, 1),
(37, '2018-06-12 18:23:55', '6e202acd08c20e51f884e39ab3bd5cfd', NULL, 1),
(38, '2018-06-12 18:28:13', '4e92cffd86192f0ba55d375d2835d288', NULL, 1),
(39, '2018-06-12 18:34:05', 'db96fa15007d2642302760c893af42ea', NULL, 1),
(40, '2018-06-12 18:48:29', '358094a9688c2a11dc6c8efd5bef81e4', NULL, 1),
(41, '2018-06-12 18:50:42', 'f0000298b3fb76aa47e85fccb0cf051', NULL, 1),
(42, '2018-06-12 19:00:34', '110acecdd4db3f8a2e348332710711ae', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ibge_id` (`ibge_id`),
  ADD UNIQUE KEY `UNQ_cities_0` (`name`,`uf`,`capital`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `nickname` (`nickname`);

--
-- Indexes for table `user_token`
--
ALTER TABLE `user_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_user_token_users` (`users`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cities`
--
ALTER TABLE `cities`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11134;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user_token`
--
ALTER TABLE `user_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `user_token`
--
ALTER TABLE `user_token`
  ADD CONSTRAINT `FK_user_token_users` FOREIGN KEY (`users`) REFERENCES `users` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
