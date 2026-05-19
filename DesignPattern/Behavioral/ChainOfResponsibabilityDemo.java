// interface Handler {
//     void setNext(Handler handler);
//     void handle(String email,String password);
// }

// abstract class BaseHandler implements Handler{
//     protected Handler handler;

//     @Override
//     public void setNext(Handler handler){
//         this.handler = handler;
//     }
// }

// class Authentication extends BaseHandler {
   
//     @Override
//     public void handle(String email,String password){
//         System.out.println(email + password);

//         checkNext(email,password);
//     }

//     protected void checkNext(String email,String password){
//         if(this.handler != null){
//             this.handler.handle(email, password);
//         }
//     }
// }

// class AUthorization extends BaseHandler {
   
//     @Override
//     public void handle(String email,String password){
//         System.out.println(email + password);

//         checkNext(email,password);
//     }

//     protected void checkNext(String email,String password){
//         if(this.handler != null){
//             this.handler.handle(email, password);
//         }
//     }
// }

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

abstract class Middleware{
    private Middleware next;

    public static Middleware link(Middleware first , Middleware... chain){
        Middleware head = first;
        for(Middleware nextInChain : chain){
            first.next = nextInChain;
            first = nextInChain;
        }
        return head;
    }

    public abstract boolean check(String email,String password);

    protected boolean checkNext(String email,String password){
        if(next == null){
            return true;
        }

        return next.check(email, password);
    }
}

class ThrottlingMiddleware extends Middleware{
    private long curretnTime;
    private long request;
    private long requestPerMinute;

    public ThrottlingMiddleware(long requestPerMinute){
        this.requestPerMinute = requestPerMinute;
        this.curretnTime = System.currentTimeMillis();
    }



    @Override
    public boolean check(String email,String password){

        if(System.currentTimeMillis() > curretnTime + 60_000){
            request = 0;
            curretnTime = System.currentTimeMillis();
        }

        request++;

        if(request > requestPerMinute){
            System.out.println(" Request limit exceeded !");
            return false;
        }
        return checkNext(email, password);
    }
}

class UserExistsMiddleware extends Middleware {
    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }
}

class RoleCheckMiddleware extends Middleware {
    public boolean check(String email, String password) {
        if (email.equals("admin@example.com")) {
            System.out.println("Hello, admin!");
            return true;
        }
        System.out.println("Hello, user!");
        return checkNext(email, password);
    }
}

class Server {
    private Map<String, String> users = new HashMap<>();
    private Middleware middleware;

    /**
     * Client passes a chain of object to server. This improves flexibility and
     * makes testing the server class easier.
     */
    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    /**
     * Server gets email and password from client and sends the authorization
     * request to the chain.
     */
    public boolean logIn(String email, String password) {
        if (middleware.check(email, password)) {
            System.out.println("Authorization have been successful!");

            // Do something useful here for authorized users.

            return true;
        }
        return false;
    }

    public void register(String email, String password) {
        users.put(email, password);
    }

    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password);
    }
}

public class ChainOfResponsibabilityDemo {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // All checks are linked. Client can build various chains using the same
        // components.
        Middleware middleware = Middleware.link(
            new ThrottlingMiddleware(2),
            new UserExistsMiddleware(server),
            new RoleCheckMiddleware()
        );

        // Server gets a chain from client code.
        server.setMiddleware(middleware);
    }
    public static void main(String [] args) throws IOException{

        init();

        boolean success;
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password);
        } while (!success);
    }
}
