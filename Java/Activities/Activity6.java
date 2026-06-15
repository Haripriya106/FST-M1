package Activities;

public class Activity6 {
	public static void main(String[] args) {

        // Create Plane object with maxPassengers = 10
        Plane plane = new Plane(10);

        // Add passengers
        plane.onboard("Raj");
        plane.onboard("Ram");
        plane.onboard("Neha");
        plane.onboard("Nisha");
        plane.onboard("Priya");

        // Print take-off time
        System.out.println("Take-off Time: " + plane.takeOff());

        // Print passenger list
        System.out.println("\nPassengers on board:");
        for (String passenger : plane.getPassengers()) {
            System.out.println(passenger);
        }

        // Simulate flight for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Flight interrupted.");
            e.printStackTrace();
        }

        // Land the plane
        plane.land();

        // Print landing time
        System.out.println("\nLanding Time: " + plane.getLastTimeLanded());
    }
}
