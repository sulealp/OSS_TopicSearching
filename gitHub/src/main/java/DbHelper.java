import java.sql.*;

/**
 * Created by burcu on 27.10.2017.
 */
public class DbHelper {

    Connection conn = null;

    // database connection
    public DbHelper() throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oss_repos?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "qweasdqwe");
            System.out.println("Bağlantı başarılı.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addTable (long id,String url,String topics,String description,String pl)  {

        String query = " insert into github_repository (id, url, topics, description,pl)"
                + " values (?, ?, ?, ?,?)";
        try {
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setDouble (1, id);

        preparedStmt.setString (2, url);
        preparedStmt.setString (3, topics);
        preparedStmt.setString(4, description);
        preparedStmt.setString(5,pl);

        // execute the preparedstatement
        preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public long getLastRepoId() {
        String query = "SELECT max(id) max FROM github_repository";

        Statement st = null;
        ResultSet rs=null;
        long max_id =0;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            max_id = rs.getLong("max");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return max_id;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
