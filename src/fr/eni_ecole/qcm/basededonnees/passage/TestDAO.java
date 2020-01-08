package fr.eni_ecole.qcm.basededonnees.passage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Reponse;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;

public class TestDAO {
	private final static String SELECT_FOR_STAGIAIRE=
				"select id, libelle, seuilReussite, tempsPassage,tempsRestant,dateDebutDisponibilite,dateFinDisponibilite "+
				"from TESTS t "+
				"inner join INSCRIPTIONS i on t.id=i.idTest "+
				"where i.idStagiaire=? and dateDebutDisponibilite<=? and dateFinDisponibilite>=? and tempsRestant>0";
	
	private final static String SELECT_SECTIONS_QUESTIONS_REPONSES=
			"select" +
			"	t.id as idTest,"+
			"	ts.nombreQuestionAUtiliser,"+
			"	ts.numeroSectionDansTest,"+
			"	s.id as idSection,"+
			"	s.libelle as libelleSection," +
			"	sq.numeroQuestionDansSection,"+
			"	q.id as idQuestion,"+
			"	q.cheminIllustration,"+
			"	q.enonceRiche as enonceQuestion,"+
			"	q.poids,"+
			"	r.enonceRiche as enonceReponse,"+
			"	r.estBonne,"+
			"	r.id as idReponse,"+
			"	r.position as positionReponse"+
			" from" +
			"	TESTS t"+
			"	inner join TESTS_SECTIONS ts on t.id=ts.idTest"+
			"	inner join SECTIONS s on ts.idSection=s.id"+
			"	inner join SECTIONS_QUESTIONS sq on s.id=sq.idSection"+
			"	inner join QUESTIONS q on sq.idQuestion=q.id"+
			"	inner join REPONSES r on q.id=r.idQuestion"+
			" where"+
			"	t.id=?" +
			" order by numeroSectionDansTest,numeroQuestionDansSection,positionReponse;";
	
	public static List<Test> getListeTests(Stagiaire stagiaire, Date aujourdhui) throws SQLException
	{
		List<Test> liste = new ArrayList<Test>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_STAGIAIRE);
		pstmt.setInt(1, stagiaire.getId());
		pstmt.setDate(2, new java.sql.Date(aujourdhui.getTime()));
		pstmt.setDate(3, new java.sql.Date(aujourdhui.getTime()));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			int seuilReussite = rs.getInt("seuilReussite");
			int tempsPassage = rs.getInt("tempsPassage");
			int tempsRestant = rs.getInt("tempsRestant");
			if(tempsRestant<tempsPassage)
			{
				libelle+=" (en cours)";
			}
			Date dateDebutDisponibilite = rs.getDate("dateDebutDisponibilite");
			Date dateFinDisponibilite = rs.getDate("dateFinDisponibilite");
			liste.add(new Test(id,libelle, seuilReussite,tempsPassage,dateDebutDisponibilite,dateFinDisponibilite));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static void chargerSectionsQuestionsReponses(Test testChoisi) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_SECTIONS_QUESTIONS_REPONSES);
		pstmt.setInt(1, testChoisi.getId());
		ResultSet rs = pstmt.executeQuery();
		Section sectionCourante=new Section();
		Question questionCourante=new Question();
		while(rs.next())
		{
			int idSection = rs.getInt("idSection");
			if(idSection!=sectionCourante.getId())
			{
				String libelleSection = rs.getString("libelleSection");
				int numeroSectionDansTest = rs.getInt("numeroSectionDansTest");
				int nombreQuestionAUtiliser = rs.getInt("nombreQuestionAUtiliser");
				sectionCourante = new Section(idSection, libelleSection, numeroSectionDansTest, nombreQuestionAUtiliser);
				testChoisi.getListeSection().add(sectionCourante);
			}
			int idQuestion = rs.getInt("idQuestion");
			if(idQuestion!=questionCourante.getId())
			{
				String enonceQuestion = rs.getString("enonceQuestion");
				String cheminIllustration = rs.getString("cheminIllustration");
				int poids = rs.getInt("poids");
				questionCourante = new Question(idQuestion, enonceQuestion, cheminIllustration, poids);
				sectionCourante.getListeQuestion().add(questionCourante);
			}
			int idReponse = rs.getInt("idReponse");
			String enonceReponse = rs.getString("enonceReponse");
			boolean estBonne = rs.getBoolean("estBonne");
			questionCourante.getListeReponse().add(new Reponse(idReponse, enonceReponse, estBonne));
		}
		rs.close();
		pstmt.close();
		cnx.close();
	}
}
