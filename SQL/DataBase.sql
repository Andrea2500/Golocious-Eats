CREATE TABLE Indirizzo (
    ClienteID INTEGER NOT NULL,
    Paese VARCHAR (32) NOT NULL,
    Provincia VARCHAR (32) NOT NULL,
    Citta VARCHAR (32) NOT NULL,
    CAP VARCHAR (10) NOT NULL,
    Indirizzo VARCHAR (100) NOT NULL,
    Eliminato BOOLEAN DEFAULT FALSE,
    IndirizzoID SERIAL PRIMARY KEY NOT NULL
);


CREATE TABLE Cliente (
    Nome VARCHAR (32) NOT NULL,
    Cognome VARCHAR (32) NOT NULL,
    Email VARCHAR (320) NOT NULL UNIQUE,
    Password VARCHAR (320) NOT NULL,
    Telefono VARCHAR (20) NOT NULL UNIQUE,
    DataDiNascita DATE NOT NULL,
    IndirizzoAttivo INTEGER REFERENCES Indirizzo(IndirizzoID) ON UPDATE CASCADE ON DELETE SET NULL DEFAULT NULL,
    ClienteID SERIAL PRIMARY KEY NOT NULL,

    CONSTRAINT CK_email CHECK (Email ~ '^(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})$'),
    CONSTRAINT CK_telefono_cliente CHECK (Telefono ~ '^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]{7,14}$')
);


ALTER TABLE Indirizzo
ADD CONSTRAINT FK_ClienteID FOREIGN KEY (ClienteID) REFERENCES Cliente(ClienteID) ON UPDATE CASCADE ON DELETE CASCADE;


CREATE UNIQUE INDEX unique_email ON cliente (lower(email));

CREATE FUNCTION lowercase_email() RETURNS trigger AS $$
    BEGIN        
        NEW.email = LOWER(NEW.email);
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_lowercase_email BEFORE INSERT OR UPDATE ON cliente
    FOR EACH ROW EXECUTE PROCEDURE lowercase_email();


CREATE TABLE Rider (
    RiderID INTEGER PRIMARY KEY NOT NULL REFERENCES Cliente(ClienteID) ON UPDATE CASCADE ON DELETE CASCADE,
    Patente VARCHAR (16) NOT NULL UNIQUE,
    Veicolo CHAR (1) NOT NULL,

    CONSTRAINT CK_Patente CHECK (Patente ~ '^[A-Za-z0-9]{10,16}$'),
	CONSTRAINT CK_Veicolo CHECK (Veicolo IN ('m','a','b'))
);


CREATE UNIQUE INDEX unique_patente ON rider (upper(patente));

CREATE FUNCTION uppercase_patente() RETURNS trigger AS $$
    BEGIN        
        NEW.patente = upper(NEW.patente);
        RETURN NEW;
    END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_uppercase_patente BEFORE INSERT OR UPDATE ON rider
    FOR EACH ROW EXECUTE PROCEDURE uppercase_patente();


CREATE TABLE Ristorante (
    Nome VARCHAR (100) NOT NULL UNIQUE,
    Indirizzo VARCHAR (320) NOT NULL,
    Telefono VARCHAR (20) NOT NULL,
    DataDiApertura DATE NOT NULL,
    RistoranteID SERIAL PRIMARY KEY NOT NULL,

    CONSTRAINT CK_telefono_ristorante CHECK (Telefono ~ '^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]{7,14}$')
);


CREATE TABLE Gestore (
    ClienteID INTEGER NOT NULL REFERENCES Cliente(ClienteID) ON UPDATE CASCADE ON DELETE CASCADE,
    RistoranteID INTEGER NOT NULL REFERENCES Ristorante(RistoranteID) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT UQ_GESTORE UNIQUE (ClienteID, RistoranteID)
);


CREATE TABLE Carrello (
    ClienteID INTEGER NOT NULL REFERENCES Cliente(ClienteID) ON UPDATE CASCADE ON DELETE CASCADE,
    RistoranteID INTEGER NOT NULL REFERENCES Ristorante(RistoranteID) ON UPDATE CASCADE ON DELETE CASCADE,
    Ordinato BOOLEAN DEFAULT false NOT NULL,
    CarrelloID SERIAL PRIMARY KEY NOT NULL
);


