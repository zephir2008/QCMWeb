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
import fr.eni_ecole.qcm.modele.administration.Theme;

public class ThemeDAO {
	
	private final static String INSERT="{?=call AJOUTER_THEME(?)}";
	private final static String SELECT="select libelle from THEMES where id=?";
	private final static String SELECT_ALL="select id, libelle from THEMES";
	private final static String SELECT_FOR_QUESTION="select id, libelle from THEMES t inner join THEMES_QUESTIONS tq on t.id=tq.idTheme where idQuestion=?";
	private final static String UPDATE="update THEMES set libelle=? where id=?";
	private final static String DELETE="delete from THEMES where id=?";
	
	public static Theme ajouter(String libelle) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, libelle);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return ThemeDAO.getTheme(id);
	}

	public static Theme mettreAJour(int id, String libelle) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, libelle);
		cstmt.setInt(2, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return ThemeDAO.getTheme(id);
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
	
	public static Theme getTheme(int id) throws SQLException
	{
		Theme unTheme=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String libelle = rs.getString("libelle");
				unTheme = new Theme(id, libelle);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return unTheme;
	}
	
	public static List<Theme> getListeThemes() throws SQLException
	{
		List<Theme> liste = new ArrayList<Theme>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			liste.add(new Theme(id, libelle));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static List<Theme> getListeThemes(Question question) throws SQLException {
		List<Theme> liste = new ArrayList<Theme>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_QUESTION);
		pstmt.setInt(1, question.getId());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			liste.add(new Theme(id, libelle));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
}
