

CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `create_date_time` datetime(6) NOT NULL,
                         `update_date_time` datetime(6) NOT NULL,
                         `detail` varchar(255) DEFAULT NULL,
                         `eupmyeondong` varchar(255) DEFAULT NULL,
                         `sido` varchar(255) NOT NULL,
                         `sigungu` varchar(255) DEFAULT NULL,
                         `name` varchar(20) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `phone` varchar(11) DEFAULT NULL,
                         `social_number` varchar(100) NOT NULL,
                         `user_id` varchar(50) NOT NULL,
                         `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                         `age` int(11) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UKgdiweoh9h0bbhh4os5to79ro4` (`user_id`),
                         UNIQUE KEY `UKq40fpvwpdo6wywq0xque8yggx` (`social_number`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--  user insert
INSERT INTO users (create_date_time,update_date_time,detail,eupmyeondong,sido,sigungu,name,password,phone,social_number,user_id,is_deleted,age)
VALUES
('2025-07-06 18:22:44.730','2025-07-08 01:01:02.151','null','null','1','22','홍길동1','$2a$10$1kApYy/uYVhfc1Ub53awxO0mKUYZnVJKf55GlxhYHF73/atZXVLoq','01012345678','iapvVvQge+r2VI9xI8/IhQ==','testAccount',0,5),
('2025-07-08 01:21:03.933','2025-07-08 02:12:03.606','테헤란로 123 삼성빌딩 7층','역삼동','서울특별시','강남구','홍길동2','$2a$10$mnyzi9oo.LVPiFVj8pM.SOa7lMVxTiJOF5OQfFOLtmfEIkACgx1PO','01012345678','nmtYNsQuX+/sRTUjsbjMCA==','testAccount2',1,15),
('2025-07-09 00:37:22.615','2025-07-09 00:37:22.615','테헤란로 123 삼성빌딩 7층','역삼동','서울특별시','강남구','홍길동','$2a$10$245b596fCBs7gwEpq21HwuO3iKX1K3o7LStqGzMqIX2dB/IUEwHwO','01012345678','q9e5gQWZfYwoJeXVDUYiZA==','testAccount3',0,15);
