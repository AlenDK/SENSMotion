package e.android.sensmotion.JDBC_Connection;


/**
 * Contains information about database - how to connect.
 * Connector makes use of this information.
 * @author Group 11
 *
 */
public abstract class Constant
{
	public static final String
	server					= "us-cdbr-gcp-east-01.cleardb.net",  // database-serveren
	database				= "gcp_b62c0105038a8eabee03",  //"jdbcdatabase", // navnet paa din database = dit studienummer
	username				= "bab5f0b0b49367", // dit brugernavn = dit studienummer
	password				= "80baff82"; // dit password som du har valgt til din database
	
	public static final int
		port					= 3306;
}
