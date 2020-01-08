package fr.eni_ecole.qcm.web.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.controleur.administration.TestControleur;
import fr.eni_ecole.qcm.controleur.passage.TestEnCoursControleur;
import fr.eni_ecole.qcm.modele.administration.Test;
import fr.eni_ecole.qcm.modele.passage.IteratorEnum;
import fr.eni_ecole.qcm.modele.passage.TestEnCours;
import fr.eni_ecole.qcm.web.session.QCMSession;

/**
 * Servlet implementation class Passage
 */
public class Passage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String repertoireArchivage=null;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Passage() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
    	this.repertoireArchivage=config.getInitParameter("repertoireArchivage");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
			request.getParameter("type") (choixtest, question, recapitulatif, resultat)
			request.getParameter("action") (valider, precedente, suivante, recapitulatif)
		*/	
		String type = request.getParameter("type");
		String action = request.getParameter("action");
		try
		{
			if("choixtest".equalsIgnoreCase(type))
			{
				traiterChoixTest(request, type, action);
			}
			else if("question".equalsIgnoreCase(type))
			{
				traiterQuestion(request, type, action);
			}
			else if("recapitulatif".equalsIgnoreCase(type))
			{
				traiterRecapitulatif(request, type, action);
			}
			else if("resultat".equalsIgnoreCase(type))
			{
				traiterResultat(request, type, action);
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
			if(request.getAttribute("type")!=null)
			{
				request.getRequestDispatcher("./passage/"+request.getAttribute("type")+".jsp").forward(request, response);					
			}
			else
			{
				request.getRequestDispatcher("./passage/"+request.getParameter("type")+".jsp").forward(request, response);
			}
		}
	}

	private void traiterChoixTest(HttpServletRequest request, String type,
			String action) throws NumberFormatException, SQLException {
		if("valider".equalsIgnoreCase(action))
		{
			String idTestChoisi = request.getParameter("testChoisi");
			Test testChoisi = TestControleur.getTest(Integer.parseInt(idTestChoisi));
			TestEnCours testEnCours = TestEnCoursControleur.demarrerOuReprendreTest(testChoisi, QCMSession.getStagiaire());
			QCMSession.setTestEnCours(testEnCours);
			QCMSession.setQuestionIterator(QCMSession.getTestEnCours().createIterator(IteratorEnum.Question));
			QCMSession.getQuestionIterator().premiereQuestion();
			QCMSession.getTestEnCours().getChronometre().demarrer();
			//redirection vers la page question
			request.setAttribute("type", "question");
		}
	}

	private void traiterQuestion(HttpServletRequest request, String type,
			String action) throws SQLException {
		
		if(request.getParameter("marque")!=null)
		{
			QCMSession.getQuestionIterator().questionCourante().setMarquee(true);
		}
		else
		{
			QCMSession.getQuestionIterator().questionCourante().setMarquee(false);
		}
		//Sauvegardez les r�ponses donn�es � la question en cours.
		TestEnCoursControleur.enregistrerReponses(	QCMSession.getTestEnCours(),
													QCMSession.getQuestionIterator().sectionCourante(),
													QCMSession.getQuestionIterator().questionCourante(),
													request.getParameterValues("reponse"),
													QCMSession.getStagiaire());
		//naviguez
		if("precedente".equalsIgnoreCase(action))
		{
			QCMSession.getQuestionIterator().questionPrecedente();
			request.setAttribute("type", "contenuquestion");
		}
		else if("suivante".equalsIgnoreCase(action))
		{
			QCMSession.getQuestionIterator().questionSuivante();
			request.setAttribute("type", "contenuquestion");
		}
		else if("recapitulatif".equalsIgnoreCase(action))
		{
			QCMSession.setQuestionIterator(null);
			request.setAttribute("type", "recapitulatif");
		}
		
		//On squizze le fonctionnement normal si le chronometre est termin�
		if(QCMSession.getTestEnCours().getChronometre().estFini())
		{
			request.setAttribute("type", "resultat");
		}
	}

	private void traiterRecapitulatif(HttpServletRequest request, String type,
			String action) throws SQLException, IOException {
		if("valider".equalsIgnoreCase(action))
		{
			TestEnCoursControleur.terminer(QCMSession.getTestEnCours(),QCMSession.getStagiaire(),this.repertoireArchivage);
			request.setAttribute("type", "resultat");
		}
		else if("toutesLesQuestions".equalsIgnoreCase(action))
		{
			QCMSession.setQuestionIterator(QCMSession.getTestEnCours().createIterator(IteratorEnum.Question));
			QCMSession.getQuestionIterator().premiereQuestion();
			request.setAttribute("type", "question");
		}
		else if("lesQuestionsNonRepondues".equalsIgnoreCase(action))
		{
			QCMSession.setQuestionIterator(QCMSession.getTestEnCours().createIterator(IteratorEnum.QuestionNonRepondue));
			QCMSession.getQuestionIterator().premiereQuestion();
			request.setAttribute("type", "question");
		}
		else if("lesQuestionsMarquees".equalsIgnoreCase(action))
		{
			QCMSession.setQuestionIterator(QCMSession.getTestEnCours().createIterator(IteratorEnum.QuestionMarquee));
			QCMSession.getQuestionIterator().premiereQuestion();
			request.setAttribute("type", "question");
		}
		
		//On squizze le fonctionnement normal si le chronometre est termin�
		if(QCMSession.getTestEnCours().getChronometre().estFini())
		{
			request.setAttribute("type", "resultat");
		}
	}

	private void traiterResultat(HttpServletRequest request, String type,
			String action) {
		
		
	}

}
