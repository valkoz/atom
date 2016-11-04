package server.info;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by valentin on 23.10.16.
 */

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idi;
    private String name;
    private String password;
    private Timestamp regdate; // ! не предоставлять api для изменения даты регистрации !
    private String email;
    String getName(){ return name; }
    User(String user){
        this.name = user;
    }
    User(String name, Timestamp registrationdate){this.name = name; this.regdate = registrationdate;}
    public User(String name, Timestamp registrationdate, String password){this.name = name; this.email = "tvoya_mamka"; this.regdate = registrationdate; this.password = password;}
    public User(String user, String password){
        this.name = user; this.password = password; this.email = "tvoya_mamka";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User person = (User) o;

        if (idi != person.idi) return false;
        return name != null ? name.equals(person.name) : person.name == null;

    }

    @Override
    public int hashCode() {
        int result = idi;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

  /*  @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof User)) return false;
        User user = (User) other;
        return Objects.equals(user.name, this.name);
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }*/

    @Override
    public String toString(){
        return "Person{" +
                "id=" + idi +
                ", name='" + name + '\'' +
                ", password=" + password +
                //", registration date" + registrationdate +
                ", email" + email +
                '}';
    }

    public int getId() {
        return idi;
    }

    public void setId(int id) {
        this.idi = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Date getRegistrationdate() {
//        return registrationdate;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}