package e.android.sensmotion.connector;

import e.android.sensmotion.JDBC_Interfaces.DALException;

/**
 * An independently running thread continually performing queries on the database (About every 20 seconds) to avoid a connection timeout.
 * A hackjob solution, but it works.
 * @author User
 *
 */
public class Pingtool implements Runnable {

	int waitTime = 200;
	int currentTime = 0;
	boolean timeToPing = false;

	/**
	 * Checks if it is time to ping (If currentTime equals waitTime) and then performs that ping.
	 * Otherwise naps, to avoid overloading resources.
	 */
	public void run() {
		while (true) {
			if (currentTime >= waitTime) {
				timeToPing = true;
			}
			if (timeToPing) {
				try {
					// Arbritary query to keep database going.
					Connector.getInstance().doQuery("SELECT * FROM recept");
					System.out.println("System sustaining ping complete.");
					timeToPing = false;
					currentTime = 0;
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				nap();
				currentTime++;
			}
		}
	}

	public double sleepInterruptable(double seconds) {
		long start = System.currentTimeMillis();
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return ((double) System.currentTimeMillis() - start) / 1000;
	}

	public double sleep(double seconds) {
		double delta = 0;
		while (delta < seconds)
			delta += sleepInterruptable(seconds - delta);
		return delta;
	}

	/** 
	 * Copied method from a previous assignment. Essentially just naps the thread for 0.1 seconds.
	 * @return Returns a double about the sleeptime we do not make use of.
	 */
	public double nap() {
		return sleep(0.1);
	}

}
