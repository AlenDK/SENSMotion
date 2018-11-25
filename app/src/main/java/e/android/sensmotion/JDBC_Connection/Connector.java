package e.android.sensmotion.JDBC_Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import e.android.sensmotion.JDBC_Interfaces.DALException;


public class Connector {

    public void connectURL(String urlInput)  throws IOException {
        URL url = new URL(urlInput);
        String readline = null;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("", "");
        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();

            while((readline = in.readLine()) != null){
                response.append(readline);
            }
            in.close();
            System.out.println("JSON String Result: " + response.toString());
        }else{
            System.out.println("GET DIDNT WORK");
        }
    }

    /**
     * To connect to a MySQL-server
     *
     * @param url
     *            must have the form "jdbc:mysql://<server>/<database>" for default
     *            port (3306) OR "jdbc:mysql://<server>:<port>/<database>" for
     *            specific port more formally "jdbc:subprotocol:subname"
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    public static Connection connectToDatabase(String url, String username, String password)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // call the driver class' no argument constructor
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        // get Connection-object via DriverManager
        return (Connection) DriverManager.getConnection(url, username, password);
    }

    private static Connection conn;
    private static Statement stm;
    private static Connector connector;

    /**
     * The connector now becomes a singleton, so several connections aren't established.
     * Only one is at first instantiated and since passed around.
     * @return Returns the connector instance.
     * @throws DALException
     */
    public static Connector getInstance() throws DALException {
        if (connector==null || stm ==null || conn == null) {
            try {
                connector = new Connector();
                Thread ping = new Thread(new Pingtool());
                ping.start();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new DALException(e.getMessage());
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new DALException(e.getMessage());
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new DALException(e.getMessage());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new DALException(e.getMessage());
            }
        }
        return connector;
    }

    /**
     * Constructor for establishing connection right away.
     * @param server
     * @param port
     * @param database
     * @param username
     * @param password
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connector(String server, int port, String database, String username, String password)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        conn = connectToDatabase("jdbc:mysql://" + server + ":" + port + "/" + database, username, password);
        stm = conn.createStatement();
    }

    /**
     * Constructor without parameters.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connector() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        this(Constant.server, Constant.port, Constant.database, Constant.username, Constant.password);
    }

    /**
     * Performs a query on the database and returns a resultset object.
     * @param cmd The statement we wish to execute.
     * @return The resultset, result of the query.
     * @throws DALException
     */
    public ResultSet doQuery(String cmd) throws DALException {
        try {
            stm = conn.createStatement();
            return stm.executeQuery(cmd);
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    /**
     * Performs an update. Returns an integer that tells how well the process went.
     * @param cmd The statement we wish to execute.
     * @return
     * @throws DALException
     */
    public int doUpdate(String cmd) throws DALException {
        try {
            return stm.executeUpdate(cmd);
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }
}
