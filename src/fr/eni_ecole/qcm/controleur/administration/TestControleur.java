package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.TestDAO;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Test;


public class TestControleur {
	
	public static Test valider(int id, String libelle, int seuilReussite,int tempsPassage,Date dateDebutDisponibilite,Date dateFinDisponibilite,Formateur formateur, List<Section> listeSection) throws SQLException
	{
		Test unTest=null;
		if(id>0)//modification
		{
			unTest= TestDAO.mettreAJour(id, libelle,seuilReussite, tempsPassage, dateDebutDisponibilite, dateFinDisponibilite, formateur);
		}
		else//ajout
		{
			unTest= TestDAO.ajouter(libelle,seuilReussite, tempsPassage, dateDebutDisponibilite, dateFinDisponibilite, formateur);
		}
		
		if(unTest!=null)
		{
			//Gestion des sections
			TestDAO.dissocierSection(unTest);
			for(Section s: listeSection)
			{
				TestDAO.associerSection(unTest,s);
			}
		}
		return unTest;
	}
	public static void supprimer(int id) throws SQLException
	{
		TestDAO.supprimer(id);
	}
	
	public static List<Test> getListTests() throws SQLException
	{
		return TestDAO.getListeTests();
	}
	public static Test getTest(int id) throws SQLException {
		return TestDAO.getTest(id);
	}
}
