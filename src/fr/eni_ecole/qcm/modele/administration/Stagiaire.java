package fr.eni_ecole.qcm.modele.administration;

import java.util.ArrayList;

import fr.eni_ecole.qcm.modele.passage.TestEnCours;

public class Stagiaire extends Personne {
	private Promotion promotion;
	private ArrayList<TestEnCours> listeTestEnCours;
	
	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public ArrayList<TestEnCours> getListeTestEnCours() {
		return listeTestEnCours;
	}

	
	public Stagiaire(int id,String nom, String prenom, String courriel,
			Promotion promotion, String login, String motDePasse) {
		super(id,nom, prenom, courriel, login, motDePasse);
		this.promotion = promotion;
		this.listeTestEnCours = new ArrayList<TestEnCours>();
	}
	
	public Stagiaire() {
		super();
		this.listeTestEnCours = new ArrayList<TestEnCours>();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("\r\n");
		sb.append(this.promotion.toString());
		return sb.toString();
	}
}
