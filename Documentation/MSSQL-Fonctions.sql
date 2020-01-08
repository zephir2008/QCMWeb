USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_INSCRIPTION]    Script Date: 26/11/2019 21:28:24 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_INSCRIPTION] 
	-- Add the parameters for the stored procedure here
	@idTest int,
	@idStagiaire int,
	@idFormateur int,
	@tempsRestant int
AS
BEGIN
	insert into INSCRIPTIONS(idTest, idStagiaire, idFormateur, tempsRestant) 
	values(@idTest, @idStagiaire, @idFormateur, @tempsRestant);
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_FORMATEUR]    Script Date: 26/11/2019 21:28:05 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_FORMATEUR] 
	-- Add the parameters for the stored procedure here
	@nom varchar(50), 
	@prenom varchar(50),
	@courriel varchar(100),
	@login varchar(100),
	@motDePasse varchar(30),
	@estResponsable bit
AS
BEGIN
	insert into formateurs(nom, prenom, courriel, login, motDePasse, estResponsable) 
	values(@nom, @prenom, @courriel, @login, @motDePasse,@estResponsable);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_PROMOTION]    Script Date: 26/11/2019 21:32:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_PROMOTION] 
	-- Add the parameters for the stored procedure here
	@libelle varchar(50)
AS
BEGIN
	insert into PROMOTIONS(libelle) values(@libelle);
	return @@identity;
END
GO
USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_PROMOTION]    Script Date: 26/11/2019 21:32:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_PROMOTION] 
	-- Add the parameters for the stored procedure here
	@libelle varchar(50)
AS
BEGIN
	insert into PROMOTIONS(libelle) values(@libelle);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_REPONSE]    Script Date: 26/11/2019 21:33:35 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_REPONSE] 
	-- Add the parameters for the stored procedure here
	@position int,
	@idQuestion int,
	@enonceRiche varchar(1000),
	@estBonne bit
AS
BEGIN
	insert into REPONSES(position,idQuestion,enonceRiche,estBonne) 
	values(@position,@idQuestion,@enonceRiche,@estBonne);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_SECTION]    Script Date: 26/11/2019 21:33:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[AJOUTER_SECTION] 
	-- Add the parameters for the stored procedure here
	@libelle varchar(50)
AS
BEGIN
	insert into SECTIONS(libelle) values(@libelle);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_SECTION_QUESTION]    Script Date: 26/11/2019 21:33:58 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[AJOUTER_SECTION_QUESTION] 
	-- Add the parameters for the stored procedure here
	@idSection int,
	@idQuestion int,
	@numeroQuestionDansSection int
AS
BEGIN
	insert into SECTIONS_QUESTIONS(idSection,idQuestion,numeroQuestionDansSection) values(@idSection,@idQuestion,@numeroQuestionDansSection);
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_STAGIAIRE]    Script Date: 26/11/2019 21:34:25 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_STAGIAIRE] 
	-- Add the parameters for the stored procedure here
	@nom varchar(50), 
	@prenom varchar(50),
	@courriel varchar(100),
	@login varchar(100),
	@motDePasse varchar(30),
	@idPromotion int
AS
BEGIN
	insert into stagiaires(nom, prenom, courriel, login, motDePasse, idPromotion) 
	values(@nom, @prenom, @courriel, @login, @motDePasse,@idPromotion);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_TEST]    Script Date: 26/11/2019 21:34:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AJOUTER_TEST] 
	-- Add the parameters for the stored procedure here
	@libelle varchar(50),
	@seuilReussite int,
	@tempsPassage int,
	@dateDebutDisponibilite datetime,
	@dateFinDisponibilite datetime,
	@idFormateur int
AS
BEGIN
	insert into TESTS(libelle,seuilReussite,tempsPassage,dateDebutDisponibilite, dateFinDisponibilite, idFormateur)
	values(@libelle,@seuilReussite,@tempsPassage,@dateDebutDisponibilite,@dateFinDisponibilite,@idFormateur);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_TEST_SECTION]    Script Date: 26/11/2019 21:34:53 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[AJOUTER_TEST_SECTION] 
	-- Add the parameters for the stored procedure here
	@idTest int,
	@idSection int,
	@nombreQuestionAUtiliser int,
	@numeroSectionDansTest int
AS
BEGIN
	insert into TESTS_SECTIONS(idTest,idSection,nombreQuestionAUtiliser, numeroSectionDansTest) 
	values(@idTest,@idSection,@nombreQuestionAUtiliser,@numeroSectionDansTest);
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_THEME]    Script Date: 26/11/2019 21:35:03 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[AJOUTER_THEME] 
	-- Add the parameters for the stored procedure here
	@libelle varchar(50)
AS
BEGIN
	insert into THEMES(libelle) values(@libelle);
	return @@identity;
END
GO

USE [qcm]
GO

/****** Object:  StoredProcedure [dbo].[AJOUTER_THEME_QUESTION]    Script Date: 26/11/2019 21:35:15 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[AJOUTER_THEME_QUESTION] 
	-- Add the parameters for the stored procedure here
	@idTheme int,
	@idQuestion int
AS
BEGIN
	insert into THEMES_QUESTIONS(idTheme,idQuestion) values(@idTheme,@idQuestion);
END
GO













