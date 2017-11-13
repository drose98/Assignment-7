package a7;
import java.util.ArrayList;

public interface ROIObserverDecorator extends ROIObserver{

    void addRegion(Region region);
    void removeRegion(Region region);
    ArrayList<Region> getRegions();
    boolean observerIntersects(Region r);
    ROIObserver getObserver();
}
