
drop user if exists 'petuser'@'localhost';
Create user 'petuser'@'localhost' identified by 'petuser123';
grant all privileges petstoredb.* to 'petuser'@'localhost';
flush privileges;

drop database if exists petstoredb;
create database petstoredb;
