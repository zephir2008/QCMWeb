package fr.eni_ecole.qcm.controleur.passage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import fr.eni_ecole.qcm.basededonnees.passage.TestDAO;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;

public class TestControleur {
	public static List<Test> getListTests(Stagiaire stagiaire) throws SQLException
	{
		return TestDAO.getListeTests(stagiaire, new Date());
	}
}
