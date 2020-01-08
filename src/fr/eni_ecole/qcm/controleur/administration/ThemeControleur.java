package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.ThemeDAO;
import fr.eni_ecole.qcm.modele.administration.Theme;


public class ThemeControleur {
	
	public static Theme valider(int id, String libelle) throws SQLException
	{
		if(id>0)//modification
		{
			return ThemeDAO.mettreAJour(id, libelle);
		}
		else//ajout
		{
			return ThemeDAO.ajouter(libelle);
		}
	}
	public static void supprimer(int id) throws SQLException
	{
		ThemeDAO.supprimer(id);
	}
	
	public static List<Theme> getListeThemes() throws SQLException
	{
		return ThemeDAO.getListeThemes();
	}
	public static Theme getTheme(int id) throws SQLException {
		return ThemeDAO.getTheme(id);
	}
}
