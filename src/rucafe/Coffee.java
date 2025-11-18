package rucafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a coffee menu item with a specific size and optional add-ins.
 * Price is determined by cup size, number of add-ins, and quantity.
 *
 * @author Sunghyun Kim
 */
public class Coffee extends MenuItem implements Customizable {

    private CupSize size;
    private final List<CoffeeAddIn> addIns;

    private static final double SHORT_PRICE = 1.89;
    private static final double TALL_PRICE = 2.29;
    private static final double GRANDE_PRICE = 2.69;
    private static final double VENTI_PRICE = 3.09;

    private static final double ADD_IN_PRICE = 0.30;

    /**
     * Constructs a Coffee with given size and quantity.
     *
     * @param size     cup size
     * @param quantity quantity of this coffee
     */
    public Coffee(CupSize size, int quantity) {
        super(quantity);
        this.size = size;
        this.addIns = new ArrayList<>();
    }

    /**
     * Constructs a Coffee with given size and default quantity 1.
     *
     * @param size cup size
     */
    public Coffee(CupSize size) {
        this(size, 1);
    }

    /**
     * Gets the cup size for this coffee.
     *
     * @return the cup size
     */
    public CupSize getSize() {
        return size;
    }

    /**
     * Sets the cup size for this coffee.
     *
     * @param size new cup size
     */
    public void setSize(CupSize size) {
        this.size = size;
    }

    /**
     * Gets an unmodifiable view of current add-ins.
     *
     * @return list of add-ins
     */
    public List<CoffeeAddIn> getAddIns() {
        return List.copyOf(addIns);
    }

    /**
     * Adds an add-in to this coffee (if not already present).
     *
     * @param obj should be a CoffeeAddIn
     * @return true if successfully added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (!(obj instanceof CoffeeAddIn)) {
            return false;
        }
        CoffeeAddIn addIn = (CoffeeAddIn) obj;
        if (addIns.contains(addIn)) {
            return false;
        }
        return addIns.add(addIn);
    }

    /**
     * Removes an add-in from this coffee.
     *
     * @param obj should be a CoffeeAddIn
     * @return true if successfully removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof CoffeeAddIn)) {
            return false;
        }
        return addIns.remove((CoffeeAddIn) obj);
    }

    /**
     * Computes the total price for this coffee based on size, add-ins, and quantity.
     *
     * @return total price for this coffee
     */
    @Override
    public double price() {
        double base;

        switch (size) {
            case SHORT:
                base = SHORT_PRICE;
                break;
            case TALL:
                base = TALL_PRICE;
                break;
            case GRANDE:
                base = GRANDE_PRICE;
                break;
            case VENTI:
                base = VENTI_PRICE;
                break;
            default:
                base = SHORT_PRICE;
        }

        double addInCost = addIns.size() * ADD_IN_PRICE;
        double singleCupPrice = base + addInCost;

        return singleCupPrice * getQuantity();
    }

    /**
     * String representation including size, add-ins, and quantity.
     *
     * @return formatted coffee description
     */
    @Override
    public String toString() {
        return "Coffee (" + size + ", add-ins=" + addIns + ", qty=" + getQuantity() + ")";
    }

    /**
     * Two coffees are equal if they have the same size and the same set of add-ins.
     *
     * @param obj object to compare
     * @return true if logically equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coffee)) {
            return false;
        }
        Coffee other = (Coffee) obj;
        return this.size == other.size && this.addIns.containsAll(other.addIns)
                && other.addIns.containsAll(this.addIns);
    }
}