CREATE DATABASE IF NOT EXISTS `post`;
GRANT ALL ON `post`.* TO 'm1s2TpProg'@'%';
USE 'post';
CREATE TABLE post (id BIGINT auto_increment PRIMARY KEY,
                   message TEXT(63205),
                   publication_date DATETIME,
                   is_public BOOLEAN,
                   user_id BIGINT);