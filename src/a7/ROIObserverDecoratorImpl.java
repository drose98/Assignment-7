package a7;

import java.util.ArrayList;

public class ROIObserverDecoratorImpl implements ROIObserverDecorator{
    //Decorated ROIObserve; Observer now "knows" the regions it is associated with

    //declaration of temp/basic observer and ArrayList for associated regions
    protected ROIObserver tempObserver;
    public ArrayList<Region> regions;

    //constructor
    public ROIObserverDecoratorImpl(ROIObserver observer, Region region) {
        tempObserver = observer;
        regions = new ArrayList<>();
        regions.add(region);
    }

    //returns basic ROIObserver
    public ROIObserver getObserver() {
        return tempObserver;
    }


    //associates a region to the observer
    public void addRegion(Region region){
        regions.add(region);
    }

    //removes a region from the observer
    public void removeRegion(Region region){
        regions.remove(region);
    }

    //returns List of regions associated with the observer
    public ArrayList<Region> getRegions() {
        return regions;
    }

    public boolean observerIntersects(Region r) {
        for (Region region : regions) {
            try {
                region.intersect(r);
                return true;
            } catch (NoIntersectionException e) {
            }
        } return false;
    }


    @Override
    public void notify(ObservablePicture picture, Region changed_region) {
        tempObserver.notify(picture, changed_region);
    }
}
