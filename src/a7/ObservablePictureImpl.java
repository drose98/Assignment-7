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

    }

    @Override
    public ROIObserver[] findROIObservers(Region r) {
        return new ROIObserver[0];
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
        return 0;
    }

    @Override
    public Pixel getPixel(int x, int y) {
        return null;
    }

    @Override
    public Pixel getPixel(Coordinate c) {
        return null;
    }

    @Override
    public void setPixel(int x, int y, Pixel p) {

    }

    @Override
    public void setPixel(Coordinate c, Pixel p) {

    }

    @Override
    public SubPicture extract(int xoff, int yoff, int width, int height) {
        return null;
    }

    @Override
    public SubPicture extract(Coordinate a, Coordinate b) {
        return null;
    }
}
