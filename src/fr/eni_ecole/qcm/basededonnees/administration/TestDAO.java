package fr.eni_ecole.qcm.basededonnees.administration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Test;

public class TestDAO{

	private final static String INSERT="{?=call AJOUTER_TEST(?, ?, ?, ?, ?, ?)}";
	private final static String INSERT_ASSOCIATION_SECTION="{call AJOUTER_TEST_SECTION(?, ?, ?, ?)}";
	private final static String SELECT="select libelle,seuilReussite,tempsPassage,dateDebutDisponibilite,dateFinDisponibilite from TESTS where id=?";
	private final static String SELECT_ALL="select id, libelle, seuilReussite, tempsPassage,dateDebutDisponibilite,dateFinDisponibilite from TESTS";
	private final static String UPDATE="update TESTS set libelle=?, seuilReussite=?, tempsPassage=?,  dateDebutDisponibilite=?, dateFinDisponibilite=?, idFormateur=? where id=?";
	private final static String DELETE="delete from TESTS where id=?";
	private final static String DELETE_ASSOCIATION_SECTION="delete from TESTS_SECTIONS where idTest=?";
	
	public static Test ajouter(String libelle, int seuilReussite, int tempsPassage, Date dateDebutDisponibilite, Date dateFinDisponibilite, Formateur formateur) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, libelle);
		cstmt.setInt(3, seuilReussite);
		cstmt.setInt(4, tempsPassage);
		cstmt.setDate(5, new java.sql.Date(dateDebutDisponibilite.getTime()));
		cstmt.setDate(6, new java.sql.Date(dateFinDisponibilite.getTime()));
		cstmt.setInt(7, formateur.getId());
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return TestDAO.getTest(id);
	}

	public static Test mettreAJour(int id,String libelle, int seuilReussite, int tempsPassage, Date dateDebutDisponibilite, Date dateFinDisponibilite, Formateur formateur) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, libelle);
		cstmt.setInt(2, seuilReussite);
		cstmt.setInt(3, tempsPassage);
		cstmt.setDate(4, new java.sql.Date(dateDebutDisponibilite.getTime()));
		cstmt.setDate(5, new java.sql.Date(dateFinDisponibilite.getTime()));
		cstmt.setInt(6, formateur.getId());
		cstmt.setInt(7, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return TestDAO.getTest(id);
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
	
	public static Test getTest(int id) throws SQLException
	{
		Test unTest=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String libelle = rs.getString("libelle");
				int seuilReussite = rs.getInt("seuilReussite");
				int tempsPassage = rs.getInt("tempsPassage");
				Date dateDebutDisponibilite = rs.getDate("dateDebutDisponibilite");
				Date dateFinDisponibilite = rs.getDate("dateFinDisponibilite");
				unTest = new Test(id,libelle, seuilReussite,tempsPassage,dateDebutDisponibilite,dateFinDisponibilite);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return unTest;
	}
	
	public static List<Test> getListeTests() throws SQLException
	{
		List<Test> liste = new ArrayList<Test>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			int seuilReussite = rs.getInt("seuilReussite");
			int tempsPassage = rs.getInt("tempsPassage");
			Date dateDebutDisponibilite = rs.getDate("dateDebutDisponibilite");
			Date dateFinDisponibilite = rs.getDate("dateFinDisponibilite");
			liste.add(new Test(id,libelle, seuilReussite,tempsPassage,dateDebutDisponibilite,dateFinDisponibilite));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static void associerSection(Test unTest, Section uneSection) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT_ASSOCIATION_SECTION);
		cstmt.setInt(1, unTest.getId());
		cstmt.setInt(2, uneSection.getId());
		cstmt.setInt(3, uneSection.getNombreQuestionAUtiliser());
		cstmt.setInt(4, uneSection.getNumero());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}

	public static void dissocierSection(Test unTest) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(DELETE_ASSOCIATION_SECTION);
		cstmt.setInt(1, unTest.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}

}
