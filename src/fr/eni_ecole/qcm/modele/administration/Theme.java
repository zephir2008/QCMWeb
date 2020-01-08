package fr.eni_ecole.qcm.modele.administration;

import java.util.ArrayList;

public class Theme {
	private int id;
	private String libelle;
	private ArrayList<Question> listeQuestion;

	public int getId() {
		return id;
	}
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public ArrayList<Question> getListeQuestion() {
		return listeQuestion;
	}

	public Theme(int id,String libelle) {
		super();
		this.id=id;
		this.libelle = libelle;
		this.listeQuestion=new ArrayList<Question>();
	}
	
	public Theme()
	{
		super();
	}
}
