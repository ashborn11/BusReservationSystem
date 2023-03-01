/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.busreservationsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
    Connection connection = null;
   public static DataBase db = new DataBase();
   static String Dest="";
   static String Source="";
  
   static ArrayList busses;

    public DataBase(){
   
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/brs",
                "root", "root");
        }catch(Exception e){e.printStackTrace();}
    }
   
    Boolean registerUser(String name,String Adhar,String phone,String age,String gender){
       
        try{
            String query="insert into users(name,age,phno,adhar_no,gender) values('"+name+"','"+age+"','"+phone+"','"+Adhar+"','"+gender+"');";
            Statement stm= connection.createStatement();
            System.out.print(query);
            stm.executeUpdate(query);
           
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
       
    }
    void findUser(String id){
        String query="select * from users where id ='"+id+"';";
        try{
            connection.createStatement().executeQuery(query);
           
        }
        catch(Exception e){}
       
    }
    
    ArrayList getBuses(){
            String query= "select * from Bus where Source='"+Source+"' and Destination='"+Dest+"'";
            busses= new ArrayList();
            try{
               ResultSet r= connection.createStatement().executeQuery(query);
               while(r.next()){
                   Object o[]={r.getInt(1),r.getString(2),r.getString(3),r.getString(4)};
                    busses.add(o);
               }
               return busses;
            }
            catch(Exception e){e.printStackTrace();}
            return null;
    }
    ArrayList<Boolean> busStatus(){
        ArrayList <Boolean> arr= new ArrayList<Boolean>();
        String query ="select * from Seat where Busno='"+BusAvailable.selBusId+"';";
        System.out.print(query);
        try{
            ResultSet rs= connection.createStatement().executeQuery(query);
            while(rs.next()){
                arr.add(rs.getInt(3)==0);
                System.out.print(rs.getInt(3)==0);
            }
            return arr;
        }catch(Exception e){e.printStackTrace();}
        return null; 
    }
    Boolean bookBus(String n,String m,String g){
        try{
                   PreparedStatement st= connection.prepareStatement("insert into Passenger(Pname,Mob,Gender,Seatno) values(?,?,?,?)");
                   st.setString(1, n);
                   st.setString(2, m);
                   st.setString(3, Character.toString( g.charAt(0)));
                   st.setInt(4,Integer.parseInt((String)Seat.selected.get(0)));
                   st.executeUpdate();
                   connection.createStatement().executeUpdate("update Seat set Status=1 where Seatno="+(String)Seat.selected.get(0)+" and Busno="+BusAvailable.selBusId+";");
                   return true;
        }catch(Exception e){e.printStackTrace();}
        
        return false;
    }

   
}