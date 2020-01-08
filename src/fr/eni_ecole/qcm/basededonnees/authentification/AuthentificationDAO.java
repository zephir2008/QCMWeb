package fr.eni_ecole.qcm.basededonnees.authentification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;

public class AuthentificationDAO {

	private final static String SELECT_FORMATEUR="select id from formateurs where login=? and motDePasse=?";
	private final static String SELECT_STAGIAIRE="select id from stagiaires where login=? and motDePasse=?";
	
	public static int getIdFormateur(String login, String motDePasse) throws SQLException {
		int id=0;
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FORMATEUR);
		pstmt.setString(1, login);
		pstmt.setString(2, motDePasse);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			id = rs.getInt("id");
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return id;
	}

	public static int getIdStagiaire(String login, String motDePasse) throws SQLException {
		int id=0;
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_STAGIAIRE);
		pstmt.setString(1, login);
		pstmt.setString(2, motDePasse);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			id = rs.getInt("id");
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return id;
	}

}
