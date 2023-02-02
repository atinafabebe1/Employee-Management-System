CREATE DATABASE EMS;
USE EMS;

CREATE TABLE users(
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
  phoneNumber VARCHAR(255),
  email VARCHAR(255),
  address VARCHAR(255),
  homeNo VARCHAR(255),
  role varchar(255),
  password VARCHAR(255) NOT NULL
);

CREATE TABLE `attendance`(
  `id` INT  primary key auto_increment,
  `employee_id` int,
  `date` DATE,
  `status` VARCHAR(255),
  FOREIGN KEY (`employee_id`) REFERENCES `employee`(`id`)
);

INSERT INTO `attendance` (`id`,`employee_id`, `date`, `status`) VALUES
( null,9, '2020-09-16 08:00:00', 'out'),
( null,9, '2020-09-16 12:00:00', 'in'),
(null,9, '2020-09-16 13:00:00', 'out'),
( null,9, '2020-09-16 17:00:00', 'in');


CREATE TABLE `allowances` (
  `id` int primary key auto_increment ,
  `allowance` text NOT NULL,
  `description` text NOT NULL
);

INSERT INTO `allowances` (`id`, `allowance`, `description`) VALUES
(null, 'Sample', 'Sample Allowance');

CREATE TABLE `deductions` (
  `id` int primary key auto_increment,
  `deduction` text NOT NULL,
  `description` text NOT NULL
);
INSERT INTO `deductions` (`id`, `deduction`, `description`) VALUES
(null, 'Cash Advance', 'Cash Advance'),
(null, 'Sample', 'Sample Deduction');

CREATE TABLE `department` (
  `id` int primary key auto_increment,
  `name` text NOT NULL
) ;
INSERT INTO `department` (`id`, `name`) VALUES
(null, 'IT Department'),
(null, 'HR Department'),
(null, 'Accounting and Finance Department');

CREATE TABLE `employee` (
  `id` int primary key,
  `employee_no` varchar(100) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `middlename` varchar(20) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `department_id` int NOT NULL,
  `position_id` int NOT NULL,
   foreign key (`department_id`) references `department`(`id`),
   foreign key (`position_id`) references `position`(`id`),
  `salary` double NOT NULL
);

INSERT INTO `employee` (`id`, `employee_no`, `firstname`, `middlename`, `lastname`, `department_id`, `position_id`, `salary`) VALUES
(9, '2020-9838', 'John', 'C', 'Smith', 1, 9, 30000);

CREATE TABLE `employee_allowances` (
  `id` int primary key auto_increment,
  `employee_id` int NOT NULL,
  `allowance_id` int NOT NULL,
  `type` tinyint NOT NULL COMMENT '1 = Monthly, 2= Semi-Montly, 3 = once',
  `amount` float NOT NULL,
  `effective_date` date NOT NULL,
  foreign key (`allowance_id`) references `allowances`(`id`),
  foreign key (`employee_id`) references `employee`(`id`),
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
);

INSERT INTO `employee_allowances` (`id`, `employee_id`, `allowance_id`, `type`, `amount`, `effective_date`) VALUES
(2, 9, 2, 1, 1000, '2020-09-29 11:20:04'),
(4, 9, 3, 2, 300, '2020-09-29 11:37:31'),
(6, 9, 2, 3, 1000, '2020-09-29 11:38:31');


CREATE TABLE `employee_deductions` (
  `id` int NOT NULL,
  `employee_id` int NOT NULL,
  `deduction_id` int NOT NULL,
  `type` tinyint NOT NULL COMMENT '1 = Monthly, 2= Semi-Montly, 3 = once',
  `amount` float NOT NULL,
  `effective_date` date NOT NULL,
  foreign key (`deduction_id`) references `deductions`(`id`),
  foreign key (`employee_id`) references `employee`(`id`),
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
);

INSERT INTO `employee_deductions` (`id`, `employee_id`, `deduction_id`, `type`, `amount`, `effective_date`) VALUES
(2, 9, 3, 2, 500, '2020-09-29 11:52:46'),
(3, 9, 1, 3, 1500, '2020-09-29 11:53:27');

CREATE TABLE `payroll` (
  `id` int primary key auto_increment,
  `ref_no` text NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date NOT NULL,
  `type` tinyint NOT NULL COMMENT '1 = monthly ,2 semi-monthly',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0 =New,1 = computed',
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
);

INSERT INTO `payroll` (`id`, `ref_no`, `date_from`, `date_to`, `type`, `status`, `date_created`) VALUES
(1, '2020-3543', '2020-09-16', '2020-09-30', 2, 1, '2020-09-29 15:04:13');

CREATE TABLE `payroll_items` (
  `id` int primary key,
  `payroll_id` int NOT NULL,
  `employee_id` int NOT NULL,
  `present` int NOT NULL,
  `absent` int NOT NULL,
  `late` text NOT NULL,
  `salary` double NOT NULL,
  `allowance_amount` double NOT NULL,
  `allowances` text NOT NULL,
  `deduction_amount` double NOT NULL,
  `deductions` text NOT NULL,
  `net` int NOT NULL,
   foreign key (`employee_id`) references `employee`(`id`),
   foreign key (`payroll_id`) references `payroll`(`id`),
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
);

INSERT INTO `payroll_items` (`id`, `payroll_id`, `employee_id`, `present`, `absent`, `late`, `salary`, `allowance_amount`, `allowances`, `deduction_amount`, `deductions`, `net`, `date_created`) VALUES
(10, 1, 9, 1, 10, '0', 30000, 1300, '[{\"aid\":\"3\",\"amount\":\"300\"},{\"aid\":\"1\",\"amount\":\"1000\"}]', 2000, '[{\"did\":\"3\",\"amount\":\"500\"},{\"did\":\"1\",\"amount\":\"1500\"}]', 664, '2020-09-29 18:46:59');

CREATE TABLE `position` (
  `id` int primary key auto_increment,
  `department_id` int,
  `name` text NOT NULL,
  foreign key (department_id) references `department`(`id`)
);

INSERT INTO `position` (`id`, `department_id`, `name`) VALUES
(null, 1, 'Programmer'),
(null, 2, 'HR Supervisor'),
(null, 3, 'Accounting Clerk');
