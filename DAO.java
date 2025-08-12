/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objects.House;
import objects.User;
import util.DBUtils;

/**
 *
 * @author ADMIN
 */


public class DAO {
    
    private static String DATABASE;
    private static String SELECTION;
    private static String VARIABLE_1;
    private static String VARIABLE_2;
    
    public ArrayList<User> validateUser(User targetUser) throws SQLException
    {
        DATABASE = "tblUser";
        SELECTION = "userID, password";
        VARIABLE_1 = "userID";
        VARIABLE_2 = "password";
        
        ArrayList<User> resultList = new ArrayList<User>();
        Connection connection = null;
        DBUtils context = new DBUtils();
        try
        {
            connection = context.getConnection();
            if (connection != null)
            {
                String query = "SELECT "+SELECTION+" FROM "+DATABASE+" WHERE "+VARIABLE_1+" = ? AND "+VARIABLE_2+" = ?";
                        
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, targetUser.getUserID());
                statement.setString(2, targetUser.getPassword());
                
                ResultSet resultSet = statement.executeQuery();
                if(resultSet != null)
                {
                    while (resultSet.next())
                    {
                        String id = resultSet.getString(1);
                        String password = resultSet.getString(2);
                        
                        User user = new User(id, password);
                        resultList.add(user);
                    }
                }
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally
        {
            if(connection!=null) connection.close();
        }
        return resultList;
    }

    public ArrayList<House> searchByName(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

   
