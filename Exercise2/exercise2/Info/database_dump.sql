-- Database: javaee

-- DROP DATABASE IF EXISTS javaee;
-- CREATE DATABASE javaee;
USE javaee;

-- Table structure for table `Author`
DROP TABLE IF EXISTS `Author`;
CREATE TABLE `Author` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

-- Dumping data for table `Author`
INSERT INTO `Author` (`id`, `name`) VALUES
(1, 'John Doe'),
(2, 'Jane Smith'),
(3, 'Alice Johnson'),
(4, 'Robert Brown'),
(5, 'Emily Davis'),
(6, 'Alen Prasevic');

-- Table structure for table `Document`
DROP TABLE IF EXISTS `Document`;
CREATE TABLE `Document` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `abstract_text` TEXT,
  `publication_date` DATE,
  `storage_location` VARCHAR(255),
  PRIMARY KEY (`id`)
);

-- Dumping data for table `Document`
INSERT INTO `Document` (`id`, `title`, `abstract_text`, `publication_date`, `storage_location`) VALUES
(1, 'Document One', 'Abstract text of document one', '2023-01-15', '/docs/doc1.pdf'),
(2, 'Document Two', 'Abstract text of document two', '2022-05-30', '/docs/doc2.pdf'),
(3, 'Document Three', 'Abstract text of document three', '2023-03-20', '/docs/doc3.pdf'),
(4, 'Document Four', 'Abstract text of document four', '2021-07-10', '/docs/doc4.pdf'),
(5, 'Document Five', 'Abstract text of document five', '2020-11-25', '/docs/doc5.pdf'),
(6, 'Document Six', 'Abstract text of document six', '2023-04-05', '/docs/doc6.pdf'),
(7, 'Document Seven', 'Abstract text of document seven', '2019-08-19', '/docs/doc7.pdf'),
(8, 'Document Eight', 'Abstract text of document eight', '2023-02-14', '/docs/doc8.pdf'),
(9, 'Document Nine', 'Abstract text of document nine', '2022-12-01', '/docs/doc9.pdf'),
(10, 'Document Ten', 'Abstract text of document ten', '2023-05-22', '/docs/doc10.pdf');

-- Table structure for table `Document_Author`
DROP TABLE IF EXISTS `Document_Author`;
CREATE TABLE `Document_Author` (
  `document_id` INT NOT NULL,
  `author_id` INT NOT NULL,
  PRIMARY KEY (`document_id`, `author_id`),
  FOREIGN KEY (`document_id`) REFERENCES `Document` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`author_id`) REFERENCES `Author` (`id`) ON DELETE CASCADE
);

-- Dumping data for table `Document_Author`
INSERT INTO `Document_Author` (`document_id`, `author_id`) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 1),
(3, 3),
(4, 4),
(4, 5),
(5, 1),
(5, 5),
(5, 6),
(6, 2),
(6, 4),
(7, 3),
(7, 5),
(8, 1),
(8, 4),
(9, 2),
(9, 6),
(9, 3),
(10, 4),
(10, 5);

-- Commit the changes
COMMIT;

