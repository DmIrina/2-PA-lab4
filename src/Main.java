import java.util.ArrayList;

public class Main {

    public final static int MIN_VALUE = 5;
    public final static int MAX_VALUE = 150;
    public final static int CITIES_COUNT = 300;
    public static ArrayList<ArrayList<Integer>> cityGraph;
    public static ArrayList<ArrayList<Double>> sightMatrix;
    public static ArrayList<ArrayList<Double>> pheromoneMatrix;
    public static int LMin = MAX_VALUE * CITIES_COUNT;
    public static final double TAU_0 = 0.01;
    public static final int ANT_NUMBER = 1000;
    public static ArrayList<Ant> antsColony;
    public static int tMax = 100;

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

    public static void initSightMatrix(){
        ArrayList<ArrayList<Double>> matrixRow = new ArrayList<>();
        ArrayList<Double> matrixCol;
        for (int i = 0; i < CITIES_COUNT; i++) {
            matrixCol = new ArrayList<>();
            matrixRow.add(matrixCol);
            for (int j = 0; j < CITIES_COUNT; j++) {
                if (i == j) {
                    matrixCol.add(0.0);
                } else {
                    matrixCol.add(1.0/cityGraph.get(i).get(j));
                }
            }
        }
        sightMatrix = matrixRow;
    }

    public static void initPheromoneMatrix(){
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

    public static void initAntsColony(){
        antsColony = new ArrayList<>();
        Ant ant;
        int cityIndex = 0;
        for (int i = 0; i < ANT_NUMBER; i++){
            if (cityIndex > CITIES_COUNT){
                cityIndex = 0;
            }
            ant = new Ant();
            ant.setCurrentCityIndex(cityIndex);
            antsColony.add(ant);
            cityIndex++;
        }
    }

    public static void main(String[] args) {
        System.out.println("lab 4");
        Main.generateGraph();
        Main.initSightMatrix();
        int k = 1;
    }
}
