CREATE TABLE Tables (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	tableNumber INT
);

CREATE TABLE MenuItems (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	category VARCHAR(100),
	item VARCHAR(100),
    price FLOAT,
    currency VARCHAR(100)
);

CREATE TABLE Orders (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	tableId INT,
	FOREIGN KEY (tableId) REFERENCES Tables(id),
	date TIMESTAMP,
	status VARCHAR(100)
);

CREATE TABLE OrdersMenuItems (
	orderId INT,
	menuItemId INT,
	FOREIGN KEY (orderId) REFERENCES Orders(id),
	FOREIGN KEY (menuItemId) REFERENCES MenuItems(id),
	PRIMARY KEY (orderId, menuItemId)
);


SELECT * FROM Tables;
SELECT * FROM MenuItems;
SELECT * FROM Orders;


-- Insert fake data into Tables
INSERT INTO Tables (tableNumber) VALUES
(1), (2), (3), (4);

-- Insert fake data into MenuItems
INSERT INTO MenuItems (category, item, price, currency) VALUES
('Appetizer', 'Spring Rolls', 5.99, 'USD'),
('Main Course', 'Chicken Alfredo', 12.99, 'USD'),
('Dessert', 'Chocolate Cake', 7.99, 'USD'),
('Appetizer', 'Caesar Salad', 8.99, 'USD'),
('Main Course', 'Grilled Salmon', 15.99, 'USD'),
('Dessert', 'Tiramisu', 9.99, 'USD'),
('Appetizer', 'Mozzarella Sticks', 6.99, 'USD'),
('Main Course', 'Beef Burger', 10.99, 'USD'),
('Dessert', 'Cheesecake', 8.99, 'USD'),
('Appetizer', 'Shrimp Cocktail', 9.99, 'USD');

-- Insert fake data into Orders
INSERT INTO Orders (tableId, date, status) VALUES
(1, '2024-01-29 12:00:00', 'PLACED'),
(2, '2024-01-29 12:15:00', 'PREPARING'),
(3, '2024-01-29 12:30:00', 'SERVED'),
(4, '2024-01-29 12:45:00', 'PREPARING'),
(1, '2024-01-29 13:00:00', 'PLACED'),
(2, '2024-01-29 13:15:00', 'SERVED'),
(3, '2024-01-29 13:30:00', 'PREPARING'),
(4, '2024-01-29 13:45:00', 'PLACED'),
(1, '2024-01-29 14:00:00', 'SERVED'),
(2, '2024-01-29 14:15:00', 'PREPARING');

-- Insert fake data into OrdersMenuItems
INSERT INTO OrdersMenuItems (orderId, menuItemId) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5),
(3, 6), (3, 7),
(4, 8), (4, 9),
(5, 10),
(6, 1), (6, 5),
(7, 2), (7, 4),
(8, 3), (8, 6),
(9, 7), (9, 9),
(10, 8), (10, 10);