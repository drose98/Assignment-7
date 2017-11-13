package a7;

import java.util.ArrayList;

public class ObservablePictureImpl implements ObservablePicture{

    //Declaration temporary Picture
    protected Picture tempOP;
    private ROIObserverDecorator decoratedObserver;
    private ArrayList<Region> regionList;
    private ArrayList<ROIObserverDecorator> observerList;

    //Constructor
    public ObservablePictureImpl(Picture p) {
        tempOP = p;
    }


    @Override
    public void registerROIObserver(ROIObserver observer, Region r) {
        decoratedObserver = new ROIObserverDecoratorImpl(observer, r);
        observerList = new ArrayList<>();
        observerList.add(decoratedObserver);
        regionList = new ArrayList<>();
        regionList.add(r);
    }

    @Override
    public void unregisterROIObservers(Region r) {
        for (ROIObserverDecorator observer : observerList) {
            for (Region observerRegion : observer.getRegions()) {
                try {
                    observerRegion.intersect(r);
                    observer.removeRegion(r);
                } catch (NoIntersectionException e) {
                }
            }
        }
    }

    @Override
    public void unregisterROIObserver(ROIObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public ROIObserver[] findROIObservers(Region r) {
        ArrayList<ROIObserver> ObserverList = new ArrayList<>();
        for (ROIObserverDecorator decObserver : observerList) {
            if (decObserver.observerIntersects(r)) {
                ObserverList.add(decObserver.getObserver());
            }
        }
        ROIObserver[] tempArray = ObserverList.toArray(new ROIObserver[ObserverList.size()]);
        return tempArray;
    }

    @Override
    public void suspendObservable() {

    }

    @Override
    public void resumeObservable() {

    }

    @Override
    public int getWidth() {
        return 0;
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
        Coordinate tempCoordinate = new Coordinate(x,y);
        Region tempRegion = new RegionImpl(tempCoordinate, tempCoordinate);


    }

    @Override
    public void setPixel(Coordinate c, Pixel p) {
        tempOP.setPixel(c,p);
        Region tempRegion = new RegionImpl(c,c);
        for(ROIObserver observer : findROIObservers(tempRegion)) {
            observer.notify(this, tempRegion);
        }
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
