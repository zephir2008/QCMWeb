package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.SectionDAO;
import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Test;

public class SectionControleur {
	
	public static Section valider(int id, String libelle, List<Question> listeQuestion) throws SQLException
	{
		Section uneSection=null;
		if(id>0) //modification
		{
			uneSection= SectionDAO.mettreAJour(id, libelle);
		}
		else //ajout
		{
			uneSection = SectionDAO.ajouter(libelle);
		}
		if(uneSection!=null)
		{
			// Gestion des questions
			SectionDAO.dissocierQuestion(uneSection);
			int i=1;
			for(Question q: listeQuestion)
			{
				SectionDAO.associerQuestion(uneSection,q,i);
				i++;
			}
		}
		return uneSection;
	}
	public static void supprimer(int id) throws SQLException
	{
		SectionDAO.supprimer(id);
	}
	
	public static List<Section> getListSections() throws SQLException
	{
		return SectionDAO.getListeSections();
	}
	public static List<Section> getListSections(Test test) throws SQLException
	{
		return SectionDAO.getListeSections(test);
	}
	public static List<Section> getListSectionsNonAssociees(Test test) throws SQLException
	{
		return SectionDAO.getListSectionsNonAssociees(test);
	}
	public static Section getSection(int id) throws SQLException {
		return SectionDAO.getSection(id);
	}
}
