/**
 * Used for examples in DS
 */
public class NetID implements Comparable<NetID> {
    
    private String netid;
    NetID (String netid) {
        this.netid = netid;
    }
    
    /* This is the method that will be used to compare keys in the BST */
    public int compareTo (NetID other) {
        return netid.compareTo(other.netid);
    }
    
    public String getID () {
        return netid;
    }
    public boolean equals (Object other) {
        if ( other == null || !(other instanceof NetID )) {
            return false;
        }
        NetID o = (NetID)other;
        return netid.equals(o.netid);
    }
    public String toString () {
        return netid;
    }
}
