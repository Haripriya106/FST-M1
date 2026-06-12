package Activities;

public class Car {
	// Variables
		String color;
		String transmission;
		int makeYear;
		int tyres = 4;
		int doors = 4;

		// Constructor
		public Car(String color, String transmission, int makeYear) {
			this.color = color;
			this.transmission = transmission;
			this.makeYear = makeYear;
		}

		// Functions
		public void displayCharacteristics() {
			System.out.println("Color of the car is: " + this.color);
			System.out.println("Transmission type of the car is: " + this.transmission);
			System.out.println("Manufacture year is: " + this.makeYear);
			System.out.println("No. of doors: " + this.doors);
			System.out.println("No. of tyres: " + this.tyres);
		}

		public void accelarate() {
			System.out.println("Car is moving.");
		}

		public void brake() {
			System.out.println("Car has stopped.");
		}
	}