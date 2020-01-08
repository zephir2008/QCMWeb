package fr.eni_ecole.qcm.modele.administration;

public class Personne {
	private int id;
	private String nom;
	private String prenom;
	private String courriel;
	private String login;
	private String motDePasse;
	
	public int getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCourriel() {
		return courriel;
	}
	public void setCourriel(String courriel) {
		this.courriel = courriel;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public Personne(int id, String nom, String prenom, String courriel, String login, String motDePasse) {
		super();
		this.id=id;
		this.nom = nom;
		this.prenom = prenom;
		this.courriel = courriel;
		this.login=login;
		this.motDePasse=motDePasse;
	}
	public Personne() {
		super();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Nom: ").append(this.nom).append(" Prénom: ").append(this.prenom);
		sb.append(" courriel: ").append(this.courriel);
		return sb.toString();
	}
}