CREATE TABLE Ordine (
    OrdineID INTEGER PRIMARY KEY NOT NULL REFERENCES Carrello(CarrelloID) ON UPDATE CASCADE,
    RiderID INTEGER NOT NULL REFERENCES Rider(RiderID) ON UPDATE CASCADE,
    RistoranteID INTEGER NOT NULL REFERENCES Ristorante(RistoranteID) ON UPDATE CASCADE,
    IndirizzoID INTEGER NOT NULL REFERENCES Indirizzo(IndirizzoID) ON UPDATE CASCADE,
    Totale MONEY NOT NULL,
    DataOrdine TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Consegnato BOOLEAN NOT NULL DEFAULT false
);


CREATE TABLE Articolo (
    Nome VARCHAR (150) NOT NULL UNIQUE,
    Prezzo MONEY NOT NULL,
    Categoria CHAR(1) NOT NULL,
    Ingredienti VARCHAR (300),
    ArticoloID SERIAL PRIMARY KEY NOT NULL,

    CONSTRAINT CK_CATEGORIA CHECK (Categoria IN ('a', 'b', 'p', 't', 'd', 'v', 'w'))
);


CREATE TABLE Menu (
    ArticoloID INTEGER NOT NULL REFERENCES Articolo(ArticoloID) ON UPDATE CASCADE ON DELETE CASCADE,
    RistoranteID INTEGER NOT NULL REFERENCES Ristorante(RistoranteID) ON UPDATE CASCADE ON DELETE CASCADE,
    Disponibile BOOLEAN DEFAULT true NOT NULL,
    
    CONSTRAINT UQ_MENU UNIQUE (ArticoloID, RistoranteID)
);


CREATE TABLE ArticoloInCarrello (
    CarrelloID INTEGER NOT NULL REFERENCES Carrello(CarrelloID) ON UPDATE CASCADE ON DELETE CASCADE,
    ArticoloID INTEGER NOT NULL REFERENCES Articolo(ArticoloID) ON UPDATE CASCADE ON DELETE SET NULL
);

-------------------------------------------VINCOLI-------------------------------------------

CREATE OR REPLACE FUNCTION ordini_attivi_rider(RiderID INTEGER) RETURNS INTEGER AS $$
	BEGIN
		RETURN (SELECT COUNT(*)
				FROM Ordine o
				WHERE o.Consegnato=FALSE AND o.RiderID=$1);
	END;
$$ LANGUAGE plpgsql;

ALTER TABLE Ordine
ADD CONSTRAINT CK_MAX_ORDINI CHECK (3>ordini_attivi_rider(RiderID));


CREATE FUNCTION max_un_carrello_attivo() RETURNS TRIGGER AS $$
    BEGIN
        IF (1>(SELECT COUNT(*)
			   FROM Carrello ca
			   WHERE Ordinato=false AND ClienteID = NEW.ClienteID))
		THEN
        ELSE RAISE EXCEPTION 'Numero massimo di Carrelli attivi raggiunto per questo Cliente';
        END IF;
        RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_max_un_carrello_attivo_insert BEFORE INSERT ON Carrello
FOR EACH ROW EXECUTE PROCEDURE max_un_carrello_attivo();

CREATE TRIGGER trigger_max_un_carrello_attivo_update AFTER UPDATE ON Carrello
FOR EACH ROW EXECUTE PROCEDURE max_un_carrello_attivo();


ALTER TABLE Cliente
ADD CONSTRAINT CK_ETA_CLIENTE CHECK (14 <= date_part('year',age(DataDiNascita)));


CREATE FUNCTION eta_rider(RiderID INTEGER) RETURNS INTEGER AS $$
    BEGIN
        RETURN (SELECT date_part('year',age(cl.DataDiNascita))
                FROM Cliente cl
                WHERE cl.ClienteID=$1);
    END;
$$ LANGUAGE plpgsql;

ALTER TABLE Rider
ADD CONSTRAINT CK_ETA_RIDER CHECK (18<=eta_rider(RiderID));


CREATE FUNCTION eta_gestore(ClienteID INTEGER) RETURNS INTEGER AS $$
BEGIN
    RETURN (SELECT date_part('year',age(cl.DataDiNascita))
	    	FROM Cliente cl
		    WHERE cl.ClienteID=$1);
END;
$$ LANGUAGE plpgsql;

ALTER TABLE Gestore
ADD CONSTRAINT CK_ETA_GESTORE CHECK (18<=eta_gestore(ClienteID));


