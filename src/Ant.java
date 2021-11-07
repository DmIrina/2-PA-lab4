import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Ant {
    private int currentCityIndex;
    private boolean elite = false;
    private LinkedList<Integer> route = new LinkedList<>();
    private HashSet<Integer> visited = new HashSet<>();
    private Integer routeLength = 0;

    public void run() {
        while (visited.size() != Main.CITIES_COUNT) {
            int d = getDestinationIndex();
            if (d == -1) {
                System.out.println("Incorrect index");
            }
            route.add(d);
            visited.add(d);
            routeLength += Main.cityGraph[currentCityIndex][d];
            currentCityIndex = d;
        }
        route.add(route.getFirst());
        routeLength += Main.cityGraph[currentCityIndex][route.getFirst()];
    }

    public boolean isElite() {
        return elite;
    }

    public void elite(){
        this.elite = true;
    }

    public int getCurrentCityIndex() {
        return currentCityIndex;
    }

    public void setCurrentCityIndex(int currentCityIndex) {
        this.currentCityIndex = currentCityIndex;
        route.add(currentCityIndex);
        visited.add(currentCityIndex);
    }

    public Integer getRouteLength() {
        return routeLength;
    }

    public LinkedList<Integer> getRoute() {
        return route;
    }

    private Integer getDestinationIndex() {
        HashMap<Double, Integer> probabilities = new HashMap<>();
        double xSum = 0;
        for (int j = 0; j < Main.CITIES_COUNT; j++) {
            if (!visited.contains(j)) {
                double x = Math.pow(Main.pheromoneMatrix.get(currentCityIndex).get(j), Main.ALFA) * Math.pow(Main.sightMatrix.get(currentCityIndex).get(j), Main.BETA);
                xSum += x;
                probabilities.put(xSum, j);
            }
        }
        double rand = Math.random();

        for (Map.Entry<Double, Integer> entry : probabilities.entrySet()) {
            double probability = entry.getKey() / xSum;
            Integer destinationIndex = entry.getValue();
            if (rand < probability) {
                return destinationIndex;
            }
        }
        return -1;
    }
}
