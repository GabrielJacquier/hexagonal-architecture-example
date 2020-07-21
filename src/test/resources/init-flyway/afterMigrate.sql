INSERT INTO account
(id, name, email, "password", balance, created_on, version)
VALUES('A1E4CC41-947E-4CC8-A238-851EDD85B5EF', 'Gabriel', 'gabriel@email.com', '$2a$10$dciYkNJA.hSH3KLtOb1E9ONnXKdsKuIkXVrXQqTaVs1Ythtrbq17K', 0, '2020-06-21 21:29:01.197', 0)
ON CONFLICT DO NOTHING;
