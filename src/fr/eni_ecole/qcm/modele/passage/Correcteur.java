package fr.eni_ecole.qcm.modele.passage;

public class Correcteur implements IVisitor {


	@Override
	public boolean visitTest(TestEnCours testEnCours) {
		testEnCours.initialiserCorrection();
		int nombreQuestion=0;
		int nombreBonneReponse=0;
		for (SectionEnCours section : testEnCours.getListeSectionEnCours()) {
			nombreQuestion+=section.getLaSectionAssociee().getNombreQuestionAUtiliser();
			nombreBonneReponse+=section.getNombreBonneReponse();
		}
		int pourcentageBonneReponse=nombreBonneReponse*100/nombreQuestion;
		
		testEnCours.setNombreBonneReponse(nombreBonneReponse);
		testEnCours.setNombreTotalQuestion(nombreQuestion);
		testEnCours.setPourcentageBonneReponse(pourcentageBonneReponse);
		return true;
	}
	
	@Override
	public boolean visitSection(SectionEnCours sectionEnCours) {
		sectionEnCours.initialiseNombreBonneReponse();
		for (QuestionSelectionnee question : sectionEnCours.getListeQuestionSelectionnee()) {
			if(question.estQuestionBienTraitee())
			{
				sectionEnCours.incrementNombreBonneReponse();
			}
		}
		int pourcentageBonneReponse = sectionEnCours.getNombreBonneReponse()*100/sectionEnCours.getLaSectionAssociee().getNombreQuestionAUtiliser();
		sectionEnCours.setPourcentageBonneReponse(pourcentageBonneReponse);
		return true;
	}
	
	
	@Override
	public boolean visitQuestion(QuestionSelectionnee questionSelectionnee) {
		boolean ok = true;
		for (ReponseTraitee reponse : questionSelectionnee.getListeReponseTraitee()) {
			if(!reponse.estReponseOK())
			{
				ok=false;
			}
		}
		questionSelectionnee.setQuestionBienTraitee(ok);
		return true;
	}
	@Override
	public boolean visitReponse	(ReponseTraitee reponseTraitee) {
		if(reponseTraitee.estSelectionnee()==reponseTraitee.getLaReponseAssociee().estBonne())
		{
			reponseTraitee.setReponseOK(true);
		}
		else
		{
			reponseTraitee.setReponseOK(false);
		}
		return true;
	}

}
