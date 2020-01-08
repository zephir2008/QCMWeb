package fr.eni_ecole.qcm.modele.administration;

import java.util.ArrayList;

public class Question {
	private int id;
	private String enonceRiche;
	private ArrayList<Reponse> listeReponse;
	private String cheminIllustration=null;
	private int poids;
	
	public int getId()
	{
		return id;
	}
	public String getEnonceRiche() {
		return enonceRiche;
	}
	public void setEnonceRiche(String enonceRiche) {
		this.enonceRiche = enonceRiche;
	}
	public ArrayList<Reponse> getListeReponse() {
		return listeReponse;
	}
	public void setListeReponse(ArrayList<Reponse> listeReponse) {
		this.listeReponse = listeReponse;
	}
	public String getIllustration() {
		return cheminIllustration;
	}
	public void setCheminIllustration(String cheminIllustration) {
		this.cheminIllustration = cheminIllustration;
	}
	public int getNombreBonneReponse()
	{
		int nb=0;
		for(Reponse uneReponse :listeReponse)
		{
			if(uneReponse.estBonne())
			{
				nb+=1;
			}
		}
		return nb;
	}
	public int getPoids()
	{
		return poids;
	}
	
	public Question(int id, String enonceRiche, String cheminIllustration, int poids) {
		super();
		this.id=id;
		this.enonceRiche = enonceRiche;
		this.cheminIllustration = cheminIllustration;
		this.listeReponse=new ArrayList<Reponse>();
		this.poids=poids;
	}
	public Question()
	{
		super();
		this.listeReponse=new ArrayList<Reponse>();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean retour=false;
		if(obj instanceof Question)
		{
			Question q = (Question)obj;
			retour = (this.getId()==q.getId());
		}
		return retour;
	}
}
