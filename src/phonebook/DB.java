
package phonebook;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB {
    //Driver létrehozása
    final String URL = "jdbc:derby:sampleDB;create=true" ;
    final String USERNAME = "";
    final String PASSWORD = "";
    
    //létrehozzuk a kapcsolatot/hidat
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    public DB(){
        //megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("A kapcsolat létrehozása sikertelen volt.\n" + ex);
            
        }
        
        //ha sikerült akkor csinálunk egy megpakolható teherautót
        if(conn!=null){
            try {
              //kérjük a teherautót a hídtól  
              createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("A kapcsolat létrehozása sikertelen volt.\n" + ex);
            }
        }
         //megnézzük hogy üres e az adadtbázis, és hogy létezik e az adott adattábla
        try {
           dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Az adatbázis leírásának létrehozása sikertelen volt.\n" + ex);
        }
        
         try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
             System.out.println("Az adattábla létrejött");
            if(!rs.next())
            { 
             createStatement.execute("create table contacts(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),lastname varchar(20), firstname varchar(20), email varchar(30), number varchar(25))");
            }
        } catch (SQLException ex) {
            System.out.println("Az adattábla létrehozása sikertelen volt.\n" + ex);
        }           
    } 
    
    public ArrayList<Person> getAllContacts(){
        String sql = "select * from contacts";
        ArrayList<Person> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            while(rs.next()){
             Person actualPerson = new Person(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"),rs.getString("email"),rs.getString("number")); 
             users.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("hiba lépett fel az userek kiolvasásakor" + ex);
        }
        return users;
    }
    
    public void addContact(Person person){
        try{
            String sql = "insert into contacts (lastname,firstname,email,number) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, person.getLastName());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getNumber());
            ps.execute();
        }catch(SQLException ex){
            System.out.println("Hiba lépett fel az user hozzáadásakor" + ex);
        }    
    }
    
     public void updateContact(Person person){
        try{
            String sql = "update contacts set lastname = ?, firstname = ?, email = ?, number = ? where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, person.getLastName());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getNumber());
            ps.setInt(5, Integer.parseInt(person.getId()));
            ps.execute();
        }catch(SQLException ex){
            System.out.println("Hiba lépett fel az user hozzáadásakor" + ex);
        }    
    }
     
     public void removeContact(Person person){
        try{
            String sql = "delete from contacts where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(person.getId()));
            ps.execute();
        }catch(SQLException ex){
            System.out.println("Hiba lépett fel az user törlésekor" + ex);
        }    
    } 
    
}
