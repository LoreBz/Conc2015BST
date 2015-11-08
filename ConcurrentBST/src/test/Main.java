/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import concurrentbst.ConcurrentBST;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author principale
 */
public class Main {

    public static void main(String[] args) {
        int ma = 1;
        int threadNum = 15;
        ConcurrentBST<Integer, Integer> tree = new ConcurrentBST<>();
         System.out.println(tree.keySet());
        TreeThread threads[] = new TreeThread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new TreeThread(tree, ma);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        ConcurrentLinkedQueue<Integer> addedTot = new ConcurrentLinkedQueue<Integer>();
        ConcurrentLinkedQueue<Integer> removedTot = new ConcurrentLinkedQueue<Integer>();
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < threads.length; i++) {
            addedTot.addAll(threads[i].getAdded());
            removedTot.addAll(threads[i].getRemoved());
        }
       
        System.out.println(addedTot);
        System.out.println(removedTot);
        for (int i : removedTot) {
            addedTot.remove(i);
        }
        LinkedList<Integer> findNodes = (LinkedList<Integer>) tree.keySet();
        addedTot.removeAll(Collections.singleton(null));
        findNodes.removeAll(Collections.singleton(null));
        //Collections.sort(addedTot);
        //Collections.sort(findNodes);
        System.out.println(addedTot);
        System.out.println(findNodes);
        for (int i : addedTot) {
            findNodes.removeFirstOccurrence(i);
        }
        assert (findNodes.isEmpty());
    }

}
