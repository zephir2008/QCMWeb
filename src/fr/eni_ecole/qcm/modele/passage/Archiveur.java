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
		sbTemp.append("\r\n").append("-Seuil réussite: ").append(testAssocie.getSeuilReussite() + "%");
		sbTemp.append("\r\n").append("-Temps de passage: ").append(testAssocie.getTempsPassage() + " min.");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sbTemp.append("\r\n").append("-Dates de passage: ").append(sdf.format(testAssocie.getDateDebutDisponibilite()) + " - " + sdf.format(testAssocie.getDateFinDisponibilite()));
		sbTemp.append("\r\n").append("\r\n");
		sbTemp.append("==>Résultat: ");
		sbTemp.append("\r\n").append(testEnCours.getNombreBonneReponse()).append(" bonnes réponses sur ").append(testEnCours.getNombreTotalQuestion());
		sbTemp.append("\r\n").append(testEnCours.getPourcentageBonneReponse() + "% de réussite.");
		if(testEnCours.getPourcentageBonneReponse()>=testAssocie.getSeuilReussite())
		{
			sbTemp.append("\r\n").append("Vous avez réussi");
		}
		else
		{
			sbTemp.append("\r\n").append("Vous avez echoué");
		}
		sbTemp.append("\r\n");sbTemp.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}
	
	@Override
	public boolean visitSection(SectionEnCours sectionEnCours) {
		StringBuffer sbTemp=new StringBuffer();
		Section sectionAssociee=sectionEnCours.getLaSectionAssociee();
		
		sbTemp.append("\r\n").append(tab+"-Section numéro: ").append(sectionAssociee.getNumero());
		sbTemp.append("\r\n").append(tab+tab+"-Libellé: ").append(sectionAssociee.getLibelle());
		sbTemp.append("\r\n").append(tab+tab+"-Nombre de question à utiliser: ").append(sectionAssociee.getNombreQuestionAUtiliser());
		sbTemp.append("\r\n").append(tab+tab+"-Nombre bonnes réponses: ").append(sectionEnCours.getNombreBonneReponse());
		sbTemp.append("\r\n").append(tab+tab+"-Pourcentage bonnes réponse: ").append(sectionEnCours.getPourcentageBonneReponse());
		sbTemp.append("\r\n");sb.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}
	
	
	@Override
	public boolean visitQuestion(QuestionSelectionnee questionSelectionnee) {
		StringBuffer sbTemp=new StringBuffer();
		Question questionAssociee = questionSelectionnee.getLaQuestionAssociee();
		sbTemp.append(tab+tab+"-Question ");
		sbTemp.append("\r\n").append(tab+tab+tab+"-Enoncé: ").append(questionAssociee.getEnonceRiche());
		sbTemp.append("\r\n").append(tab+tab+tab+"-Poids: ").append(questionAssociee.getPoids());
		sbTemp.append("\r\n").append(tab+tab+tab+"-Bien répondue: ").append(questionSelectionnee.estQuestionBienTraitee());
		sbTemp.append("\r\n");sb.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}
	@Override
	public boolean visitReponse	(ReponseTraitee reponseTraitee) {
		StringBuffer sbTemp=new StringBuffer();
		Reponse reponse = reponseTraitee.getLaReponseAssociee();
		sbTemp.append(tab+tab+tab+"-Réponse ");
		sbTemp.append("\r\n").append(tab+tab+tab+tab+"-Enoncé: ").append(reponse.getEnonceRiche());
		sbTemp.append("\r\n").append(tab+tab+tab+tab+"-Est bonne: ").append(reponse.estBonne());
		sbTemp.append("\r\n").append(tab+tab+tab+tab+"-Est cochée: ").append(reponseTraitee.estSelectionnee());
		sbTemp.append("\r\n");sbTemp.append("\r\n");
		sb.insert(0, sbTemp);
		return true;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
