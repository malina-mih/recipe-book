-- create tables

CREATE TABLE Recipes (
    recipe_id INT PRIMARY KEY,
    recipe_name VARCHAR(255),
    category VARCHAR(50)
	);

CREATE TABLE Ingredients (
	ingredient_id INT PRIMARY KEY,
    ingredient_name VARCHAR(255),
    );
    
CREATE TABLE Steps (
	step_id INT PRIMARY KEY,
    description TEXT
    );

CREATE TABLE RecipeIngredients (
	recipe_id INT,
    ingredient_id INT,
    PRIMARY KEY (recipe_id, ingredient_id),
    FOREIGN KEY (recipe_id) references Recipes(recipe_id),
    FOREIGN KEY (ingredient_id) references Ingredients(ingredient_id)
    );

CREATE TABLE RecipeSteps (
    recipe_id INT,
    step_id INT,
    PRIMARY KEY (recipe_id, step_id),
    FOREIGN KEY (recipe_id) references Recipes(recipe_id),
    FOREIGN KEY (step_id) references Steps(step_id)
);

-- insert data

INSERT IGNORE INTO Recipes(recipe_id, recipe_name, category)
VALUES
  (1, 'Spaghetti Bolognese', 'Savory'),
  (2, 'Chocolate Cake', 'Sweet'),
  (3, 'Scrambled Eggs', 'Breakfast'),
  (4, 'Greek Salad', 'Vegetarian'),
  (5, 'French Toast', 'Breakfast'),
  (6, 'Veggie Burgers', 'Vegan'),
  (7, 'Bread Recipe', 'Vegan'),
  (8, 'Roasted Salmon', 'Savory'),
  (9, 'Caramel Muffins', 'Sweet'),
  (10, 'Vegetarian Lasagna', 'Vegetarian'),
  (11, 'Fruit Smoothie', 'Vegan'),
  (12, 'Blueberry Pancakes', 'Breakfast');


INSERT IGNORE INTO Steps (step_id, description) 
VALUES
  (1, 'Cook ground beef in a pan.'),
  (2, 'Add tomato sauce, season with salt, pepper and fresh herbs and simmer.'),
  (3, 'Cook spaghetti according to package instructions and mix them into the sauce'),
  (4, 'Preheat oven to 180°C and prepare cake pans.'),
  (5, 'Cream together 200g of butter and 250g of sugar until fluffy.'),
  (6, 'Sift together 300g of flour, 50g of cocoa powder, 2 teaspoons of baking powder, and a pinch of salt.'),
  (7, 'Add 250ml of milk and 2 teaspoons of vanilla extract to the batter'),
  (8, 'Pour batter into cake pans and bake for 30 minutes.'),
  (9, 'Whisk 3 eggs in a bowl.'),
  (10, 'Melt butter in a pan, add eggs, and stir until softly scrambled.'),
  (11, 'Season with salt and pepper to taste.'),
  (12, 'Chop 1 cucumber, 2 tomatoes, and 1 cup of olives.'),
  (13, 'Toss vegetables with 50ml of olive oil and 100g of feta cheese.'),
  (14, 'Serve the salad chilled.'),
  (15, 'Whisk 2 eggs, 120ml of milk, and a pinch of cinnamon in a bowl.'),
  (16, 'Dip 4 slices of bread into the egg mixture.'),
  (17, 'Cook on a pan until golden brown on both sides.'),
  (18, 'Combine 1 can of black beans, 1/2 cup of breadcrumbs, 1 finely chopped onion in a blender. Season with salt, pepper, chilli, garlic powder'),
  (19, 'Form the mixture into patties and grill for 5-7 minutes per side.'),
  (20, 'Assemble the burgers with ketchup, mustard, tomatoes and lettuce'),
  (21, 'Mix 500g of flour and a pinch of salt. Add a package of dry yeast'),
  (22, 'Gradually add 300ml of warm water, knead the dough, and let it rise for 1 hour.'),
  (23, 'Bake in a preheated oven at 220°C for 25-30 minutes.'),
  (24, 'Preheat the oven to 200°C.'),
  (25, 'Place 4 salmon fillets on a baking sheet, drizzle with olive oil, and season with salt and pepper. Stuff lemon wedges between salmon fillets.'),
  (26, 'Roast for 12-15 minutes until salmon is cooked through.'),
  (27, 'Preheat the oven to 180°C and line a muffin tin with paper liners.'),
  (28, 'In a bowl, mix 200g of flour, 1 teaspoon of baking powder, and a pinch of salt.'),
  (29, 'Add 2 beaten eggs and 100g melted butter to the dry ingreidients. Spoon into muffin cups.'),
  (30, 'Bake the muffins for 10 minutes and dip tops in caramel sauce'),
  (31, 'Cook 9 lasagna sheets according to package instructions.'),
  (32, 'In a pan, sauté 1 cup of spinach, 1 cup of mushrooms, and 1 cup of ricotta cheese.'),
  (33, 'Layer noodles with vegetable mixture and marinara sauce. Repeat layers and bake for 40 minutes at 200 C.'),
  (34, 'Blend 1 banana, 1 cup of blueberries, and 1 cup of almond milk until smooth.'),
  (35, 'Add 1 cup of vanilla protein powder and blend again.'),
  (36, 'Pour into a glass and enjoy!'),
  (37, 'Mix 200g of flour, 2 teaspoons of baking powder, and a pinch of salt in a bowl.'),
  (38, 'In a separate bowl, whisk together 2 eggs and 250ml of milk. Add to dry ingredients.'),
  (39, 'Fold in 1 cup of fresh blueberries. Cook pancakes in a pan until golden brown.');
  
  


