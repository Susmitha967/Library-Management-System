/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Sudhir Kushwaha
 */
public class Connect {
    static Connection con=null;
    public static Connection ConnectToDB() {
        try {
            Map<String, String> env = System.getenv();

            String url = System.getProperty("LIB_DB_URL");
            if (url == null || url.isBlank()) {
                url = env.getOrDefault("LIB_DB_URL", "jdbc:postgresql://localhost:5432/library");
            }

            String user = System.getProperty("LIB_DB_USER");
            if (user == null || user.isBlank()) {
                user = env.getOrDefault("LIB_DB_USER", "postgres");
            }

            String password = System.getProperty("LIB_DB_PASSWORD");
            if (password == null) {
                password = env.getOrDefault("LIB_DB_PASSWORD", "postgres");
            }

            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            Map<String, String> env = System.getenv();
            JOptionPane.showMessageDialog(
                    null,
                    "Database connection failed.\n\n"
                            + "URL: " + (System.getProperty("LIB_DB_URL") != null ? System.getProperty("LIB_DB_URL") : env.getOrDefault("LIB_DB_URL", "jdbc:postgresql://localhost:5432/library")) + "\n"
                            + "User: " + (System.getProperty("LIB_DB_USER") != null ? System.getProperty("LIB_DB_USER") : env.getOrDefault("LIB_DB_USER", "postgres")) + "\n\n"
                            + "Error: " + ex.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return con;
}
}
