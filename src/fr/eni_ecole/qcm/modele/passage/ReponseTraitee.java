package fr.eni_ecole.qcm.modele.passage;

import fr.eni_ecole.qcm.modele.administration.Reponse;



public class ReponseTraitee implements IVisitable{

	private boolean selectionnee;
	private Reponse laReponseAssociee;
	private boolean reponseOK;
	
	public boolean estSelectionnee() {
		return selectionnee;
	}
	public void setSelectionnee(boolean selectionnee) {
		this.selectionnee=selectionnee;
	}
	
	public Reponse getLaReponseAssociee()
	{
		return this.laReponseAssociee;
	}
	
	protected boolean estReponseOK()
	{
		return this.reponseOK;
	}
	protected void setReponseOK(boolean OK)
	{
		this.reponseOK=OK;
	}
	

	public ReponseTraitee(Reponse reponse) {
		super();
		this.selectionnee=false;
		this.laReponseAssociee=reponse;
		this.reponseOK=false;
	}

	
	@Override
	public void Accept(IVisitor visitor) {
		visitor.visitReponse(this);
	}

}
