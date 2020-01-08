package fr.eni_ecole.qcm.web.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni_ecole.qcm.controleur.administration.FormateurControleur;
import fr.eni_ecole.qcm.controleur.administration.InscriptionControleur;
import fr.eni_ecole.qcm.controleur.administration.PromotionControleur;
import fr.eni_ecole.qcm.controleur.administration.QuestionControleur;
import fr.eni_ecole.qcm.controleur.administration.SectionControleur;
import fr.eni_ecole.qcm.controleur.administration.StagiaireControleur;
import fr.eni_ecole.qcm.controleur.administration.TestControleur;
import fr.eni_ecole.qcm.controleur.administration.ThemeControleur;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Promotion;
import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Reponse;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;
import fr.eni_ecole.qcm.modele.administration.Theme;
import fr.eni_ecole.qcm.web.session.QCMSession;

/**
 * Servlet implementation class Administration
 */
public class Administration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Administration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
			request.getParameter("type") (formateurs, promotions, stagiaires, questions, sections, tests, inscriptions)
			request.getParameter("action") (valider, supprimer, annuler, previsualiser)
		*/	
		String type = request.getParameter("type");
		String action = request.getParameter("action");
		
		try
		{
			if("formateurs".equalsIgnoreCase(type))
			{
				traiterFormateur(request, type, action);
				
			}
			else if("promotions".equalsIgnoreCase(type))
			{
				traiterPromotion(request, type, action);
			}
			else if("stagiaires".equalsIgnoreCase(type))
			{
				traiterStagiaire(request, type, action);
			}
			else if("themes".equalsIgnoreCase(type))
			{
				traiterTheme(request, type, action);
			}
			else if("questions".equalsIgnoreCase(type))
			{
				traiterQuestion(request, type, action);
			}
			else if("sections".equalsIgnoreCase(type))
			{
				traiterSection(request, type, action);
			}
			else if("tests".equalsIgnoreCase(type))
			{
				traiterTest(request, type, action);
			}
			else if("inscriptions".equalsIgnoreCase(type))
			{
				traiterInscription(request, type, action);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String message = e.getMessage();
			if(message==null)
			{
				message="Une erreur non g�r�e s'est produite";
			}
			request.setAttribute("error", message);
		}
		finally
		{
			request.getRequestDispatcher("./administration/"+request.getParameter("type")+".jsp").forward(request, response);
		}
	}

	private void traiterFormateur(HttpServletRequest request, String type, String action) throws NumberFormatException, SQLException {
		String id = request.getParameter("listeFormateur");
		if("valider".equalsIgnoreCase(action))
		{
			String nom = request.getParameter("txtNom");
			String prenom = request.getParameter("txtPrenom");
			String courriel = request.getParameter("txtCourriel");
			String login = request.getParameter("txtLogin");
			String motDePasse = request.getParameter("txtMotDePasse");
			String responsable = request.getParameter("ckEstResponsable");
			boolean estResponsable =false;
			if("on".equalsIgnoreCase(responsable))
			{
				estResponsable=true;
			}
			Formateur formateurSelectionne = FormateurControleur.valider(Integer.parseInt(id),nom, prenom, courriel, login, motDePasse,estResponsable);
			request.setAttribute("id_formateur",formateurSelectionne.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			FormateurControleur.supprimer(Integer.parseInt(id));
		}
	}
	
	private void traiterPromotion(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		String id = request.getParameter("listePromotionAdmin");
		if("valider".equalsIgnoreCase(action))
		{
			String libelle = request.getParameter("txtLibelle");
			
			Promotion promotionSelectionnee = PromotionControleur.valider(Integer.parseInt(id),libelle);
			request.setAttribute("id_promotion",promotionSelectionnee.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			PromotionControleur.supprimer(Integer.parseInt(id));
		}
	}

	private void traiterStagiaire(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		String id = request.getParameter("listeStagiaire");
		
		String idPromotion = request.getParameter("listePromotionAppartenance");
		Promotion promotion =null;
		if(idPromotion!=null)
		{
			promotion = PromotionControleur.getPromotion(Integer.parseInt(idPromotion));
		}
		
		if("valider".equalsIgnoreCase(action))
		{
			String nom = request.getParameter("txtNom");
			String prenom = request.getParameter("txtPrenom");
			String courriel = request.getParameter("txtCourriel");
			String login = request.getParameter("txtLogin");
			String motDePasse = request.getParameter("txtMotDePasse");
			
			Stagiaire stagiaireSelectionne = StagiaireControleur.valider(Integer.parseInt(id),nom, prenom, courriel, login, motDePasse,promotion);
			request.setAttribute("id_stagiaire",stagiaireSelectionne.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			StagiaireControleur.supprimer(Integer.parseInt(id));
		}
		request.setAttribute("id_promotion",promotion.getId());
	}

	private void traiterTheme(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		String id = request.getParameter("listeThemeAdmin");
		if("valider".equalsIgnoreCase(action))
		{
			String libelle = request.getParameter("txtLibelle");
			
			Theme themeSelectionnee = ThemeControleur.valider(Integer.parseInt(id),libelle);
			request.setAttribute("id_theme",themeSelectionnee.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			ThemeControleur.supprimer(Integer.parseInt(id));
		}
	}
	
	private void traiterQuestion(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		String id = request.getParameter("listeQuestion");
		
		String idTheme = request.getParameter("listeTheme");
		Theme theme =null;
		if(idTheme!=null)
		{
			theme = ThemeControleur.getTheme(Integer.parseInt(idTheme));
		}
		
		if("valider".equalsIgnoreCase(action))
		{
			String enonceRiche = request.getParameter("txtEnonceRiche");
			String cheminIllustration = request.getParameter("txtIllustration");
			String sPoids = request.getParameter("listePoids");
			
			int poids=1;
			if(sPoids!=null)
			{
				poids=Integer.parseInt(sPoids);
			}
			
			//Creation des r�ponses associ�es
			List<Reponse> listeReponse = new ArrayList<Reponse>();
			for(int i=1;i<=6;i++)
			{
				if(request.getParameter("txtReponse"+i)!=null && !request.getParameter("txtReponse"+i).equalsIgnoreCase("<br>"))
				{
					boolean estBonne=false;
					if("on".equalsIgnoreCase(request.getParameter("ckReponse"+i)))
					{
						estBonne=true;
					}
					listeReponse.add(new Reponse(0, request.getParameter("txtReponse"+i), estBonne));
				}
			}
			//Recherche des th�mes associ�es
			String[] themesAssocie = request.getParameterValues("listeThemesAssocies");
			List<Theme> listeTheme = new ArrayList<Theme>();
			if(themesAssocie!=null)
			{
				for(String s: themesAssocie)
				{
					listeTheme.add(new Theme(Integer.parseInt(s),""));
				}
			}
			
			Question questionSelectionnee = QuestionControleur.valider(Integer.parseInt(id), enonceRiche, cheminIllustration, poids,listeTheme,listeReponse);
			request.setAttribute("id_question",questionSelectionnee.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			QuestionControleur.supprimer(Integer.parseInt(id));
		}
		request.setAttribute("id_theme",theme.getId());
	}

	private void traiterSection(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		String id = request.getParameter("listeSectionAdmin");
		if("valider".equalsIgnoreCase(action))
		{
			String libelle = request.getParameter("txtLibelle");
			
			//Recherche des questions associ�es
			String[] questionsAssociees = request.getParameterValues("listeQuestionsAssociees");
			List<Question> listeQuestion = new ArrayList<Question>();
			if(questionsAssociees!=null)
			{
				for(String s: questionsAssociees)
				{
					listeQuestion.add(new Question(Integer.parseInt(s),"","",0));
				}
			}
			
			
			Section sectionSelectionnee = SectionControleur.valider(Integer.parseInt(id),libelle,listeQuestion);
			request.setAttribute("id_section",sectionSelectionnee.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			SectionControleur.supprimer(Integer.parseInt(id));
		}
	}

	private void traiterTest(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException, ParseException {
		String id = request.getParameter("listeTestAdmin");
		if("valider".equalsIgnoreCase(action)  || "dupliquer".equalsIgnoreCase(action))
		{
			String libelle = request.getParameter("txtLibelle");
			if("dupliquer".equalsIgnoreCase(action))
			{
				id="0";
				libelle = "Copie de " + libelle;
			}
			String seuilReussite = request.getParameter("listeSeuilReussite");
			String tempsPassage = request.getParameter("listeTempsPassage");
			String dateDebutDisponibilite = request.getParameter("txtDateDebutDisponibilite");
			String dateFinDisponibilite = request.getParameter("txtDateFinDisponibilite");
			Date debut = new SimpleDateFormat("dd/MM/yyyy").parse(dateDebutDisponibilite);
			Date fin = new SimpleDateFormat("dd/MM/yyyy").parse(dateFinDisponibilite);
			
			//Recherche des sections associ�es (=au moins une question demand�e)
			List<Section> listeSection = new ArrayList<Section>();
			List<Integer> ordreDesSections=getOrdreSection(request);
			for(int i=0;i<ordreDesSections.size();i++)
			{
				Section s = SectionControleur.getSection(ordreDesSections.get(i));
				String nombreQuestionAUtiliser = request.getParameter("nombreQuestionSection_"+s.getId());
				s.setNombreQuestionAUtiliser(Integer.parseInt(nombreQuestionAUtiliser));
				s.setNumero(i+1);
				listeSection.add(s);
			}
			
			Test testSelectionne = TestControleur.valider(Integer.parseInt(id),
					libelle,
					Integer.parseInt(seuilReussite), 
					Integer.parseInt(tempsPassage),
					debut,
					fin,
					QCMSession.getFormateur(),
					listeSection);
			request.setAttribute("id_test",testSelectionne.getId());
		}
		else if("supprimer".equalsIgnoreCase(action))
		{
			TestControleur.supprimer(Integer.parseInt(id));
		}
		
	}

	private List<Integer> getOrdreSection(HttpServletRequest request) {
		//retourne l'ordre des sections sous la forme: ordre=2&ordre=1
		String ordreSection = request.getParameter("txtOrdre");
		String[] ordre = ordreSection.split("ordre=");
		List<Integer> ordreFinal = new ArrayList<Integer>();
		for(int i=0;i<ordre.length;i++)
		{
			try
			{
				if(ordre[i]!="")
				{
					ordre[i]=ordre[i].replace("&", "");
					ordreFinal.add(Integer.parseInt(ordre[i]));
				}
			}
			catch(Exception e)
			{
				//do nothing
			}
		}
		return ordreFinal;
	}

	private void traiterInscription(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		if("valider".equalsIgnoreCase(action))
		{
			String id = request.getParameter("listeTest");
			Test testSelectionne = TestControleur.getTest(Integer.parseInt(id));
			String[] stagiairesAssocies = request.getParameterValues("listeStagiairesAssocies");
			List<Stagiaire> listeStagiaires = new ArrayList<Stagiaire>();
			if(stagiairesAssocies!=null && stagiairesAssocies.length>0)
			{
				for(String s: stagiairesAssocies)
				{
					listeStagiaires.add(new Stagiaire(Integer.parseInt(s), null, null, null, null, null, null));
				}
			}
			InscriptionControleur.valider(testSelectionne, listeStagiaires, QCMSession.getFormateur());
			request.setAttribute("id_test",testSelectionne.getId());
		}
		
	}

}
