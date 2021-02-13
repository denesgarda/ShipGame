package sample;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class Connection {
    public static boolean insertFeedbackRow(java.sql.Connection conn, String feedback) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            //rs = stmt.executeQuery("SELECT foo FROM bar");
            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
            //String query = "insert into bugreports (username, reason)" + " values (username, reason)";
            String query = "insert into feedback (feedback)" + " values (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, feedback);
            preparedStatement.executeUpdate();
            //if (stmt.execute(query)) {
            //rs = stmt.getResultSet();
            //}
            // Now do something with the ResultSet ....
            return true;
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    public static double retrieveMaxVersion(java.sql.Connection conn) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select max(version) from shipgame.versions;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("max(version)");
            return Double.parseDouble(result);
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }
    public static String retrieveLatestLink(java.sql.Connection conn, double latestVersion) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select downloadlink from shipgame.versions where version = " + latestVersion;
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("downloadlink");
            return result;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public static String getEmailViaUsername(java.sql.Connection conn, String username) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select email from shipgame.accountsandstats where username = \"" + username + "\";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("email");
            return result;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public static boolean checkUsername(java.sql.Connection conn, String username) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            //String query = "select username from shipgame.accountsandstats";
            String query = "select * from shipgame.accountsandstats where username = " + "\"" + username + "\"";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String result = rs.getString("username");
                if(result.equals(username)) {
                    return true;
                }
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return false;
    }
    public static boolean checkPassword(java.sql.Connection conn, String username, String password) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select password from shipgame.accountsandstats where username = " + "\"" + username + "\"";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("password");
            if(result.equals(password)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean checkEmailViaUsername(java.sql.Connection conn, String username, String email) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select email from shipgame.accountsandstats where username = " + "\"" + username + "\"";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("email");
            if(result.equals(email)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean checkEmail(java.sql.Connection conn, String email) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            //String query = "select username from shipgame.accountsandstats";
            String query = "select * from shipgame.accountsandstats where email = " + "\"" + email + "\"";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String result = rs.getString("email");
                if(result.equals(email)) {
                    return true;
                }
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return false;
    }
    public static boolean insertUser(java.sql.Connection conn, String username, String password, String email) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "insert into shipgame.accountsandstats (username, password, wins, losses, email)" + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, "0");
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean insertDeleteReason(java.sql.Connection conn, String account, String reason) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "insert into shipgame.deletereason (account, reason)" + " values (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, reason);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean deleteUser(java.sql.Connection conn, String username) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "DELETE FROM shipgame.accountsandstats WHERE username = \"" + username + "\";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean changePassword(java.sql.Connection conn, String email, String password) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "UPDATE shipgame.accountsandstats SET password = \"" + password + "\" WHERE email = \"" + email + "\";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean changeEmail(java.sql.Connection conn, String username, String email) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "UPDATE shipgame.accountsandstats SET email = \"" + email + "\" WHERE username = \"" + username + "\";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean changePasswordViaUsername(java.sql.Connection conn, String username, String password) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "UPDATE shipgame.accountsandstats SET password = \"" + password + "\" WHERE username = \"" + username + "\";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean changeUsername(java.sql.Connection conn, String oldUsername, String newUsername) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "UPDATE shipgame.accountsandstats SET username = \"" + newUsername + "\" WHERE username = \"" + oldUsername + "\";";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static String getWins(java.sql.Connection conn, String username) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select wins from shipgame.accountsandstats where username = " +  "\"" + username + "\"";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("wins");
            return result;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return "";
        }
    }
    public static String getLosses(java.sql.Connection conn, String username) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "select losses from shipgame.accountsandstats where username = " +  "\"" + username + "\"";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            String result = rs.getString("losses");
            return result;
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return "";
        }
    }
    public static boolean addWin(java.sql.Connection conn, String username, double gameVersion) {
        try {
            if(gameVersion == retrieveMaxVersion(conn)) {
                Statement stmt = null;
                ResultSet rs = null;
                stmt = conn.createStatement();
                String wins = getWins(conn, username);
                int winsInt = Integer.parseInt(wins);
                int winsToSet = winsInt + 1;
                String query = "update shipgame.accountsandstats set wins = " + winsToSet + " where username = " + "\"" + username + "\"";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                int i = preparedStatement.executeUpdate();
                return true;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are using either an outdated version of the game or a pre-release version, so the win could not be added to your account.", ButtonType.OK);
                alert.setTitle("Unsupported Version");
                alert.setHeaderText("Unsupported Version");
                alert.showAndWait();
                return false;
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean addLoss(java.sql.Connection conn, String username, double gameVersion) {
        try {
            if(gameVersion == retrieveMaxVersion(conn)) {
                Statement stmt = null;
                ResultSet rs = null;
                stmt = conn.createStatement();
                String losses = getLosses(conn, username);
                int lossesInt = Integer.parseInt(losses);
                int lossesToSet = lossesInt + 1;
                String query = "update shipgame.accountsandstats set losses = " + lossesToSet + " where username = " + "\"" + username + "\"";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                int i = preparedStatement.executeUpdate();
                return true;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are using either an outdated version of the game or a pre-release version, so the loss could not be added to your account.", ButtonType.OK);
                alert.setTitle("Unsupported Version");
                alert.setHeaderText("Unsupported Version");
                alert.showAndWait();
                return false;
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}

