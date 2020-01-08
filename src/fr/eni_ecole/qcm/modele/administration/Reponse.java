package fr.eni_ecole.qcm.modele.administration;

public class Reponse {
	private int id;
	private String enonceRiche;
	private boolean bonne;
	
	public int getId()
	{
		return id;
	}
	public String getEnonceRiche() {
		return enonceRiche;
	}
	public void setEnonceRiche(String enonceRiche) {
		this.enonceRiche=enonceRiche;
	}
	public boolean estBonne() {
		return bonne;
	}
	public void setEstBonne(boolean estBonne) {
		this.bonne = estBonne;
	}
	
	
	public Reponse(int id, String enonceRiche, boolean estBonne) {
		super();
		this.id=id;
		this.enonceRiche = enonceRiche;
		this.bonne = estBonne;
	}
	public Reponse()
	{
		super();
	}
}
