import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {

    public final static int MIN_VALUE = 5;
    public final static int MAX_VALUE = 150;
    public final static int CITIES_COUNT = 300;
    public static int ANT_NUMBER = 800;
    public static int ELITE_ANT_NUMBER = 10;
    public static int tMax = 100;
    public static double ALFA = 2.0;
    public static double BETA = 2.0;
    public static double RO = 0.425;
    public final static double TAU_0 = 0.1;
    public static double LMin = MAX_VALUE * CITIES_COUNT;
    public static int[][] cityGraph;
    public static ArrayList<ArrayList<Double>> sightMatrix;
    public static ArrayList<ArrayList<Double>> pheromoneMatrix;
    public static ArrayList<Ant> antsColony;

    public static void generateGraph() {
        int[][] mat = new int[CITIES_COUNT][CITIES_COUNT];
        for (int i = 0; i < CITIES_COUNT; i++) {
            for (int j = 0; j < CITIES_COUNT; j++) {
                if (i == j) {
                    mat[i][j] = 0;
                } else {
                    int x = (int) (Math.random() * (Main.MAX_VALUE - Main.MIN_VALUE) + Main.MIN_VALUE);
                    mat[i][j] = x;
                    mat[j][i] = x;
                }
            }
            cityGraph = mat;
        }
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
                    matrixCol.add(1.0 / cityGraph[i][j]);
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
            if (cityIndex >= CITIES_COUNT) {
                cityIndex = 0;
            }
            ant = new Ant();
            ant.setCurrentCityIndex(cityIndex);
            antsColony.add(ant);
            cityIndex++;
        }
    }

    public static void initAntsColonyInOneCity() {
        antsColony = new ArrayList<>();
        Ant ant;
        int cityIndex = (int) (Math.random() * Main.CITIES_COUNT);
        for (int i = 0; i < ANT_NUMBER; i++) {
            ant = new Ant();
            ant.setCurrentCityIndex(cityIndex);
            antsColony.add(ant);
        }
    }

    private static void findEliteAnts() {
        Comparator<Ant> comparator = Comparator.comparing(Ant::getRouteLength);
        antsColony.sort(comparator);
        for (int i = 0; i < ELITE_ANT_NUMBER; i++) {
            antsColony.get(i).elite();
        }
    }

    public static void updatePheromone() {
        double deltaTau;
        int origin;
        int destination;
        for (Ant ant : antsColony) {
            deltaTau = Main.LMin / ant.getRouteLength();
            if (ant.isElite()) {
                deltaTau *= 2;
            }

            LinkedList<Integer> route = (LinkedList<Integer>) ant.getRoute().clone();
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

    public static void printGraph() {
        for (int i = 0; i < CITIES_COUNT; i++) {
            for (int j = 0; j < CITIES_COUNT; j++) {
                System.out.print(" " + cityGraph[i][j]);
            }
            System.out.println();
        }
    }

    public static void printRoute(LinkedList<Integer> bestRoute, int bestRouteLength) {
        for (Integer index : bestRoute) {
            System.out.print(" " + index);
        }
        System.out.println();
        System.out.println("Route length = " + bestRouteLength);
    }

    public static void main(String[] args) {
        System.out.println("lab 4");
        Main.generateGraph();

        // тестування по параметрам
        //for (int i = 0; i < 41; i++) {

        Main.initSightMatrix();
        Main.initPheromoneMatrix();
        int bestRouteLength = Integer.MAX_VALUE;
        LinkedList<Integer> bestRoute = new LinkedList<>();
        for (int t = 0; t < tMax; t++) {
            Main.initAntsColony();
            for (Ant ant : antsColony) {
                ant.run();
                if (ant.getRouteLength() < bestRouteLength) {
                    bestRoute = ant.getRoute();
                    bestRouteLength = ant.getRouteLength();
                    Main.LMin = bestRouteLength;
                }
            }
            Main.evaporatePheromone();
            Main.findEliteAnts();
            Main.updatePheromone();

            // вивід шляху по ітераціям
            // System.out.println("Best route in " + t + " iteration");
            // Main.printRoute(bestRoute, bestRouteLength);
            System.out.println(t + ", " + bestRouteLength);
        }

        // тестування по параметрам
        // System.out.println(ALFA + ", " + BETA + ", " + RO + ", " + ANT_NUMBER + ", " + ELITE_ANT_NUMBER + ", " + bestRouteLength);
        // ELITE_ANT_NUMBER += 1;
        //}
    }


}
