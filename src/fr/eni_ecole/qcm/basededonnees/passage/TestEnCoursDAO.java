package fr.eni_ecole.qcm.basededonnees.passage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Reponse;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;
import fr.eni_ecole.qcm.modele.passage.QuestionSelectionnee;
import fr.eni_ecole.qcm.modele.passage.ReponseTraitee;
import fr.eni_ecole.qcm.modele.passage.SectionEnCours;
import fr.eni_ecole.qcm.modele.passage.TestEnCours;

public class TestEnCoursDAO {

	public static final String SELECT_EST_EN_COURS ="select idTest, idStagiaire from QUESTIONS_SELECTIONNEES where idTest=? and idStagiaire=?";
	
	public static final String SELECT_TEST_EN_COURS =""+
								"select " +
								"/*info table questions_selectionnees*/ "+
								"qs.idTest, qs.idStagiaire, qs.idSection, qs.idQuestionSelectionnee, "+
								"/*info table tests*/ "+
								"t.libelle as libelleTest,t.seuilReussite,t.tempsPassage,t.dateDebutDisponibilite,t.dateFinDisponibilite, "+
								"/*info table sections*/ "+
								"s.libelle as libelleSection, ts.numeroSectionDansTest, ts.nombreQuestionAUtiliser, "+
								"/*info table questions*/ "+
								"q.enonceRiche as enonceQuestion,q.cheminIllustration,q.poids, "+
								"/*info table reponses*/ "+
								"r.id as idReponse, r.enonceRiche as enonceReponse,r.estBonne,r.position, "+
								"/*info table inscriptions*/ "+
								"i.tempsRestant, "+
								"/*info r�ponse donn�es*/ "+
								"case when (rd.idReponse is not null) then 1 else 0 end as reponse "+
								"from QUESTIONS_SELECTIONNEES qs "+
								"inner join TESTS t on qs.idTest=t.id "+
								"inner join SECTIONS s on qs.idSection=s.id "+
								"inner join QUESTIONS q on qs.idQuestionSelectionnee=q.id "+
								"inner join REPONSES r on q.id=r.idQuestion "+
								"/*info tempsRestant*/ "+
								"inner join INSCRIPTIONS i on qs.idTest=i.idTest and qs.idStagiaire=i.idStagiaire "+
								"/*info r�ponses d�j� donn�es*/ "+
								"left outer join REPONSES_DONNEES rd on qs.idTest=rd.idTest and qs.idStagiaire=rd.idStagiaire and qs.idSection=rd.idSection and qs.idQuestionSelectionnee=rd.idQuestionSelectionnee and r.id=rd.idReponse "+
								"/*jointures pour tris*/ "+
								"inner join TESTS_SECTIONS ts on qs.idTest=ts.idTest and qs.idSection=ts.idSection "+
								"inner join SECTIONS_QUESTIONS sq on qs.idSection=sq.idSection and qs.idQuestionSelectionnee=sq.idQuestion "+
								"where qs.idTest=? and qs.idStagiaire=? "+
								"order by ts.numeroSectionDansTest, sq.numeroQuestionDansSection,r.position";
	
	public static final String INSERT_QUESTION_CHOISIE ="insert into QUESTIONS_SELECTIONNEES(idTest,idStagiaire,idSection,idQuestionSelectionnee) values(?,?,?,?)";
	public static final String INSERT_REPONSES_CHOISIES ="insert into REPONSES_DONNEES(idTest,idStagiaire,idSection,idQuestionSelectionnee,idReponse) values(?,?,?,?,?)";
	public static final String DELETE_REPONSES_CHOISIES_FOR_QUESTIONS="delete from REPONSES_DONNEES where idTest=? and idStagiaire=? and idSection=? and idQuestionSelectionnee=?";
	public static final String UPDATE_CLOTURE="update INSCRIPTIONS set tempsRestant=0 where idTest=? and idStagiaire=?";
	public static final String UPDATE_TEMPS_RESTANT="update INSCRIPTIONS set tempsRestant=? where idTest=? and idStagiaire=?";
	
