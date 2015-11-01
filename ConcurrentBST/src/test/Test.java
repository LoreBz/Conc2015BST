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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentBST<Integer, Object> tree = new ConcurrentBST<>();
//        //sequential test

//        tree.insert(2, "A");
//        
        tree.insert(1, 1+"");
        tree.insert(3, 3+"");
        tree.insert(5, 5+"");
        tree.insert(3,3+"");
        tree.insert(4, 4+"");
        tree.insert(7, 7+"");
        tree.insert(12, 12+"");
//        tree.insert(10, 10+"");
//        tree.insert(14, 14+"");
//        BTreePrinter.printNode(tree.getRoot());
//        
//        tree.delete(5);
//        tree.delete(1);
//        tree.delete(5);
//        tree.delete(6);
//        tree.delete(1);
//        tree.delete(3);
//        tree.delete(2);
        
        try {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            int hours = time.getHours();
            int minutes = time.getMinutes();
            int seconds = time.getSeconds();
            tree.printTree2DotFile(hours + ":" + minutes + ":" + seconds);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
