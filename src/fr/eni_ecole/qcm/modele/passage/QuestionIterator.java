package fr.eni_ecole.qcm.modele.passage;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

public class QuestionIterator {
	private TestEnCours testEncours;
	private int currentQuestionSelectionnee;
	private ArrayList<SimpleEntry<QuestionSelectionnee, SectionEnCours>> listeQuestionSection;
	
	public QuestionIterator(TestEnCours testEncours) {
		super();
		this.testEncours = testEncours;
		this.currentQuestionSelectionnee=0;
		this.initialiserListeQuestion();
	}
	
	private void initialiserListeQuestion() {
		this.listeQuestionSection=new ArrayList<SimpleEntry<QuestionSelectionnee,SectionEnCours>>();
		for (SectionEnCours section : testEncours.getListeSectionEnCours()) {
			for (QuestionSelectionnee question : section.getListeQuestionSelectionnee()) {
				if(!this.testEncours.getChronometre().estFini() && estQuestionAIterer(question)){
					this.listeQuestionSection.add(new SimpleEntry<QuestionSelectionnee, SectionEnCours>(question,section));
				}
			}
		}
	}

	public int getNombreQuestion()
	{
		return this.listeQuestionSection.size();
	}
	public int getNumeroQuestionCourante()
	{
		return this.currentQuestionSelectionnee+1;
	}
	
	public QuestionSelectionnee premiereQuestion()
	{
		this.currentQuestionSelectionnee=0;
		return this.questionCourante();
	}

	public QuestionSelectionnee questionSuivante()
	{
		if(this.currentQuestionSelectionnee<this.listeQuestionSection.size()-1)
		{
			this.currentQuestionSelectionnee+=1;
		}
		return this.questionCourante();
	}
	
	public QuestionSelectionnee questionPrecedente()
	{
		if(this.currentQuestionSelectionnee>0)
		{
			this.currentQuestionSelectionnee-=1;
		}
		return this.questionCourante();
	}
	
	public QuestionSelectionnee derniereQuestion()
	{
		this.currentQuestionSelectionnee=this.listeQuestionSection.size()-1;
		return this.questionCourante();
	}
	
	public QuestionSelectionnee questionCourante()
	{
		if(this.currentQuestionSelectionnee>=0 && this.listeQuestionSection.size()>0)
		{
			return this.listeQuestionSection.get(currentQuestionSelectionnee).getKey();			
		}
		return null;
	}

	public SectionEnCours sectionCourante()
	{
		if(this.currentQuestionSelectionnee>=0  && this.listeQuestionSection.size()>0)
		{
			return this.listeQuestionSection.get(currentQuestionSelectionnee).getValue();
		}
		return null;
	}
	
	protected boolean estQuestionAIterer(QuestionSelectionnee question)
	{
		return true;
	}
	
}
