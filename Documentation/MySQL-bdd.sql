DROP TABLE [dbo].[REPONSES_DONNEES];
DROP TABLE [dbo].[QUESTIONS_SELECTIONNEES];
DROP TABLE [dbo].[INSCRIPTIONS];
DROP TABLE [dbo].[REPONSES];
DROP TABLE [dbo].[stagiaires];
DROP TABLE [dbo].[TESTS];
DROP TABLE [dbo].[THEMES_QUESTIONS];
DROP TABLE [dbo].[TESTS_SECTIONS];
DROP TABLE [dbo].[THEMES];
DROP TABLE [dbo].[SECTIONS];
DROP TABLE [dbo].[SECTIONS_QUESTIONS];
DROP TABLE [dbo].[formateurs];
DROP TABLE [dbo].[PROMOTIONS];
DROP TABLE [dbo].[QUESTIONS];



create table formateurs
(
	id int identity(1,1) primary key,
	nom nvarchar(50) not null,
	prenom nvarchar(50),
	courriel nvarchar(100),
	[login] nvarchar(100),
	motDePasse nvarchar(30),
	estResponsable bit default 0
);

create table PROMOTIONS
(
	id int identity(1,1) primary key,
	libelle nvarchar(50) not null,
	
);

create table stagiaires
(
	id int identity(1,1) primary key,
	nom nvarchar(50) not null,
	prenom nvarchar(50),
	courriel nvarchar(100),
	[login] nvarchar(100),
	motDePasse nvarchar(30),
	idPromotion int,
	constraint FK_STAGIAIRES_PROMOTIONS foreign key(idPromotion) references PROMOTIONS(id)
);

create table TESTS
(
	id int identity(1,1) primary key,
	libelle nvarchar(50) not null,
	seuilReussite int not null default 50,
	tempsPassage int not null default 3600,
	dateDebutDisponibilite datetime not null,
	dateFinDisponibilite datetime not null,
	idFormateur int not null,
	constraint FK_TESTS_FORMATEURS foreign key(idFormateur) references formateurs(id)
);

create table SECTIONS
(
	id int identity(1,1) primary key,
	libelle nvarchar(50) not null
);

create table TESTS_SECTIONS
(
	idTest int not null,
	idSection int not null,
	nombreQuestionAUtiliser int not null,
	numeroSectionDansTest int not null,
	constraint PK_TESTS_SECTIONS primary key(idTest,idSection)
);

create table QUESTIONS
(
	id int identity(1,1) primary key,
	enonceRiche nvarchar(1000) not null,
	cheminIllustration nvarchar(500),
	poids int not null default 1
);

create table SECTIONS_QUESTIONS
(
	idSection int not null,
	idQuestion int not null,
	numeroQuestionDansSection int not null,
	constraint PK_SECTIONS_QUESTIONS primary key(idSection,idQuestion)
);

create table REPONSES
(
	id int identity(1,1) not null,
	position int not null,
	idQuestion int not null,
	enonceRiche nvarchar(1000) not null,
	estBonne bit default 0,
	constraint PK_REPONSES primary key(id),
	constraint FK_REPONSES_QUESTIONS foreign key(idQuestion) references QUESTIONS(id),
	constraint UN_REPONSES_position_idQuestion UNIQUE(position, idQuestion)
);

create table THEMES
(
	id int identity(1,1) primary key,
	libelle nvarchar(50) not null
);

create table THEMES_QUESTIONS
(
	idTheme int not null,
	idQuestion int not null,
	constraint PK_THEMES_QUESTIONS primary key(idTheme, idQuestion),
	constraint FK_THEMES_QUESTIONS_THEMES foreign key(idTheme) references THEMES(id),
	constraint FK_THEMES_QUESTIONS_QUESTIONS foreign key(idQuestion) references QUESTIONS(id)
);

create table INSCRIPTIONS
(
	idTest int not null,
	idStagiaire int not null,
	idFormateur int not null,
	tempsRestant int not null,
	constraint PK_INSCRIPTIONS primary key (idTest,idStagiaire),
	constraint FK_INSCRIPTIONS_TESTS foreign key(idTest) references TESTS(id),
	constraint FK_INSCRIPTIONS_STAGIAIRES foreign key(idStagiaire) references stagiaires(id),
	constraint FK_INSCRIPTIONS_FORMATEURS foreign key(idFormateur) references formateurs(id)
);


create table QUESTIONS_SELECTIONNEES
(
	idTest int not null,
	idStagiaire int not null,
	idSection int not null,
	idQuestionSelectionnee int not null,
	constraint PK_QUESTIONS_SELECTIONNEES primary key(idTest,idStagiaire,idSection,idQuestionSelectionnee),
	constraint FK_QUESTIONS_SELECTIONNEES_INSCRIPTIONS foreign key(idTest,idStagiaire) references INSCRIPTIONS(idTest,idStagiaire),
	constraint FK_QUESTIONS_SELECTIONNEES_QUESTIONS foreign key(idQuestionSelectionnee) references QUESTIONS(id),
	constraint FK_QUESTIONS_SELECTIONNEES_SECTIONS foreign key(idSection) references SECTIONS(id)
);

create table REPONSES_DONNEES
(
	idTest int not null,
	idStagiaire int not null,
	idSection int not null,
	idQuestionSelectionnee int not null,
	idReponse int not null,
	constraint PK_REPONSES_DONNEES primary key(idTest,idStagiaire,idSection,idQuestionSelectionnee,idReponse),
	constraint FK_REPONSES_DONNEES_QUESTIONS_SELECTIONNEES 
		foreign key(idTest,idStagiaire,idSection,idQuestionSelectionnee) 
		references QUESTIONS_SELECTIONNEES(idTest,idStagiaire,idSection,idQuestionSelectionnee),
	constraint FK_REPONSES_DONNEES_REPONSES foreign key(idReponse) references REPONSES(id)	
);













