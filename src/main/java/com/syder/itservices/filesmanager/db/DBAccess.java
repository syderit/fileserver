package com.syder.itservices.filesmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.syder.itservices.filesmanager.utils.PropertiesManager;
import com.syder.itservices.filesmanager.utils.Utils;

/**
 * @author Juan
 *
 */
public class DBAccess {
	final static Logger logger = Logger.getLogger(DBAccess.class);
//	private static String CONNECTION_STRING_DEV = "jdbc:mysql://localhost/fileserver?user=juan&password=23458923";
//	private static String CONNECTION_STRING_PROD = "mysql://mysql:3306/syderdb?user=syderdbuser&password=qwe123123";
	private static String CONNECTION_STRING = "jdbc:mysql://" + PropertiesManager.getProperty("db.server") + "/" + PropertiesManager.getProperty("db.schema") + "?user=" + PropertiesManager.getProperty("db.user") + "&password=" + PropertiesManager.getProperty("db.pass");
	
    public static boolean checkUser(String username, String password)  throws Exception {
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
        try {
        	logger.info("checkUser username=" + username + ", password=" + password + " BEGIN");
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection(CONNECTION_STRING);
        	
            String statementStr = "select id from users where username=? and password=?";
            preparedStatement = connection.prepareStatement(statementStr);
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            logger.info("result=" + result);
            if (result) {
            	int user_id = resultSet.getInt("id");
            	logger.debug("user user_id=" + user_id);
            }
            return result;
        } catch (Exception e) {
        	logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
        	throw e;
        } finally {
        	try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                	preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            	logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
            }
        }
    }
}
