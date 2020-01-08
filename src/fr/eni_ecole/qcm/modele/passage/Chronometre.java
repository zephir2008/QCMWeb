package fr.eni_ecole.qcm.modele.passage;

import java.util.Timer;
import java.util.TimerTask;

public class Chronometre {
	private int tempsRestantSeconde;
	private int delaiRafraichissementSeconde=10;
	private boolean fini;
	private int uneSeconde=1000;
	
	private Timer compteARebours;
	private TimerTask tacheCompteARoubours;
	
	public boolean estFini() {
		return fini;
	}
	
	/**
	 * Retourne le temps restant à la minute près (à la minute supérieure).
	 * Si le temps exact restant est de 1 min. 45 sec., le retour sera 2.
	 * Si le temps exact restant est de 1 min. 0 sec., le retour sera 1. 
	 * */
	public int getTempsRestantEnMinute()
	{
		int tempsRestant = this.tempsRestantSeconde;
		int nbMinute = tempsRestant/60;
		if(tempsRestant%60>0)
		{
			nbMinute+=1;
		}
		return nbMinute;
	}
	
	public Chronometre(int tempsRestantEnMinute) {
		super();
		this.tempsRestantSeconde = tempsRestantEnMinute*60;
		this.fini=false;
	}
	
	
	public void demarrer()
	{
		this.compteARebours=new Timer(true);
		this.tacheCompteARoubours=new TimerTask() {
			@Override
			public void run() {
				tempsRestantSeconde-=delaiRafraichissementSeconde;
				if(tempsRestantSeconde<=0)
				{
					terminer();
				}
			}
		};
		this.compteARebours.scheduleAtFixedRate(this.tacheCompteARoubours, 
												this.delaiRafraichissementSeconde*this.uneSeconde, 
												this.delaiRafraichissementSeconde*uneSeconde);
	}
	
	public void terminer()
	{
		fini=true;
		this.compteARebours.cancel();
	}
	
	@Override
	public String toString() {
		int tempsRestant=this.tempsRestantSeconde;
		int nbMinutes=tempsRestant/60;
		int nbSecondes=tempsRestant%60;
		if(nbMinutes>0)
		{
			return nbMinutes+ " min. " + nbSecondes + " sec.";
		}
		else if(this.tempsRestantSeconde>0)
		{
			return nbSecondes + " sec.";
		}
		else
		{
			return "FINI";
		}
	}
}
