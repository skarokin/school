public class BinarySearchReview {
    
    /**
     * RUNNING TIME: O(logn)
     * Iterative Solution
     * 1 - 2 - 3 - 4 - 5 - 6 - 7, target = 6
     * mid = a[3]
     * a[3] < target
     * new lo = mid + 1 (mid + 1 because we already checked mid)
     * @return the index where target is found, or -1 if not target not found 
     * @param target is the target integer, a is the array being searched 
     */
    public static int iterative(Integer target, Integer[] a) {

        int hi = a.length;
        int lo = 0;
        String indent = "";

        while (hi > lo) {

            // since hi = a.length, and a.length is not an index of a, use this new formula for mid
            int mid = lo + (hi - lo) / 2;
            System.out.println(indent + "(" + lo + "," + mid + "," + hi + ")");

            // compareTo returns > 0 if a[mid] > target, < 0 if a[mid] < target, = 0 if a[mid] == target
            int comparison = a[mid].compareTo(target);

            if (comparison == 0) {
                System.out.println(indent + "found at: " + mid);
                return mid;
            } else if (comparison > 0) {       // target is greater than a[mid]
                hi = mid;               // we don't do hi = mid - 1 because we have hi = a.length, not hi = a.length - 1
                indent += "\t";
            } else {
                lo = mid + 1;           // target is less than a[mid]
                indent += "\t";
            }

        }

        // if target not found, return -1
        return -1;

    }
    
    /**
     * RUNNING TIME: O(logn)
     * Recursive Solution
     * 1 - 2 - 3 - 4 - 5 - 6 - 7, target = 6
     * mid = a[3]
     * a[3] < target
     * new lo = mid + 1 (mid + 1 because we already checked mid)
     * @return the index where target is found, or -1 if not target not found 
     * @param target is the target integer, a is the array being searched 
     */
    public static int recursive(Integer target, Integer[] a, int lo, int hi, String indent) {
        
        System.out.println(indent + "search("+target+","+a+","+lo+","+hi+")");
        if (hi <= lo) return -1;    // target is not present in array a

        int mid = lo + (hi - lo) / 2;

        int compare = a[mid].compareTo(target);
        System.out.println(indent + "(" + lo + "," + mid + "," + hi + ")");

        if (compare == 0) {
            System.out.println(indent + "found at:" + mid);
            return mid;
        } else if (compare > 0) {
            // we don't do hi = mid - 1 because we have hi = a.length, not hi = a.length - 1
            return recursive(target, a, lo, mid, indent+"\t");  
        } else {
            return recursive(target, a, mid+1, hi, indent+"\t");
        }
    }

    /**
     * CLIENT CODE
     */
    public static void main(String[] args) {

        Integer[] a = {1, 3, 67, 120, 1233, 4578, 10033};

        String indent = "";

        recursive(1233, a, 0, a.length, indent);

        iterative(4578, a);
    }


}
