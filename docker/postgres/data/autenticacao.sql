CREATE DATABASE basegeografica;

\c basegeografica;

CREATE TABLE "Regioes" (
    "IdRegiao" INT NOT NULL,
    "CodRegiao" VARCHAR(2) NOT NULL,
    "NomeRegiao" VARCHAR(20) NOT NULL,
    CONSTRAINT "PK_Regioes" PRIMARY KEY ("IdRegiao")
);

CREATE TABLE "Estados" (
    "SiglaEstado" CHAR(2) NOT NULL,
    "NomeEstado" VARCHAR(20) NOT NULL,
    "NomeCapital" VARCHAR(20) NOT NULL,
    "IdRegiao" INT NOT NULL,
    CONSTRAINT "PK_Estados" PRIMARY KEY ("SiglaEstado"),
    CONSTRAINT "FK_Estado_Regiao" FOREIGN KEY ("IdRegiao") REFERENCES "Regioes" ("IdRegiao")
);

INSERT INTO "Regioes"("IdRegiao", "CodRegiao", "NomeRegiao")
VALUES (1, 'CO', 'Centro-Oeste');

INSERT INTO "Regioes" ("IdRegiao", "CodRegiao", "NomeRegiao")
VALUES (2, 'NE', 'Nordeste');

INSERT INTO "Regioes" ("IdRegiao", "CodRegiao", "NomeRegiao")
VALUES (3, 'N', 'Norte');

INSERT INTO "Regioes" ("IdRegiao", "CodRegiao", "NomeRegiao")
VALUES (4, 'SE', 'Sudeste');

INSERT INTO "Regioes" ("IdRegiao", "CodRegiao", "NomeRegiao")
VALUES (5, 'S', 'Sul');
