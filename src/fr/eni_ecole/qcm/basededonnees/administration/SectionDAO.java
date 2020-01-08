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
import fr.eni_ecole.qcm.modele.administration.Test;

public class SectionDAO {
	
	private final static String INSERT="{?=call AJOUTER_SECTION(?)}";
	private final static String INSERT_ASSOCIATION_QUESTION="{call AJOUTER_SECTION_QUESTION(?, ?, ?)}";
	private final static String SELECT="select libelle from SECTIONS where id=?";
	private final static String SELECT_ALL="select id, libelle from SECTIONS";
	private final static String SELECT_FOR_TEST="select id, libelle, nombreQuestionAUtiliser, numeroSectionDansTest from SECTIONS s inner join TESTS_SECTIONS ts on s.id=ts.idSection where idTest=? order by numeroSectionDansTest";
	private final static String SELECT_NOT_IN_TEST="select id, libelle from SECTIONS s where s.id not in(select idSection from TESTS_SECTIONS where idTest=?)";
	private final static String UPDATE="update SECTIONS set libelle=? where id=?";
	private final static String DELETE="delete from SECTIONS where id=?";
	private final static String DELETE_ASSOCIATION_QUESTION="delete from SECTIONS_QUESTIONS where idSection=?";
	
	public static Section ajouter(String libelle) throws SQLException
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT);
		cstmt.setString(2, libelle);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.executeUpdate();
		int id = cstmt.getInt(1);
		cstmt.close();
		cnx.close();
		return SectionDAO.getSection(id);
	}

	public static Section mettreAJour(int id, String libelle) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(UPDATE);
		cstmt.setString(1, libelle);
		cstmt.setInt(2, id);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		return SectionDAO.getSection(id);
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
	
	public static Section getSection(int id) throws SQLException
	{
		Section uneSection=null;
		if(id>0)
		{
			Connection cnx = PoolConnexion.getUneConnexion();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				String libelle = rs.getString("libelle");
				uneSection = new Section(id, libelle);
			}
			rs.close();
			pstmt.close();
			cnx.close();
		}
		return uneSection;
	}
	
	public static List<Section> getListeSections() throws SQLException
	{
		List<Section> liste = new ArrayList<Section>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			liste.add(new Section(id, libelle));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
	
	public static List<Section> getListeSections(Test test) throws SQLException
	{
		List<Section> liste = new ArrayList<Section>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_FOR_TEST);
		pstmt.setInt(1, test.getId());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			int nombreQuestonAUtiliser = rs.getInt("nombreQuestionAUtiliser");
			int numeroSectionDansTest = rs.getInt("numeroSectionDansTest");
			liste.add(new Section(id, libelle,numeroSectionDansTest,nombreQuestonAUtiliser));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}
	
	public static List<Section> getListSectionsNonAssociees(Test test) throws SQLException {
		List<Section> liste = new ArrayList<Section>();
		Connection cnx = PoolConnexion.getUneConnexion();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_NOT_IN_TEST);
		pstmt.setInt(1, test.getId());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String libelle = rs.getString("libelle");
			liste.add(new Section(id, libelle,0,0));
		}
		rs.close();
		pstmt.close();
		cnx.close();
		return liste;
	}

	public static void dissocierQuestion(Section uneSection) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(DELETE_ASSOCIATION_QUESTION);
		cstmt.setInt(1, uneSection.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}

	public static void associerQuestion(Section uneSection, Question uneQuestion, int position) throws SQLException {
		Connection cnx = PoolConnexion.getUneConnexion();
		CallableStatement cstmt  = cnx.prepareCall(INSERT_ASSOCIATION_QUESTION);
		cstmt.setInt(1, uneSection.getId());
		cstmt.setInt(2, uneQuestion.getId());
		cstmt.setInt(3, position);
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
		
	}
}