CREATE FUNCTION articolo_stesso_ristorante_carrello(CarrelloId INTEGER, ArticoloID INTEGER) RETURNS BOOLEAN AS $$
    BEGIN
        RETURN ((SELECT ca.RistoranteID
                 FROM Carrello ca
                 WHERE ca.CarrelloID=$1) IN (SELECT me.RistoranteID
                                             FROM Menu me
                                             WHERE me.ArticoloID = $2));
    END;
$$ LANGUAGE plpgsql;

ALTER TABLE ArticoloInCarrello
ADD CONSTRAINT CK_ARTICOLO_STESSO_RISTORANTE_CARRELLO CHECK (articolo_stesso_ristorante_carrello(CarrelloID, ArticoloID));


CREATE FUNCTION riderid_diverso_clienteid(RiderID INTEGER, OrdineID INTEGER) RETURNS BOOLEAN AS $$
    BEGIN
        RETURN ($1 NOT IN (SELECT ca.ClienteID
                           FROM Carrello ca
                           WHERE ca.CarrelloID=$2));
    END;
$$ LANGUAGE plpgsql;

ALTER TABLE Ordine
ADD CONSTRAINT CK_RIDERID_DIVERSO_CLIENTEID CHECK (riderid_diverso_clienteid(RiderID, OrdineID));


CREATE FUNCTION min_un_articolo_ordine(OrdineID INTEGER) RETURNS BOOLEAN AS $$
    BEGIN
        RETURN ($1 IN (SELECT ac.CarrelloID
                       FROM ArticoloInCarrello ac
                       WHERE ac.CarrelloID=$1));
    END;
$$ LANGUAGE plpgsql;

ALTER TABLE Ordine
ADD CONSTRAINT CK_MIN_UN_ARTICOLO_ORDINE CHECK (min_un_articolo_ordine(OrdineID));


CREATE FUNCTION rider_non_gestore(RiderID INTEGER) RETURNS BOOLEAN AS $$
    BEGIN
        RETURN NOT EXISTS (SELECT ClienteID
                           FROM Gestore
                           WHERE ClienteID=$1);
    END;
$$ LANGUAGE plpgsql;

ALTER TABLE Rider
ADD CONSTRAINT CK_RIDER_NON_GESTORE CHECK (rider_non_gestore(RiderID));


CREATE FUNCTION gestore_non_rider(ClienteID INTEGER) RETURNS BOOLEAN AS $$
    BEGIN
        RETURN NOT EXISTS (SELECT RiderID
                           FROM Rider
                           WHERE RiderID=$1);
    END;
$$ LANGUAGE plpgsql;

ALTER TABLE Gestore
ADD CONSTRAINT CK_GESTORE_NON_RIDER CHECK (gestore_non_rider(ClienteID));


CREATE FUNCTION assegnamento_rider(OrdineID INTEGER) RETURNS INTEGER AS $$
	BEGIN
        DROP TABLE IF EXISTS rider_disponibili;
		CREATE TEMP TABLE rider_disponibili AS
		SELECT RiderID
		FROM Rider
		EXCEPT (SELECT ClienteID
				FROM Carrello
				WHERE CarrelloID = $1
				UNION (SELECT RiderID
					   FROM Ordine o
					   GROUP BY RiderID
					   HAVING COUNT(o.OrdineID)>=3));
		RETURN (SELECT *
				FROM rider_disponibili OFFSET floor(random()*(SELECT COUNT(*)
															  FROM rider_disponibili))
				LIMIT 1);
	END
$$ LANGUAGE plpgsql;


CREATE FUNCTION ins_ordine() RETURNS TRIGGER AS $$
	BEGIN
		NEW.RiderID = assegnamento_rider(NEW.OrdineID);
		NEW.RistoranteID = (SELECT RistoranteID
							FROM Carrello
							WHERE CarrelloID = NEW.OrdineID);
		NEW.Totale = (SELECT SUM(ar.Prezzo)
					  FROM ArticoloInCarrello ac NATURAL JOIN Articolo ar
					  WHERE ac.CarrelloID = NEW.OrdineID);
		NEW.IndirizzoID = (SELECT cl.IndirizzoAttivo
						   FROM Carrello ca NATURAL JOIN Cliente cl
						   WHERE ca.CarrelloID = NEW.OrdineID);
		UPDATE Carrello
		SET Ordinato = true
		WHERE CarrelloID = NEW.OrdineID;
		RETURN NEW;
	END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_ins_ordine BEFORE INSERT ON Ordine
	FOR EACH ROW EXECUTE PROCEDURE ins_ordine();


