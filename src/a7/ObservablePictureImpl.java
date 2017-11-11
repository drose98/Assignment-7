package a7;

public class ObservablePictureImpl implements ObservablePicture{

    public ObservablePictureImpl(Picture p) {

    }


    @Override
    public void registerROIObserver(ROIObserver observer, Region r) {

    }

    @Override
    public void unregisterROIObservers(Region r) {

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
