DROP TABLE CATEGORIES;
DROP TABLE ENCHERES;
DROP TABLE RETRAITS;
DROP TABLE UTILISATEURS;
DROP TABLE ARTICLES_VENDUS;

CREATE TABLE CATEGORIES (
                            no_categorie   INTEGER primary key generated always as identity NOT NULL,
                            libelle        VARCHAR(30) NOT NULL
);

CREATE TABLE ENCHERES (
                          no_utilisateur   INTEGER NOT NULL,
                          no_article       INTEGER NOT NULL,
                          date_enchere     timestamp  NOT NULL,
                          montant_enchere  INTEGER NOT NULL,
                          PRIMARY KEY (no_utilisateur, no_article)
);

CREATE TABLE RETRAITS (
                          no_article         INTEGER primary key generated always as identity,
                          rue              VARCHAR(30) NOT NULL,
                          code_postal      VARCHAR(15) NOT NULL,
                          ville            VARCHAR(30) NOT NULL,
                          effectue		Boolean DEFAULT FALSE
);


CREATE TABLE UTILISATEURS (
                              no_utilisateur   INTEGER  primary key generated always as identity,
                              pseudo           VARCHAR(30) NOT NULL,
                              nom              VARCHAR(30) NOT NULL,
                              prenom           VARCHAR(30) NOT NULL,
                              email            VARCHAR(20) NOT NULL,
                              telephone        VARCHAR(15),
                              rue              VARCHAR(30) NOT NULL,
                              code_postal      VARCHAR(10) NOT NULL,
                              ville            VARCHAR(30) NOT NULL,
                              mot_de_passe     VARCHAR(30) NOT NULL,
                              credit           INTEGER NOT NULL,
                              administrateur   bit NOT NULL
);



CREATE TABLE ARTICLES_VENDUS (
                                 no_article                    INTEGER primary key generated always as identity,
                                 nom_article                   VARCHAR(30) NOT NULL,
                                 description                   VARCHAR(300) NOT NULL,
                                 date_debut_encheres           DATE NOT NULL,
                                 date_fin_encheres             DATE NOT NULL,
                                 prix_initial                  INTEGER,
                                 prix_vente                    INTEGER,
                                 no_utilisateur                INTEGER NOT NULL,
                                 no_categorie                  INTEGER NOT NULL
);

ALTER TABLE utilisateurs add constraint UQ_utilisateurs_email UNIQUE (email)