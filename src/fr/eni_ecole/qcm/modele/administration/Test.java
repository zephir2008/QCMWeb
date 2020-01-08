package fr.eni_ecole.qcm.modele.administration;

import java.util.ArrayList;
import java.util.Date;

public class Test {
	private int id;
	private String libelle;
	private int seuilReussite;
	private ArrayList<Section> listeSection;
	private int tempsPassage;
	private Date dateDebutDisponibilite;
	private Date dateFinDisponibilite;
	
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
	public double getSeuilReussite() {
		return seuilReussite;
	}
	public void setSeuilReussite(int seuilReussite) {
		this.seuilReussite = seuilReussite;
	}
	public int getTempsPassage() {
		return tempsPassage;
	}
	public void setTempsPassage(int tempsPassage) {
		this.tempsPassage = tempsPassage;
	}
	public ArrayList<Section> getListeSection() {
		return listeSection;
	}
	
	public Date getDateDebutDisponibilite() {
		return dateDebutDisponibilite;
	}
	public void setDateDebutDisponibilite(Date dateDebutDisponibilite) {
		this.dateDebutDisponibilite = dateDebutDisponibilite;
	}
	public Date getDateFinDisponibilite() {
		return dateFinDisponibilite;
	}
	public void setDateFinDisponibilite(Date dateFinDisponibilite) {
		this.dateFinDisponibilite = dateFinDisponibilite;
	}
	public Test(int id,String libelle, int seuilReussite, int tempsPassage, Date dateDebutDisponibilite, Date dateFinDisponibilite) {
		super();
		this.id=id;
		this.libelle = libelle;
		this.seuilReussite = seuilReussite;
		this.tempsPassage = tempsPassage;
		this.listeSection=new ArrayList<Section>();
		this.dateDebutDisponibilite=dateDebutDisponibilite;
		this.dateFinDisponibilite=dateFinDisponibilite;
	}
	public Test()
	{
		super();
	}
}
