package server.info;

import java.util.Objects;

/**
 * Created by valentin on 23.10.16.
 */
public class User {
    private String name;
    String getName(){ return name; }
    User(String user){
        this.name = user;
    }

    @Override
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
    }

    @Override
    public String toString(){
        return "{" + name + "}";
    }
}