CREATE FUNCTION indirizzo_attivo_del_cliente(IndirizzoAttivo INTEGER, ClienteID INTEGER) RETURNS BOOLEAN AS $$
	BEGIN
		IF (($1 IN (SELECT IndirizzoID
					FROM Indirizzo i
					WHERE i.ClienteID = $2)) OR ($1 IS null))
		THEN RETURN true;
		ELSE RETURN false;
		END IF;
	END
$$ LANGUAGE plpgsql;

ALTER TABLE Cliente
ADD CONSTRAINT ck_indirizzo_attivo_del_cliente CHECK (indirizzo_attivo_del_cliente(IndirizzoAttivo, ClienteID));

/*
CREATE FUNCTION elimina_cliente_rider() RETURNS TRIGGER AS $$
	BEGIN
		UPDATE Rider
		SET Eliminato = NEW.Eliminato
		WHERE RiderID = NEW.ClienteID;
		RETURN NEW;
	END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_elimina_cliente_rider AFTER UPDATE ON Cliente
	FOR EACH ROW EXECUTE PROCEDURE elimina_cliente_rider();


CREATE FUNCTION elimina_cliente_gestore() RETURNS TRIGGER AS $$
	BEGIN
		DELETE Rider
		SET Eliminato = NEW.Eliminato
		WHERE RiderID = NEW.ClienteID;
		RETURN NEW;
	END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_elimina_cliente_gestore AFTER UPDATE ON Cliente
	FOR EACH ROW EXECUTE PROCEDURE elimina_cliente_gestore();


CREATE FUNCTION elimina_unico_gestore_ristorante() RETURNS TRIGGER AS $$
	BEGIN
        IF NEW.Eliminato = true THEN
            IF NOT EXISTS (SELECT *
                           FROM Gestore as g1 NATURAL JOIN (SELECT COUNT(ClienteID), RistoranteID
                                                            FROM Gestore
                                                            GROUP BY RistoranteID
                                                            HAVING COUNT(ClienteID) = 1) as g2
                           WHERE g1.ClienteID = NEW.ClienteID)
            THEN RETURN NEW;
            ELSE RAISE EXCEPTION 'Utente singolo gestore di ristorante';
            END IF;
		ELSE RETURN OLD;
		END IF;
	END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_elimina_unico_gestore_ristorante BEFORE UPDATE ON Cliente
	FOR EACH ROW EXECUTE PROCEDURE elimina_unico_gestore_ristorante();
*/
-------------------------------------------POPOLAMENTO-------------------------------------------

INSERT INTO Ristorante VALUES
('Golocious Burger&Wine Napoli', 'Italia', 'NA', 'Napoli', '80127', 'Via Domenico Cimarosa, 26', '+39 081 587 2195', '04/05/2020'),
('Golocious Burger&Wine Roma', 'Italia', 'RM', 'Roma', '00151', 'Viale Isacco Newton, 68', '+39 06 653 0724', '13/10/2020'),
('Golocious Pizza&Cucina Napoli', 'Italia', 'NA', 'Napoli', '80127', 'Via Domenico Cimarosa, 144', '+39 081 556 8169', '01/12/2020'),
('Golocious Pizza in Teglia Napoli', 'Italia', 'NA', 'Napoli', '80127', 'Via Domenico Cimarosa, 144', '+39 081 556 8169', '01/12/2020'),
('Golocious Burger & Wine Dark Kitchen Milano', 'Italia', 'MI', 'Milano', '20124', 'Piazza Quattro Novembre, 3', '+39 02 2217 5500', '14/01/2021');

