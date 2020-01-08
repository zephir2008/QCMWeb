package fr.eni_ecole.qcm.basededonnees.administration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.modele.administration.Promotion;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;

public class StagiaireDAO {
	
	private final static String INSERT="{?=call AJOUTER_STAGIAIRE(?, ?, ?, ?, ?, ?)}";
	private final static String SELECT="select nom, prenom, courriel, login, motDePasse, idPromotion, libelle as promotion from stagiaires s left outer join PROMOTIONS p on  s.idPromotion=p.id where s.id=?";
	private final static String SELECT_ALL="select s.id, nom, prenom, courriel, login, motDePasse, idPromotion, libelle as promotion from stagiaires s left outer join PROMOTIONS p on  s.idPromotion=p.id where p.id=?";
	private final static String SELECT_FOR_TEST="select s.id, nom, prenom, courriel, login, motDePasse, idPromotion, libelle as promotion from stagiaires s left outer join PROMOTIONS p on  s.idPromotion=p.id inner join INSCRIPTIONS i on s.id=i.idStagiaire where i.idTest=?";
	private final static String UPDATE="update stagiaires set nom=?, prenom=?, courriel=?, login=?, motDePasse=?, idPromotion=? where id=?";
	private final static String DELETE="delete from stagiaires where id=?";
	
	public static Stagiaire ajouter(String nom, String prenom, String courriel, String login, String motDePasse, Promotion promotion) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, nom);
		cstmt.setString(3, prenom);
		cstmt.setString(4, courriel);
		cstmt.setString(5, login);
		cstmt.setString(6, motDePasse);
		if(promotion!=null)
		{
			cstmt.setInt(7, promotion.getId());
		}
		else
		{
			cstmt.setNull(7, java.sql.Types.INTEGER);
		}
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return StagiaireDAO.getStagiaire(id);
	}

	public static Stagiaire mettreAJour(int id, String nom, String prenom,
			String courriel, String login, String motDePasse, Promotion promotion) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, nom);
		cstmt.setString(2, prenom);
		cstmt.setString(3, courriel);
		cstmt.setString(4, login);
		cstmt.setString(5, motDePasse);
		if(promotion!=null)
		{
			cstmt.setInt(6, promotion.getId());	
		}
		else
		{
			cstmt.setNull(6, java.sql.Types.INTEGER);
		}	
		cstmt.setInt(7, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return StagiaireDAO.getStagiaire(id);
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
	
	public static Stagiaire getStagiaire(int id) throws SQLException
	{
		Stagiaire unStagiaire=null;
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
				Promotion promotion = new Promotion(rs.getInt("idPromotion"),rs.getString("promotion"));
				unStagiaire = new Stagiaire(id, nom, prenom, courriel,promotion, login, motDePasse);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return unStagiaire;
	}
	
	public static List<Stagiaire> getListeStagiaires(Promotion unePromotion) throws SQLException
	{
		List<Stagiaire> liste = new ArrayList<Stagiaire>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		if(unePromotion.getId()>0)
		{
			pstmt.setInt(1, unePromotion.getId());
		}
		else
		{
			pstmt.setNull(1, java.sql.Types.INTEGER);
		}
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String courriel = rs.getString("courriel");
			String login = rs.getString("login");
			String motDePasse = rs.getString("motDePasse");
			Promotion promotion = new Promotion(rs.getInt("idPromotion"),rs.getString("promotion"));
			liste.add(new Stagiaire(id, nom, prenom, courriel,promotion, login, motDePasse));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static List<Stagiaire> getListeStagiaires(Test unTest) throws SQLException {
		List<Stagiaire> liste = new ArrayList<Stagiaire>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_TEST);
		if(unTest.getId()>0)
		{
			pstmt.setInt(1, unTest.getId());
		}
		else
		{
			pstmt.setNull(1, java.sql.Types.INTEGER);
		}
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String courriel = rs.getString("courriel");
			String login = rs.getString("login");
			String motDePasse = rs.getString("motDePasse");
			Promotion promotion = new Promotion(rs.getInt("idPromotion"),rs.getString("promotion"));
			liste.add(new Stagiaire(id, nom, prenom, courriel,promotion, login, motDePasse));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
}
