/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utilities.ControllerGeneralModel;

/**
 *
 * @author JorgeLPR
 */
public class CountryDAO {
    
    public  static ArrayList<String> EXCEPCIONES;
    
    public ArrayList<Country> selectCountry(){
        
        EXCEPCIONES = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;        
        
        Country country;
        ArrayList<Country> listCountry =new ArrayList<>();
        
        try{
            
            connection = ConnectionPoolMySQL.getInstance().getConnection();
            
            if(connection!=null){
                
                String sql = "SELECT id, country, city "
                        + "FROM countries "
                        + "WHERE 1 "
                        + "ORDER BY country ASC";
                
                pst = connection.prepareStatement(sql);
                
                rs = pst.executeQuery();
                
                while(rs.next()){
                    country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setCountry(rs.getString("country"));
                    country.setCity(rs.getString("city"));
                    listCountry.add(country);
                }
                
            }else{                
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+"Error al conectar con la base de datos");
            }
            
        }catch(HeadlessException | SQLException ex){
            EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());            
        }finally{
            
            try{
                if(connection != null){
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);            
                }            
            }catch(SQLException ex){
                EXCEPCIONES.add(ControllerGeneralModel.enumSizeExcepcion(EXCEPCIONES)+"- "+ex.getMessage());
            }

        }
        
        return listCountry;
        
    }    
    
}
