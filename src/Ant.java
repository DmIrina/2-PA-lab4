import java.util.HashSet;
import java.util.LinkedList;

public class Ant {
    private int currentCityIndex;
    private boolean elite = false;
    public LinkedList<Integer> route = new LinkedList<>();
    public HashSet<Integer> visited = new HashSet<>();
    public Integer routeLength = 0;

    public void Run(){

    }

    public boolean isElite(){
        return elite;
    }

    public int getCurrentCityIndex() {
        return currentCityIndex;
    }

    public void setCurrentCityIndex(int currentCityIndex) {
        this.currentCityIndex = currentCityIndex;
    }
}
