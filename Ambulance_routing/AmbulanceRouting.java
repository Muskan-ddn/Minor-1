import java.io.*;
import java.util.*;

public class AmbulanceRouting {

    static HashMap<String, HashMap<String, Integer>> locationMap;
    static HashSet<String> hospitals;

    public AmbulanceRouting() {
        locationMap = new HashMap<>();
        hospitals = new HashSet<>();
    }

    public void addLocation(String locationName) {
        locationMap.put(locationName, new HashMap<>());
    }

    public void addHospital(String hospitalName) {
        hospitals.add(hospitalName);
    }

    public void addConnection(String source, String destination, int distance) {
        if (!locationMap.containsKey(source) || !locationMap.containsKey(destination)) {
            System.out.println("Invalid location names.");
            return;
        }
        locationMap.get(source).put(destination, distance);
        locationMap.get(destination).put(source, distance);
    }

    public void addHospitalConnection(String hospital, String location, int distance) {
        if (!hospitals.contains(hospital) || !locationMap.containsKey(location)) {
            System.out.println("Invalid hospital or location names.");
            return;
        }

        // Check if hospital location is present in locationMap
        if (locationMap.containsKey(hospital)) {
            locationMap.get(hospital).put(location, distance);
        } else {
            System.out.println("Hospital location not found: " + hospital);
        }

        // Add connection from location to hospital
        locationMap.get(location).put(hospital, distance);
    }

    public void displayLocations() {
        System.out.println("List of Locations:");
        locationMap.keySet().forEach(location -> System.out.println(location));
    }

    public void displayHospitals() {
        System.out.println("List of Hospitals:");
        hospitals.forEach(hospital -> System.out.println(hospital));
    }

    public void displayLocationMap() {
        System.out.println("Location Route Map:");
        locationMap.forEach((location, connections) -> {
            System.out.println(location + " =>");
            connections.forEach((destination, distance) -> System.out.printf("\t%s (Distance: %d kilometers)%n",
                    destination, distance));
        });
    }

    public int ambulanceRoute(String hospital, String location, int distance) {
        if (!hospitals.contains(hospital) || !locationMap.containsKey(location)) {
            System.out.println("Invalid hospital or location names.");
            return -1;
        }

        // Add hospital to the locationMap if not already present
        if (!locationMap.containsKey(hospital)) {
            locationMap.put(hospital, new HashMap<>());
        }

        locationMap.get(hospital).put(location, distance);
        locationMap.get(location).put(hospital, distance);
        return distance;
    }

