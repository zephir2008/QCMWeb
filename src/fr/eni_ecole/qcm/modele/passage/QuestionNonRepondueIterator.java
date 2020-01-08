package fr.eni_ecole.qcm.modele.passage;

public class QuestionNonRepondueIterator extends QuestionIterator {

	public QuestionNonRepondueIterator(TestEnCours testEncours) {
		super(testEncours);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean estQuestionAIterer(QuestionSelectionnee question) {
		boolean retour=true;
		for (ReponseTraitee reponse : question.getListeReponseTraitee()) {
			if(reponse.estSelectionnee())
			{
				retour=false;
			}
		}
		return retour;
	}
}
