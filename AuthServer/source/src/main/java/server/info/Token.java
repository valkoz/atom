package server.info;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by valentin on 23.10.16.
 */
public class Token {
    public Long token;
    public Long getToken(){return token;}
    public Token(Long token){
        this.token = token;
    }
    public static Long randomToken(){
       return ThreadLocalRandom.current().nextLong();
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Token)) return false;
        Token user = (Token) other;
        return Objects.equals(user.token, this.token);
        }

    @Override
    public int hashCode(){
        return token.hashCode();
    }
}
