/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author pityk
 */
public class Person {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty number;
    private final SimpleStringProperty id;

    public Person() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
        this.number = new SimpleStringProperty("");
    }

    public Person(String lastName, String firstName, String email, String number) {
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.email = new SimpleStringProperty(email);
        this.number = new SimpleStringProperty(number);
        this.id = new SimpleStringProperty("");
    }
    
     public Person(Integer id, String lastName, String firstName, String email, String number) {
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.email = new SimpleStringProperty(email);
        this.number = new SimpleStringProperty(number);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public String getNumber() {
        return number.get();
    }

    public void setNumber(String numb) {
        this.number.set(numb);
    }
    
    public String getId(){
        return id.get();
    }
    
    
    public void setId(String id){
        this.id.set(id);
    }
    
    

}