INSERT INTO Articolo VALUES
--BURGER&WINE--
('Frittatina alla Nerano', '3,5', 'a', 'Frittatina con besciamella, zucchine fritte e in crema, grana e pepe'),
('Chips Fresche Rigate Classiche', '4', 'a', null),
('Chips Fresche Rigate con Pulled Pork e Cheddar', '6', 'a', null),
('Pulled Pork Nuggets', '6', 'a', '3 pepite croccanti di Pulled Pork artigianale selezione Corbisiero impanate nei cornflakes e fritte, accompagnate da salsa bbq'),
('Chips Cacio, Pepe, Tartufo e Porcini', '6', 'a', 'Chips fresche rigate con fonduta cacio e pepe aromatizzata con tartufo e porcini'),
('Parmijana di Patate', '6', 'a', 'Parmigiana di patate con besciamella al pistacchio, mortadella, grana padano e provola'),
--PIZZA&CUCINA E PIZZA IN TEGLIA-- 
('Crocchettone Panko', '3,5', 'a', 'Crocché napoletano di patate fresche panato al pane panko, ripieno di provola e fritto'),
('Frittatina al Ragù', '3,5', 'a', 'Frittatina di bucatini napoletana con besciamella, ragù bolognese, piselli, provola dei monti e grana padano'),
('Frittatina al Pistacchio', '3,5', 'a', 'Frittatina di bucatini napoletana con besciamella, provola dei monti, grana padano e cuore ripieno di pesto di pistacchio e mortadella IGP bolognese'),
('Paccarotti Golocious', '4', 'a', '3 paccheroni fritti ripieni di ricotta, mortadella IGP bolognese, provola dei monti e pistacchio, ricoperti di besciamella al pistacchio'),
('Croquetas de Jamon', '5', 'a', '3 crocchette di besciamella con fiocco di prosciutto crudo, grana padano, pepe e provola, panate nel panko e fritte'),
--BURGER&WINE--
('Cisburger', '7', 'b', 'Hamburger di macina Golocious 200g e cheddar'),
('Golocheese', '9', 'b', 'Hamburger di macina Golocious 200g, cheddar, bacon croccante, insalata e pomodoro'),
('Crispy Golocious', '10', 'b', 'Hamburger di macina Golocious 200g, cheddar stagionato 24 mesi, bacon croccante e salsa crispy'),
('Carbonara Burger', '10', 'b', 'Hamburger di macina Golocious 200g, bacon croccante, carbocrema e provola'),
('Dabbol Cis', '10', 'b', 'Hamburger di macina Golocious 200g, cipolla caramellata, doppio cheddar stagionato 24 mesi, salsa bbq e bacon croccante'),
('Golocious Bleu', '10', 'b', 'Cordon bleu artigianale di sovracoscia di pollo allevato a terra panato nei cornflakes e ripieno di formaggio e prosciutto cotto di alta qualità, con bacon, insalata iceberg e salsa "Jana"'),
('Nerano Burger', '10,5', 'b', 'Hamburger di macina Golocious 200g, fonduta cacio e pepe, zucchine fritte, provola, crema di zucchine alla Nerano'),
('Nerano Veggy', '10,5', 'b', 'Hamburger di zucchine, chips di grana croccante, crema di zucchine alla Nerano, zucchine fritte, provola e fonduta cacio e pepe'),
('The Lord of Pistacchio', '11', 'b', 'Hamburger di macina Golocious 200g, creama di pistacchio, chips di patate, fetta di mortadella alla brace e provola'),
('Suicide Burger', '11,5', 'b', 'Hamburger di macina Golocious 200g, pulled pork artigianale selezione Corbisiero, cheddar, bacon, insalata, pomodoro e salsa bbq'),
('Golocious Perfume', '12', 'b', 'Hamburger di macina Golocious 200g, provola, fonduta al tartufo, bacon e cialda croccante di parmigiano'),
('Gran Crispy Golocious', '13', 'b', 'Hamburger di macina Golocious 200g, cheddar stagionato 24 mesi, bacon croccante, salsa crispy e pulled pork artigianale selezione Corbisiero'),
--PIZZA&CUCINA--
('Marinara', '4,5', 'p', 'Pomodoro San Marzano, olio extra vergine di oliva DOP, aglio, origano siciliano e basilico'),
('Margherita', '6', 'p', 'Fiordilatte della penisola, pomodoro San Marzano, olio extra vergine di oliva DOP e basilico'),
('Cosacca', '5', 'p', 'Pomodoro San Marzano, pecorino romano, olio extra vergine di oliva DOP e basilico'),
('Crudo 2.0', '8,5', 'p', 'Fiocco di prosciutto crudo, fiordilatte della penisola, pesto di rucola, scaglie di grana padano, olio extra vergine di oliva DOP e basilico'),
('Freematriciana', '8,5', 'p', 'Pomodoro super tirato ed emulsionato, stracciata, guanciale croccante di suino casertano, olio extra vergine di oliva DOP e basilico'),
('Nerano', '10', 'p', 'Crema di zucchine alla Nerano, fiordilatte della penisola, chips di zucchine, fonduta cacio e pepe, chips di grana padano e basilico'),
('Dabol Friariello', '10', 'p', 'Crema di friarielli saltati, friarielli croccanti, provola dei monti, salsiccia di suino nazionale, olio extra vergine di oliva DOP e basilico'),
('Rotolo', '11,5', 'p', 'Crocché panato al panko, crema di noci, provola dei monti, bacon croccante, olio extra vergine di oliva DOP e basilico'),
('Crispy', '11,5', 'p', 'Sticks di patate dolci americane fritte, salsiccia di suino nazionale, provola dei monti, salsa crispy e olio extra vergine di oliva DOP'),
('Food Porn', '12', 'p', 'Pulled pork artigianale selezione Corbisiero, provola dei monti, bacon croccante, dadini di patate al forno, fonduta di cheddar e olio extra vergine di oliva DOP'),
('Queen of Pistacchio', '12', 'p', 'Mortadella IGP bolognese, crema di pistacchio, provola dei monti, olio extra vergine di oliva DOP e basilico'),
('Perfume', '12,5', 'p', 'Crema cacio e pepe profumata con porcini e tartufo, speck IGP, crema di noci, gherigli di noci, provola dei monti, olio extra vergine di oliva DOP e basilico'),
--PIZZA IN TEGLIA--
('Marinara', '1,8', 't', 'Pomodoro San Marzano, olio extra vergine di oliva DOP, aglio, origano siciliano e basilico'),
('Margherita', '2,2', 't', 'Fiordilatte della penisola, pomodoro San Marzano, olio extra vergine di oliva DOP e basilico'),
('Crudo 2.0', '3,3', 't', 'Fiocco di prosciutto crudo, fiordilatte della penisola, pesto di rucola, scaglie di grana padano, olio extra vergine di oliva DOP e basilico'),
('Freematriciana', '3,3', 't', 'Pomodoro super tirato ed emulsionato, stracciata, guanciale croccante di suino casertano, olio extra vergine di oliva DOP e basilico'),
('Nerano', '3,3', 't', 'Crema di zucchine alla Nerano, fiordilatte della penisola, chips di zucchine, fonduta cacio e pepe, chips di grana padano e basilico'),
('Friariello', '3,3', 't', 'Crema di friarielli saltati, provola dei monti, salsiccia di suino nazionale, olio extra vergine di oliva DOP e basilico'),
('Rotolo', '3,3', 't', 'Crocché panato al panko, crema di noci, provola dei monti, bacon croccante, olio extra vergine di oliva DOP e basilico'),
('Crispy', '3,3', 't', 'Sticks di patate dolci americane fritte, salsiccia di suino nazionale, provola dei monti, salsa crispy e olio extra vergine di oliva DOP'),
('Food Porn', '3,3', 't', 'Pulled pork artigianale selezione Corbisiero, provola dei monti, bacon croccante, dadini di patate al forno, fonduta di cheddar e olio extra vergine di oliva DOP'),
('Queen of Pistacchio', '3,3', 't', 'Mortadella IGP bolognese, crema di pistacchio, provola dei monti, olio extra vergine di oliva DOP e basilico'),
--BURGER&WINE--
('Pastel de Nata', '5', 'd', 'Tortino di pasta sfoglia con crema all''uovo e cannella, servito con dulce de leche'),
('BrownieMisù alla Nutella', '5', 'd', 'Classica crema "Tiramisù" con base brownie leggermente bagnata al caffè'),
('BrownieMisù al Pistacchio', '5', 'd', 'Classica crema "Tiramisù", con base brownie leggermente bagnata al caffè'),
('Cannolo Scomposto', '5,5', 'd', 'Ricotta siciliana con gocce di cioccolato, granella di pistacchio, cialde di cannolo e crema di pistacchio'),
--PIZZA&CUCINA E PIZZA IN TEGLIA-- 
('Ops... mi è caduta la crostata', '5', 'd', 'Crema pasticcera al limone con ricotta, pasta frolla croccante e fragoline'),
('Tiramisù alla Nutella', '5', 'd', 'Tiramisù con savoiardi bagnati al caffè zuccherato, Nutella e cacao'),
('Tiramisù al Pistacchio', '5', 'd', 'Tiramisù con savoiardi bagnati al caffè zuccherato, crema spalmabile al pistacchio e granella di pistacchio'),
--TUTTI--
('Acqua Naturale 33cl', '1', 'v', null),
('Acqua Frizzante 33cl', '1', 'v', null),
('Coca-Cola 33cl', '2', 'v', null),
('Coca-Cola Zero 33cl', '2', 'v', null),
('Fanta 33cl', '2', 'v', null),
('Sprite 33cl', '2', 'v', null),
('Birra Ichnusa non filtrata 33cl', '5', 'v', null),
--BURGER&WINE--
('Nativ Falanghina Campania IGT', '12', 'w', null),
('Nativ Aglianico Campania IGT', '12', 'w', null),
('Rubicondo Rosso Verona IGT - Ca''Botta', '15', 'w', null),
('Cuvée Paul IGT - St. Pauls', '15', 'w', null),
('Valpolicella Ripasso DOC Superiore ALTA - Ca''Botta', '20', 'w', null),
('Justina Gewurztraminer Alto Adige DOC - St. Pauls', '22', 'w', null);

