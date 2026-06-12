package Activities;

public class Activity3 {

    public static String adjustDevice(String device, int value) {

        return switch (device) {

            case null ->
                    "[Error] Device cannot be null.";

            case String d when d.equalsIgnoreCase("THERMOSTAT") && value >= 40 ->
                    "[Thermostat] Warning: Temperature high.";

            case String d when d.equalsIgnoreCase("THERMOSTAT") && value < 40 ->
                    "[Thermostat] Temperature is set to " + value + ".";

            case String d when d.equalsIgnoreCase("LIGHT") ->
                    "[Light] Adjusting brightness to " + value + "%.";

            default ->
                    "[Error] Unsupported device.";
        };
    }

    public static void main(String[] args) {
        System.out.println(adjustDevice("THERMOSTAT", 45));
        System.out.println(adjustDevice("THERMOSTAT", 25));
        System.out.println(adjustDevice("LIGHT", 80));
        System.out.println(adjustDevice(null, 10));
    }
}