package rucafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sandwich menu item with bread, protein, extras, and quantity.
 * Price is determined by base sandwich price and number of extras.
 *
 * @author Sunghyun Kim
 */
public class Sandwich extends MenuItem implements Customizable {

    private Bread bread;
    private Protein protein;
    private final List<SandwichExtra> extras;

    private static final double BASE_PRICE = 6.99;
    private static final double EXTRA_PRICE = 0.99;

    /**
     * Constructs a Sandwich with given bread, protein, extras, and quantity.
     *
     * @param bread    bread type
     * @param protein  protein choice
     * @param quantity quantity
     */
    public Sandwich(Bread bread, Protein protein, int quantity) {
        super(quantity);
        this.bread = bread;
        this.protein = protein;
        this.extras = new ArrayList<>();
    }

    /**
     * Gets the bread type.
     *
     * @return bread type
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Sets the bread type.
     *
     * @param bread new bread type
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }

    /**
     * Gets the protein choice.
     *
     * @return protein choice
     */
    public Protein getProtein() {
        return protein;
    }

    /**
     * Sets the protein choice.
     *
     * @param protein new protein choice
     */
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    /**
     * Gets an unmodifiable list of extras.
     *
     * @return extras list
     */
    public List<SandwichExtra> getExtras() {
        return List.copyOf(extras);
    }

    /**
     * Adds an extra topping to the sandwich.
     *
     * @param obj should be a SandwichExtra
     * @return true if added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (!(obj instanceof SandwichExtra)) {
            return false;
        }
        SandwichExtra extra = (SandwichExtra) obj;
        if (extras.contains(extra)) {
            return false;
        }
        return extras.add(extra);
    }

    /**
     * Removes an extra topping from the sandwich.
     *
     * @param obj should be a SandwichExtra
     * @return true if removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof SandwichExtra)) {
            return false;
        }
        return extras.remove((SandwichExtra) obj);
    }

    /**
     * Computes the total price of the sandwich based on base price,
     * number of extras, and quantity.
     *
     * @return total price
     */
    @Override
    public double price() {
        double single = BASE_PRICE + (extras.size() * EXTRA_PRICE);
        return single * getQuantity();
    }

    @Override
    public String toString() {
        return "Sandwich (" + bread + ", " + protein
                + ", extras=" + extras + ", qty=" + getQuantity() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sandwich)) {
            return false;
        }
        Sandwich other = (Sandwich) obj;
        return this.bread == other.bread
                && this.protein == other.protein
                && this.extras.containsAll(other.extras)
                && other.extras.containsAll(this.extras);
    }
}