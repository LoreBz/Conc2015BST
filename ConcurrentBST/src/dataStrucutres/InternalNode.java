package dataStrucutres;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *
 * @author Lorenzo
 */
public class InternalNode<K extends Integer, V extends Object> extends
		Node<K, V> {

	/**
	 * The Update filed where info about node's state and Info are stored
	 */
	public AtomicStampedReference<Info> update;
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
	 * @param key
	 *            the key associated with this node
	 */
	public InternalNode(K key) {
		super(key);
	}

	public InternalNode(Update update, Node left, Node right, Integer key) {
		super(key);
		this.update = new AtomicStampedReference<>(update.info,
				State.CLEAN.ordinal());
		this.left = new AtomicReference<>(left);
		this.right = new AtomicReference<>(right);
	}

	public InternalNode() {
		super(Dummy.dummy2);
		this.update = new AtomicStampedReference<>(null, State.CLEAN.ordinal());
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
		return this.update.compareAndSet(expected.info, update.info,
				expected.state.ordinal(), update.state.ordinal());
	}

	public Update getUpdate() {
		Update result = new Update(
		// recover State enum ordinal value
				State.values()[this.update.getStamp()],
				// and info part too
				this.update.getReference());

		return result;
	}

}
