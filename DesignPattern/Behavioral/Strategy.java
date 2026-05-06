
class Order{
    public double getTotalWeight(){
        return 3.5;
    }

    public String getDestinationZone(){
        return "ZoneA";
    }

    public double getTotalValue(){
        return 56.5;
    }
}

interface ShippingStrategy {
    double calculateCost(Order order);
}

class FlatRateStrategy implements ShippingStrategy{
    private final double rate;

    FlatRateStrategy(double rate){
        this.rate = rate;
    }

    @Override
    public double calculateCost(Order order){
        return this.rate;
    }
}

class WeightBaseStrategy implements ShippingStrategy{
    private final double ratePerKg;

    WeightBaseStrategy(double rate){
        this.ratePerKg = rate;
    }

    @Override
    public double calculateCost(Order order){
        return ratePerKg*order.getTotalWeight();
    }
}


class DistanceBasedStrategy implements ShippingStrategy{
    private final double ratePerKm;

    DistanceBasedStrategy(double ratePerKm){
        this.ratePerKm = ratePerKm;
    }

    @Override
    public double calculateCost(Order order){
        switch (order.getDestinationZone()) {
            case "ZoneA":
                return 5 * ratePerKm;
            case "ZoneB":
                return 7 * ratePerKm;
            default:
                return 10 * ratePerKm;
        }
    }
}

class ThirdPartyApiShipping implements ShippingStrategy {
    private final double baseFee;
    private final double percentageFee;

    ThirdPartyApiShipping(double baseFee,double percentageFee){
        this.baseFee = baseFee;
        this.percentageFee = percentageFee;
    }

    @Override
    public double calculateCost(Order order){
        return baseFee + order.getTotalValue()*percentageFee;
    }
}

//Context Class

class ShipingCostService{
    private ShippingStrategy shippingStrategy;

    public ShipingCostService(ShippingStrategy shippingStrategy){
        this.shippingStrategy = shippingStrategy;
    }

    public void setShippingStrategy(ShippingStrategy shippingStrategy){
        this.shippingStrategy = shippingStrategy;
    }

    public double calculateShippingCost(Order order){
        if(shippingStrategy == null){
            //exception throw

            throw new IllegalStateException("Shipping Strategy is null.");
        }

        double cost = shippingStrategy.calculateCost(order);
        return cost;
    }
}


//Using below as client class
public class Strategy {

    public static void main(String[] args){
        Order order1 = new Order();

        // Create different strategy instances
        ShippingStrategy flatRate = new FlatRateStrategy(10.0);
        ShippingStrategy weightBased = new WeightBaseStrategy(2.5);
        ShippingStrategy distanceBased = new DistanceBasedStrategy(5.0);
        ShippingStrategy thirdParty = new ThirdPartyApiShipping(7.5, 0.02);

        // Create context with an initial strategy
        ShipingCostService shippingService = new ShipingCostService(flatRate);

        System.out.println("--- Order 1: Using Flat Rate (initial) ---");
        shippingService.calculateShippingCost(order1);

        System.out.println("\n--- Order 1: Changing to Weight-Based ---");
        shippingService.setShippingStrategy(weightBased);
        shippingService.calculateShippingCost(order1);

        System.out.println("\n--- Order 1: Changing to Distance-Based ---");
        shippingService.setShippingStrategy(distanceBased);
        shippingService.calculateShippingCost(order1);

        System.out.println("\n--- Order 1: Changing to Third-Party API ---");
        shippingService.setShippingStrategy(thirdParty);
        shippingService.calculateShippingCost(order1);

        // Adding a NEW strategy is easy:
        // 1. Create a new class implementing ShippingStrategy (e.g., FreeShippingStrategy)
        // 2. Client can then instantiate and use it:
        //    ShippingStrategy freeShipping = new FreeShippingStrategy();
        //    shippingService.setStrategy(freeShipping);
        //    shippingService.calculateShippingCost(primeMemberOrder);
        // No modification to ShippingCostService is needed!
    }
	
}
