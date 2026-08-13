

//To add new feature we should not change the existing code (Abstraction will help to achieve this)

//Stretagy & Decorator uses this OCP

//composition is more flexible then inheritance


interface PaymentMethod {
    void processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {
    private String cardNumber;

    @Override
    public void processPayment(double amount){
        System.out.println("Paymet of : " + amount + " in card " + this.cardNumber + " is Done.");
    }
}

class PayPalPayment implements PaymentMethod {
    private String phoneNumber;

    @Override
    public void processPayment(double amount){
        System.out.println("Payment of : "  + amount + " in Paypal Account " + this.phoneNumber + " is Done.");
    }
}

class UpiPayment implements PaymentMethod {
    private String upi;

    @Override
    public void processPayment(double amount){
        System.out.println("Payment of : "  + amount + " in UPI Account " + this.upi + " is Done.");
    }
}

class BitcoinPayment implements PaymentMethod {
    private String walletNumber;

    @Override
    public void processPayment(double amount){
        System.out.println("Payment of : "  + amount + " in Bitcoin Wallet : " + this.walletNumber + " is Done.");
    }
}


class PaymentProcessor {
    public void process(PaymentMethod paymentMethod , double amount){
        paymentMethod.processPayment(amount);
    }
}

class CheckoutService {
    public void processPayment(PaymentMethod paymentMethod, double amount){
        PaymentProcessor processor = new PaymentProcessor();

        processor.process(paymentMethod, amount);
    }
}


public class OpenClosed {
    //Usage
    public static void main(String[] args) {
        CheckoutService service = new CheckoutService();
        service.processPayment(new CreditCardPayment(),120.4);
        service.processPayment(new UpiPayment(),120.4);


        //Shippin Cost Calculator

        ShippingCostCalculator  calculator = new ShippingCostCalculator();

        calculator.calculate(new StandardShipping(),10);

        calculator.calculate(new ExpressShipping(),15.5);

    }
}


//EXAMPLE: SHIPPING COST CALCULATOR

interface ShippingStrategy {
    double calculateCost(double weight);
}

class StandardShipping implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return 10*weight + 30.0;
    }
}

class ExpressShipping implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return 20*weight + 40.0;
    }
}

class OvernightShipping implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return 30*weight + 50.0;
    }
}

class InternationalShipping implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return 50*weight + 100.0;
    }
}

class ShippingCostCalculator {
    public double calculate(ShippingStrategy shippingStrategy, double weight){
        return shippingStrategy.calculateCost(weight);
    }
}



