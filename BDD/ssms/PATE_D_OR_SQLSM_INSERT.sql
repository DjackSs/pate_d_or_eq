
/*
*											DATABASE_BETA TEST DATAS
*/

-- Cards

INSERT INTO Cards (name)
VALUES
    ('Collection Hiver'),
    ('Saveurs de Printemps'),
    ('Délices d''Automne'),
    ('Spécialités d''été');


-- Restaurants
INSERT INTO Restaurants (name, address, postal_code, town, id_card)
VALUES
    ('Expérience Gastronomique', '123 Rue élégante', '44000', 'NANTES', 1),
    ('Fusion Gastronomique', '456 Avenue Culinaire', '44000', 'NANTES', 2),
    ('Bistrot Méditerranéen', '789 Chemin Savoureux', '44000', 'NANTES', 3),
    ('Cuisine Contemporaine', '101 Place Moderne', '44000', 'NANTES', 4);


-- Schedules
INSERT INTO Schedules (open_hour, close_hour, id_restaurant)
VALUES
    (CAST('2024-01-01 12:00:00' AS DATETIME), CAST('2024-01-01 14:30:00' AS DATETIME), 1),
	(CAST('2024-01-01 18:00:00' AS DATETIME), CAST('2024-01-01 22:30:00' AS DATETIME), 1),
    (CAST('2024-01-01 11:30:00' AS DATETIME), CAST('2024-01-01 15:00:00' AS DATETIME), 2),
    (CAST('2024-01-01 12:15:00' AS DATETIME), CAST('2024-01-01 15:00:00' AS DATETIME), 3),
    (CAST('2024-01-01 12:00:00' AS DATETIME), CAST('2024-01-01 14:30:00' AS DATETIME), 4);


-- Dishes
INSERT INTO Dishes (name, price, description, category, id_card)
VALUES
    ('Spaghetti Bolognese', 18, 'Pâtes italiennes classiques avec une sauce à la viande riche', 'dish', 1),
    ('Filet Mignon', 28, 'Steak de boeuf tendre, parfaitement cuit selon vos préférences', 'dish', 2),
    ('Martini Royale', 12, 'Un mélange sophistiqué de vodka et de vermouth', 'beverage', 3),
    ('Mousse au Chocolat', 10, 'Délice au chocolat fondant, une fin sucrée pour votre repas', 'desert', 4),
    ('Panna Cotta à la Vanille', 9, 'Dessert à la vanille crémeux avec compote de baies', 'desert', 1),
    ('Ratatouille Proven�ale', 22, 'Un mélange de légumes frais dans une sauce tomate savoureuse', 'dish', 2),
    ('Salade César', 14, 'Laitue romaine croquante, fromage parmesan et vinaigrette César', 'entry', 3),
    ('Gratin Dauphinois', 18, 'Gratin de pommes de terre crémeux avec fromage fondu, un classique français', 'dish', 4),
    ('Old Fashioned', 15, 'Un cocktail intemporel avec bourbon, sucre et bitter', 'beverage', 1),
    ('Limonade Pétillante', 8, 'Limonade rafraichissante avec une touche d''effervescence', 'beverage', 2),
    ('Salade de Chêvre', 12, 'Salade mixte garnie de fromage de chêvre chaud et vinaigrette balsamique', 'entry', 3),
    ('Confit de Canard', 24, 'Cuisse de canard cuite lentement avec une peau croustillante et une viande tendre', 'dish', 4),
    ('Kebab d''Agneau', 20, 'Brochettes d''agneau marinées grillées, un délice savoureux', 'dish', 1),
    ('Tarte aux Pommes', 10, 'Tarte aux pommes maison avec une croute feuilletée, servie chaude', 'desert', 2),
    ('Porc Rôti aux Carottes', 22, 'Porc rôti tendre avec des carottes glacées, un plat copieux', 'dish', 3);


-- Messages
INSERT INTO Messages (object, content, id_user)
VALUES
    ('Feedback Qualité', 'Merci pour l''expérience culinaire exceptionnelle! La nourriture était exceptionnelle.', 10),
    ('Demande de Menu', 'Pourriez-vous fournir des informations sur les options végétariennes dans le menu?', 10);


-- Tables
INSERT INTO Tables (number_place, state, id_restaurant)
VALUES
    (4, 'pres', 1),
    (2, null, 1),
    (6, 'pres', 2),
    (8, null, 3);


-- Orders
INSERT INTO Orders (state, id_table)
VALUES
    (null, 1),
    ('take', 1),
    ('read', 1),
    ('serv', 1),
    ('sold', 1);


-- Reservations
INSERT INTO Reservations (reservation_time, state, id_table, id_user)
VALUES
    (CAST('2024-01-05 18:30:00' AS DATETIME), 'hold', 1, 10),
    (CAST('2024-01-07 19:00:00' AS DATETIME), 'gran', 2, 10),
    (CAST('2024-01-10 20:00:00' AS DATETIME), 'deni', 3, 10)


-- Orders_dishes
INSERT INTO Orders_Dishes (id_order, id_dish)
VALUES
    (1, 3),
    (2, 5),
    (3, 6),
    (4, 9),
    (5, 12),
    (1, 1),
    (2, 4),
    (3, 8),
    (4, 11);
