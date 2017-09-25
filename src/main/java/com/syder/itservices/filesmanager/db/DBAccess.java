package com.syder.itservices.filesmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;

import com.syder.itservices.filesmanager.model.File;
import com.syder.itservices.filesmanager.utils.PropertiesManager;
import com.syder.itservices.filesmanager.utils.Utils;

/**
 * @author Juan
 *
 */
public class DBAccess {
	final static Logger logger = Logger.getLogger(DBAccess.class);
	private static String CONNECTION_STRING = "jdbc:mysql://" + PropertiesManager.getProperty("db.server") + "/" + PropertiesManager.getProperty("db.schema") + "?user=" + PropertiesManager.getProperty("db.user") + "&password=" + PropertiesManager.getProperty("db.pass");
	
    public static int checkUser(String username, String password)  throws Exception {
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	
    	int user_id = -1;
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
            	user_id = resultSet.getInt("id");
            	logger.debug("user user_id=" + user_id);
            }
            return user_id;
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

    public static int insertNewFile(String originalname, String uid, String location)  throws Exception {
    	Connection connection = null;
    	PreparedStatement insertStatement = null;
    	ResultSet resultSet = null;
        try {
        	logger.info("insert new file originalname=" + originalname + ", uid=" + uid + ", location=" + location + " BEGIN");
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection(CONNECTION_STRING);
        	
            String statementStr = "INSERT INTO `files` (`originalname`, `uid`, `upload_date`, `location`) VALUES (?, ?, now(), ?);";
            insertStatement = connection.prepareStatement(statementStr, Statement.RETURN_GENERATED_KEYS);
            
            insertStatement.setString(1, originalname);
            insertStatement.setString(2, uid);
            insertStatement.setString(3, location);
            
            insertStatement.executeUpdate();
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
            	generatedKeys.next();
            	int id = generatedKeys.getInt(1);
            	logger.info("new insert file id=" + id);
            	return id;
            }
        } catch (Exception e) {
        	logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
        	throw e;
        } finally {
        	try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertStatement != null) {
                	insertStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            	logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
            }
        }
    }
    
    public static File getFile(String uid)  throws Exception {
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
        try {
        	File file = null;
        	
        	logger.info("getFile uid=" + uid + " BEGIN");
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection(CONNECTION_STRING);
        	
            String statementStr = "select * from files where uid=?";
            preparedStatement = connection.prepareStatement(statementStr);
            
            preparedStatement.setString(1, uid);
            
            resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            logger.info("result=" + result);
            if (result) {
            	file = new File();
            	int id = resultSet.getInt("id");
            	String originalname = resultSet.getString("originalname");
            	Date uploadDate = resultSet.getTimestamp("upload_date");
            	String location = resultSet.getString("location");
            	
            	file.setId(id);
            	file.setOriginalname(originalname);
            	file.setUid(uid);
            	file.setUploadDate(uploadDate);
            	file.setLocation(location);
            	logger.debug("file=" + file);
            }
            return file;
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
    
    public static void saveAccess(int idUser, int idFile)  throws Exception {
    	Connection connection = null;
    	PreparedStatement insertStatement = null;
    	ResultSet resultSet = null;
        try {
        	logger.info("insert new access idUser=" + idUser + ", idFile=" + idFile + " BEGIN");
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection(CONNECTION_STRING);
        	
            String statementStr = "INSERT INTO `users_files` (`id_user`, `id_file`, `access_date`) VALUES (?, ?, now());";
            insertStatement = connection.prepareStatement(statementStr);
            
            insertStatement.setInt(1, idUser);
            insertStatement.setInt(2, idFile);
            
            insertStatement.executeUpdate();
        } catch (Exception e) {
        	logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
        	throw e;
        } finally {
        	try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertStatement != null) {
                	insertStatement.close();
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
