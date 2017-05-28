-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2017 at 03:06 PM
-- Server version: 10.1.22-MariaDB
-- PHP Version: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kar54`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `artist`
--

CREATE TABLE `artist` (
  `artid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `artist_type` varchar(20) NOT NULL,
  `info` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `galary`
--

CREATE TABLE `galary` (
  `gid` int(11) NOT NULL,
  `path` varchar(200) NOT NULL,
  `up_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `galary`
--

INSERT INTO `galary` (`gid`, `path`, `up_time`) VALUES
(1, '/abcd', '2017-05-22 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `genid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `karaoke_song`
--

CREATE TABLE `karaoke_song` (
  `kid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `subtitle_path` varchar(200) NOT NULL,
  `beat_path` varchar(200) NOT NULL,
  `view_no` int(11) NOT NULL,
  `up_time` datetime NOT NULL,
  `image` varchar(200) NOT NULL,
  `uid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `karaoke_song_belong_in`
--

CREATE TABLE `karaoke_song_belong_in` (
  `kid` int(11) NOT NULL,
  `genid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `karaoke_song_perform`
--

CREATE TABLE `karaoke_song_perform` (
  `kid` int(11) NOT NULL,
  `artid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ksong_report`
--

CREATE TABLE `ksong_report` (
  `rid` int(11) NOT NULL,
  `ksong_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `like_`
--

CREATE TABLE `like_` (
  `uid` int(11) NOT NULL,
  `shared_record_id` int(11) NOT NULL,
  `like_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `rid` int(11) NOT NULL,
  `content` text NOT NULL,
  `up_time` datetime NOT NULL,
  `uid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `shared_record`
--

CREATE TABLE `shared_record` (
  `srid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `content` text NOT NULL,
  `record_type` varchar(10) NOT NULL,
  `ksong_id` int(11) NOT NULL,
  `share_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `shared_record_report`
--

CREATE TABLE `shared_record_report` (
  `rid` int(11) NOT NULL,
  `shared_record_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `uid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT 'your name',
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `phone_no` varchar(15) NOT NULL DEFAULT '0901234567',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `birthday` date NOT NULL DEFAULT '1995-01-01',
  `avatar` varchar(200) NOT NULL DEFAULT '',
  `remember_token` varchar(200) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `name`, `email`, `password`, `phone_no`, `gender`, `birthday`, `avatar`, `remember_token`) VALUES
(1, 'thong', 't@mail.com', '123455', '0901234567', 0, '1995-01-01', '', ''),
(8, 'asdf', 't03@gmail.com', '$2y$10$jim2chSmk7osOgXPSUKBFueoeHejt.ws.AQ0kCMIB.njD8eTjR8LC', '0901234567', 0, '1995-01-01', '', ''),
(9, 'ta01', 'ta01@gmail.com', '$2y$10$vV8XKBNmSHGwR7d6yAwnj.8pD4Tqd50WnobhOEQ1J9f8.KBM8cNli', '0901234567', 0, '1995-01-01', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`artid`);

--
-- Indexes for table `galary`
--
ALTER TABLE `galary`
  ADD PRIMARY KEY (`gid`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genid`);

--
-- Indexes for table `karaoke_song`
--
ALTER TABLE `karaoke_song`
  ADD PRIMARY KEY (`kid`),
  ADD KEY `karaoke_song_ibfk_1` (`uid`);

--
-- Indexes for table `karaoke_song_belong_in`
--
ALTER TABLE `karaoke_song_belong_in`
  ADD KEY `genid` (`genid`),
  ADD KEY `kid` (`kid`);

--
-- Indexes for table `karaoke_song_perform`
--
ALTER TABLE `karaoke_song_perform`
  ADD KEY `artid` (`artid`),
  ADD KEY `kid` (`kid`);

--
-- Indexes for table `ksong_report`
--
ALTER TABLE `ksong_report`
  ADD KEY `rid` (`rid`),
  ADD KEY `ksong_id` (`ksong_id`);

--
-- Indexes for table `like_`
--
ALTER TABLE `like_`
  ADD KEY `shared_record_id` (`shared_record_id`),
  ADD KEY `like__ibfk_2` (`uid`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`rid`),
  ADD KEY `report_ibfk_1` (`uid`);

--
-- Indexes for table `shared_record`
--
ALTER TABLE `shared_record`
  ADD PRIMARY KEY (`srid`),
  ADD KEY `ksong_id` (`ksong_id`),
  ADD KEY `uid` (`uid`);

--
-- Indexes for table `shared_record_report`
--
ALTER TABLE `shared_record_report`
  ADD KEY `rid` (`rid`),
  ADD KEY `shared_record_id` (`shared_record_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `uid` (`uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `galary`
--
ALTER TABLE `galary`
  MODIFY `gid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `karaoke_song`
--
ALTER TABLE `karaoke_song`
  MODIFY `kid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `shared_record`
--
ALTER TABLE `shared_record`
  MODIFY `srid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `karaoke_song`
--
ALTER TABLE `karaoke_song`
  ADD CONSTRAINT `karaoke_song_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE;

--
-- Constraints for table `karaoke_song_belong_in`
--
ALTER TABLE `karaoke_song_belong_in`
  ADD CONSTRAINT `karaoke_song_belong_in_ibfk_1` FOREIGN KEY (`genid`) REFERENCES `genre` (`genid`) ON DELETE CASCADE,
  ADD CONSTRAINT `karaoke_song_belong_in_ibfk_2` FOREIGN KEY (`kid`) REFERENCES `karaoke_song` (`kid`) ON DELETE CASCADE;

--
-- Constraints for table `karaoke_song_perform`
--
ALTER TABLE `karaoke_song_perform`
  ADD CONSTRAINT `karaoke_song_perform_ibfk_1` FOREIGN KEY (`artid`) REFERENCES `artist` (`artid`) ON DELETE CASCADE,
  ADD CONSTRAINT `karaoke_song_perform_ibfk_2` FOREIGN KEY (`kid`) REFERENCES `karaoke_song` (`kid`) ON DELETE CASCADE;

--
-- Constraints for table `ksong_report`
--
ALTER TABLE `ksong_report`
  ADD CONSTRAINT `ksong_report_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `report` (`rid`) ON DELETE CASCADE,
  ADD CONSTRAINT `ksong_report_ibfk_2` FOREIGN KEY (`ksong_id`) REFERENCES `karaoke_song` (`kid`) ON DELETE CASCADE;

--
-- Constraints for table `like_`
--
ALTER TABLE `like_`
  ADD CONSTRAINT `like__ibfk_1` FOREIGN KEY (`shared_record_id`) REFERENCES `shared_record` (`srid`) ON DELETE CASCADE,
  ADD CONSTRAINT `like__ibfk_2` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE;

--
-- Constraints for table `shared_record`
--
ALTER TABLE `shared_record`
  ADD CONSTRAINT `shared_record_ibfk_1` FOREIGN KEY (`ksong_id`) REFERENCES `karaoke_song` (`kid`) ON DELETE CASCADE,
  ADD CONSTRAINT `shared_record_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE;

--
-- Constraints for table `shared_record_report`
--
ALTER TABLE `shared_record_report`
  ADD CONSTRAINT `shared_record_report_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `report` (`rid`) ON DELETE CASCADE,
  ADD CONSTRAINT `shared_record_report_ibfk_2` FOREIGN KEY (`shared_record_id`) REFERENCES `shared_record` (`srid`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
