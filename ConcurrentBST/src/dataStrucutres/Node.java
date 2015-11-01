/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStrucutres;

/**
 *
 * @author Lorenzo
 *
 */
public abstract class Node {

    Comparable key;

    /**
     *
     * @param key the key used to perform routing inside the tree data structure
     */
    public Node(Comparable key) {
        this.key = key;

    }

    /**
     *
     * @return the key associated with this Node
     */
    public Comparable getKey() {
        return key;
    }

    public void setKey(Comparable key) {
        this.key = key;
    }

}
