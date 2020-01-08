package fr.eni_ecole.qcm.basededonnees.administration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.modele.administration.Formateur;

public class FormateurDAO {
	
	private final static String INSERT="{?=call AJOUTER_FORMATEUR(?, ?, ?, ?, ?, ?)}";
	private final static String SELECT="select nom, prenom, courriel, login, motDePasse, estResponsable from formateurs where id=?";
	private final static String SELECT_ALL="select id, nom, prenom, courriel, login, motDePasse, estResponsable from formateurs";
	private final static String UPDATE="update formateurs set nom=?, prenom=?, courriel=?, login=?, motDePasse=?, estResponsable=? where id=?";
	private final static String DELETE="delete from formateurs where id=?";
	
	public static Formateur ajouter(String nom, String prenom, String courriel, String login, String motDePasse, boolean estResponsable) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, nom);
		cstmt.setString(3, prenom);
		cstmt.setString(4, courriel);
		cstmt.setString(5, login);
		cstmt.setString(6, motDePasse);
		cstmt.setBoolean(7, estResponsable);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return FormateurDAO.getFormateur(id);
	}

	public static Formateur mettreAJour(int id, String nom, String prenom,
			String courriel, String login, String motDePasse, boolean estResponsable) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, nom);
		cstmt.setString(2, prenom);
		cstmt.setString(3, courriel);
		cstmt.setString(4, login);
		cstmt.setString(5, motDePasse);
		cstmt.setBoolean(6, estResponsable);
		cstmt.setInt(7, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return FormateurDAO.getFormateur(id);
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
	
	public static Formateur getFormateur(int id) throws SQLException
	{
		Formateur unFormateur=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String courriel = rs.getString("courriel");
				String login = rs.getString("login");
				String motDePasse = rs.getString("motDePasse");
				Boolean estResponsable = rs.getBoolean("estResponsable");
				unFormateur = new Formateur(id, nom, prenom, courriel, login, motDePasse, estResponsable);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return unFormateur;
	}
	
	public static List<Formateur> getListeFormateurs() throws SQLException
	{
		List<Formateur> liste = new ArrayList<Formateur>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String courriel = rs.getString("courriel");
			String login = rs.getString("login");
			String motDePasse = rs.getString("motDePasse");
			Boolean estResponsable = rs.getBoolean("estResponsable");
			liste.add(new Formateur(id, nom, prenom, courriel, login, motDePasse, estResponsable));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
}
