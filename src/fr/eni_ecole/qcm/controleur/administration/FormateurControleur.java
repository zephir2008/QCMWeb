package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.FormateurDAO;
import fr.eni_ecole.qcm.modele.administration.Formateur;

public class FormateurControleur {
	
	public static Formateur valider(int id, String nom, String prenom, String courriel, String login, String motDePasse, boolean estResponsable) throws SQLException
	{
		if(id>0)//modification
		{
			return FormateurDAO.mettreAJour(id, nom, prenom, courriel, login, motDePasse, estResponsable);
		}
		else//ajout
		{
			return FormateurDAO.ajouter(nom, prenom, courriel, login, motDePasse, estResponsable);
		}
	}
	public static void supprimer(int id) throws SQLException
	{
		FormateurDAO.supprimer(id);
	}
	
	public static List<Formateur> getListFormateurs() throws SQLException
	{
		return FormateurDAO.getListeFormateurs();
	}
	public static Formateur getFormateur(int id) throws SQLException {
		return FormateurDAO.getFormateur(id);
	}
}
