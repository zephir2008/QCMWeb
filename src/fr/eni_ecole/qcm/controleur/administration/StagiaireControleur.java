package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.StagiaireDAO;
import fr.eni_ecole.qcm.modele.administration.Promotion;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;

public class StagiaireControleur {
	
	public static Stagiaire valider(int id, String nom, String prenom, String courriel, String login, String motDePasse, Promotion promotion) throws SQLException
	{
		if(id>0)//modification
		{
			return StagiaireDAO.mettreAJour(id, nom, prenom, courriel, login, motDePasse, promotion);
		}
		else//ajout
		{
			return StagiaireDAO.ajouter(nom, prenom, courriel, login, motDePasse, promotion);
		}
	}
	public static void supprimer(int id) throws SQLException
	{
		StagiaireDAO.supprimer(id);
	}
	
	public static List<Stagiaire> getListeStagiaires(Promotion unePromotion) throws SQLException
	{
		return StagiaireDAO.getListeStagiaires(unePromotion);
	}
	public static List<Stagiaire> getListeStagiaires(Test unTest) throws SQLException
	{
		return StagiaireDAO.getListeStagiaires(unTest);
	}
	public static Stagiaire getStagiaire(int id) throws SQLException {
		return StagiaireDAO.getStagiaire(id);
	}
}
