package fr.eni_ecole.qcm.modele.administration;

import java.util.ArrayList;

public class Section {

	private int id;
	private String libelle;
	private int nombreQuestionAUtiliser;
	private ArrayList<Question> listeQuestion;
	private int numero;
	
	public int getId()
	{
		return id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getNombreQuestionAUtiliser() {
		return nombreQuestionAUtiliser;
	}
	public void setNombreQuestionAUtiliser(int nombreQuestionAUtiliser) {
		this.nombreQuestionAUtiliser = nombreQuestionAUtiliser;		
	}
	public ArrayList<Question> getListeQuestion() {
		return listeQuestion;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero=numero;
	}
	public Section(int id, String libelle, int numero, int nombreQuestionAUtiliser) {
		super();
		this.id=id;
		this.libelle = libelle;
		this.numero=numero;
		this.listeQuestion=new ArrayList<Question>();
		this.nombreQuestionAUtiliser=nombreQuestionAUtiliser;
	}
	public Section(int id, String libelle) {
		super();
		this.id=id;
		this.libelle = libelle;
		this.numero=0;
		this.listeQuestion=new ArrayList<Question>();
		this.nombreQuestionAUtiliser=0;
	}
	public Section()
	{
		super();
	}
	@Override
	public boolean equals(Object obj) {
		boolean retour=false;
		if(obj instanceof Section)
		{
			Section s = (Section)obj;
			retour = (this.getId()==s.getId());
		}
		return retour;
	}
}
