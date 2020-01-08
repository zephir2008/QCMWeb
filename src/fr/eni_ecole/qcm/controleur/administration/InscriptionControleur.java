package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.InscriptionDAO;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;


public class InscriptionControleur {
	
	public static void valider(Test unTest, List<Stagiaire> listeStagiaire, Formateur formateur) throws SQLException
	{
		InscriptionDAO.mettreAJour(unTest, listeStagiaire,formateur);
	}
}
