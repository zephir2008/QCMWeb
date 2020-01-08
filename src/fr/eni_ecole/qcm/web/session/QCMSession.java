package fr.eni_ecole.qcm.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.passage.QuestionIterator;
import fr.eni_ecole.qcm.modele.passage.TestEnCours;
/**
 * {@link QCMSession} est une classe abstraite proposant des méthodes statiques permettant de gérer les éléments mis en Session.
 * */
public abstract class QCMSession {
	private static HttpSession session;
	
	private static String AttFormateur="formateur";
	private static String AttStagiaire="stagiaire";
	private static String AttTestEnCours="testEnCours";
	private static String AttQuestionIterator="questionIterator";
	
	/**
	 * Permet d'initialiser une {@link HttpSession}
	 * */
	public static void createIfNotExist(HttpServletRequest request)
	{
		session = request.getSession();
	}
	/**
	 * Permet de supprimer l'ensemble des éléments mis en cache dans la session:
	 * -L'objet {@link Formateur}
	 * -L'objet {@link Stagiaire}
	 * -L'objet {@link TestEnCours}
	 * -L'objet {@link QuestionIterator}
	 * */
	public static void viderSession()
	{
		session.removeAttribute(AttFormateur);
		session.removeAttribute(AttStagiaire);
		session.removeAttribute(AttTestEnCours);
		session.removeAttribute(AttQuestionIterator);
	}
	/**
	 * Permet de savoir si la personne connectée est un {@link Formateur}
	 * */
	public static boolean isFormateur()
	{
		boolean retour = false;
		Object formateur = session.getAttribute(AttFormateur);
		if(formateur!=null && formateur instanceof Formateur)
		{
			retour=true;
		}
		return retour;
	}
	/**
	 * Permet de savoir si la personne connectée est un {@link Stagiaire}
	 * */
	public static boolean isStagiaire()
	{
		boolean retour = false;
		Object stagiaire = session.getAttribute(AttStagiaire);
		if(stagiaire!=null && stagiaire instanceof Stagiaire)
		{
			retour=true;
		}
		return retour;
	}
	/**
	 * Permet de mettre en cache le {@link Formateur}
	 * */
	public static void setFormateur(Formateur formateur)
	{
		session.setAttribute(AttFormateur, formateur);
	}
	/**
	 * Permet de mettre en cache le {@link Stagiaire}
	 * */
	public static void setStagiaire(Stagiaire stagiaire)
	{
		session.setAttribute(AttStagiaire, stagiaire);
	}
	/**
	 * Permet de récupérer le {@link Formateur} en cache
	 * */
	public static Formateur getFormateur()
	{
		return (Formateur)session.getAttribute(AttFormateur);
	}
	/**
	 * Permet de récupérer le {@link Stagiaire} en cache
	 * */
	public static Stagiaire getStagiaire()
	{
		return (Stagiaire)session.getAttribute(AttStagiaire);
	}
	/**
	 * Permet de mettre en cache le {@link TestEnCours}
	 * @param testEnCours
	 */
	public static void setTestEnCours(TestEnCours testEnCours) {
		session.setAttribute(AttTestEnCours, testEnCours);
		session.setAttribute(AttQuestionIterator, null);
	}
	/**
	 * Permet de récupérer le {@link TestEnCours}
	 * */
	public static TestEnCours getTestEnCours()
	{
		return (TestEnCours)session.getAttribute(AttTestEnCours);
	}
	/**
	 * Permet de mettre en cache l'iterateur de question ({@link QuestionIterator}) actuellement utilisé
	 * @param questionIterator
	 */
	public static void setQuestionIterator(QuestionIterator questionIterator) {
		session.setAttribute(AttQuestionIterator, questionIterator);
	}
	/**
	 * Permet de récupérer l'itérateur de question actuellement utilisé
	 * @return
	 */
	public static QuestionIterator getQuestionIterator() {
		return (QuestionIterator)session.getAttribute(AttQuestionIterator);
	}
}
