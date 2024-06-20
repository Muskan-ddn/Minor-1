The program calculates the shortest route between a specified hospital and an accident spot using Dijkstra's algorithm. This ensures that the ambulance can reach the accident spot in the minimum possible time.

Functionality

Add Locations and Hospitals:
      The application allows users to add multiple locations and hospitals to the system, which are stored in the locationMap and hospitals respectively.
      
Add Connections:
      Users can define connections between locations and specify the distance between them. The connections are stored in the locationMap as bidirectional routes.

Display Information:
     The system can display all added locations, hospitals, and the entire route map with distances.

Shortest Path Calculation:
     Using Dijkstra's algorithm, the application calculates the shortest path between a specified hospital and an accident spot. It does so by:
     Initializing distances for all locations to infinity, except the source, which is set to zero.
     Iteratively visiting the nearest unvisited location and updating the distances to its neighbors.
     Returning the shortest distance to the destination.

Menu-Driven Interface:
     The main method provides a user-friendly interface with options to list locations, hospitals, show the location map, get the ambulance route, and exit the application.
    
Running the Application

Compile the Program : javac AmbulanceRouting.java

Run the Program : java AmbulanceRouting

Interact with the Menu : Enter numbers corresponding to menu options to list locations, hospitals, show the map, and find the shortest ambulance route.
