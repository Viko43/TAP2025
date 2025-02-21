package com.example.demo1.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static String DB = "restaurantec2";
    private static String USER = "admin2";
    private static String PWD = "1234";
    private static String Host = "localhost"; //127.0.0.1
    private static String Port = "3306";
    private static Connection connection;

    public static void createConnection(){
        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+DB,USER,PWD);
            System.out.println("Conexion establecida");
        }catch(Exception e){
             e.printStackTrace();
        }
    }
}
