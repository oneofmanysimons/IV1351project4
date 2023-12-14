DROP TABLE IF EXISTS "lesson";

INSERT INTO student (student_id,name,email,phone,personal_number)
VALUES
  (1,'Price Harrington','in.scelerisque@icloud.net',468638365,199904908157),
  (2,'Samson Acevedo','ipsum.porta@protonmail.org',467145990,199907984523),
  (3,'Sierra Petty','cursus.nunc@protonmail.couk',464991980,199909227097),
  (4,'Meghan Steele','sed@aol.net',463948470,199909060102),
  (5,'Cara Bates','donec@protonmail.couk',467979217,199904601331),
  (6,'Dane Waller','pellentesque.sed@google.edu',464148650,199911520901),
  (7,'Levi Mccormick','tristique@outlook.edu',460444183,199901784508),
  (8,'Venus Morse','vivamus.nisi@hotmail.com',464851689,199907532106),
  (9,'Jena Callahan','rhoncus.donec@yahoo.couk',464077897,199904436331),
  (10,'Stephen Kline','pellentesque.habitant.morbi@icloud.com',463399503,199904725814),
  (11,'Geoffrey Burris','commodo.ipsum.suspendisse@aol.edu',467971205,199906051942),
  (12,'Desiree Blankenship','a.feugiat@aol.couk',466400761,199902225833),
  (13,'Thor Brennan','amet.lorem@icloud.edu',461602728,199902214330),
  (14,'Jaquelyn Wagner','et@aol.com',469425124,199901591679),
  (15,'Ella Woodard','odio@google.couk',460622408,199905739396),
  (16,'Liberty Mcintyre','augue.ac@hotmail.org',468098832,199911460268),
  (17,'Connor Reid','nunc.lectus@protonmail.net',466781431,199905017542),
  (18,'Chadwick Gray','commodo.at@protonmail.ca',468817362,199902131778),
  (19,'Idona Mcgowan','iaculis.nec.eleifend@google.couk',460073603,199908856436),
  (20,'Orson Fowler','duis.cursus.diam@yahoo.couk',466134255,199908957763),
  (21,'Vincent Simon','non.dui@outlook.ca',461017440,199909665133),
  (22,'Thaddeus Hodges','phasellus.dapibus@google.edu',465315924,199907091092),
  (23,'Oleg Gates','at.arcu@icloud.com',468524293,199906169953),
  (24,'Chaim Buchanan','et.eros@outlook.couk',465223514,199907456024),
  (25,'Brody Sharp','arcu.vestibulum@outlook.ca',460250224,199911154196),
  (26,'Alfreda Larsen','venenatis.lacus@icloud.com',461094297,199903551165),
  (27,'Gwendolyn Knapp','magna.a.neque@protonmail.ca',463206085,199911655561),
  (28,'Amanda Camacho','metus.in@outlook.edu',463836397,199911536781),
  (29,'Demetrius Bishop','sagittis.nullam.vitae@google.net',468208500,199909572117),
  (30,'Silas Bradley','nisi@protonmail.couk',463685325,199904925410);

INSERT INTO sibling (student_id,sibling_id)
VALUES
  (4,23),
  (24,29),
  (16,10),
  (17,8),
  (13,30),
  (8,1),
  (15,1),
  (7,24),
  (25,7),
  (29,2);
