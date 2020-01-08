package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.PromotionDAO;
import fr.eni_ecole.qcm.modele.administration.Promotion;

public class PromotionControleur {
	
	public static Promotion valider(int id, String libelle) throws SQLException
	{
		if(id>0)//modification
		{
			return PromotionDAO.mettreAJour(id, libelle);
		}
		else//ajout
		{
			return PromotionDAO.ajouter(libelle);
		}
	}
	public static void supprimer(int id) throws SQLException
	{
		PromotionDAO.supprimer(id);
	}
	
	public static List<Promotion> getListPromotions() throws SQLException
	{
		return PromotionDAO.getListePromotions();
	}
	public static Promotion getPromotion(int id) throws SQLException {
		return PromotionDAO.getPromotion(id);
	}
}
