package fr.eni_ecole.qcm.controleur.authentification;

import java.sql.SQLException;

import fr.eni_ecole.qcm.basededonnees.authentification.AuthentificationDAO;
import fr.eni_ecole.qcm.controleur.administration.FormateurControleur;
import fr.eni_ecole.qcm.controleur.administration.StagiaireControleur;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;

public class AuthentificationControleur {

	public static Formateur getFormateur(String login, String motDePasse) throws SQLException
	{
		Formateur formateur = null;
		int id=AuthentificationDAO.getIdFormateur(login, motDePasse);
		if( id>0)
		{
			formateur=FormateurControleur.getFormateur(id);
		}
		return formateur;
	}
	
	public static Stagiaire getStagiaire(String login, String motDePasse) throws SQLException
	{
		Stagiaire stagiaire = null;
		int id=AuthentificationDAO.getIdStagiaire(login, motDePasse);
		if(id>0)
		{
			stagiaire = StagiaireControleur.getStagiaire(id);
		}
		return stagiaire;
	}
}
