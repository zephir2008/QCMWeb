package fr.eni_ecole.qcm.basededonnees.administration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Theme;

public class QuestionDAO {
	private final static String INSERT="{?=call AJOUTER_QUESTION(?, ?, ?)}";
	private final static String INSERT_ASSOCIATION_THEME="{call AJOUTER_THEME_QUESTION(?, ?)}";
	private final static String SELECT="select enonceRiche, cheminIllustration, poids from QUESTIONS where id=?";
	private final static String SELECT_ALL="select id, enonceRiche, cheminIllustration, poids from QUESTIONS";
	private final static String SELECT_FOR_THEME="select id, enonceRiche, cheminIllustration, poids from QUESTIONS q inner join THEMES_QUESTIONS tq on q.id=tq.idQuestion where tq.idTheme=?";
	private final static String SELECT_FOR_SECTION="select id, enonceRiche, cheminIllustration, poids from QUESTIONS q inner join SECTIONS_QUESTIONS sq on q.id=sq.idQuestion where sq.idSection=? order by numeroQuestionDansSection";
	private final static String UPDATE="update QUESTIONS set enonceRiche=?, cheminIllustration=?, poids=? where id=?";
	private final static String DELETE="delete from QUESTIONS where id=?";
	private final static String DELETE_ASSOCIATION_THEME="delete from THEMES_QUESTIONS where idQuestion=?";
	
	public static Question ajouter(String enonceRiche, String cheminIllustration, int poids) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, enonceRiche);
		if(cheminIllustration!="" && cheminIllustration!=null)
		{
			cstmt.setString(3, cheminIllustration);	
		}
		else
		{
			cstmt.setNull(3, java.sql.Types.VARCHAR);
		}
		cstmt.setInt(4, poids);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return QuestionDAO.getQuestion(id);
	}

	public static Question mettreAJour(int id, String enonceRiche, String cheminIllustration, int poids) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, enonceRiche);
		cstmt.setString(2, cheminIllustration);
		cstmt.setInt(3, poids);
		cstmt.setInt(4, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return QuestionDAO.getQuestion(id);
	}

	public static void supprimer(int id) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(DELETE);
		cstmt.setInt(1, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}
	
	public static Question getQuestion(int id) throws SQLException
	{
		Question uneQuestion=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String enonceRiche = rs.getString("enonceRiche");
				String cheminIllustration = rs.getString("cheminIllustration");
				int poids = rs.getInt("poids");
				uneQuestion = new Question(id,enonceRiche, cheminIllustration, poids);
				uneQuestion.getListeReponse().addAll(ReponseDAO.getReponses(uneQuestion));
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return uneQuestion;
	}
	
	public static List<Question> getListeQuestions() throws SQLException
	{
		List<Question> liste = new ArrayList<Question>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String enonceRiche = rs.getString("enonceRiche");
			String cheminIllustration = rs.getString("cheminIllustration");
			int poids = rs.getInt("poids");
			Question uneQuestion =new Question(id, enonceRiche, cheminIllustration, poids);
			uneQuestion.getListeReponse().addAll(ReponseDAO.getReponses(uneQuestion));
			liste.add(uneQuestion);
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static List<Question> getListeQuestions(Theme theme) throws SQLException {
		List<Question> liste = new ArrayList<Question>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_THEME);
		pstmt.setInt(1, theme.getId());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String enonceRiche = rs.getString("enonceRiche");
			String cheminIllustration = rs.getString("cheminIllustration");
			int poids = rs.getInt("poids");
			Question uneQuestion =new Question(id, enonceRiche, cheminIllustration, poids);
			uneQuestion.getListeReponse().addAll(ReponseDAO.getReponses(uneQuestion));
			liste.add(uneQuestion);
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
	public static List<Question> getListeQuestions(Section section) throws SQLException {
		List<Question> liste = new ArrayList<Question>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_SECTION);
		pstmt.setInt(1, section.getId());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String enonceRiche = rs.getString("enonceRiche");
			String cheminIllustration = rs.getString("cheminIllustration");
			int poids = rs.getInt("poids");
			Question uneQuestion =new Question(id, enonceRiche, cheminIllustration, poids);
			uneQuestion.getListeReponse().addAll(ReponseDAO.getReponses(uneQuestion));
			liste.add(uneQuestion);
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static void associerTheme(Question uneQuestion, Theme unTheme) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT_ASSOCIATION_THEME);
		cstmt.setInt(1, unTheme.getId());
		cstmt.setInt(2, uneQuestion.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}

	public static void dissocierTheme(Question uneQuestion) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(DELETE_ASSOCIATION_THEME);
		cstmt.setInt(1, uneQuestion.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}
}
