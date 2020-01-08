package fr.eni_ecole.qcm.controleur.passage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import fr.eni_ecole.qcm.basededonnees.passage.TestDAO;
import fr.eni_ecole.qcm.basededonnees.passage.TestEnCoursDAO;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Stagiaire;
import fr.eni_ecole.qcm.modele.administration.Test;
import fr.eni_ecole.qcm.modele.passage.Archiveur;
import fr.eni_ecole.qcm.modele.passage.Correcteur;
import fr.eni_ecole.qcm.modele.passage.QuestionSelectionnee;
import fr.eni_ecole.qcm.modele.passage.ReponseTraitee;
import fr.eni_ecole.qcm.modele.passage.SectionEnCours;
import fr.eni_ecole.qcm.modele.passage.TestEnCours;

public class TestEnCoursControleur 
{

	public static TestEnCours demarrerOuReprendreTest(Test testChoisi,
			Stagiaire stagiaire) throws SQLException {
		TestEnCours retour=null;
		if(TestEnCoursDAO.estDejaEnCours(testChoisi, stagiaire))
		{
			retour = TestEnCoursDAO.getTestEnCours(testChoisi,stagiaire);
		}
		else
		{
			retour =TestEnCoursControleur.genererNouveauTestEnCours(testChoisi,stagiaire);
		}
		return retour;
	}

	private static TestEnCours genererNouveauTestEnCours(Test testChoisi,
			Stagiaire stagiaire) throws SQLException {
		TestDAO.chargerSectionsQuestionsReponses(testChoisi);
		TestEnCoursControleur.tirerLesQuestions(testChoisi);
		TestEnCoursDAO.sauvegarderQuestionsChoisies(testChoisi,stagiaire);
		return new TestEnCours(testChoisi, testChoisi.getTempsPassage());
	}

	private static void tirerLesQuestions(Test testChoisi) {
		for(Section s:testChoisi.getListeSection())
		{
			while(s.getListeQuestion().size()>s.getNombreQuestionAUtiliser())
			{
				//enlever une question
				int laQuestionAEnlever= new Random().nextInt(s.getListeQuestion().size());
				s.getListeQuestion().remove(laQuestionAEnlever);
			}
		}
	}

	public static void enregistrerReponses(TestEnCours testEnCours, SectionEnCours sectionEnCours,
			QuestionSelectionnee qs, String[] idReponsesSelectionnees,Stagiaire stagiaire) throws SQLException {
		for(ReponseTraitee rt :qs.getListeReponseTraitee())
		{
			rt.setSelectionnee(false);
		}
		if(idReponsesSelectionnees!=null)
		{
			for(int i=0;i<idReponsesSelectionnees.length;i++)
			{
				for(ReponseTraitee rt :qs.getListeReponseTraitee())
				{
					if(String.valueOf(rt.getLaReponseAssociee().getId()).equalsIgnoreCase(idReponsesSelectionnees[i]))
					{
						rt.setSelectionnee(true);
					}
				}
			}
		}
		TestEnCoursDAO.sauvegarderReponsesChoisies(testEnCours,sectionEnCours,qs,stagiaire);
	}

	public static void terminer(TestEnCours testEnCours, Stagiaire stagiaire, String repertoireArchivage) throws SQLException, IOException {
		//Corriger le test
		Correcteur c = new Correcteur();
		testEnCours.Accept(c);
		//Cloturer l'inscription en mettant le temps � 0
		TestEnCoursDAO.cloturer(testEnCours,stagiaire);
		//G�n�rer le pdf de suavegarde
		genererArchive(testEnCours,stagiaire, repertoireArchivage);
		//envoi resultat par mail
		envoiResultat(testEnCours,stagiaire);
	}

	private static void genererArchive(TestEnCours testEnCours, Stagiaire stagiaire, String repertoireArchivage) throws IOException {
		Archiveur archiveur = new Archiveur();
		testEnCours.Accept(archiveur);
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
		repertoireArchivage+=stagiaire.getLogin()+"_"+testEnCours.getLeTestAssocie().getId()+"_"+sdf.format(new Date())+".bak";
		FileOutputStream fos = new FileOutputStream(repertoireArchivage);
		fos.write(stagiaire.toString().getBytes());
		fos.write(archiveur.toString().getBytes());
		fos.flush();
		fos.close();
	}

	private static void envoiResultat(TestEnCours testEnCours,
			Stagiaire stagiaire) {
		// TODO Auto-generated method stub
		
	}
}
