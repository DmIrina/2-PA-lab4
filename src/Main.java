import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public final static int MIN_VALUE = 5;
    public final static int MAX_VALUE = 150;
    public final static int CITIES_COUNT = 300;
    public static double LMin = MAX_VALUE * CITIES_COUNT;
    public final static int ANT_NUMBER = 1000;
    public static ArrayList<Ant> antsColony;
    public static int tMax = 100;
    public final static int ALFA = 1;
    public final static int BETA = 1;
    public final static double RO = 0.1;
    public final static double TAU_0 = 0.01;
    public static ArrayList<ArrayList<Integer>> cityGraph;
    public static ArrayList<ArrayList<Double>> sightMatrix;
    public static ArrayList<ArrayList<Double>> pheromoneMatrix;

    public static void generateGraph() {
        ArrayList<ArrayList<Integer>> graphRow = new ArrayList<>();
        ArrayList<Integer> graphCol;
        for (int i = 0; i < CITIES_COUNT; i++) {
            graphCol = new ArrayList<>();
            graphRow.add(graphCol);
            for (int j = 0; j < CITIES_COUNT; j++) {
                if (i == j) {
                    graphCol.add(0);
                } else {
                    graphCol.add((int) (Math.random() * (Main.MAX_VALUE - Main.MIN_VALUE) + Main.MIN_VALUE));
                }
            }
        }
        cityGraph = graphRow;
    }

    public static void initSightMatrix() {
        ArrayList<ArrayList<Double>> matrixRow = new ArrayList<>();
        ArrayList<Double> matrixCol;
        for (int i = 0; i < CITIES_COUNT; i++) {
            matrixCol = new ArrayList<>();
            matrixRow.add(matrixCol);
            for (int j = 0; j < CITIES_COUNT; j++) {
                if (i == j) {
                    matrixCol.add(0.0);
                } else {
                    matrixCol.add(1.0 / cityGraph.get(i).get(j));
                }
            }
        }
        sightMatrix = matrixRow;
    }

    public static void initPheromoneMatrix() {
        ArrayList<ArrayList<Double>> matrixRow = new ArrayList<>();
        ArrayList<Double> matrixCol;
        for (int i = 0; i < CITIES_COUNT; i++) {
            matrixCol = new ArrayList<>();
            matrixRow.add(matrixCol);
            for (int j = 0; j < CITIES_COUNT; j++) {
                if (i == j) {
                    matrixCol.add(0.0);
                } else {
                    matrixCol.add(TAU_0);
                }
            }
        }
        pheromoneMatrix = matrixRow;
    }

    public static void initAntsColony() {
        antsColony = new ArrayList<>();
        Ant ant;
        int cityIndex = 0;
        for (int i = 0; i < ANT_NUMBER; i++) {
            if (cityIndex > CITIES_COUNT) {
                cityIndex = 0;
            }
            ant = new Ant();
            ant.setCurrentCityIndex(cityIndex);
            antsColony.add(ant);
            cityIndex++;
        }
    }

    public static void updatePheromone() {
        double deltaTau;
        int origin;
        int destination;
        for (Ant ant : antsColony) {
            deltaTau = Main.LMin / ant.getRouteLength();
            LinkedList<Integer> route = ant.getRoute();
            origin = route.pollFirst();
            for (Integer city : route) {
                destination = city;
                double tau = pheromoneMatrix.get(origin).get(destination);
                pheromoneMatrix.get(origin).set(destination, tau + deltaTau);
                origin = destination;
            }
        }
    }

    private static void evaporatePheromone() {
        for (int i = 0; i < CITIES_COUNT; i++) {
            for (int j = 0; j < CITIES_COUNT; j++) {
                double d = Main.pheromoneMatrix.get(i).get(j);
                Main.pheromoneMatrix.get(i).set(j, d * (1 - Main.RO));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("lab 4");
        Main.generateGraph();
        Main.initSightMatrix();
        Main.initPheromoneMatrix();
        Main.initAntsColony();
        int bestRouteLength = Integer.MAX_VALUE;
        LinkedList<Integer> bestRoute;
        for (Ant ant : antsColony) {
            ant.run();
            if (ant.getRouteLength() < bestRouteLength) {
                bestRoute = ant.getRoute();
                bestRouteLength = ant.getRouteLength();
                Main.LMin = bestRouteLength;
            }
        }
        Main.evaporatePheromone();
        Main.updatePheromone();


        int k = 1;
    }
}
