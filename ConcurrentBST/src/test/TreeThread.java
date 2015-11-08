/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import concurrentbst.ConcurrentBST;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author principale
 */
public class TreeThread extends Thread {

    ConcurrentBST<Integer, Integer> tree;
    int max;
    Random r;
    static int s = 0;
    ConcurrentLinkedQueue<Integer> added = new ConcurrentLinkedQueue<Integer>();
    ConcurrentLinkedQueue<Integer> removed = new ConcurrentLinkedQueue<Integer>();

    public TreeThread(ConcurrentBST<Integer, Integer> tree, int max) {
        this.tree = tree;
        this.max = max;
        this.r = new Random();
    }

    public ConcurrentLinkedQueue<Integer> getAdded() {
        return added;
    }

    public ConcurrentLinkedQueue<Integer> getRemoved() {
        return removed;
    }

    @Override
    public void run() {
        for (int i = max * 10; i > 0; i--) {
            int key = r.nextInt(max);
            if (r.nextDouble() < 0.5) {
                if (tree.insert(key, key)) {
                    added.add(key);
                    System.out.println("Inserted " + key);
                }
            } else {
                if (tree.delete(key)) {
                    removed.add(key);
                    System.out.println("Removed " + key);
                }
            }
        }
    }
}
