INSERT INTO students (name,name_reading,nickname,mail_address,city,age,gender,is_delete)
VALUES ('山田太郎', 'やまだたろう', 'たろう', 'taro@sample.jp', '愛知県名古屋市', 20, '男',false),
       ('田中翔太', 'たなかしょうた', 'しょう', 'shota@sample.jp', '岐阜県恵那市', 30, '男',false),
       ('山本美咲', 'やまもとみさき', 'みさみさ', 'misaki@sample.jp', '福岡県仙台市', 40, '女',false),
       ('佐藤健一', 'さとうけんいち', 'けんちゃん', 'kennichi@sample.jp', '石川県金沢市', 20, '男',true),
       ('鈴木真理', 'すずきまり', 'まりこ', 'mariko@sample.jp', '愛知県碧南市', 45, 'その他',true);

INSERT INTO course_id_name (course_id,course_name)
VALUES ('C1', 'Java'),
       ('C2', '英会話'),
       ('C3', 'デザイン'),
       ('C4', 'Phython'),
       ('C5', 'AWS'),
       ('C6', 'マーケティング');

INSERT INTO students_courses (student_id,course_id,course_name,started_date,finish_date,is_delete)
VALUES (1, 'C1', 'Java', '2024-08-23', '2025-02-23',false),
       (2, 'C1', 'Java', '2023-06-06', '2023-12-06',false),
       (4, 'C4', 'Phython', '2024-04-17', '2024-10-17',true),
       (3, 'C3', 'デザイン', '2022-06-20', '2022-12-20',false),
       (5, 'C5', 'AWS', '2024-08-25', '2025-02-25',true),
       (2, 'C3', 'デザイン', '2023-08-30', '2024-02-29',false),
       (3, 'C2', '英会話', '2023-05-15', '2023-11-15',false),
       (3, 'C6', 'マーケティング', '2024-06-11', '2024-12-11',false),
       (5, 'C2', '英会話', '2022-07-18', '2023-02-18',true);