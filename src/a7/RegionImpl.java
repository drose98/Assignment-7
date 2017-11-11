package a7;


public class RegionImpl implements Region{

    private Coordinate c1, c2;

    public RegionImpl(Coordinate a, Coordinate b) {

        //local variables in order to create new reformed coordinates
        int mostLeft = Math.min(a.getX(), b.getX());
        int mostRight = Math.max(a.getX(), b.getX());
        int highest = Math.min(a.getY(), b.getY());
        int lowest = Math.max(a.getY(), b.getY());

        //creation of private coordinates for region
        this.c1 = new Coordinate(mostLeft, highest);
        this.c2 = new Coordinate(mostRight, lowest);
    }

    @Override
    public Coordinate getUpperLeft() {
        return c1;
    }

    @Override
    public Coordinate getLowerRight() {
        return c2;
    }

    @Override
    public int getTop() {
        return c1.getY();
    }

    @Override
    public int getBottom() {
        return c2.getY();
    }

    @Override
    public int getLeft() {
        return c1.getX();
    }

    @Override
    public int getRight() {
        return c2.getX();
    }

    @Override
    public Region intersect(Region other) throws NoIntersectionException {

        //temporary local variables to create return region
        int tempLeft, tempRight, tempTop, tempBottom;

        // checks to see if Region parameter is null
        if (other == null) {
            throw new NoIntersectionException();
        }

        // determines integer values of intersection region
        tempLeft = Math.max(this.getLeft(), other.getLeft());
        tempRight = Math.min(this.getRight(), other.getRight());
        tempTop = Math.max(this.getTop(), other.getTop());
        tempBottom = Math.min(this.getBottom(), other.getBottom());

        //ensures intersection, if not, throws exception
        if (tempLeft > tempRight || tempTop > tempBottom) {
            throw new NoIntersectionException();
        }

        //temporary coordinates for region return
        Coordinate cReturn1 = new Coordinate(tempLeft, tempTop);
        Coordinate cReturn2 = new Coordinate(tempRight, tempBottom);

        //return new region
        return new RegionImpl(cReturn1, cReturn2);

    }

    @Override
    public Region union(Region other) {

        //temporary local variables to create return region
        int tempLeft, tempRight, tempTop, tempBottom;

        //returns itself if Region parameter is null
        if (other == null) {
            return this;
        }

        // determines integer values of union region
        tempLeft = Math.min(this.getLeft(), other.getLeft());
        tempRight = Math.max(this.getRight(), other.getRight());
        tempTop = Math.min(this.getTop(), other.getTop());
        tempBottom = Math.max(this.getBottom(), other.getBottom());

        //temporary coordinates for region return
        Coordinate cReturn1 = new Coordinate(tempLeft, tempTop);
        Coordinate cReturn2 = new Coordinate(tempRight, tempBottom);

        //return new region
        return new RegionImpl(cReturn1, cReturn2);

    }
}
