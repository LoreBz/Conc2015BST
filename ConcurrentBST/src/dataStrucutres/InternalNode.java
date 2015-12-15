package dataStrucutres;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Lorenzo
 */
public class InternalNode<K extends Integer, V extends Object> extends Node<K, V> {

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
    public InternalNode(K key) {
        super(key);
    }

    public InternalNode(Update update, Node left, Node right, Integer key) {
        super(key);
        this.update = new AtomicReference<>(update);
        this.left = new AtomicReference<>(left);
        this.right = new AtomicReference<>(right);
    }

    public InternalNode() {
        super(Dummy.dummy2);
        this.update = new AtomicReference<>(new Update(State.CLEAN, null));
        this.left = new AtomicReference<>();
        this.left.set(new Leaf(Dummy.dummy1, null));
        this.right = new AtomicReference<>();
        this.right.set(new Leaf(Dummy.dummy2, null));
    }

    @Override
    public String toString() {
        if (this.getKey() == Dummy.dummy1) {
            return "iDummy1";
        } else if (this.getKey() == Dummy.dummy2) {
            return "iDummy2";
        } else {
            return "i" + this.getKey() + "";
        }
    }

    public boolean CASUpdate(Update expected, Update update) {
        return this.update.compareAndSet(
                expected, update);
    }

}
