package fr.eni_ecole.qcm.modele.passage;

public class QuestionMarqueeIterator extends QuestionIterator{

	
	public QuestionMarqueeIterator(TestEnCours testEncours) {
		super(testEncours);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean estQuestionAIterer(QuestionSelectionnee question) {
		return question.estMarquee();
	}
}
