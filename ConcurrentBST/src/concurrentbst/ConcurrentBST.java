/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentbst;

import interfaces.ITree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dataStrucutres.Dinfo;
import dataStrucutres.Iinfo;
import dataStrucutres.InternalNode;
import dataStrucutres.Leaf;
import dataStrucutres.Node;
import dataStrucutres.SearchOutput;
import dataStrucutres.State;
import dataStrucutres.Update;

public class ConcurrentBST<K extends Integer, V extends Object> implements
		ITree {

	private final Node<K, V> root;

	public ConcurrentBST() {
		// initialization with state clean and null info; dummy1 left child
		// dummy2 right child, dummy2 key and no value
		this.root = new InternalNode<>();

	}

	private SearchOutput search(Comparable target)
			throws IllegalStateException, NullPointerException {
		if (isEmpty()) {
			return new SearchOutput(null, null, null, null, null);
		}

		InternalNode gp = null, p = null;
		Node l = root;
		Update gpupdate = null, pupdate = null;
		// while l points to an internal node
		while (l instanceof InternalNode) {
			gp = p;
			p = (InternalNode) l;
			gpupdate = pupdate;
			pupdate = p.getUpdate();

			if (target.compareTo(l.getKey()) < 0) {
				l = (Node) ((InternalNode) l).left.get();
			} else {
				l = (Node) ((InternalNode) l).right.get();
			}

		}
		return new SearchOutput(gp, p, (Leaf) l, pupdate, gpupdate);
	}

	@Override
	public Leaf find(Integer key) {
		Leaf result = search(key).getL();
		if (result.getKey().equals(key)) {
			System.out.println("node " + key + " found");
		} else {
			System.out.println("node " + key + " not found");
		}
		return search(key).getL();
	}

	@Override
	public boolean insert(Integer key, Object value) {
		InternalNode p, newInternal;
		Leaf l, newSibling;
		Leaf newLeaf = new Leaf(key, value);
		Update pupdate;
		Iinfo op;

		while (true) {
			SearchOutput so = search(key);
			p = so.getP();
			l = so.getL();
			pupdate = so.getPupdate();
			// Cannot insert dupicate key
			if (l != null && l.getKey().equals(key)) {
				return false;
			}
			if (pupdate.getState() != State.CLEAN) {// help other operation
				help(pupdate);// help other operation and retry
			} else {
				// create new nodes
				newSibling = new Leaf(l.getKey(), l.getValue());
				Leaf smallChild = newLeaf, bigChild = newSibling;
				if (newSibling.getKey().compareTo(newLeaf.getKey()) < 0) {
					smallChild = newSibling;
					bigChild = newLeaf;
				}
				int newkey = Math.max(key, l.getKey());
				newInternal = new InternalNode(new Update(State.CLEAN, null),
						smallChild, bigChild, newkey);

				// define what to do after iflag
				op = new Iinfo(p, newInternal, l);
				// iflag
				boolean result = p.CASUpdate(pupdate, new Update(State.IFlag,
						op));
				if (result) {
					// then we let the helpinsert to complete the
					// "pointers-swing"
					helpinsert(op);
					return true;
				} else {
					// if sombody else has stolen the flag while we were near to
					// conquer it...well help the thief and retry
					help(p.getUpdate());
				}
			}

		}

	}

	@Override
	public boolean delete(Integer key) {
		InternalNode gp, p;
		Leaf l;
		Update pupdate, gpupdate;
		Dinfo op;

		while (true) {
			// first of all perform a search for the specified key
			SearchOutput so = search(key);
			gp = so.getGp();
			p = so.getP();
			l = so.getL();
			pupdate = so.getPupdate();
			gpupdate = so.getGpupdate();
			// if the key is not found simply return false
			if (l.getKey() != key) {
				return false;
			}
			if (!gpupdate.getState().equals(State.CLEAN)) {
				// if the grandparent is somehow "busy" help to complete the
				// operation in which gp is involved
				help(gpupdate);
			} else if (!pupdate.getState().equals(State.CLEAN)) {
				// else if the parent is busy (and gp is not) help him
				help(pupdate);
			} else {
				// if both gp and p are not busy
				op = new Dinfo(gp, p, l, pupdate);
				// dflag
				boolean result = gp.CASUpdate(gpupdate, new Update(State.DFlag,
						op));
				if (result) {
					if (helpdelete(op)) {
						return true;
					}
				} else {
					help(gpupdate);
				}
			}
		}

	}

	public boolean isEmpty() {
		return (root.getKey() == null);
	}

	public Node<K, V> getRoot() {
		return root;
	}

	private void help(Update u) {
		if (u.getState().equals(State.IFlag)) {
			helpinsert((Iinfo) u.getInfo());
		} else if (u.getState().equals(State.MARK)) {
			helpmarked((Dinfo) u.getInfo());
		} else if (u.getState().equals(State.DFlag)) {
			helpdelete((Dinfo) u.getInfo());
		}
	}

	private void helpinsert(Iinfo op) {
		if (op == null || !(op instanceof Iinfo)) {
			throw new NullPointerException(
					"It is not possible to complete this operation because the requested operation is null");
		}
		// ichild
		casChild(op.p, op.l, op.newInternal);
		// iunflag
		op.p.CASUpdate(op.p.getUpdate(), new Update(State.CLEAN, op));
	}

	private void casChild(InternalNode parent, Node old, Node newInternal) {
		if (!(parent instanceof InternalNode) || newInternal == null) {
			throw new NullPointerException();
		}
		if (newInternal.getKey().compareTo(parent.getKey()) < 0) {
			parent.left.compareAndSet(old, newInternal);
		} else {
			parent.right.compareAndSet(old, newInternal);
		}
	}

	private boolean helpdelete(Dinfo op) {
		if (op == null || !(op instanceof Dinfo)) {
			throw new NullPointerException();
		}
		boolean result = op.p.CASUpdate(op.pupdate, new Update(State.MARK, op));
		if (result || op.pupdate.getState().equals(State.MARK)) {
			helpmarked(op);
			return true;
		} else {
			help(op.p.getUpdate());
			// backtrack CAS
			op.gp.CASUpdate(op.gp.getUpdate(), new Update(State.CLEAN, op));
			return false;
		}
	}

	private void helpmarked(Dinfo op) {
		if (op == null || !(op instanceof Dinfo)) {
			throw new NullPointerException();
		}
		Node other;
		if (op.p.left.get() == op.l) {
			other = (Node) op.p.right.get();
		} else {
			other = (Node) op.p.left.get();
		}
		casChild(op.gp, op.p, other);
		// dunflag
		op.gp.CASUpdate(op.gp.getUpdate(), new Update(State.CLEAN, op));
	}

	public void printTree2DotFile(String filename)
			throws FileNotFoundException, UnsupportedEncodingException,
			IOException {
		PrintWriter writer = new PrintWriter(filename + ".dot", "UTF-8");
		writer.println("digraph BST {");

		Queue<Node> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Node pop = queue.poll();
			if (pop instanceof InternalNode) {
				InternalNode i = (InternalNode) pop;
				Node left = (Node) i.left.get();
				Node right = (Node) i.right.get();
				writer.println(i + " [shape=box];");
				// System.out.println(i + " [shape=box];");
				writer.println(i + " -> " + left + ";");
				// System.out.println(i + " -> " + left + ";");
				writer.println(i + " -> " + right + ";");
				// System.out.println(i + " -> " + right + ";");

				queue.add(left);
				queue.add(right);
			} else {
				// writer.println(pop.getKey()+" [shape=box];");
			}
		}
		writer.println("}");
		writer.close();

	}

	public List<Integer> keySet() {
		List<Integer> keylist = new LinkedList<>();

		Queue<Node> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Node pop = queue.poll();
			if (pop instanceof InternalNode) {
				InternalNode i = (InternalNode) pop;
				Node left = (Node) i.left.get();
				Node right = (Node) i.right.get();

				queue.add(left);
				queue.add(right);
			} else {
				Leaf l = (Leaf) pop;
				keylist.add(l.getKey());
			}
		}
		return keylist;
	}

}
