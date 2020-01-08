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
import fr.eni_ecole.qcm.modele.administration.Reponse;

public class ReponseDAO {
	
	private final static String INSERT="{?=call AJOUTER_REPONSE(?,?,?,?)}";
	private final static String SELECT="select position, enonceRiche, estBonne from REPONSES where id=?";
	private final static String SELECT_ALL="select id, position, enonceRiche, estBonne from REPONSES";
	private final static String SELECT_FOR_QUESTION="select id, position, enonceRiche, estBonne from REPONSES where idQuestion=?";
	private final static String UPDATE="update REPONSES set position=?, enonceRiche=?, estBonne=? where id=?";
	private final static String DELETE="delete from REPONSES where id=?";
	private final static String DELETE_FOR_QUESTION="delete from REPONSES where idQuestion=?";
	
	public static Reponse ajouter(String enonceRiche, int position, boolean estBonne, Question question) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setInt(2, position);
		cstmt.setInt(3, question.getId());
		cstmt.setString(4, enonceRiche);
		cstmt.setBoolean(5, estBonne);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return ReponseDAO.getReponse(id);
	}

	public static Reponse mettreAJour(int id, String enonceRiche, int position, boolean estBonne) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setInt(1, position);
		cstmt.setString(2, enonceRiche);
		cstmt.setBoolean(3, estBonne);
		cstmt.setInt(4, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return ReponseDAO.getReponse(id);
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
	
	public static void supprimer(Question question) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(DELETE_FOR_QUESTION);
		cstmt.setInt(1, question.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}
	
	public static Reponse getReponse(int id) throws SQLException
	{
		Reponse uneReponse=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String enonceRiche = rs.getString("enonceRiche");
				boolean estBonne = rs.getBoolean("estBonne");
				uneReponse = new Reponse(id, enonceRiche, estBonne);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return uneReponse;
	}
	
	public static List<Reponse> getListeReponses() throws SQLException
	{
		List<Reponse> liste = new ArrayList<Reponse>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String enonceRiche = rs.getString("enonceRiche");
			boolean estBonne = rs.getBoolean("estBonne");
			liste.add(new Reponse(id, enonceRiche, estBonne));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static List<Reponse> getReponses(Question uneQuestion) throws SQLException {
		List<Reponse> liste = new ArrayList<Reponse>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_QUESTION);
		pstmt.setInt(1, uneQuestion.getId());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String enonceRiche = rs.getString("enonceRiche");
			boolean estBonne = rs.getBoolean("estBonne");
			liste.add(new Reponse(id, enonceRiche, estBonne));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
}
