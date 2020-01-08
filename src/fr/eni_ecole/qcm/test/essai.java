package fr.eni_ecole.qcm.test;

import fr.eni_ecole.qcm.modele.passage.Chronometre;

public class essai {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Chronometre chrono = new Chronometre(60);
		chrono.demarrer();
		int i=0;
		while(true)
		{
			try {
				Thread.sleep(1000);
				i++;
				if(i==10)
				{
					chrono.terminer();
					System.out.println("terminé!!");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