INSERT IGNORE INTO Ingredients(ingredient_id, ingredient_name)
VALUES
  (1, 'Ground Beef'),
  (2, 'Tomato Sauce'),
  (3, 'Salt'),
  (4, 'Pepper'),
  (5, 'Fresh Herbs'),
  (6, 'Spaghetti'),
  (7, 'Butter'),
  (8, 'Sugar'),
  (9, 'Flour'),
  (10, 'Cocoa Powder'),
  (11, 'Baking Powder'),
  (12, 'Milk'),
  (13, 'Vanilla Extract'),
  (14, 'Eggs'),
  (15, 'Bread Slices'),
  (16, 'Cinnamon'),
  (17, 'Black Beans'),
  (18, 'Breadcrumbs'),
  (19, 'Onion'), 
  (20, 'Peanuts'),
  (21, 'Chili Powder'),
  (22, 'Garlic Powder'),
  (23, 'Ketchup'),
  (24, 'Mustard'),
  (25, 'Tomatoes'),
  (26, 'Lettuce'),
  (27, 'Flour'),
  (28, 'Dry Yeast'),
  (29, 'Warm Water'),
  (30, 'Salmon Fillets'),
  (31, 'Olive Oil'),
  (32, 'Lemon Wedges'),
  (33, 'Baking Powder'),
  (34, 'Melted Butter'),
  (35, 'Caramel Sauce'),
  (36, 'Lasagna Sheets'),
  (37, 'Spinach'),
  (38, 'Mushrooms'),
  (39, 'Ricotta Cheese'),
  (40, 'Marinara Sauce'),
  (41, 'Banana'),
  (42, 'Blueberries'),
  (43, 'Almond Milk'),
  (44, 'Vanilla Protein Powder'),
  (45, 'Cucumber'),
  (46, 'Olives');

INSERT IGNORE INTO RecipeIngredients (recipe_id, ingredient_id)
VALUES
(1,1), (1,2), (1,3), (1,4), (1,5),(1,6),
(2,7), (2,8), (2, 9), (2, 10), (2, 11), (2, 12), (2,13),
(3,3), (3,4), (3,14),
(4,45), (4,46), (4,25),
(5,15), (5,16), (5,14), (5,12),
(6,3), (6,4), (6,21), (6,22), (6,23), (6,24),(6,26),(6,17), (6,18), (6,19),
(7,27),(7,28),(7,29),(7,3),
(8,30),(8,31),(8,32),(8,3),(8,4),
(9,33), (9, 9), (9,34),(9,35),(9,14),
(10, 36), (10, 37), (10, 38), (10,39), (10,40),
(11, 41), (11,42), (11,43), (11,44),
(12,9), (12,11),(12,12),(12,14),(12,3),(12,42);

SELECT * from RecipeIngredients order by recipe_id;

INSERT IGNORE INTO RecipeSteps (recipe_id, step_id)
VALUES
(1,1),(1,2),(1,3),
(2,4),(2,5),(2,6),(2,7),(2,8),
(3,9),(3,10),(3,11),
(4,12),(4,13),(4,14),
(5,15),(5,16),(5,17),
(6,18),(6,19),(6,20),
(7,21),(7,22),(7,23),
(8,25),(8,26),(8,27),
(9,28),(9,29),(9,30),
(10,31),(10,32),(10,33),
(11,34),(11,35),(11,36),
(12,37),(12,38),(12,39);

SELECT * from RecipeSteps order by recipe_id;

-- update tables, change content of tables, modify structure of tables, delete data

