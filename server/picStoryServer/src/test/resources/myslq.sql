SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `picStorys` DEFAULT CHARACTER SET big5 ;
USE `picStorys` ;

-- -----------------------------------------------------
-- Table `picStorys`.`LBS`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`LBS` (
  `idLBS` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `Longitude` DECIMAL(20) NULL COMMENT '经度' ,
  `Latitude` DECIMAL(20) NULL COMMENT '纬度' ,
  PRIMARY KEY (`idLBS`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`USER`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`USER` (
  `idUSER` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `LBS_idLBS` BIGINT(20) NOT NULL ,
  `UserCode` VARCHAR(45) NULL ,
  `UserName` VARCHAR(45) NULL ,
  `PSWORD` VARCHAR(45) NULL ,
  PRIMARY KEY (`idUSER`) ,
  INDEX `fk_USER_LBS1_idx` (`LBS_idLBS` ASC) ,
  CONSTRAINT `fk_USER_LBS1`
    FOREIGN KEY (`LBS_idLBS` )
    REFERENCES `picStorys`.`LBS` (`idLBS` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`FOLLOWERS`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`FOLLOWERS` (
  `idFOLLOWERS` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `USER_idUSER` BIGINT(20) NOT NULL ,
  `FollowerId` BIGINT(20) NULL COMMENT '粉丝' ,
  `Bidirectional` TINYINT(1) NULL COMMENT '是否双向关注' ,
  PRIMARY KEY (`idFOLLOWERS`) ,
  INDEX `fk_FOLLOWERS_USER_idx` (`USER_idUSER` ASC) ,
  CONSTRAINT `fk_FOLLOWERS_USER`
    FOREIGN KEY (`USER_idUSER` )
    REFERENCES `picStorys`.`USER` (`idUSER` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`STORY`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`STORY` (
  `idSTORY` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `StoryCode` VARCHAR(45) NULL ,
  `StoryName` VARCHAR(45) NULL ,
  `StoryDescribe` VARCHAR(45) NULL ,
  `USER_idUSER` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`idSTORY`) ,
  INDEX `fk_STORY_USER1_idx` (`USER_idUSER` ASC) ,
  CONSTRAINT `fk_STORY_USER1`
    FOREIGN KEY (`USER_idUSER` )
    REFERENCES `picStorys`.`USER` (`idUSER` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`PAGE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`PAGE` (
  `idPAGE` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `STORY_idSTORY` BIGINT(20) NOT NULL COMMENT '故事的id' ,
  `Media_Url` VARCHAR(45) NULL COMMENT '多媒体文件地址' ,
  `Media_Type` INT NULL COMMENT '多媒体文件类型：图片，6秒视频，声音' ,
  `STORY_pageIndex` INT NOT NULL DEFAULT 0 COMMENT '在story中的页号封面index=0\n' ,
  PRIMARY KEY (`idPAGE`) ,
  INDEX `fk_PAGE_STORY1_idx` (`STORY_idSTORY` ASC) ,
  CONSTRAINT `fk_PAGE_STORY1`
    FOREIGN KEY (`STORY_idSTORY` )
    REFERENCES `picStorys`.`STORY` (`idSTORY` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`FEEDS`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`FEEDS` (
  `idFEEDS` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `UKey` VARCHAR(60) NULL COMMENT 'feed 唯一码 追踪信息' ,
  `USER_idUSER` BIGINT(20) NOT NULL ,
  `TimeLine` TIMESTAMP NULL COMMENT '时间戳' ,
  `PAGE_idPAGE` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`idFEEDS`, `PAGE_idPAGE`) ,
  INDEX `fk_FEEDS_USER1_idx` (`USER_idUSER` ASC) ,
  INDEX `fk_FEEDS_PAGE1_idx` (`PAGE_idPAGE` ASC) ,
  UNIQUE INDEX `uKey_UNIQUE` (`UKey` ASC) ,
  CONSTRAINT `fk_FEEDS_USER1`
    FOREIGN KEY (`USER_idUSER` )
    REFERENCES `picStorys`.`USER` (`idUSER` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FEEDS_PAGE1`
    FOREIGN KEY (`PAGE_idPAGE` )
    REFERENCES `picStorys`.`PAGE` (`idPAGE` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`COMMEND`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`COMMEND` (
  `idCOMMEND` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `PAGE_idPAGE` BIGINT(20) NOT NULL ,
  `CommendCode` VARCHAR(45) NULL ,
  `CommendText` LONGTEXT NULL ,
  PRIMARY KEY (`idCOMMEND`) ,
  INDEX `fk_COMMEND_PAGE1_idx` (`PAGE_idPAGE` ASC) ,
  CONSTRAINT `fk_COMMEND_PAGE1`
    FOREIGN KEY (`PAGE_idPAGE` )
    REFERENCES `picStorys`.`PAGE` (`idPAGE` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`STORYFOLLOWERS`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`STORYFOLLOWERS` (
  `idSTORYFLLOWERS` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `STORY_idSTORY` BIGINT(20) NOT NULL ,
  `FollowerId` BIGINT(20) NULL ,
  `FollowerTime` DATETIME NULL ,
  PRIMARY KEY (`idSTORYFLLOWERS`) ,
  INDEX `fk_STORYFLLOWERS_STORY1_idx` (`STORY_idSTORY` ASC) ,
  CONSTRAINT `fk_STORYFLLOWERS_STORY1`
    FOREIGN KEY (`STORY_idSTORY` )
    REFERENCES `picStorys`.`STORY` (`idSTORY` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picStorys`.`ATTENTIONS`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `picStorys`.`ATTENTIONS` (
  `idATTENTIONS` INT NOT NULL AUTO_INCREMENT ,
  `USER_idUSER` BIGINT(20) NOT NULL ,
  `AttentionId` BIGINT(20) NULL ,
  `Bidirectional` TINYINT(1) NULL ,
  PRIMARY KEY (`idATTENTIONS`) ,
  INDEX `fk_ATTENTIONS_USER1_idx` (`USER_idUSER` ASC) ,
  CONSTRAINT `fk_ATTENTIONS_USER1`
    FOREIGN KEY (`USER_idUSER` )
    REFERENCES `picStorys`.`USER` (`idUSER` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `picStorys` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
