/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStrucutres;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Lorenzo
 */
public class InternalNode extends Node {

    /**
     * The Update filed where info about node's state and Info are stored
     */
    public AtomicReference<Update> update;
    public AtomicReference<Node> left,
            /**
             * The left child of this node
             */
            /**
             * The right child of this node
             */
            right;

    /**
     *
     * @param key the key associated with this node
     */
    public InternalNode(Comparable key) {
        super(key);
    }

    public InternalNode(Update update, Node left, Node right, Comparable key) {
        super(key);
        this.update = new AtomicReference<>(update);
        this.left = new AtomicReference<>(left);
        this.right = new AtomicReference<>(right);
    }

    public InternalNode() {
        super(null);
        this.update = new AtomicReference<>(new Update(State.CLEAN, null));
        this.left = new AtomicReference<>(null);
        this.right = new AtomicReference<>(null);
    }

}
