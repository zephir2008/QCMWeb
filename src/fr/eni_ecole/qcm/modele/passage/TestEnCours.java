package fr.eni_ecole.qcm.modele.passage;

import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.modele.administration.Section;
import fr.eni_ecole.qcm.modele.administration.Test;

public class TestEnCours implements IVisitable{
	
	
	private int nombreBonneReponse;
	private double pourcentageBonneReponse;
	private int nombreTotalQuestion;
	private Test leTestAssocie;
	private ArrayList<SectionEnCours> listeSectionEnCours;
	private Chronometre chronometre;
	
	public double getPourcentageBonneReponse() {
		return pourcentageBonneReponse;
	}
	public int getNombreBonneReponse() {
		return nombreBonneReponse;
	}
	public int getNombreTotalQuestion() {
		return nombreTotalQuestion;
	}

	
	public Test getLeTestAssocie() {
		return leTestAssocie;
	}
	public ArrayList<SectionEnCours> getListeSectionEnCours() {
		return listeSectionEnCours;
	}
	
	protected void setNombreBonneReponse(int nombreBonneReponse) {
		this.nombreBonneReponse = nombreBonneReponse;
	}

	protected void setPourcentageBonneReponse(double pourcentageBonneReponse) {
		this.pourcentageBonneReponse = pourcentageBonneReponse;
	}

	protected void setNombreTotalQuestion(int nombreTotalQuestion) {
		this.nombreTotalQuestion = nombreTotalQuestion;
	}
	
	public int getNombreQuestionNonRepondue()
	{
		int nb=0;
		for(SectionEnCours s:this.getListeSectionEnCours())
		{
			for(QuestionSelectionnee qs:s.getListeQuestionSelectionnee())
			{
				boolean uneReponseDonnee=false;
				for(ReponseTraitee rt:qs.getListeReponseTraitee())
				{
					if(rt.estSelectionnee())
					{
						uneReponseDonnee=true;
					}
				}
				if(!uneReponseDonnee)
				{
					nb+=1;
				}
			}
		}
		return nb;
	}
	
	public int getNombreQuestionMarquee()
	{
		int nb=0;
		for(SectionEnCours s:this.getListeSectionEnCours())
		{
			for(QuestionSelectionnee qs:s.getListeQuestionSelectionnee())
			{
				if(qs.estMarquee())
				{
					nb+=1;
				}
			}
		}
		return nb;
	}

	
	public Chronometre getChronometre() {
		return chronometre;
	}
	public TestEnCours(Test nouveauTest, int tempsRestant) {
		super();
		this.initialiserCorrection();
		this.leTestAssocie=nouveauTest;
		this.listeSectionEnCours=new ArrayList<SectionEnCours>();
		for(Section  s : nouveauTest.getListeSection())
		{
			this.listeSectionEnCours.add(new SectionEnCours(s));
		}
		this.chronometre=new Chronometre(tempsRestant);
	}
	
	public void demarrer()
	{
		this.chronometre.demarrer();
	}
	
	public void terminer()
	{
		this.chronometre.terminer();
	}

	@Override
	public void Accept(IVisitor visitor) {
		for (SectionEnCours maSection : listeSectionEnCours) {
			maSection.Accept(visitor);
		}
		visitor.visitTest(this);
	}
	
	public QuestionIterator createIterator(IteratorEnum typeIterateur)
	{
		switch (typeIterateur) {
		case Question:
			return new QuestionIterator(this);
		case QuestionMarquee:
			return new QuestionMarqueeIterator(this);
		case QuestionNonRepondue:
			return new QuestionNonRepondueIterator(this);
		default:
			return new QuestionIterator(this);
		}
	}
	protected void initialiserCorrection() {
		this.pourcentageBonneReponse=0;
		this.nombreBonneReponse=0;
		this.nombreTotalQuestion=0;
		
	}
	public void chargerReponsesDejaDonnees(
			List<ReponseTraitee> listeReponseDejaSelectionnee) {
		for(ReponseTraitee reponseDejaDonnee : listeReponseDejaSelectionnee)
		{
			for(SectionEnCours s: this.getListeSectionEnCours())
			{
				for(QuestionSelectionnee qs: s.getListeQuestionSelectionnee())
				{
					for(ReponseTraitee rt:qs.getListeReponseTraitee())
					{
						if(reponseDejaDonnee.getLaReponseAssociee().getId()==rt.getLaReponseAssociee().getId())
						{
							rt.setSelectionnee(reponseDejaDonnee.estSelectionnee());
						}
					}
				}
			}
		}
	}
	
}
