package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        
        // create new Product object
        Product newProduct = new Product(id, name, stock, day, demand);

        // find the last digit of the given product ID. This is the index we add our new Product to
        int indexOfSector = id%10;

        // add our newProduct to the 
        sectors[indexOfSector].add(newProduct);

    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        
        // find the last digit of the given product ID
        int indexOfSector = id%10;

        // Sector[] is a MinHeap, where each index is a Product object that contains a popularity variable. This MinHeap is based on popularity
        int size = sectors[indexOfSector].getSize();

        sectors[indexOfSector].swim(size);        

    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {

        // find the last digit of the given product ID
        int indexOfSector = id%10;

        int size = sectors[indexOfSector].getSize();

        if (size == 5) {
            // to prevent shifting array, bring the last entry to the root and vice versa
            sectors[indexOfSector].swap(1, size);
            // since we are deleting the root, and the root is at the last entry, perform deleteLast()
            sectors[indexOfSector].deleteLast();
            // sink this last entry to bring it to its correct spot in the binary heap
            sectors[indexOfSector].sink(1);
        }        
        
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        
        // find the last digit of the given product ID
        int indexOfSector = id%10;

        // find how many products are in sectors[indexOfSector]
        int amountOfProducts = sectors[indexOfSector].getSize();

        // update the stock of that certain product
        for (int i = 1; i <= amountOfProducts; i++) {
            if (sectors[indexOfSector].get(i).getId() == id) {
                sectors[indexOfSector].get(i).updateStock(amount);
            }
        }
        
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        
        // find the last digit of the given product ID
        int indexOfSector = id%10;

        // find how many products are in sectors[indexOfSector]
        int amountOfProducts = sectors[indexOfSector].getSize();

        for (int i = 1; i <= amountOfProducts; i++) {
            if (sectors[indexOfSector].get(i).getId() == id) {
                sectors[indexOfSector].swap(i, amountOfProducts);
                sectors[indexOfSector].deleteLast();
                sectors[indexOfSector].sink(i);
                break;
            }
        }
        

    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        // IMPLEMENT THIS METHOD
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
