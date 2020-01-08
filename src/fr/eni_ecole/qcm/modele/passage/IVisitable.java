package fr.eni_ecole.qcm.modele.passage;

public interface IVisitable {
	void Accept(IVisitor visitor);
}
