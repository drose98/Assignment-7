package a7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObservablePictureImpl implements ObservablePicture{

    //Declaration temporary Picture
    protected Picture tempOP;
    private ROIObserverDecorator decoratedObserver;
    private ArrayList<Region> regionList = new ArrayList<>();
    private ArrayList<ROIObserverDecorator> observerList = new ArrayList<>();
    private boolean suspended = false;
    private Region unionRegion;
    private int offlineCounter = 0;

    //Constructor
    public ObservablePictureImpl(Picture p) {
        tempOP = p;
    }


    @Override
    public void registerROIObserver(ROIObserver observer, Region r) {
        decoratedObserver = new ROIObserverDecoratorImpl(observer, r);
        observerList.add(decoratedObserver);
        regionList.add(r);
    }

    @Override
    public void unregisterROIObservers(Region r) {
        Iterator<ROIObserverDecorator> iter = observerList.iterator();
        while (iter.hasNext()) {
            if (iter.next().hasRegion(r)) {
                iter.remove();
            }
        }

    }

    @Override
    public void unregisterROIObserver(ROIObserver observer) {
        Iterator<ROIObserverDecorator> iter = observerList.iterator();
        while (iter.hasNext()) {
            if (iter.next().getObserver() == observer) {
                iter.remove();
            }
        }
    }

    @Override
    public ROIObserver[] findROIObservers(Region r) {
        int tempInt = 0;
        List<ROIObserver> tempList = new ArrayList<>();
        for (ROIObserverDecorator observer : observerList) {
            tempInt = observer.howManyIntersectedROI(r);
            for (int i = 0; i < tempInt; i++) {
                tempList.add(observer.getObserver());
            }
        }
        return tempList.toArray(new ROIObserver[tempList.size()]);

    }

    @Override
    public void suspendObservable() {
        suspended = true;
    }

    @Override
    public void resumeObservable() {
        suspended = false;
        offlineCounter = 0;

        for (ROIObserverDecorator observer : observerList) {
            for (Region region : observer.getRegions()) {
                try {
                    observer.notify(this, region.intersect(unionRegion));
                } catch (NoIntersectionException e) {
                }
            }
        }
    }

    @Override
    public int getWidth() {
        return tempOP.getWidth();
    }

    @Override
    public int getHeight() {
        return tempOP.getHeight();
    }

    @Override
    public Pixel getPixel(int x, int y) {
        return tempOP.getPixel(x, y);
    }

    @Override
    public Pixel getPixel(Coordinate c) {
        return tempOP.getPixel(c);
    }

    @Override
    public void setPixel(int x, int y, Pixel p) {
        tempOP.setPixel(x,y,p);
        Region regionToNotify;

        if (!suspended) {
            for (ROIObserverDecorator observer : observerList) {
                for (Region region : observer.getRegions()) {
                    if (region.getBottom() >= y && y >= region.getTop()
                        && region.getRight() >= x && x >= region.getLeft()) {
                        regionToNotify = new RegionImpl(new Coordinate(x, y), new Coordinate(x, y));
                        observer.notify(this, regionToNotify);
                    }
                }
            }
        } else {
            regionToNotify = new RegionImpl(new Coordinate(x, y), new Coordinate(x, y));
            if (offlineCounter == 0) {
                unionRegion = regionToNotify;
                offlineCounter++;
            } else {
                unionRegion = unionRegion.union(regionToNotify);
                offlineCounter++;
            }
        }


    }

    @Override
    public void setPixel(Coordinate c, Pixel p) {
        this.setPixel(c.getX(), c.getY(), p);
    }

    @Override
    public SubPicture extract(int xoff, int yoff, int width, int height) {
        return tempOP.extract(xoff,yoff,width,height);
    }

    @Override
    public SubPicture extract(Coordinate a, Coordinate b) {
        return tempOP.extract(a,b);
    }
}