    private int ambulancePath(String source, String destination) {
        Map<String, Integer> distances = new HashMap<>();
        Set<String> unvisited = new HashSet<>(locationMap.keySet());

        for (String locationName : unvisited) {
            distances.put(locationName, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        while (!unvisited.isEmpty()) {
            String current = unvisited.stream()
                    .min(Comparator.comparingInt(distances::get))
                    .orElse(null);

            if (current == null) {
                System.out.println("No path found.");
                return -1;
            }

            unvisited.remove(current);

            locationMap.get(current).forEach((neighbor, distance) -> {
                int newDistance = distances.get(current) + distance;
                Integer currentDistance = distances.get(neighbor);
                if (currentDistance != null && newDistance < currentDistance) {
                    distances.put(neighbor, newDistance);
                }
            });
        }

        Integer result = distances.get(destination);
        return (result != null) ? result.intValue() : -1;
    }

    public static void main(String[] args) throws IOException {
        AmbulanceRouting ambulanceNetwork = new AmbulanceRouting();

        ambulanceNetwork.addLocation("ISBT");
        ambulanceNetwork.addLocation("Jolly Grant Airport");
        ambulanceNetwork.addLocation("Kargi Chowk");
        ambulanceNetwork.addLocation("Rispana Pull");
        ambulanceNetwork.addLocation("Clock Tower");
        ambulanceNetwork.addLocation("Jogiwala");
        ambulanceNetwork.addLocation("Ballupur Chowk");
        ambulanceNetwork.addLocation("IMA");
        ambulanceNetwork.addLocation("FRI");
        ambulanceNetwork.addLocation("Pacific Mall");
        ambulanceNetwork.addLocation("Prem Nagar");
        ambulanceNetwork.addLocation("Survey Chowk");
        ambulanceNetwork.addLocation("Sai Mandir");
        ambulanceNetwork.addLocation("IT Park");
        ambulanceNetwork.addLocation("Railway Station");
        ambulanceNetwork.addLocation("Musoorie Diversion");
        ambulanceNetwork.addLocation("Balliwala Chowk");

        ambulanceNetwork.addHospital("Max Hospital");
        ambulanceNetwork.addHospital("Synergy Hospital");
        ambulanceNetwork.addHospital("Graphic Era Hospital");
        ambulanceNetwork.addHospital("Kailash Hospital");

        ambulanceNetwork.addConnection("ISBT", "Prem Nagar", 9);
        ambulanceNetwork.addConnection("ISBT", "Kargi Chowk", 3);
        ambulanceNetwork.addConnection("ISBT", "Balliwala Chowk", 6);
        ambulanceNetwork.addConnection("Prem Nagar", "IMA", 2);
        ambulanceNetwork.addConnection("IMA", "FRI", 2);
        ambulanceNetwork.addConnection("IMA", "Balliwala Chowk", 3);
        ambulanceNetwork.addConnection("FRI", "Ballupur Chowk", 2);
        ambulanceNetwork.addConnection("FRI", "Balliwala Chowk", 2);
        ambulanceNetwork.addConnection("Ballupur Chowk", "Balliwala Chowk", 2);
        ambulanceNetwork.addConnection("Ballupur Chowk", "Clock Tower", 4);
        ambulanceNetwork.addConnection("Balliwala Chowk", "Sai Mandir", 2);
        ambulanceNetwork.addConnection("Clock Tower", "Survey Chowk", 2);
        ambulanceNetwork.addConnection("Clock Tower", "Pacific Mall", 6);
        ambulanceNetwork.addConnection("Clock Tower", "IT Park", 8);
        ambulanceNetwork.addConnection("Clock Tower", "Sai Mandir", 2);
        ambulanceNetwork.addConnection("Clock Tower", "Railway Station", 2);
        ambulanceNetwork.addConnection("Clock Tower", "Rispana Pull", 5);
        ambulanceNetwork.addConnection("Musoorie Diversion", "Pacific Mall", 1);
        ambulanceNetwork.addConnection("Musoorie Diversion", "IT Park", 3);
        ambulanceNetwork.addConnection("IT Park", "Survey Chowk", 6);
        ambulanceNetwork.addConnection("Survey Chowk", "Rispana Pull", 5);
        ambulanceNetwork.addConnection("Sai Mandir", "Railway Station", 2);
        ambulanceNetwork.addConnection("Railway Station", "Kargi Chowk", 4);
        ambulanceNetwork.addConnection("Railway Station", "Rispana Pull", 4);
        ambulanceNetwork.addConnection("Kargi Chowk", "Rispana Pull", 4);
        ambulanceNetwork.addConnection("Rispana Pull", "Jogiwala", 2);
        ambulanceNetwork.addConnection("Jogiwala", "Jolly Grant Airport", 22);

        // Add connections between hospitals and locations
        ambulanceNetwork.addHospitalConnection("Max Hospital", "Musoorie Diversion", 1);
        ambulanceNetwork.addHospitalConnection("Synergy Hospital", "Ballupur Chowk", 1);
        ambulanceNetwork.addHospitalConnection("Graphic Era Hospital", "Prem Nagar", 8);
        ambulanceNetwork.addHospitalConnection("Kailash Hospital", "Rispana Pull", 1);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n~~");
            System.out.println("\nMenu:");
            System.out.println("1. List all locations");
            System.out.println("2. List all hospitals");
            System.out.println("3. Show location map");
            System.out.println("4. Get ambulance route");
            System.out.println("5. Exit");
            System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 5) : ");

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.println("\n~~");
                    ambulanceNetwork.displayLocations();
                    break;
                case 2:
                    System.out.println("\n~~");
                    ambulanceNetwork.displayHospitals();
                    break;
                case 3:
                    System.out.println("\n~~");
                    ambulanceNetwork.displayLocationMap();
                    break;
                case 4:
                    System.out.println("\n~~");
                    System.out.print("Enter hospital name: ");
                    String hospital = reader.readLine();
                    System.out.print("Enter accident spot name: ");
                    String accidentSpot = reader.readLine();
                    int distance = ambulanceNetwork.ambulancePath(hospital, accidentSpot);
                    System.out.println("\n~~");
                    if (distance != -1) {
                        System.out.println("Shortest distance for the ambulance: " + distance + " Kilometer");
                    }
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}