-- UTILISATEURS
INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES
    ('ganu', 'alex', 'rovere', 'alex.rovere@gmail.com', '0601030215', '17 rue du castillet', '66000', 'perpignan', '{bcrypt}$2a$10$a7xnU5PaBWAlwsH9UkHgzO4ALSwHTfRcT.Q0RWjFz4KEd6BUYeknm', 5000, true);

INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES
    ('garance83', 'garance', 'david', 'garance.david@gmail.com', '0765982564',  '17 rue du marchal', '48000', 'montelimar', '{bcrypt}$2a$10$a7xnU5PaBWAlwsH9UkHgzO4ALSwHTfRcT.Q0RWjFz4KEd6BUYeknm', 200, true);

INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
VALUES
    ('justine83', 'justine', 'defois', 'justine.defois@gmail.com', '0798154675',  '17 rue du capitaine', '75000', 'paris', '{bcrypt}$2a$10$a7xnU5PaBWAlwsH9UkHgzO4ALSwHTfRcT.Q0RWjFz4KEd6BUYeknm', 300, true);


-- CATEGORIES
insert into categories (libelle) values ('Décoration');
insert into categories (libelle) values ('Vetêment');
insert into categories (libelle) values ('Jeux');
insert into categories (libelle) values ('Divers');



-- ARTICLES
insert into articles (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, retrait_effectue)
VALUES('pantalon', 'en tissu', '2025-01-15', '2025-03-20', 100, null, 1, 2, false),
      ('Monopoly', 'jeux famillial', '2025-02-15', '2025-04-04', 200, null, 2, 3, false),
      ('tableau', 'la joconde !', '2025-01-01', '2025-02-01', 10, null, 3, 1, false),
      ('télé', '4K 65p', '2024-12-15', '2024-12-25', 500, null, 1, 4, false);

-- RETRAITS
insert into retraits (no_article, rue, code_postal, ville) values (1, '12 rue du castillet', '64897', 'saleilles');
insert into retraits (no_article, rue, code_postal, ville) values (2, '12 rue du castillet', '12598', 'saleilles');
insert into retraits (no_article, rue, code_postal, ville) values (3, '12 rue du castillet', '65873', 'saleilles');
insert into retraits (no_article, rue, code_postal, ville) values (4, '12 rue du castillet', '96425', 'saleilles');

-- ENCHERES
insert into encheres (no_utilisateur, no_article, date_enchere, montant_enchere) values (1, 3, '2025-01-01', 111)