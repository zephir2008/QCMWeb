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

public class PromotionDAO {
	
	private final static String INSERT="{?=call AJOUTER_PROMOTION(?)}";
	private final static String SELECT="select libelle from PROMOTIONS where id=?";
	private final static String SELECT_ALL="select id, libelle from PROMOTIONS";
	private final static String UPDATE="update PROMOTIONS set libelle=? where id=?";
	private final static String DELETE="delete from PROMOTIONS where id=?";
	
	public static Promotion ajouter(String libelle) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, libelle);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return PromotionDAO.getPromotion(id);
	}

	public static Promotion mettreAJour(int id, String libelle) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, libelle);
		cstmt.setInt(2, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return PromotionDAO.getPromotion(id);
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
	
	public static Promotion getPromotion(int id) throws SQLException
	{
		Promotion unePromotion=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String libelle = rs.getString("libelle");
				unePromotion = new Promotion(id, libelle);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return unePromotion;
	}
	
	public static List<Promotion> getListePromotions() throws SQLException
	{
		List<Promotion> liste = new ArrayList<Promotion>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			liste.add(new Promotion(id, libelle));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
}
