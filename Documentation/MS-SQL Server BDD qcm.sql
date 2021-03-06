USE [qcm]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_THEME_QUESTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_THEME_QUESTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_THEME]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_THEME]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_TEST_SECTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_TEST_SECTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_TEST]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_TEST]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_STAGIAIRE]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_STAGIAIRE]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_SECTION_QUESTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_SECTION_QUESTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_SECTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_SECTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_REPONSE]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_REPONSE]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_QUESTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_QUESTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_PROMOTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_PROMOTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_INSCRIPTION]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_INSCRIPTION]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_FORMATEUR]    Script Date: 08/01/2020 13:18:11 ******/
DROP PROCEDURE IF EXISTS [dbo].[AJOUTER_FORMATEUR]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[THEMES_QUESTIONS]') AND type in (N'U'))
ALTER TABLE [dbo].[THEMES_QUESTIONS] DROP CONSTRAINT IF EXISTS [FK_THEMES_QUESTIONS_THEMES]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[THEMES_QUESTIONS]') AND type in (N'U'))
ALTER TABLE [dbo].[THEMES_QUESTIONS] DROP CONSTRAINT IF EXISTS [FK_THEMES_QUESTIONS_QUESTIONS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TESTS]') AND type in (N'U'))
ALTER TABLE [dbo].[TESTS] DROP CONSTRAINT IF EXISTS [FK_TESTS_FORMATEURS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[STAGIAIRES]') AND type in (N'U'))
ALTER TABLE [dbo].[STAGIAIRES] DROP CONSTRAINT IF EXISTS [FK_STAGIAIRES_PROMOTIONS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[REPONSES_DONNEES]') AND type in (N'U'))
ALTER TABLE [dbo].[REPONSES_DONNEES] DROP CONSTRAINT IF EXISTS [FK_REPONSES_DONNEES_REPONSES]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[REPONSES_DONNEES]') AND type in (N'U'))
ALTER TABLE [dbo].[REPONSES_DONNEES] DROP CONSTRAINT IF EXISTS [FK_REPONSES_DONNEES_QUESTIONS_SELECTIONNEES]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[REPONSES]') AND type in (N'U'))
ALTER TABLE [dbo].[REPONSES] DROP CONSTRAINT IF EXISTS [FK_REPONSES_QUESTIONS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[QUESTIONS_SELECTIONNEES]') AND type in (N'U'))
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES] DROP CONSTRAINT IF EXISTS [FK_QUESTIONS_SELECTIONNEES_SECTIONS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[QUESTIONS_SELECTIONNEES]') AND type in (N'U'))
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES] DROP CONSTRAINT IF EXISTS [FK_QUESTIONS_SELECTIONNEES_QUESTIONS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[QUESTIONS_SELECTIONNEES]') AND type in (N'U'))
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES] DROP CONSTRAINT IF EXISTS [FK_QUESTIONS_SELECTIONNEES_INSCRIPTIONS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[INSCRIPTIONS]') AND type in (N'U'))
ALTER TABLE [dbo].[INSCRIPTIONS] DROP CONSTRAINT IF EXISTS [FK_INSCRIPTIONS_TESTS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[INSCRIPTIONS]') AND type in (N'U'))
ALTER TABLE [dbo].[INSCRIPTIONS] DROP CONSTRAINT IF EXISTS [FK_INSCRIPTIONS_STAGIAIRES]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[INSCRIPTIONS]') AND type in (N'U'))
ALTER TABLE [dbo].[INSCRIPTIONS] DROP CONSTRAINT IF EXISTS [FK_INSCRIPTIONS_FORMATEURS]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TESTS]') AND type in (N'U'))
ALTER TABLE [dbo].[TESTS] DROP CONSTRAINT IF EXISTS [DF__TESTS__tempsPass__21A0F6C4]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TESTS]') AND type in (N'U'))
ALTER TABLE [dbo].[TESTS] DROP CONSTRAINT IF EXISTS [DF__TESTS__seuilReus__20ACD28B]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[REPONSES]') AND type in (N'U'))
ALTER TABLE [dbo].[REPONSES] DROP CONSTRAINT IF EXISTS [DF__REPONSES__estBon__32CB82C6]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[QUESTIONS]') AND type in (N'U'))
ALTER TABLE [dbo].[QUESTIONS] DROP CONSTRAINT IF EXISTS [DF__QUESTIONS__poids__2D12A970]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[FORMATEURS]') AND type in (N'U'))
ALTER TABLE [dbo].[FORMATEURS] DROP CONSTRAINT IF EXISTS [DF__FORMATEUR__estRe__1352D76D]
GO
/****** Object:  Index [UN_REPONSES_position_idQuestion]    Script Date: 08/01/2020 13:18:11 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[REPONSES]') AND type in (N'U'))
ALTER TABLE [dbo].[REPONSES] DROP CONSTRAINT IF EXISTS [UN_REPONSES_position_idQuestion]
GO
/****** Object:  Table [dbo].[THEMES_QUESTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[THEMES_QUESTIONS]
GO
/****** Object:  Table [dbo].[THEMES]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[THEMES]
GO
/****** Object:  Table [dbo].[TESTS_SECTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[TESTS_SECTIONS]
GO
/****** Object:  Table [dbo].[TESTS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[TESTS]
GO
/****** Object:  Table [dbo].[STAGIAIRES]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[STAGIAIRES]
GO
/****** Object:  Table [dbo].[SECTIONS_QUESTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[SECTIONS_QUESTIONS]
GO
/****** Object:  Table [dbo].[SECTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[SECTIONS]
GO
/****** Object:  Table [dbo].[REPONSES_DONNEES]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[REPONSES_DONNEES]
GO
/****** Object:  Table [dbo].[REPONSES]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[REPONSES]
GO
/****** Object:  Table [dbo].[QUESTIONS_SELECTIONNEES]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[QUESTIONS_SELECTIONNEES]
GO
/****** Object:  Table [dbo].[QUESTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[QUESTIONS]
GO
/****** Object:  Table [dbo].[PROMOTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[PROMOTIONS]
GO
/****** Object:  Table [dbo].[INSCRIPTIONS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[INSCRIPTIONS]
GO
/****** Object:  Table [dbo].[FORMATEURS]    Script Date: 08/01/2020 13:18:11 ******/
DROP TABLE IF EXISTS [dbo].[FORMATEURS]
GO
USE [master]
GO
/****** Object:  Database [qcm]    Script Date: 08/01/2020 13:18:11 ******/
DROP DATABASE IF EXISTS [qcm]
GO
/****** Object:  Database [qcm]    Script Date: 08/01/2020 13:18:11 ******/
CREATE DATABASE [qcm]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'qcm', FILENAME = N'C:\RECAP\MSSQL14.RECAP\MSSQL\DATA\qcm.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'qcm_log', FILENAME = N'C:\RECAP\MSSQL14.RECAP\MSSQL\Log\qcm_log.ldf' , SIZE = 6272KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [qcm] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [qcm].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [qcm] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [qcm] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [qcm] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [qcm] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [qcm] SET ARITHABORT OFF 
GO
ALTER DATABASE [qcm] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [qcm] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [qcm] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [qcm] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [qcm] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [qcm] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [qcm] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [qcm] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [qcm] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [qcm] SET  DISABLE_BROKER 
GO
ALTER DATABASE [qcm] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [qcm] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [qcm] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [qcm] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [qcm] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [qcm] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [qcm] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [qcm] SET RECOVERY FULL 
GO
ALTER DATABASE [qcm] SET  MULTI_USER 
GO
ALTER DATABASE [qcm] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [qcm] SET DB_CHAINING OFF 
GO
ALTER DATABASE [qcm] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [qcm] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [qcm] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'qcm', N'ON'
GO
ALTER DATABASE [qcm] SET QUERY_STORE = OFF
GO
USE [qcm]
GO
/****** Object:  Table [dbo].[FORMATEURS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FORMATEURS](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nom] [nvarchar](50) NOT NULL,
	[prenom] [nvarchar](50) NULL,
	[courriel] [nvarchar](100) NULL,
	[login] [nvarchar](100) NULL,
	[motDePasse] [nvarchar](30) NULL,
	[estResponsable] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[INSCRIPTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[INSCRIPTIONS](
	[idTest] [int] NOT NULL,
	[idStagiaire] [int] NOT NULL,
	[idFormateur] [int] NOT NULL,
	[tempsRestant] [int] NOT NULL,
 CONSTRAINT [PK_INSCRIPTIONS] PRIMARY KEY CLUSTERED 
(
	[idTest] ASC,
	[idStagiaire] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PROMOTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PROMOTIONS](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[libelle] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[QUESTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[QUESTIONS](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[enonceRiche] [nvarchar](1000) NOT NULL,
	[cheminIllustration] [nvarchar](500) NULL,
	[poids] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[QUESTIONS_SELECTIONNEES]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[QUESTIONS_SELECTIONNEES](
	[idTest] [int] NOT NULL,
	[idStagiaire] [int] NOT NULL,
	[idSection] [int] NOT NULL,
	[idQuestionSelectionnee] [int] NOT NULL,
 CONSTRAINT [PK_QUESTIONS_SELECTIONNEES] PRIMARY KEY CLUSTERED 
(
	[idTest] ASC,
	[idStagiaire] ASC,
	[idSection] ASC,
	[idQuestionSelectionnee] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[REPONSES]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[REPONSES](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[position] [int] NOT NULL,
	[idQuestion] [int] NOT NULL,
	[enonceRiche] [nvarchar](1000) NOT NULL,
	[estBonne] [bit] NULL,
 CONSTRAINT [PK_REPONSES] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[REPONSES_DONNEES]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[REPONSES_DONNEES](
	[idTest] [int] NOT NULL,
	[idStagiaire] [int] NOT NULL,
	[idSection] [int] NOT NULL,
	[idQuestionSelectionnee] [int] NOT NULL,
	[idReponse] [int] NOT NULL,
 CONSTRAINT [PK_REPONSES_DONNEES] PRIMARY KEY CLUSTERED 
(
	[idTest] ASC,
	[idStagiaire] ASC,
	[idSection] ASC,
	[idQuestionSelectionnee] ASC,
	[idReponse] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SECTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SECTIONS](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[libelle] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SECTIONS_QUESTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SECTIONS_QUESTIONS](
	[idSection] [int] NOT NULL,
	[idQuestion] [int] NOT NULL,
	[numeroQuestionDansSection] [int] NOT NULL,
 CONSTRAINT [PK_SECTIONS_QUESTIONS] PRIMARY KEY CLUSTERED 
(
	[idSection] ASC,
	[idQuestion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[STAGIAIRES]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[STAGIAIRES](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nom] [nvarchar](50) NOT NULL,
	[prenom] [nvarchar](50) NULL,
	[courriel] [nvarchar](100) NULL,
	[login] [nvarchar](100) NULL,
	[motDePasse] [nvarchar](30) NULL,
	[idPromotion] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TESTS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TESTS](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[libelle] [nvarchar](50) NOT NULL,
	[seuilReussite] [int] NOT NULL,
	[tempsPassage] [int] NOT NULL,
	[dateDebutDisponibilite] [datetime] NOT NULL,
	[dateFinDisponibilite] [datetime] NOT NULL,
	[idFormateur] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TESTS_SECTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TESTS_SECTIONS](
	[idTest] [int] NOT NULL,
	[idSection] [int] NOT NULL,
	[nombreQuestionAUtiliser] [int] NOT NULL,
	[numeroSectionDansTest] [int] NOT NULL,
 CONSTRAINT [PK_TESTS_SECTIONS] PRIMARY KEY CLUSTERED 
(
	[idTest] ASC,
	[idSection] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[THEMES]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[THEMES](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[libelle] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[THEMES_QUESTIONS]    Script Date: 08/01/2020 13:18:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[THEMES_QUESTIONS](
	[idTheme] [int] NOT NULL,
	[idQuestion] [int] NOT NULL,
 CONSTRAINT [PK_THEMES_QUESTIONS] PRIMARY KEY CLUSTERED 
(
	[idTheme] ASC,
	[idQuestion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[FORMATEURS] ON 

INSERT [dbo].[FORMATEURS] ([id], [nom], [prenom], [courriel], [login], [motDePasse], [estResponsable]) VALUES (1, N'RICHARD', N'Thierry', N'', N'trichard', N'mdp', 1)
INSERT [dbo].[FORMATEURS] ([id], [nom], [prenom], [courriel], [login], [motDePasse], [estResponsable]) VALUES (2, N'Guyader', N'Thélo', N'tguyader@eni.fr', N'tguyader', N'mdp', 0)
SET IDENTITY_INSERT [dbo].[FORMATEURS] OFF
INSERT [dbo].[INSCRIPTIONS] ([idTest], [idStagiaire], [idFormateur], [tempsRestant]) VALUES (1, 1, 1, 0)
INSERT [dbo].[INSCRIPTIONS] ([idTest], [idStagiaire], [idFormateur], [tempsRestant]) VALUES (2, 1, 1, 30)
INSERT [dbo].[INSCRIPTIONS] ([idTest], [idStagiaire], [idFormateur], [tempsRestant]) VALUES (3, 1, 1, 0)
SET IDENTITY_INSERT [dbo].[PROMOTIONS] ON 

INSERT [dbo].[PROMOTIONS] ([id], [libelle]) VALUES (1, N'CDI4')
INSERT [dbo].[PROMOTIONS] ([id], [libelle]) VALUES (2, N'CDI5')
SET IDENTITY_INSERT [dbo].[PROMOTIONS] OFF
SET IDENTITY_INSERT [dbo].[QUESTIONS] ON 

INSERT [dbo].[QUESTIONS] ([id], [enonceRiche], [cheminIllustration], [poids]) VALUES (1, N'une question avec une réponse<br>', NULL, 1)
INSERT [dbo].[QUESTIONS] ([id], [enonceRiche], [cheminIllustration], [poids]) VALUES (2, N'Une deuxième question pas plus intéressante...<br>', N'', 1)
SET IDENTITY_INSERT [dbo].[QUESTIONS] OFF
INSERT [dbo].[QUESTIONS_SELECTIONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee]) VALUES (1, 1, 1, 1)
INSERT [dbo].[QUESTIONS_SELECTIONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee]) VALUES (1, 1, 1, 2)
INSERT [dbo].[QUESTIONS_SELECTIONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee]) VALUES (3, 1, 1, 1)
INSERT [dbo].[QUESTIONS_SELECTIONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee]) VALUES (3, 1, 1, 2)
SET IDENTITY_INSERT [dbo].[REPONSES] ON 

INSERT [dbo].[REPONSES] ([id], [position], [idQuestion], [enonceRiche], [estBonne]) VALUES (7, 1, 1, N'C''est la bonne réponse!!!<br>', 1)
INSERT [dbo].[REPONSES] ([id], [position], [idQuestion], [enonceRiche], [estBonne]) VALUES (8, 2, 1, N'une autre réponse<br>', 0)
INSERT [dbo].[REPONSES] ([id], [position], [idQuestion], [enonceRiche], [estBonne]) VALUES (16, 1, 2, N'réponse 1<br>', 0)
INSERT [dbo].[REPONSES] ([id], [position], [idQuestion], [enonceRiche], [estBonne]) VALUES (17, 2, 2, N'réponse 2<br>', 0)
INSERT [dbo].[REPONSES] ([id], [position], [idQuestion], [enonceRiche], [estBonne]) VALUES (18, 3, 2, N'réponse 3 (la bonne)<br>', 1)
INSERT [dbo].[REPONSES] ([id], [position], [idQuestion], [enonceRiche], [estBonne]) VALUES (19, 4, 2, N'une autre réponse possible<br>', 1)
SET IDENTITY_INSERT [dbo].[REPONSES] OFF
INSERT [dbo].[REPONSES_DONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee], [idReponse]) VALUES (1, 1, 1, 1, 8)
INSERT [dbo].[REPONSES_DONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee], [idReponse]) VALUES (1, 1, 1, 2, 18)
INSERT [dbo].[REPONSES_DONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee], [idReponse]) VALUES (1, 1, 1, 2, 19)
INSERT [dbo].[REPONSES_DONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee], [idReponse]) VALUES (3, 1, 1, 1, 7)
INSERT [dbo].[REPONSES_DONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee], [idReponse]) VALUES (3, 1, 1, 2, 17)
INSERT [dbo].[REPONSES_DONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee], [idReponse]) VALUES (3, 1, 1, 2, 18)
SET IDENTITY_INSERT [dbo].[SECTIONS] ON 

INSERT [dbo].[SECTIONS] ([id], [libelle]) VALUES (1, N'Une section')
SET IDENTITY_INSERT [dbo].[SECTIONS] OFF
INSERT [dbo].[SECTIONS_QUESTIONS] ([idSection], [idQuestion], [numeroQuestionDansSection]) VALUES (1, 1, 1)
INSERT [dbo].[SECTIONS_QUESTIONS] ([idSection], [idQuestion], [numeroQuestionDansSection]) VALUES (1, 2, 2)
SET IDENTITY_INSERT [dbo].[STAGIAIRES] ON 

INSERT [dbo].[STAGIAIRES] ([id], [nom], [prenom], [courriel], [login], [motDePasse], [idPromotion]) VALUES (1, N'HAREL', N'Thibault', N'', N'tharel', N'mdp', 2)
SET IDENTITY_INSERT [dbo].[STAGIAIRES] OFF
SET IDENTITY_INSERT [dbo].[TESTS] ON 

INSERT [dbo].[TESTS] ([id], [libelle], [seuilReussite], [tempsPassage], [dateDebutDisponibilite], [dateFinDisponibilite], [idFormateur]) VALUES (1, N'un nouveau test', 5, 30, CAST(N'2012-09-17T00:00:00.000' AS DateTime), CAST(N'2012-09-29T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[TESTS] ([id], [libelle], [seuilReussite], [tempsPassage], [dateDebutDisponibilite], [dateFinDisponibilite], [idFormateur]) VALUES (2, N'Copie de un nouveau test', 5, 30, CAST(N'2012-09-21T00:00:00.000' AS DateTime), CAST(N'2012-09-29T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[TESTS] ([id], [libelle], [seuilReussite], [tempsPassage], [dateDebutDisponibilite], [dateFinDisponibilite], [idFormateur]) VALUES (3, N'Copie de Copie de un nouveau test', 5, 30, CAST(N'2012-09-21T00:00:00.000' AS DateTime), CAST(N'2012-10-25T00:00:00.000' AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[TESTS] OFF
INSERT [dbo].[TESTS_SECTIONS] ([idTest], [idSection], [nombreQuestionAUtiliser], [numeroSectionDansTest]) VALUES (1, 1, 2, 1)
INSERT [dbo].[TESTS_SECTIONS] ([idTest], [idSection], [nombreQuestionAUtiliser], [numeroSectionDansTest]) VALUES (2, 1, 1, 1)
INSERT [dbo].[TESTS_SECTIONS] ([idTest], [idSection], [nombreQuestionAUtiliser], [numeroSectionDansTest]) VALUES (3, 1, 2, 1)
SET IDENTITY_INSERT [dbo].[THEMES] ON 

INSERT [dbo].[THEMES] ([id], [libelle]) VALUES (1, N'Theme 1')
SET IDENTITY_INSERT [dbo].[THEMES] OFF
INSERT [dbo].[THEMES_QUESTIONS] ([idTheme], [idQuestion]) VALUES (1, 1)
INSERT [dbo].[THEMES_QUESTIONS] ([idTheme], [idQuestion]) VALUES (1, 2)
/****** Object:  Index [UN_REPONSES_position_idQuestion]    Script Date: 08/01/2020 13:18:12 ******/
ALTER TABLE [dbo].[REPONSES] ADD  CONSTRAINT [UN_REPONSES_position_idQuestion] UNIQUE NONCLUSTERED 
(
	[position] ASC,
	[idQuestion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FORMATEURS] ADD  DEFAULT ((0)) FOR [estResponsable]
GO
ALTER TABLE [dbo].[QUESTIONS] ADD  DEFAULT ((1)) FOR [poids]
GO
ALTER TABLE [dbo].[REPONSES] ADD  DEFAULT ((0)) FOR [estBonne]
GO
ALTER TABLE [dbo].[TESTS] ADD  DEFAULT ((50)) FOR [seuilReussite]
GO
ALTER TABLE [dbo].[TESTS] ADD  DEFAULT ((3600)) FOR [tempsPassage]
GO
ALTER TABLE [dbo].[INSCRIPTIONS]  WITH CHECK ADD  CONSTRAINT [FK_INSCRIPTIONS_FORMATEURS] FOREIGN KEY([idFormateur])
REFERENCES [dbo].[FORMATEURS] ([id])
GO
ALTER TABLE [dbo].[INSCRIPTIONS] CHECK CONSTRAINT [FK_INSCRIPTIONS_FORMATEURS]
GO
ALTER TABLE [dbo].[INSCRIPTIONS]  WITH CHECK ADD  CONSTRAINT [FK_INSCRIPTIONS_STAGIAIRES] FOREIGN KEY([idStagiaire])
REFERENCES [dbo].[STAGIAIRES] ([id])
GO
ALTER TABLE [dbo].[INSCRIPTIONS] CHECK CONSTRAINT [FK_INSCRIPTIONS_STAGIAIRES]
GO
ALTER TABLE [dbo].[INSCRIPTIONS]  WITH CHECK ADD  CONSTRAINT [FK_INSCRIPTIONS_TESTS] FOREIGN KEY([idTest])
REFERENCES [dbo].[TESTS] ([id])
GO
ALTER TABLE [dbo].[INSCRIPTIONS] CHECK CONSTRAINT [FK_INSCRIPTIONS_TESTS]
GO
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES]  WITH CHECK ADD  CONSTRAINT [FK_QUESTIONS_SELECTIONNEES_INSCRIPTIONS] FOREIGN KEY([idTest], [idStagiaire])
REFERENCES [dbo].[INSCRIPTIONS] ([idTest], [idStagiaire])
GO
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES] CHECK CONSTRAINT [FK_QUESTIONS_SELECTIONNEES_INSCRIPTIONS]
GO
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES]  WITH CHECK ADD  CONSTRAINT [FK_QUESTIONS_SELECTIONNEES_QUESTIONS] FOREIGN KEY([idQuestionSelectionnee])
REFERENCES [dbo].[QUESTIONS] ([id])
GO
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES] CHECK CONSTRAINT [FK_QUESTIONS_SELECTIONNEES_QUESTIONS]
GO
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES]  WITH CHECK ADD  CONSTRAINT [FK_QUESTIONS_SELECTIONNEES_SECTIONS] FOREIGN KEY([idSection])
REFERENCES [dbo].[SECTIONS] ([id])
GO
ALTER TABLE [dbo].[QUESTIONS_SELECTIONNEES] CHECK CONSTRAINT [FK_QUESTIONS_SELECTIONNEES_SECTIONS]
GO
ALTER TABLE [dbo].[REPONSES]  WITH CHECK ADD  CONSTRAINT [FK_REPONSES_QUESTIONS] FOREIGN KEY([idQuestion])
REFERENCES [dbo].[QUESTIONS] ([id])
GO
ALTER TABLE [dbo].[REPONSES] CHECK CONSTRAINT [FK_REPONSES_QUESTIONS]
GO
ALTER TABLE [dbo].[REPONSES_DONNEES]  WITH CHECK ADD  CONSTRAINT [FK_REPONSES_DONNEES_QUESTIONS_SELECTIONNEES] FOREIGN KEY([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee])
REFERENCES [dbo].[QUESTIONS_SELECTIONNEES] ([idTest], [idStagiaire], [idSection], [idQuestionSelectionnee])
GO
ALTER TABLE [dbo].[REPONSES_DONNEES] CHECK CONSTRAINT [FK_REPONSES_DONNEES_QUESTIONS_SELECTIONNEES]
GO
ALTER TABLE [dbo].[REPONSES_DONNEES]  WITH CHECK ADD  CONSTRAINT [FK_REPONSES_DONNEES_REPONSES] FOREIGN KEY([idReponse])
REFERENCES [dbo].[REPONSES] ([id])
GO
ALTER TABLE [dbo].[REPONSES_DONNEES] CHECK CONSTRAINT [FK_REPONSES_DONNEES_REPONSES]
GO
ALTER TABLE [dbo].[STAGIAIRES]  WITH CHECK ADD  CONSTRAINT [FK_STAGIAIRES_PROMOTIONS] FOREIGN KEY([idPromotion])
REFERENCES [dbo].[PROMOTIONS] ([id])
GO
ALTER TABLE [dbo].[STAGIAIRES] CHECK CONSTRAINT [FK_STAGIAIRES_PROMOTIONS]
GO
ALTER TABLE [dbo].[TESTS]  WITH CHECK ADD  CONSTRAINT [FK_TESTS_FORMATEURS] FOREIGN KEY([idFormateur])
REFERENCES [dbo].[FORMATEURS] ([id])
GO
ALTER TABLE [dbo].[TESTS] CHECK CONSTRAINT [FK_TESTS_FORMATEURS]
GO
ALTER TABLE [dbo].[THEMES_QUESTIONS]  WITH CHECK ADD  CONSTRAINT [FK_THEMES_QUESTIONS_QUESTIONS] FOREIGN KEY([idQuestion])
REFERENCES [dbo].[QUESTIONS] ([id])
GO
ALTER TABLE [dbo].[THEMES_QUESTIONS] CHECK CONSTRAINT [FK_THEMES_QUESTIONS_QUESTIONS]
GO
ALTER TABLE [dbo].[THEMES_QUESTIONS]  WITH CHECK ADD  CONSTRAINT [FK_THEMES_QUESTIONS_THEMES] FOREIGN KEY([idTheme])
REFERENCES [dbo].[THEMES] ([id])
GO
ALTER TABLE [dbo].[THEMES_QUESTIONS] CHECK CONSTRAINT [FK_THEMES_QUESTIONS_THEMES]
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_FORMATEUR]    Script Date: 08/01/2020 13:18:12 ******/
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
	insert into FORMATEURS(nom, prenom, courriel, login, motDePasse, estResponsable) 
	values(@nom, @prenom, @courriel, @login, @motDePasse,@estResponsable);
	return @@identity;
