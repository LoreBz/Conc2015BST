package dataStrucutres;

/**
 *
 * @author Lorenzo
 */
public class Leaf <K extends Integer, V extends Object> extends Node<K, V> {

    Object value;

    /**
     *
     * @param key the key associated with this Node
     * @param value a value stored in this Leaf
     */
    public Leaf(K key, Object value) {
        super(key);
        this.value = value;
    }

    /**
     *
     * @return the value associated with this Leaf
     */
    public Object getValue() {
        return value;
    }

    /**
     *
     * @param value the value associated with this Leaf
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.getKey() == Dummy.dummy1) {
            return "Dummy1";
        } else if (this.getKey() == Dummy.dummy2) {
            return "Dummy2";
        } else {
            return ""+this.getKey() + "";
        }
    }
    
    

}
