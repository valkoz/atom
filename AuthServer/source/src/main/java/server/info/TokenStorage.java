package server.info;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by valentin on 23.10.16.
 */

public class TokenStorage /*implements Dao<User>*/{
    private static final Logger log = LogManager.getLogger(TokenStorage.class);
    private static ConcurrentHashMap<User, String> credentials;
    private static ConcurrentHashMap<User, Token> tokens;
    private static ConcurrentHashMap<Token, User> tokensReversed;


    private static ObjectMapper mapper = new ObjectMapper();

    static {
        credentials = new ConcurrentHashMap<>();
        credentials.put(new User("admin", "suka"), "admin");
        tokens = new ConcurrentHashMap<>();
        tokens.put(new User("admin"), new Token(1L));
        tokensReversed = new ConcurrentHashMap<>();
        tokensReversed.put(new Token(1L), new User("admin"));
    }

    public static String writeJson() throws JsonProcessingException {
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper.writeValueAsString("{users : " + credentials.keySet().toString()+"}");
    }

    public static String registerNewUser(String user, String password){
        return credentials.putIfAbsent(new User(user, password), password);
    }

    public static boolean authenticate(String user, String password) throws Exception {
        User a = new User(user);
        return password.equals(credentials.get(a));
    }

    public static Token issueToken(String user) {
        User a = new User(user);
        Token token = tokens.get(a);
        if (token != null) {
            return token;
        }
        token = new Token(Token.randomToken());
        tokens.put(a, token);
        tokensReversed.put(token, a);
        return token;
    }

    public static boolean logOut(Token token){
        if (tokensReversed.containsKey(token)){
            User user = tokensReversed.get(token);
            tokensReversed.remove(token);
            tokens.remove(user);
            return true;
        }
        else
            return false;
    }

    public static String changeName(Token token, String name){
        if (tokensReversed.containsKey(token)) {
            User lastName = tokensReversed.get(token);
            tokensReversed.put(token, new User(name));
            tokens.remove(lastName);
            tokens.put(new User(name), token);
            String passwd = credentials.get(lastName);
            credentials.remove(lastName);
            credentials.put(new User(name), passwd);
            return lastName.toString();
        }
        else
            return null;
    }

    public static boolean containsKey(Token token){
        return tokensReversed.containsKey(token);
    }

    public static void validateToken(String rawToken) throws Exception {
        Token token = new Token(Long.parseLong(rawToken));
        if (!TokenStorage.containsKey(token)) {
            throw new Exception("Token validation exception");
        }

        log.info("Correct token from '{}'", TokenStorage.tokensReversed.get(token));
    }


    public void insert(User person) {
        Database.doTransactional(session -> session.save(person));
    }

}