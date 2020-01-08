package fr.eni_ecole.qcm.basededonnees.administration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.PoolConnexion;
import fr.eni_ecole.qcm.controleur.administration.StagiaireControleur;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;

public class InscriptionDAO{

	private final static String INSERT="{call AJOUTER_INSCRIPTION(?, ?, ?, ?)}";
	private final static String DELETE="delete from INSCRIPTIONS where idTest=? and idStagiaire not in(LISTE_STAGIAIRES)";
	
	public static void mettreAJour(Test unTest, List<Stagiaire> listeStagiaire, Formateur formateur) throws SQLException 
	{
		InscriptionDAO.supprimerLesAbsents(unTest, listeStagiaire);
		InscriptionDAO.ajouterLesPresents(unTest, listeStagiaire, formateur);
	}

	private static void ajouterLesPresents(Test unTest,	List<Stagiaire> listeStagiaire, Formateur formateur) throws SQLException {
		List<Stagiaire> lesStagairesDejaInscrits = StagiaireControleur.getListeStagiaires(unTest);
		Connection cnx = PoolConnexion.getUneConnexion();
		for(Stagiaire unStagiaireAInscrire:listeStagiaire)
		{
			boolean estDejaInscrit=false;
			for(Stagiaire stagiaireDejaInscrit:lesStagairesDejaInscrits)
			{
				if(unStagiaireAInscrire.getId()==stagiaireDejaInscrit.getId())
				{
					estDejaInscrit=true;
				}
			}
			if(!estDejaInscrit)
			{
				CallableStatement cstmt  = cnx.prepareCall(INSERT);
				cstmt.setInt(1, unTest.getId());
				cstmt.setInt(2, unStagiaireAInscrire.getId());
				cstmt.setInt(3, formateur.getId());
				cstmt.setInt(4, unTest.getTempsPassage());
				cstmt.executeUpdate();
				cstmt.close();
			}
		}
		cnx.close();
	}


	private static void supprimerLesAbsents(Test unTest, List<Stagiaire> listeStagiaire) throws SQLException 
	{
		Connection cnx = PoolConnexion.getUneConnexion();
		String listeIdIstagiaire="0";
		for(Stagiaire s : listeStagiaire)
		{
			listeIdIstagiaire+=","+s.getId();
		}
		
		String REQUETE = DELETE.replaceAll("LISTE_STAGIAIRES", listeIdIstagiaire);
		CallableStatement cstmt  = cnx.prepareCall(REQUETE);
		cstmt.setInt(1, unTest.getId());
		cstmt.executeUpdate();
		cstmt.close();
		cnx.close();
	}
}
