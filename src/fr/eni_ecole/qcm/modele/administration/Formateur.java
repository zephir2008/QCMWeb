package fr.eni_ecole.qcm.modele.administration;

import java.util.ArrayList;

public class Formateur extends Personne {

	private boolean responsable;
	private ArrayList<Test> listeTestEnCharge;
	
	public boolean estResponsable() {
		return responsable;
	}
	public void setResponsable(boolean responsable) {
		this.responsable = responsable;
	}
	
	public ArrayList<Test> getListeTestEnCharge() {
		return listeTestEnCharge;
	}

	public void setListeTestEnCharge(ArrayList<Test> listeTestEnCharge) {
		this.listeTestEnCharge = listeTestEnCharge;
	}

	public Formateur(int id,String nom, String prenom, String courriel, String login, String motDePasse, boolean estResponsable) {
		super(id,nom, prenom, courriel, login, motDePasse);
		this.responsable=estResponsable;
		this.listeTestEnCharge=new ArrayList<Test>();
	}
	public Formateur() {
		super();
		this.listeTestEnCharge=new ArrayList<Test>();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("\r\n");
		sb.append("Est responsable: ").append(this.estResponsable());
		return sb.toString();
	}
}
