/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStrucutres;

/**
 *
 * @author Lorenzo
 */
public class Leaf extends Node {

    Object value;

    /**
     *
     * @param key the key associated with this Node
     * @param value a value stored in this Leaf
     */
    public Leaf(Comparable key, Object value) {
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
        return this.getKey()+"";
    }
    
    

}
