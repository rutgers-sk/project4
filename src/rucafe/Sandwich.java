package rucafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sandwich menu item with bread, protein, extras, and quantity.
 * The base price depends on the protein, and each extra topping adds cost.
 *
 * The final price is (base + extras) * quantity.
 *
 * @author Sunghyun Kim
 */
public class Sandwich extends MenuItem implements Customizable {

    private Bread bread;
    private Protein protein;
    private final List<SandwichExtra> extras;

    // base price per protein
    private static final double BEEF_PRICE    = 12.99;
    private static final double CHICKEN_PRICE = 10.99;
    private static final double SALMON_PRICE  = 14.99;

    // extra prices
    private static final double VEGGIE_PRICE = 0.30; // lettuce, tomatoes, onions
    private static final double CHEESE_PRICE = 1.00;

    /**
     * Constructs a Sandwich with given bread, protein and quantity.
     * Extras list is initially empty.
     *
     * @param bread    bread type
     * @param protein  protein choice
     * @param quantity quantity of this sandwich
     */
    public Sandwich(Bread bread, Protein protein, int quantity) {
        super(quantity);
        this.bread = bread;
        this.protein = protein;
        this.extras = new ArrayList<>();
    }

    /**
     * Constructs a Sandwich with quantity 1.
     *
     * @param bread   bread type
     * @param protein protein choice
     */
    public Sandwich(Bread bread, Protein protein) {
        this(bread, protein, 1);
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
     * Computes the base price based on the selected protein.
     *
     * @return base price for a single sandwich
     */
    private double getBasePrice() {
        switch (protein) {
            case BEEF:
                return BEEF_PRICE;
            case CHICKEN:
                return CHICKEN_PRICE;
            case SALMON:
                return SALMON_PRICE;
            default:
                return 0.0;
        }
    }

    /**
     * Computes the extra price based on selected extras.
     *
     * @return total extras price for a single sandwich
     */
    private double getExtrasPrice() {
        double total = 0.0;
        for (SandwichExtra extra : extras) {
            if (extra == SandwichExtra.CHEESE) {
                total += CHEESE_PRICE;
            } else { // LETTUCE, TOMATOES, ONIONS
                total += VEGGIE_PRICE;
            }
        }
        return total;
    }

    /**
     * Computes the total price of the sandwich:
     * (base price + extras price) * quantity.
     *
     * @return total price
     */
    @Override
    public double price() {
        double single = getBasePrice() + getExtrasPrice();
        return single * getQuantity();
    }

    /**
     * String representation including bread, protein, extras, and quantity.
     *
     * @return formatted sandwich description
     */
    @Override
    public String toString() {
        return "Sandwich (" + bread + ", " + protein
                + ", extras=" + extras + ", qty=" + getQuantity() + ")";
    }

    /**
     * Two sandwiches are equal if they have same bread, protein, and extras set.
     *
     * @param obj object to compare
     * @return true if logically equal, false otherwise
     */
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