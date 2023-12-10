DROP TABLE IF EXISTS "lesson";

INSERT INTO instrument (intrument_id,instrument_name)
VALUES
(1,'Giacomo French'),
  (2,'Edward Meadows'),
  (3,'Hyacinth Mcdaniel'),
  (4,'Aladdin Brewer'),
  (5,'Winifred Dorsey'),
  (6,'Charles Salinas'),
  (7,'Alexis Santana'),
  (8,'Rahim Yates'),
  (9,'Brett Jefferson'),
  (10,'Florence Dalton'),
  (11,'Claire Alston'),
  (12,'Quinn Floyd'),
  (13,'Zoe Melendez'),
  (14,'Zelda Gonzales'),
  (15,'Kirsten Barker'),
  (16,'Kasimir Duffy'),
  (17,'Finn Perkins'),
  (18,'Melinda House'),
  (19,'Latifah Mccarty'),
  (20,'Kelsie Gomez'),
  (21,'Lisandra Rowland'),
  (22,'Dominic Blake'),
  (23,'Macon Pitts'),
  (24,'Mary Gates'),
  (25,'Beverly Whitehead'),
  (26,'Fredericka Blevins'),
  (27,'Ignatius Rose'),
  (28,'Martin Williams'),
  (29,'Brent Jones'),
  (30,'Leslie Buck');

INSERT INTO instrument_rent (intrument_id,student_id,date_rent,date_return,date_must_return,numberrange)
VALUES
  (8,3,'Feb 7, 2023','Mar 7, 2023','Feb 7, 2023',276),
  (13,16,'Nov 26, 2023','dec 26, 2023','Nov 26, 2024',269),
  (13,9,'Sep 12, 2023','okt 12, 2023','Sep 12, 2024',297),
  (19,17,'Aug 1, 2023','Sep 1, 2023','Aug 1, 2024',295),
  (29,28,'Jun 13, 2023','Aug 13, 2023','Jun 13, 2024',251),
  (1,25,'Nov 2, 2023','Dec 2, 2023','Nov 2, 2024',286),
  (10,13,'Jun 30, 2023','Aug 30, 2023','Jun 30, 2024',256),
  (2,19,'Jan 7, 2023','Feb 7, 2023','Jan 7, 2024',281),
  (25,2,'Nov 13, 2023','Dec 13, 2023','Nov 13, 2024',233),
  (11,5,'Aug 11, 2023','Sep 11, 2023','Aug 11, 2024',271);