UPDATE Recipes
SET recipe_name = 'Spicy Spaghetti Bolognese'
WHERE recipe_id = 1;


ALTER TABLE Recipes AUTO_INCREMENT = 16;



UPDATE Steps
SET description = 'Saute a finely chopped red chilli pepper, add tomato sauce, season with salt, pepper and simmer.'
where step_id= 2;

INSERT INTO Ingredients(ingredient_id, ingredient_name)
values (47, 'Red chilli pepper');

INSERT INTO recipeingredients(recipe_id, ingredient_id)
values (1,47);

DELETE FROM Ingredients
WHERE ingredient_id=20;

UPDATE Ingredients
set ingredient_name = 'Baking Soda'
where ingredient_id = 33;



ALTER TABLE Ingredients
add column availability VARCHAR(20) DEFAULT 'Yes';

ALTER TABLE Ingredients
ADD CONSTRAINT CHK_Availability CHECK (availability IN ('Yes', 'No'));

ALTER TABLE Ingredients
ADD CONSTRAINT unique_ingredient_name UNIQUE (ingredient_name);


UPDATE Ingredients
SET availability = 'No'
WHERE ingredient_name IN ('Garlic Powder', 'Eggs', 'Tomato Sauce')
and ingredient_id>0;


-- views
-- updated view, added grouping
CREATE OR REPLACE VIEW RecipeCollection as
select r.recipe_id, r.recipe_name, r.category,
group_concat(distinct i.ingredient_name order by ri.ingredient_id) as ingredients,
group_concat(distinct s.description order by rs.step_id separator ' ') as step
from recipes r
join recipesteps rs on r.recipe_id = rs.recipe_id
join steps s on rs.step_id = s.step_id
join recipeingredients ri on r.recipe_id = ri.recipe_id
join ingredients i on ri.ingredient_id = i.ingredient_id
group by  r.recipe_id, r.recipe_name, r.category;



SELECT * FROM RecipeCollection
INTO OUTFILE 'C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\RecipeCollection1.csv'
FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\n';


CREATE OR REPLACE VIEW ShoppingListView as
select ingredient_id, ingredient_name
from ingredients
where availability = 'No';

SELECT * FROM ShoppingListView
INTO OUTFILE 'C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\ShoppingList.csv'
FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\n';

-- examples of usage
SELECT * FROM RecipeCollection WHERE category = 'Vegetarian';

SELECT DISTINCT r.recipe_id, r.recipe_name 
from Recipes r
join RecipeIngredients ri on ri.recipe_id = r.recipe_id
join ingredients i on ri.ingredient_id = i.ingredient_id
where i.ingredient_name in ("Milk");

SELECT DISTINCT r.recipe_id, r.recipe_name
FROM Recipes r
where r.recipe_id not in (
    SELECT DISTINCT r.recipe_id
	from Recipes r
	join RecipeIngredients ri on ri.recipe_id = r.recipe_id
	join ingredients i on ri.ingredient_id = i.ingredient_id
	where i.ingredient_name = "Eggs"
);



SELECT i.ingredient_name as Ingredients
from RecipeIngredients ri
join Ingredients i on ri.ingredient_id = i.ingredient_id
where ri.recipe_id IN (1,2);

CREATE  ROLE 'recipe_owner';
CREATE  ROLE 'recipe_editor';
CREATE  ROLE 'recipe_viewer';

CREATE USER 'owner_user'@'localhost' IDENTIFIED WITH 'caching_sha2_password' DEFAULT ROLE 'recipe_owner';
CREATE USER 'editor_user'@'localhost' IDENTIFIED WITH 'caching_sha2_password' DEFAULT ROLE 'recipe_editor';
CREATE USER 'viewer_user'@'localhost' IDENTIFIED WITH 'caching_sha2_password' DEFAULT ROLE 'recipe_viewer';

GRANT SELECT, INSERT, UPDATE, DELETE ON recipe_book.* TO 'recipe_owner';
GRANT SELECT, INSERT, UPDATE ON recipe_book.* TO 'recipe_editor';
GRANT SELECT ON recipe_book.* TO 'recipe_viewer';

GRANT 'recipe_owner' TO 'owner_user'@'localhost';
GRANT 'recipe_editor' TO 'editor_user'@'localhost';
GRANT 'recipe_viewer' TO 'viewer_user'@'localhost';


CREATE TABLE ShoppingList (
    ingredient_id INT PRIMARY KEY,
    ingredient_name VARCHAR(255)
);

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
alter table Users add column password VARCHAR(255);
insert into users (user_id, user_name, role, password)
values (1, 'malina','owner', '123');





