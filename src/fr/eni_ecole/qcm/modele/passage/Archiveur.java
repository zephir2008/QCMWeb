package fr.eni_ecole.qcm.modele.passage;

import java.text.SimpleDateFormat;

import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Reponse;
import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Test;

public class Archiveur implements IVisitor {

	private StringBuffer sb=new StringBuffer();
	private String tab="     ";
	@Override
	public boolean visitTest(TestEnCours testEnCours) {
		StringBuffer sbTemp=new StringBuffer();
		Test testAssocie=testEnCours.getLeTestAssocie();
		sbTemp.append("==>Test: ").append(testAssocie.getLibelle());
		sbTemp.append("\r\n").append("-Seuil r�ussite: ").append(testAssocie.getSeuilReussite() + "%");
		sbTemp.append("\r\n").append("-Temps de passage: ").append(testAssocie.getTempsPassage() + " min.");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sbTemp.append("\r\n").append("-Dates de passage: ").append(sdf.format(testAssocie.getDateDebutDisponibilite()) + " - " + sdf.format(testAssocie.getDateFinDisponibilite()));
		sbTemp.append("\r\n").append("\r\n");
		sbTemp.append("==>R�sultat: ");
		sbTemp.append("\r\n").append(testEnCours.getNombreBonneReponse()).append(" bonnes r�ponses sur ").append(testEnCours.getNombreTotalQuestion());
		sbTemp.append("\r\n").append(testEnCours.getPourcentageBonneReponse() + "% de r�ussite.");
		if(testEnCours.getPourcentageBonneReponse()>=testAssocie.getSeuilReussite())
		{
			sbTemp.append("\r\n").append("Vous avez r�ussi");
		}
		else
		{
			sbTemp.append("\r\n").append("Vous avez echou�");
		}
		sbTemp.append("\r\n");sbTemp.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}
	
	@Override
	public boolean visitSection(SectionEnCours sectionEnCours) {
		StringBuffer sbTemp=new StringBuffer();
		Section sectionAssociee=sectionEnCours.getLaSectionAssociee();
		
		sbTemp.append("\r\n").append(tab+"-Section num�ro: ").append(sectionAssociee.getNumero());
		sbTemp.append("\r\n").append(tab+tab+"-Libell�: ").append(sectionAssociee.getLibelle());
		sbTemp.append("\r\n").append(tab+tab+"-Nombre de question � utiliser: ").append(sectionAssociee.getNombreQuestionAUtiliser());
		sbTemp.append("\r\n").append(tab+tab+"-Nombre bonnes r�ponses: ").append(sectionEnCours.getNombreBonneReponse());
		sbTemp.append("\r\n").append(tab+tab+"-Pourcentage bonnes r�ponse: ").append(sectionEnCours.getPourcentageBonneReponse());
		sbTemp.append("\r\n");sb.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}
	
	
	@Override
	public boolean visitQuestion(QuestionSelectionnee questionSelectionnee) {
		StringBuffer sbTemp=new StringBuffer();
		Question questionAssociee = questionSelectionnee.getLaQuestionAssociee();
		sbTemp.append(tab+tab+"-Question ");
		sbTemp.append("\r\n").append(tab+tab+tab+"-Enonc�: ").append(questionAssociee.getEnonceRiche());
		sbTemp.append("\r\n").append(tab+tab+tab+"-Poids: ").append(questionAssociee.getPoids());
		sbTemp.append("\r\n").append(tab+tab+tab+"-Bien r�pondue: ").append(questionSelectionnee.estQuestionBienTraitee());
		sbTemp.append("\r\n");sb.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}
	@Override
	public boolean visitReponse	(ReponseTraitee reponseTraitee) {
		StringBuffer sbTemp=new StringBuffer();
		Reponse reponse = reponseTraitee.getLaReponseAssociee();
		sbTemp.append(tab+tab+tab+"-R�ponse ");
		sbTemp.append("\r\n").append(tab+tab+tab+tab+"-Enonc�: ").append(reponse.getEnonceRiche());
		sbTemp.append("\r\n").append(tab+tab+tab+tab+"-Est bonne: ").append(reponse.estBonne());
		sbTemp.append("\r\n").append(tab+tab+tab+tab+"-Est coch�e: ").append(reponseTraitee.estSelectionnee());
		sbTemp.append("\r\n");sbTemp.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