INSERT INTO Menu VALUES
(1,1),(2,1),(3,1),(4,1),(5,1),(6,1),
(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),
(1,5),(2,5),(3,5),(4,5),(5,5),(6,5),
(7,3),(8,3),(9,3),(10,3),(11,3),
(7,4),(8,4),(9,4),(10,4),(11,4),
(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),
(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(21,2),(22,2),(23,2),
(12,5),(13,5),(14,5),(15,5),(16,5),(17,5),(18,5),(19,5),(20,5),(21,5),(22,5),(23,5),
(24,3),(25,3),(26,3),(27,3),(28,3),(29,3),(30,3),(31,3),(32,3),(33,3),(34,3),(35,3),
(36,4),(37,4),(38,4),(39,4),(40,4),(41,4),(42,4),(43,4),(44,4),(45,4),
(46,1),(47,1),(48,1),(49,1),
(46,2),(47,2),(48,2),(49,2),
(46,5),(47,5),(48,5),(49,5),
(50,3),(51,3),(52,3),
(50,4),(51,4),(52,4),
(53,1),(54,1),(55,1),(56,1),(57,1),(58,1),(59,1),
(53,2),(54,2),(55,2),(56,2),(57,2),(58,2),(59,2),
(53,3),(54,3),(55,3),(56,3),(57,3),(58,3),(59,3),
(53,4),(54,4),(55,4),(56,4),(57,4),(58,4),(59,4),
(53,5),(54,5),(55,5),(56,5),(57,5),(58,5),(59,5),
(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),
(60,2),(61,2),(62,2),(63,2),(64,2),(65,2),
(60,5),(61,5),(62,5),(63,5),(64,5),(65,5);

/*
INSERT INTO Cliente VALUES
('Andrea','Pepe','andrea.pepe2@studenti.unina.it','andrea25','+39 347 871 9922','25/05/2000'),
('Marcello','Russo','marcello.russo@studenti.unina.it','marcello04','+39 366 134 7726','04/06/2000'),
('Vincenzo','Falcone','falcone@golocious.it','vincenzogolo','+39 345 724 3434','28/01/1990'),
('Gian Andrea','Squadrilli','jana@golocious.it','janagolo','+39 333 842 6645','15/08/1994');

INSERT INTO Indirizzo VALUES
(1,'Italia','NA','Napoli','80137','Via A. de Curtis, 20',1),
(2,'Italia','NA','Napoli','80137','Via Abate Minichini, 19',2);

UPDATE Cliente
SET indirizzoattivo = 1 WHERE clienteid = '1';

UPDATE Cliente
SET indirizzoattivo = 2 WHERE clienteid = '2';

INSERT INTO Gestore VALUES 
(3,1,'Titolare'),
(3,2,'Titolare'),
(3,3,'Titolare'),
(3,4,'Titolare'),
(4,1,'Titolare'),
(4,2,'Titolare'),
(4,3,'Titolare'),
(4,4,'Titolare');

INSERT INTO Rider VALUES
(1,'NA7297377W','m'),
(2,'NA7276894N','m');
*/