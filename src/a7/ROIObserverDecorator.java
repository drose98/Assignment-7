package a7;
import java.util.ArrayList;

public interface ROIObserverDecorator extends ROIObserver{

    void addRegion(Region region);
    void removeRegion(Region region);
    ArrayList<Region> getRegions();
    ROIObserver getObserver();
    boolean hasRegion(Region r);
    void removeAllRegions();
    int howManyIntersectedROI(Region r);
}
