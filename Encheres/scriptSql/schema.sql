drop table if exists encheres;
drop table if exists articles_vendus;
drop table if exists articles;
drop table if exists categories;
drop table if exists retraits;
drop table if exists utilisateurs;

CREATE TABLE categories
(
    no_categorie INTEGER primary key generated always as identity NOT NULL,
    libelle      VARCHAR(30)                                      NOT NULL
);

CREATE TABLE encheres
(
    no_utilisateur  INTEGER   NOT NULL,
    no_article      INTEGER   NOT NULL,
    date_enchere    timestamp NOT NULL,
    montant_enchere INTEGER   NOT NULL,
    PRIMARY KEY (no_utilisateur, no_article)
);

CREATE TABLE retraits
(
    no_article  INTEGER primary key generated always as identity,
    rue         VARCHAR(30) NOT NULL,
    code_postal VARCHAR(15) NOT NULL,
    ville       VARCHAR(30) NOT NULL
);


CREATE TABLE utilisateurs
(
    no_utilisateur INTEGER primary key generated always as identity,
    pseudo         VARCHAR(50) NOT NULL,
    nom            VARCHAR(50) NOT NULL,
    prenom         VARCHAR(50) NOT NULL,
    email          VARCHAR(50) NOT NULL,
    telephone      VARCHAR(20),
    rue            VARCHAR(50) NOT NULL,
    code_postal    VARCHAR(20) NOT NULL,
    ville          VARCHAR(50) NOT NULL,
    mot_de_passe   VARCHAR(100) NOT NULL,
    credit         INTEGER     NOT NULL default 0,
    administrateur boolean     NOT NULL default false
);



CREATE TABLE articles
(
    no_article          INTEGER primary key generated always as identity,
    nom_article         VARCHAR(50)  NOT NULL,
    description         VARCHAR(300) NOT NULL,
    date_debut_encheres DATE         NOT NULL,
    date_fin_encheres   DATE         NOT NULL,
    prix_initial        INTEGER,
    prix_vente          INTEGER,
    no_utilisateur      INTEGER      NOT NULL,
    no_categorie        INTEGER      NOT NULL,
    retrait_effectue    boolean default false
);

ALTER TABLE utilisateurs
    add constraint UQ_utilisateurs_email UNIQUE (email);


ALTER TABLE utilisateurs
    add constraint UQ_utilisateurs_pseudo UNIQUE (pseudo);