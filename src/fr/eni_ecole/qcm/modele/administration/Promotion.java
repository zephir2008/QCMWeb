package fr.eni_ecole.qcm.modele.administration;

public class Promotion {
	private int id;
	private String libelle;
	
	public int getId() {
		return id;
	}
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
	public Promotion(int id, String libelle) {
		super();
		this.id=id;
		this.libelle = libelle;
	}
	
	public Promotion()
	{
		super();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Promotion: ").append(this.libelle);
		return sb.toString();
	}
}
