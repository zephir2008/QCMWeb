package fr.eni_ecole.qcm.modele.passage;

public interface IVisitor {
	public boolean visitTest(TestEnCours testEnCours);
	public boolean visitSection(SectionEnCours sectionEnCours);
	public boolean visitQuestion(QuestionSelectionnee questionSelectionnee);
	public boolean visitReponse(ReponseTraitee reponseTraitee);
}
