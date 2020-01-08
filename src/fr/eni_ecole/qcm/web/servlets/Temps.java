package fr.eni_ecole.qcm.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.controleur.passage.TempsControleur;
import fr.eni_ecole.qcm.web.session.QCMSession;

/**
 * Servlet implementation class Temps
 */
public class Temps extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Temps() {
        super();
        // TODO Auto-generated constructor stub
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
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.write(QCMSession.getTestEnCours().getChronometre().toString());
		//Mise à jour du temps en base de données...
		//Je sais, ce n'est pas le meilleur endroit.
		try {
			TempsControleur.enregistreTempsRestant(QCMSession.getTestEnCours(),QCMSession.getStagiaire());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.write("Erreur...");
		}
		out.flush();
		out.close();
	}

}
