CREATE TABLE Equipes_Du_Club (
   Id_Equipes_Du_Club SERIAL PRIMARY KEY,
   Nom VARCHAR(50),
   TerrainEntrainement VARCHAR(50),
   Stade VARCHAR(50)
);

CREATE TABLE Competitions (
   NomCompetition VARCHAR(50) PRIMARY KEY,
   DernierResultat VARCHAR(50)
);

CREATE TABLE Evenement (
   Id_Evenement SERIAL PRIMARY KEY,
   date_debut TIMESTAMP,
   date_fin TIMESTAMP,
   type VARCHAR(50)
);

CREATE TABLE Trophees (
   NomTrophee VARCHAR(50) PRIMARY KEY,
   DeuxiemeDeLaCompetition VARCHAR(50),
   StadeDeLaFinale VARCHAR(50)
);

CREATE TABLE Contrat_ (
   Id_Contrat SERIAL PRIMARY KEY,
   DateDebut DATE NOT NULL,
   DateFin DATE NOT NULL,
   PrixTransfert INT,
   Salaire_ INT,
   Bonus INT
);

CREATE TABLE Sante (
   Id_Sante SERIAL PRIMARY KEY,
   EtatDeForme VARCHAR(50),
   EtatDeSante VARCHAR(50)
);

CREATE TABLE Staff (
   id_staff SERIAL PRIMARY KEY,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   role VARCHAR(50)
);

CREATE TABLE blessure (
   id_blessure SERIAL PRIMARY KEY,
   date_debut DATE,
   date_fin DATE,
   type VARCHAR(50)
);

CREATE TABLE Joueurs (
   Id_Joueurs SERIAL PRIMARY KEY,
   Nom VARCHAR(50) NOT NULL,
   Prenom VARCHAR(50) NOT NULL,
   Numero VARCHAR(50) NOT NULL,
   DateNaissance DATE NOT NULL,
   Nationalite VARCHAR(50),
   Poste VARCHAR(50) NOT NULL,
   AutresPostes VARCHAR(50),
   Pied VARCHAR(50) NOT NULL,
   Taille INT NOT NULL,
   Poids INT NOT NULL,
   Photo VARCHAR(50),
   Qualites VARCHAR(50),
   Id_Sante INT NOT NULL,
   Id_Contrat INT NOT NULL,
   Id_Equipes_Du_Club INT,
   FOREIGN KEY(Id_Sante) REFERENCES Sante(Id_Sante),
   FOREIGN KEY(Id_Contrat) REFERENCES Contrat_(Id_Contrat),
   FOREIGN KEY(Id_Equipes_Du_Club) REFERENCES Equipes_Du_Club(Id_Equipes_Du_Club)
);

CREATE TABLE Matchs (
   Id_Matchs SERIAL PRIMARY KEY,
   Adversaire VARCHAR(50),
   Stade VARCHAR(50),
   Score_adverse INT NOT NULL,
   HommeDuMatch VARCHAR(50),
   NomCompetition VARCHAR(50) NOT NULL,
   FOREIGN KEY(NomCompetition) REFERENCES Competitions(NomCompetition)
);

CREATE TABLE A_joue_le_match (
   Id_Joueurs INT,
   Id_Matchs INT,
   Buts INT,
   PassesDecisives INT,
   CartonsJaunes INT,
   CartonsRouges INT,
   NoteDuMatch INT,
   MinutesJouees INT,
   PRIMARY KEY(Id_Joueurs, Id_Matchs),
   FOREIGN KEY(Id_Joueurs) REFERENCES Joueurs(Id_Joueurs),
   FOREIGN KEY(Id_Matchs) REFERENCES Matchs(Id_Matchs)
);

CREATE TABLE Historique_Des_Matchs (
   Id_Matchs INT,
   Id_Equipes_Du_Club INT,
   PRIMARY KEY(Id_Matchs, Id_Equipes_Du_Club),
   FOREIGN KEY(Id_Matchs) REFERENCES Matchs(Id_Matchs),
   FOREIGN KEY(Id_Equipes_Du_Club) REFERENCES Equipes_Du_Club(Id_Equipes_Du_Club)
);

CREATE TABLE Participe_a (
   Id_Equipes_Du_Club INT,
   NomCompetition VARCHAR(50),
   Classement INT,
   PRIMARY KEY(Id_Equipes_Du_Club, NomCompetition),
   FOREIGN KEY(Id_Equipes_Du_Club) REFERENCES Equipes_Du_Club(Id_Equipes_Du_Club),
   FOREIGN KEY(NomCompetition) REFERENCES Competitions(NomCompetition)
);

CREATE TABLE A_Participe_A_Entrainement (
   Id_Joueurs INT,
   Id_Evenement INT,
   PRIMARY KEY(Id_Joueurs, Id_Evenement),
   FOREIGN KEY(Id_Joueurs) REFERENCES Joueurs(Id_Joueurs),
   FOREIGN KEY(Id_Evenement) REFERENCES Evenement(Id_Evenement)
);

CREATE TABLE A_Remporte_Ce_Trophee (
   Id_Equipes_Du_Club INT,
   NomTrophee VARCHAR(50),
   Annee VARCHAR(50),
   PRIMARY KEY(Id_Equipes_Du_Club, NomTrophee),
   FOREIGN KEY(Id_Equipes_Du_Club) REFERENCES Equipes_Du_Club(Id_Equipes_Du_Club),
   FOREIGN KEY(NomTrophee) REFERENCES Trophees(NomTrophee)
);

CREATE TABLE a_subi (
   Id_Joueurs INT,
   id_blessure INT,
   PRIMARY KEY(Id_Joueurs, id_blessure),
   FOREIGN KEY(Id_Joueurs) REFERENCES Joueurs(Id_Joueurs),
   FOREIGN KEY(id_blessure) REFERENCES blessure(id_blessure)
);

CREATE SEQUENCE auto_increment
    AS bigint
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    CACHE 1;

