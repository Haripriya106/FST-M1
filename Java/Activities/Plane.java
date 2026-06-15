package Activities;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Plane {
	// Private variables
    private int maxPassengers;
    private String[] passengers;
    private LocalDateTime lastTimeLanded;
    private int passengerCount;

    // Constructor
    public Plane(int maxPassengers) {
        this.maxPassengers = maxPassengers;
        this.passengers = new String[0]; // empty array
        this.passengerCount = 0;
    }

    // Add passenger to array
    public void onboard(String passenger) {

        if (passengers.length >= maxPassengers) {
            System.out.println("Plane is full.");
            return;
        }

        passengers = Arrays.copyOf(passengers, passengers.length + 1);
        passengers[passengers.length - 1] = passenger;
        passengerCount++;
    }

    // Returns current date and time
    public LocalDateTime takeOff() {
        return LocalDateTime.now();
    }

    // Sets landing time and clears passengers
    public void land() {
        lastTimeLanded = LocalDateTime.now();
        passengers = new String[0]; // clear array
        passengerCount = 0;
    }

    // Returns last landing time
    public LocalDateTime getLastTimeLanded() {
        return lastTimeLanded;
    }

    // Returns passenger array
    public String[] getPassengers() {
        return passengers;
    }

    // Optional getter
    public int getMaxPassengers() {
        return maxPassengers;
    }
}