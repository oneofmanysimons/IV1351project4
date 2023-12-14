SELECT EXTRACT('day' FROM a.date) AS day, 
CASE
WHEN b.genre = 0 THEN 'Rock'
WHEN b.genre = 1 THEN 'Blues'
WHEN b.genre = 2 THEN 'Country'
WHEN b.genre = 3 THEN 'Soul'
END AS Genre,  
CASE 
WHEN (a.student_max - b.student_num > 2) THEN 'many seats left' 
WHEN (a.student_max - b.student_num = 1) THEN '1 or 2 seats left' 
WHEN (a.student_max - b.student_num = 2) THEN '1 or 2 seats left' 
WHEN (a.student_max - b.student_num = 0) THEN 'many seats left' 
END AS No_of_free_seats
FROM lesson a
INNER JOIN ensemble b ON a.lesson_id = b.lesson_id
WHERE a.date > NOW() AND a.date < CAST((INTERVAL '7 days' + NOW()) AS date)
ORDER BY day;