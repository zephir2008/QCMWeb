package fr.eni_ecole.qcm.modele.passage;

import java.util.ArrayList;

import fr.eni_ecole.qcm.modele.administration.Question;
import fr.eni_ecole.qcm.modele.administration.Reponse;

public class QuestionSelectionnee implements IVisitable{

	private boolean marquee;
	private Question laQuestionAssociee;
	private ArrayList<ReponseTraitee> listeReponseTraitee;
	private boolean questionBienTraitee;
	
	public boolean estMarquee() {
		return marquee;
	}
	public void setMarquee(boolean marquee) {
		this.marquee=marquee;
	}
	public Question getLaQuestionAssociee() {
		return laQuestionAssociee;
	}
	public ArrayList<ReponseTraitee> getListeReponseTraitee() {
		return listeReponseTraitee;
	}
	protected boolean estQuestionBienTraitee()
	{
		return this.questionBienTraitee;
	}
	protected void setQuestionBienTraitee(boolean OK)
	{
		this.questionBienTraitee=OK;
	}
	public boolean hasUneSeuleReponse()
	{
		int nbBonneReponse=0;;
		for(ReponseTraitee rt : this.getListeReponseTraitee())
		{
			if(rt.getLaReponseAssociee().estBonne())
			{
				nbBonneReponse+=1;
			}
		}
			
		return (nbBonneReponse==1);
	}
	

	public QuestionSelectionnee(Question question) {
		super();
		this.marquee=false;
		this.laQuestionAssociee=question;
		this.listeReponseTraitee=new ArrayList<ReponseTraitee>();
		for(Reponse r: question.getListeReponse())
		{
			this.listeReponseTraitee.add(new ReponseTraitee(r));
		}
		this.questionBienTraitee=false;
	}

	
	@Override
	public void Accept(IVisitor visitor) {
		for (ReponseTraitee maReponse : listeReponseTraitee) {
			maReponse.Accept(visitor);
		}
		visitor.visitQuestion(this);
	}

}
