package fr.eni_ecole.qcm.controleur.passage;

import java.sql.SQLException;

import fr.eni_ecole.qcm.basededonnees.passage.TestEnCoursDAO;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.passage.TestEnCours;

public class TempsControleur {

	public static void enregistreTempsRestant(TestEnCours testEnCours,
			Stagiaire stagiaire) throws SQLException {
		TestEnCoursDAO.mettreAJourTempsRestant(testEnCours,stagiaire);
	}

}