	public static boolean estDejaEnCours(Test testChoisi, Stagiaire stagiaire) throws SQLException {
		boolean retour=false;
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_EST_EN_COURS);
		pstmt.setInt(1, testChoisi.getId());
		pstmt.setInt(2,stagiaire.getId());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			retour=true;
		}
		rs.close();
		pstmt.close();
		cnx.close();
		
		return retour;
	}

	public static TestEnCours getTestEnCours(Test testChoisi,
			Stagiaire stagiaire) throws SQLException {
		TestEnCours retour=null;
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_TEST_EN_COURS);
		pstmt.setInt(1, testChoisi.getId());
		pstmt.setInt(2,stagiaire.getId());
		ResultSet rs = pstmt.executeQuery();
		int tempsRestant=0;
		Section sectionCourante=new Section();
		Question questionCourante=new Question();
		List<ReponseTraitee> listeReponseDejaSelectionnee = new ArrayList<ReponseTraitee>();
		while(rs.next())
		{
			tempsRestant=rs.getInt("tempsRestant");
			int idSection = rs.getInt("idSection");
			if(idSection!=sectionCourante.getId())
			{
				String libelleSection = rs.getString("libelleSection");
				int numeroSectionDansTest = rs.getInt("numeroSectionDansTest");
				int nombreQuestionAUtiliser = rs.getInt("nombreQuestionAUtiliser");
				sectionCourante = new Section(idSection, libelleSection, numeroSectionDansTest, nombreQuestionAUtiliser);
				testChoisi.getListeSection().add(sectionCourante);
			}
			int idQuestion = rs.getInt("idQuestionSelectionnee");
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
			Reponse reponseCourante = new Reponse(idReponse, enonceReponse, estBonne);
			questionCourante.getListeReponse().add(reponseCourante);
			//Alimenter une liste des r�ponses d�j� donn�es afin de les charger ensuite dans le testEnCours
			if((rs.getInt("reponse")==1))
			{
				ReponseTraitee rt = new ReponseTraitee(reponseCourante);
				rt.setSelectionnee(true);
				listeReponseDejaSelectionnee.add(rt);
			}
		}
		retour= new TestEnCours(testChoisi, tempsRestant);
		retour.chargerReponsesDejaDonnees(listeReponseDejaSelectionnee);
		rs.close();
		pstmt.close();
		cnx.close();
		return retour;
	}

	public static void sauvegarderQuestionsChoisies(Test testChoisi,
			Stagiaire stagiaire) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT_QUESTION_CHOISIE);
		for(Section s : testChoisi.getListeSection())
		{
			for(Question q:s.getListeQuestion())
			{
				cstmt.setInt(1, testChoisi.getId());
				cstmt.setInt(2, stagiaire.getId());
				cstmt.setInt(3, s.getId());
				cstmt.setInt(4, q.getId());
				cstmt.executeUpdate();
			}
		}
		cstmt.close();
		cnx.close();
	}

	public static void sauvegarderReponsesChoisies(TestEnCours testEnCours,SectionEnCours sectionEnCours, QuestionSelectionnee qs,
			Stagiaire stagiaire) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		//Suppression des anciennes r�ponses
		CallableStatement cstmt  = cnx.prepareCall(DELETE_REPONSES_CHOISIES_FOR_QUESTIONS);
		cstmt.setInt(1, testEnCours.getLeTestAssocie().getId());
		cstmt.setInt(2, stagiaire.getId());
		cstmt.setInt(3, sectionEnCours.getLaSectionAssociee().getId());
		cstmt.setInt(4, qs.getLaQuestionAssociee().getId());
		cstmt.executeUpdate();
		
		//Ajout des nouvelles r�ponses
		cstmt  = cnx.prepareCall(INSERT_REPONSES_CHOISIES);
			for(ReponseTraitee rt:qs.getListeReponseTraitee())
			{
				if(rt.estSelectionnee())
				{
					cstmt.setInt(1, testEnCours.getLeTestAssocie().getId());
					cstmt.setInt(2, stagiaire.getId());
					cstmt.setInt(3, sectionEnCours.getLaSectionAssociee().getId());
					cstmt.setInt(4, qs.getLaQuestionAssociee().getId());
					cstmt.setInt(5, rt.getLaReponseAssociee().getId());
					cstmt.executeUpdate();
				}
			}
		cstmt.close();
		cnx.close();
		
	}

	public static void cloturer(TestEnCours testEnCours, Stagiaire stagiaire) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE_CLOTURE);
		cstmt.setInt(1, testEnCours.getLeTestAssocie().getId());
		cstmt.setInt(2, stagiaire.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		
	}

	public static void mettreAJourTempsRestant(TestEnCours testEnCours,
			Stagiaire stagiaire) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE_TEMPS_RESTANT);
		cstmt.setInt(1, testEnCours.getChronometre().getTempsRestantEnMinute());
		cstmt.setInt(2, testEnCours.getLeTestAssocie().getId());
		cstmt.setInt(3, stagiaire.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}

}
