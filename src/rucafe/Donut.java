package rucafe;

/**
 * Represents a donut menu item with type, flavor, and quantity.
 * Price is determined by donut type and quantity.
 *
 * @author Sunghyun Kim
 */
public class Donut extends MenuItem {

    private DonutType type;
    private DonutFlavor flavor;

    // ====== 타입별 기본 가격 (pdf 보고 정확히 수정!!) ======
    private static final double YEAST_PRICE = 1.59;
    private static final double CAKE_PRICE = 1.79;
    private static final double HOLE_PRICE = 0.39;

    /**
     * Constructs a Donut with given type, flavor, and quantity.
     *
     * @param type     donut type
     * @param flavor   donut flavor
     * @param quantity quantity
     */
    public Donut(DonutType type, DonutFlavor flavor, int quantity) {
        super(quantity);
        this.type = type;
        this.flavor = flavor;
    }

    /**
     * Gets the donut type.
     *
     * @return donut type
     */
    public DonutType getType() {
        return type;
    }

    /**
     * Sets the donut type.
     *
     * @param type new donut type
     */
    public void setType(DonutType type) {
        this.type = type;
    }

    /**
     * Gets the donut flavor.
     *
     * @return donut flavor
     */
    public DonutFlavor getFlavor() {
        return flavor;
    }

    /**
     * Sets the donut flavor.
     *
     * @param flavor new flavor
     */
    public void setFlavor(DonutFlavor flavor) {
        this.flavor = flavor;
    }

    /**
     * Computes the total price for this donut item based on type and quantity.
     *
     * @return total price
     */
    @Override
    public double price() {
        double base;
        switch (type) {
            case YEAST:
                base = YEAST_PRICE;
                break;
            case CAKE:
                base = CAKE_PRICE;
                break;
            case HOLE:
                base = HOLE_PRICE;
                break;
            default:
                base = YEAST_PRICE;
        }
        return base * getQuantity();
    }

    @Override
    public String toString() {
        return "Donut (" + type + ", " + flavor + ", qty=" + getQuantity() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Donut)) {
            return false;
        }
        Donut other = (Donut) obj;
        return this.type == other.type && this.flavor == other.flavor;
    }
}