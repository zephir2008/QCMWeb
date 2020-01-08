package fr.eni_ecole.qcm.basededonnees;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PoolConnexion {
	public static Connection getUneConnexion() throws SQLException
	{
		  Connection cnx=null;

		  // Charger l'annuaire JNDI
		  InitialContext jndi = null;
		  try 
		  {
		    jndi = new InitialContext();
		  } 
		  catch (NamingException e) 
		  {
		    throw new SQLException("Impossible d'atteindre l'arbre JNDI");
		  }
		  DataSource ds = null;
		  // Rechercher la ressource dans l'annuaire
		  try 
		  {
		   ds=(DataSource) jndi.lookup("java:comp/env/jdbc/qcm");
		  } 
		  catch (NamingException ne) 
		  {
		      throw new SQLException("Pool de connexion introuvable dans l'arbre jndi");
		  }
				
		  // Activer une connexion du pool
		  cnx=ds.getConnection();
				
		  return cnx;
	}
}
