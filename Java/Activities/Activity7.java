package Activities;

public class Activity7 {
	public static void main(String[] args) {

        MountainBike mb = new MountainBike(3, 0, 25);

        System.out.println(mb.bicycleDesc());

        mb.speedUp(20);
        System.out.println("\nAfter speeding up by 20:");
        System.out.println(mb.bicycleDesc());

        mb.applyBrake(5);
        System.out.println("\nAfter applying brake by 5:");
        System.out.println(mb.bicycleDesc());
    }
}
