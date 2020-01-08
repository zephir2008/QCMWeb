USE qcm;

-- 1
DROP FUNCTION IF EXISTS ajouter_formateur;

CREATE FUNCTION ajouter_formateur(
	nom varchar(50),
	prenom varchar(50),
	courriel varchar(100),
	login varchar(100),
	motDePasse varchar(30),
	estResponsable bit)
RETURNS int
BEGIN
	insert into formateurs(nom, prenom, courriel, login, motDePasse, estResponsable) 
		values(nom, prenom, courriel, login, motDePasse, estResponsable);
	return LAST_INSERT_ID();
END

-- 2
DROP PROCEDURE IF EXISTS ajouter_inscription;
delimiter |
CREATE PROCEDURE ajouter_inscription(
	IN idTest INT,
	IN idStagiaire INT,
	IN idFormateur INT,
	IN tempsRestant INT)
BEGIN
	INSERT INTO inscriptions(idTest, idStagiaire, idFormateur, tempsRestant)
		VALUES ( idTest, idStagiaire, idFormateur, tempsRestant);
END;|
delimiter ;

-- 3
DROP FUNCTION IF EXISTS ajouter_promotion;
delimiter |
CREATE FUNCTION ajouter_promotion( 
	libelle varchar(50))
RETURNS int
BEGIN
	insert into PROMOTIONS(libelle) values(libelle);
	return LAST_INSERT_ID();
END
delimiter ;


drop function if exists ajouter_question;
delimiter |
CREATE FUNCTION ajouter_question(
	enonceRiche varchar(1000),
	cheminIllustration varchar(500),
	poids int)
RETURNS int
BEGIN
	insert into QUESTIONS(enonceRiche,cheminIllustration,poids) 
		values( enonceRiche, cheminIllustration, poids);
	return LAST_INSERT_ID();
END
delimiter ;


-- 4
DROP FUNCTION IF EXISTS ajouter_reponse;
delimiter |
CREATE FUNCTION ajouter_reponse( 
	position int,
	idQuestion int,
	enonceRiche varchar(1000),
	estBonne bit)
RETURNS int
BEGIN
	insert into REPONSES(position,idQuestion,enonceRiche,estBonne) 
		values(position, idQuestion, enonceRiche, estBonne);
	return LAST_INSERT_ID();
END
delimiter ;


-- 5
DROP FUNCTION IF EXISTS ajouter_section;
delimiter |
CREATE FUNCTION ajouter_section(
	libelle varchar(50)
	)
RETURNS int
BEGIN
	insert into SECTIONS(libelle) values(libelle);
	return LAST_INSERT_ID();
END;|
delimiter ;


-- 6
DROP PROCEDURE IF EXISTS ajouter_section_question;
delimiter |
CREATE PROCEDURE ajouter_section_question(
	IN idSection int,
	IN idQuestion int,
	IN numeroQuestionDansSection int)
BEGIN
	insert into SECTIONS_QUESTIONS(idSection,idQuestion,numeroQuestionDansSection) values( idSection, idQuestion, numeroQuestionDansSection );
END;|
delimiter ;

-- 7
DROP FUNCTION IF EXISTS ajouter_stagiaire;
delimiter |
CREATE FUNCTION ajouter_stagiaire(
	nom varchar(50), 
	prenom varchar(50),
	courriel varchar(100),
	login varchar(100),
	motDePasse varchar(30),
	idPromotion int)
RETURNS int
BEGIN
	insert into stagiaires(nom, prenom, courriel, login, motDePasse, idPromotion) 
		values( nom, prenom, courriel, login, motDePasse, idPromotion);
	return LAST_INSERT_ID();
END;|
delimiter ;

-- 8
DROP FUNCTION IF EXISTS ajouter_test;
delimiter |
CREATE FUNCTION ajouter_test(
	libelle varchar(50),
	seuilReussite int,
	tempsPassage int,
	dateDebutDisponibilite datetime,
	dateFinDisponibilite datetime,
	idFormateur int)
RETURNS int
BEGIN
	insert into TESTS(libelle,seuilReussite,tempsPassage,dateDebutDisponibilite, dateFinDisponibilite, idFormateur)
		values( libelle, seuilReussite, tempsPassage, dateDebutDisponibilite, dateFinDisponibilite, idFormateur);
	return LAST_INSERT_ID();
END;|
delimiter ;

-- 9
drop procedure if exists ajouter_test_section;
delimiter |
create PROCEDURE ajouter_test_section( 
	in idTest int,
	in idSection int,
	in nombreQuestionAUtiliser int,
	in numeroSectionDansTest int
)
BEGIN
	insert into TESTS_SECTIONS(idTest,idSection,nombreQuestionAUtiliser, numeroSectionDansTest) 
		values( idTest, idSection, nombreQuestionAUtiliser, numeroSectionDansTest);
END;|
delimiter ;

-- 10
DROP FUNCTION IF EXISTS ajouter_theme;
delimiter |
create FUNCTION ajouter_theme(
	libelle varchar(50)
)
RETURNS int
BEGIN
	insert into THEMES(libelle) values(libelle);
	return LAST_INSERT_ID();
END;|
delimiter ;

-- 11
drop procedure if exists ajouter_theme_question;
delimiter |
create PROCEDURE ajouter_theme_question(
	IN idTheme int,
	IN idQuestion int)
BEGIN
	insert into THEMES_QUESTIONS(idTheme,idQuestion) values( idTheme, idQuestion);
END
delimiter ;
