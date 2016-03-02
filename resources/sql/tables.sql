SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema e_course
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `e_course` ;
CREATE SCHEMA IF NOT EXISTS `e_course` DEFAULT CHARACTER SET utf8 ;
USE `e_course` ;

-- -----------------------------------------------------
-- Table `e_course`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `e_course`.`user` ;

CREATE TABLE IF NOT EXISTS `e_course`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `enabled` VARCHAR(45) NOT NULL DEFAULT 'WAITING',
  `image` VARCHAR(45) NOT NULL DEFAULT 'noimage.jpg',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `login_password_index` (`login` ASC, `password` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `e_course`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `e_course`.`course` ;

CREATE TABLE IF NOT EXISTS `e_course`.`course` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tutor_id` INT(11) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  `image` VARCHAR(100) NOT NULL DEFAULT 'noimage.jpg',
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'BEFORE_START',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC),
  INDEX `fk_course_user1_idx` (`tutor_id` ASC),
  CONSTRAINT `fk_course_user1`
    FOREIGN KEY (`tutor_id`)
    REFERENCES `e_course`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `e_course`.`course_themes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `e_course`.`course_themes` ;

CREATE TABLE IF NOT EXISTS `e_course`.`course_themes` (
  `course_id` INT(11) NOT NULL,
  `theme` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`course_id`, `theme`),
  UNIQUE INDEX `course_theme` (`course_id` ASC, `theme` ASC),
  CONSTRAINT `fk_course_themes_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `e_course`.`course` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `e_course`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `e_course`.`message` ;

CREATE TABLE IF NOT EXISTS `e_course`.`message` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `from_id` INT(11) NOT NULL,
  `to_id` INT(11) NOT NULL,
  `message` VARCHAR(100) NOT NULL,
  `is_read` TINYINT(1) NOT NULL DEFAULT '0',
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_message_user1_idx` (`from_id` ASC),
  INDEX `fk_message_user2_idx` (`to_id` ASC),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`from_id`)
    REFERENCES `e_course`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_message_user2`
    FOREIGN KEY (`to_id`)
    REFERENCES `e_course`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `e_course`.`student_courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `e_course`.`student_courses` ;

CREATE TABLE IF NOT EXISTS `e_course`.`student_courses` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `mark` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `student_course` (`user_id` ASC, `course_id` ASC),
  INDEX `fk_student_courses_course1_idx` (`course_id` ASC),
  CONSTRAINT `fk_student_courses_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `e_course`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_courses_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `e_course`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `e_course`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `e_course`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `e_course`.`user_roles` (
  `user_id` INT(11) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`, `role`),
  UNIQUE INDEX `user_role` (`user_id` ASC, `role` ASC),
  CONSTRAINT `fk_user_roles_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `e_course`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `e_course` ;

-- -----------------------------------------------------
-- Placeholder table for view `e_course`.`journalview`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `e_course`.`journalview` (`user_id` INT, `name` INT, `login` INT, `password` INT, `email` INT, `course_id` INT, `id` INT, `title` INT, `description` INT, `start_date` INT, `end_date` INT, `status` INT, `mark` INT);

-- -----------------------------------------------------
-- Placeholder table for view `e_course`.`tutorview`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `e_course`.`tutorview` (`user_id` INT, `name` INT, `login` INT, `password` INT, `email` INT, `course_id` INT, `title` INT, `description` INT, `start_date` INT, `end_date` INT, `status` INT, `tutor_id` INT);

-- -----------------------------------------------------
-- View `e_course`.`journalview`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `e_course`.`journalview` ;
DROP TABLE IF EXISTS `e_course`.`journalview`;
USE `e_course`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `e_course`.`journalview` AS select `e_course`.`user`.`id` AS `user_id`,`e_course`.`user`.`name` AS `name`,`e_course`.`user`.`login` AS `login`,`e_course`.`user`.`password` AS `password`,`e_course`.`user`.`email` AS `email`,`e_course`.`course`.`id` AS `course_id`,`e_course`.`student_courses`.`id` AS `id`,`e_course`.`course`.`title` AS `title`,`e_course`.`course`.`description` AS `description`,`e_course`.`course`.`start_date` AS `start_date`,`e_course`.`course`.`end_date` AS `end_date`,`e_course`.`course`.`status` AS `status`,`e_course`.`student_courses`.`mark` AS `mark` from ((`e_course`.`user` left join `e_course`.`student_courses` on((`e_course`.`user`.`id` = `e_course`.`student_courses`.`user_id`))) left join `e_course`.`course` on((`e_course`.`student_courses`.`course_id` = `e_course`.`course`.`id`)));

-- -----------------------------------------------------
-- View `e_course`.`tutorview`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `e_course`.`tutorview` ;
DROP TABLE IF EXISTS `e_course`.`tutorview`;
USE `e_course`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `e_course`.`tutorview` AS select `e_course`.`user`.`id` AS `user_id`,`e_course`.`user`.`name` AS `name`,`e_course`.`user`.`login` AS `login`,`e_course`.`user`.`password` AS `password`,`e_course`.`user`.`email` AS `email`,`e_course`.`course`.`id` AS `course_id`,`e_course`.`course`.`title` AS `title`,`e_course`.`course`.`description` AS `description`,`e_course`.`course`.`start_date` AS `start_date`,`e_course`.`course`.`end_date` AS `end_date`,`e_course`.`course`.`status` AS `status`,`e_course`.`course`.`tutor_id` AS `tutor_id` from (`e_course`.`user` left join `e_course`.`course` on((`e_course`.`user`.`id` = `e_course`.`course`.`tutor_id`)));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
