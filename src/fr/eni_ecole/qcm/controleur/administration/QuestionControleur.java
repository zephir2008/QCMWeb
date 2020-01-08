package fr.eni_ecole.qcm.controleur.administration;

import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.administration.QuestionDAO;
import fr.eni_ecole.qcm.basededonnees.administration.ReponseDAO;
import fr.eni_ecole.qcm.basededonnees.administration.ThemeDAO;
import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Reponse;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Theme;



public class QuestionControleur {
	
	public static Question valider(int id, String enonceRiche, String cheminIllustration, int poids, List<Theme> listeTheme, List<Reponse> listeReponse) throws SQLException
	{
		Question uneQuestion=null;
		if(id>0)//modification
		{
			uneQuestion= QuestionDAO.mettreAJour(id, enonceRiche, cheminIllustration, poids);
		}
		else//ajout
		{
			uneQuestion= QuestionDAO.ajouter(enonceRiche, cheminIllustration, poids);
		}
		
		if(uneQuestion!=null)
		{
			//Gestion des réponses
			ReponseDAO.supprimer(uneQuestion);
			int i=1;
			for(Reponse r:listeReponse)
			{
				if(r.getEnonceRiche()!="" && r.getEnonceRiche()!=null)
				{
					ReponseDAO.ajouter(r.getEnonceRiche(),i, r.estBonne(), uneQuestion);
					i++;
				}
			}
			//Gestion des thèmes
			QuestionDAO.dissocierTheme(uneQuestion);
			for(Theme t: listeTheme)
			{
				QuestionDAO.associerTheme(uneQuestion,t);
			}
		}
		
		return uneQuestion;
	}
	public static void supprimer(int id) throws SQLException
	{
		QuestionDAO.supprimer(id);
	}
	
	public static List<Question> getListeQuestions() throws SQLException
	{
		return QuestionDAO.getListeQuestions();
	}
	public static List<Question> getListeQuestions(Theme theme) throws SQLException
	{
		return QuestionDAO.getListeQuestions(theme);
	}
	public static List<Question> getListeQuestions(Section section) throws SQLException
	{
		return QuestionDAO.getListeQuestions(section);
	}
	public static Question getQuestion(int id) throws SQLException {
		return QuestionDAO.getQuestion(id);
	}
	public static List<Theme> getListeThemes(Question question) throws SQLException
	{
		return ThemeDAO.getListeThemes(question);
	}
}
