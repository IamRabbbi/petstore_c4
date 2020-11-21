SET FOREIGN_KEY_CHECKS  = 0;

truncate table pet;
truncate table store;

INSERT into store(`id`, `name`, `location`, `contact_no`)
VALUES(21, 'super store', 'nassarawa', '09084883332'),
(22, 'Elite store', 'Kaduna', '09084883332'),
(23, 'Manhon store', 'Abuja', '09084883332'),
(24, 'yaba pets store', 'Lagos Yaba', '09084883332'),
(25, 'runaway pet store', 'Ajegunle', '09084883332');

INSERT INTO pet(`id`, `name`, `color`, `breed`, `age`, `pet_sex`, `store_id`)
VALUES (31, 'jill', 'blue', 'parrot', 6, 'MALE', 21),
(32, 'jack', 'black', 'dog', 2, 'MALE', 21),
(33, 'blue', 'white', 'cat', 3, 'FEMALE', 21),
(34, 'sally', 'brown', 'dog', 5, 'FEMALE', 21),
(35, 'mill', 'grey', 'rabbit', 14, 'MALE', 21),
(36, 'ross', 'black', 'dog', 6, 'MALE', 21),
(37, 'gem', 'yellow', 'parrot', 3, 'MALE', 21);

SET FOREIGN_KEY_CHECKS  = 1;

