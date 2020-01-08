package fr.eni_ecole.qcm.modele.administration;

public class Inscription {
	private Stagiaire stagiaire;
	private Test test;
	
	public Stagiaire getStagiaire() {
		return stagiaire;
	}
	public Test getTest() {
		return test;
	}
	
	public Inscription(Stagiaire stagiaire, Test test) {
		super();
		this.stagiaire = stagiaire;
		this.test = test;
	}
	
}
