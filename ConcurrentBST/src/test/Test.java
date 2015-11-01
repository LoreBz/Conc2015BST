/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import concurrentbst.ConcurrentBST;
import dataStrucutres.InternalNode;
import dataStrucutres.Leaf;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentBST<String, Object> tree = new ConcurrentBST<>();
        //sequential test
        InternalNode root = tree.getRoot();
        root.setKey("B");
        root.left.set(new Leaf("A", "A"));
        root.right.set(new Leaf("B", "B"));
//        tree.insert("A", "A");
//        tree.insert("B", "B");
        if (tree.insert("C", "C")) {
            System.out.println("c inserted");
        }
        tree.insert("F", "F");

        Thread.sleep(500);
        try {
            tree.printTree2DotFile();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
