
//SRP - one reason to change for class.

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserService {

    //This class is GOD class with so many responsibalities
    private String username;
    private String email;
    private String password;

    //constructors,getter & setters

    public String validatePasswordAndGenerateHash(String email,String password){
            return email + ":" + password;
    }

    public void savePassword(String email, String password){
        // data base call to save this email,password entry
    }

    public String welcomeEmail(String email){
        String message = "Welcom to the portal";
        return message;
    }

    public String generateJWTTOken(String email,String password){
        return email + "." + password;
    }
}

//SRP implementation is below

//Just Storing the user information
class User{
    private String username;
    private String email;
    private String password;


    public User(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername(){ return this.username; }
    public String getEmail(){ return this.email; }
    public String getPassword(){ return this.password; }
}

//Validating & Hashing the password
class PasswordHasher {
    public String validateAndHash(String password){
        if(password.length() < 8){
            throw new IllegalArgumentException("Password must be at least of 8 characters.");
        }

        //user salt & bcrypt package for this purpose

        return "bcrypt_hashed_" + password;
    }
}

//Database Communication

class UserRepository {
    public void save(User user){
        //JDBC code or ORM logic to save this entity
        
        System.out.println("User : " + user.toString() + " is Saved in database.");
    }
}

//creation of authentication tokens.

class AuthTokenService {
    //create JWT token with user claims
    public String generateToken(User user){
        String payload = "{\"username\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() + "\"}";

        return "eyJhbGciOiJIUzI1NiJ9." + payload + ".signature";
    }
     
}

//Sending Emails

class EmailService {
    public void sendWelcomeEmail(User user){
        System.out.println("Sending welcome email to: " + user.getEmail());
        System.out.println("Welcome to our platform, " + user.getUsername() + "!");
    }

    public void sendPaySlip(){
        //
    }
}

//If we want to swith to specific service we just need to change in the specific class

//focus on cohesion not fragmentaion , group the logic which belongs to same business concern & changes together.

//utility classes do not ignore them apply SRP if needed

//Reason - business logic & technical behaviour

//class - one reason to change

// method - should do one thing only

//module - encapsulate one area of functionality

//service - single domain(microservice)

//system - 

//SRP mindset - seprate concerns

public class SRP {
    
}

//EXAMPLE OF ORDER SERVICE

class Order {
    private String orderId;
    private String productId;
    private int quatity;
    private String email;
    private double total;

    Order(String orderId,String productId,int quantity,String email,double total){
        this.orderId = orderId;
        this.productId = productId;
        this.quatity = quantity;
        this.email = email;
        this.total = total;
    }

    public String getProductId() {
        return productId;
    }
    public int getQuatity() {
        return quatity;
    }
    
    public String getEmail() {
        return email;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotal() {
        return total;
    }
}

class InventoryManager {
    private Map<String,Integer> inventory;

    InventoryManager(){
        this.inventory = new HashMap<>();
    }
    boolean checkInventory(String productId,int quantity){

        Integer stock = inventory.get(productId);

        if(stock == null || stock < quantity){
            System.out.println("Insufficient stock for " + productId);
            return false;
        }

        return true;
    }


    void updateInventory(String productId,int stock){
        inventory.put(productId,stock);
    }

}

class OrderProcessor {
    private List<Order>orders;
    private InventoryManager invetory;
    private NotificationService notificationService;
    

    OrderProcessor(InventoryManager inventoryManager,NotificationService notificationService){
        this.invetory = inventoryManager;
        this.notificationService = notificationService;
    }

    void placeOrder(String productId,int quantity,String email){

        if(!invetory.checkInventory(productId, quantity)){
            return;
        }
        double unitPrice = 150.0;//could get from another map

        double total = unitPrice * quantity;

        String orderId = "ORD-" + (orders.size() + 1);

        Order order = new Order(orderId,productId,quantity,email,total);

        orders.add(order);
        notificationService.sendNotification(order);
    }
}

class NotificationService{
    void sendNotification(Order order){
        String customerEmail = order.getEmail();
        String orderId = order.getOrderId();
        double total = order.getTotal();

        System.out.println("Email to " + customerEmail + ": Order " + orderId
            + " confirmed. Total: $" + total);
    }
}   


