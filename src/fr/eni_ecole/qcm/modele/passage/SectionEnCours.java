package fr.eni_ecole.qcm.modele.passage;

import java.util.ArrayList;

import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Section;

public class SectionEnCours implements IVisitable{

	private int nombreBonneReponse;
	private double pourcentageBonneReponse;
	
	private Section laSectionAssociee;
	private ArrayList<QuestionSelectionnee> listeQuestionSelectionnee;
	
	public int getNombreBonneReponse() {
		return nombreBonneReponse;
	}
	public double getPourcentageBonneReponse()
	{
		return pourcentageBonneReponse;
	}
	protected void setPourcentageBonneReponse(double pourcentageBonneReponse) {
		this.pourcentageBonneReponse = pourcentageBonneReponse;
	}
	
	public Section getLaSectionAssociee() {
		return laSectionAssociee;
	}
	protected ArrayList<QuestionSelectionnee> getListeQuestionSelectionnee() {
		return listeQuestionSelectionnee;
	}

	public SectionEnCours(Section section) {
		super();
		this.initialiseNombreBonneReponse();
		this.laSectionAssociee=section;
		this.listeQuestionSelectionnee=new ArrayList<QuestionSelectionnee>();
		for(Question q : section.getListeQuestion())
		{
			this.listeQuestionSelectionnee.add(new QuestionSelectionnee(q));
		}
	}
	
	@Override
	public void Accept(IVisitor visitor) {
		for (QuestionSelectionnee maQuestion : listeQuestionSelectionnee) {
			maQuestion.Accept(visitor);
		}
		visitor.visitSection(this);
	}

	protected void incrementNombreBonneReponse() {
		this.nombreBonneReponse+=1;
	}
	protected void initialiseNombreBonneReponse() {
		this.nombreBonneReponse=0;
	}
	
}
