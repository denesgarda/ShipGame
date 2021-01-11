package sample;
import java.sql.*;
import java.util.Arrays;
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
    public static boolean insertUser(java.sql.Connection conn, String username, String password) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String query = "insert into shipgame.accountsandstats (username, password, wins, losses)" + " values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, "0");
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
    public static boolean addWin(java.sql.Connection conn, String username) {
        try {
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
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    public static boolean addLoss(java.sql.Connection conn, String username) {
        try {
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
        catch(SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
}