END
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_INSCRIPTION]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_PROMOTION]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_QUESTION]    Script Date: 08/01/2020 13:18:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[AJOUTER_QUESTION] 
	-- Add the parameters for the stored procedure here
	@enonceRiche varchar(1000),
	@cheminIllustration varchar(500),
	@poids int
AS
BEGIN
	insert into QUESTIONS(enonceRiche,cheminIllustration,poids) 
	values(@enonceRiche,@cheminIllustration,@poids);
	return @@identity;
END
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_REPONSE]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_SECTION]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_SECTION_QUESTION]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_STAGIAIRE]    Script Date: 08/01/2020 13:18:12 ******/
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
	insert into STAGIAIRES(nom, prenom, courriel, login, motDePasse, idPromotion) 
	values(@nom, @prenom, @courriel, @login, @motDePasse,@idPromotion);
	return @@identity;
END
GO
/****** Object:  StoredProcedure [dbo].[AJOUTER_TEST]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_TEST_SECTION]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_THEME]    Script Date: 08/01/2020 13:18:12 ******/
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
/****** Object:  StoredProcedure [dbo].[AJOUTER_THEME_QUESTION]    Script Date: 08/01/2020 13:18:12 ******/
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
USE [master]
GO
ALTER DATABASE [qcm] SET  READ_WRITE 
GO
