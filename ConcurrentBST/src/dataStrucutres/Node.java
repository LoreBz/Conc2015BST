package dataStrucutres;

import java.util.HashMap;

/**
 *
 * @author Lorenzo

 *
 */
public abstract class Node<K extends Integer, V extends Object> implements Comparable<Node> {

    Integer key;

    public Node(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
        new HashMap<String, Object>();

    }

    @Override
    public int compareTo(Node t) {
        return this.key.compareTo(t.getKey());
    }

}
