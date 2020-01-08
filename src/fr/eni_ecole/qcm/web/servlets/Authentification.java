package fr.eni_ecole.qcm.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.controleur.authentification.AuthentificationControleur;
import fr.eni_ecole.qcm.modele.administration.Formateur;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.web.session.QCMSession;

/**
 * Servlet implementation class Authentification
 */
public class Authentification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authentification() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isFormateur = false;
		try {
			if (request.getParameter("demande_formateur") != null) {
				// Une authentification formateur est demand�e
				isFormateur = true;
				afficherAuthentification(isFormateur, request, response);
			} else if (request.getParameter("demande_stagiaire") != null) {
				// Une authentification stagiaire est demand�e
				afficherAuthentification(isFormateur, request, response);
			} else if (request.getParameter("valider_formateur") != null) {
				isFormateur = true;
				validerAuthentification(isFormateur, request, response);
			} else if (request.getParameter("valider_stagiaire") != null) {
				validerAuthentification(isFormateur, request, response);
			} else {
				// Une authentification inconnue est demand�e
				request.getRequestDispatcher("./").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			if (message == null) {
				message = "Une erreur non g�r�e s'est produite";
			}
			request.setAttribute("error", message);
			afficherAuthentification(isFormateur, request, response);
		}
	}

	private void afficherAuthentification(boolean isFormateur, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("is_formateur", isFormateur);
		request.getRequestDispatcher("./authentification/authentification.jsp").forward(request, response);
	}

	private void validerAuthentification(boolean isFormatteur, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QCMSession.createIfNotExist(request);
		QCMSession.viderSession();
		String login = request.getParameter("login");
		String motDePasse = request.getParameter("mdp");
		if (isFormatteur) {
			Formateur formateur = AuthentificationControleur.getFormateur(login, motDePasse);
			if (formateur == null) {
				throw new Exception("Le login et ou le mot de passe sont erronés");
			}
			QCMSession.setFormateur(formateur);
			request.getRequestDispatcher("./administration/index.jsp").forward(request, response);
		} else {
			Stagiaire stagiaire = AuthentificationControleur.getStagiaire(login, motDePasse);
			if (stagiaire == null) {
				throw new Exception("Le login et ou le mot de passe sont erronés");
			}
			QCMSession.setStagiaire(stagiaire);
			request.getRequestDispatcher("./passage/index.jsp").forward(request, response);
		}
	}
}
