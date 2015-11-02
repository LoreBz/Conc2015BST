/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import concurrentbst.ConcurrentBST;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import myUtil.Operation;
import myUtil.OperationType;
import myUtil.TaskReport;

/**
 *
 * @author Lorenzo
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentBST<Integer, Object> tree = new ConcurrentBST<>();
        //sequential test
        try {
//            someSequentialInsert(tree);
//            printTree(tree, "insertion");
//            someSequentialDelete(tree);
//            printTree(tree, "deletion");
            //concurrent test
            startConcurrentTest(tree);
        } catch (ExecutionException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        printTree(tree, "afterConcurrentTest");
    }

    public static void printTree(ConcurrentBST<Integer, Object> tree, String fileTitle) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            int hours = time.getHours();
            int minutes = time.getMinutes();
            int seconds = time.getSeconds();
            tree.printTree2DotFile(fileTitle + hours + "h" + minutes + "m" + seconds+"s_");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void someSequentialInsert(ConcurrentBST<Integer, Object> tree) {
        tree.insert(3, 3 + "");
        tree.insert(5, 5 + "");
        tree.insert(3, 3 + "");
        tree.insert(4, 4 + "");
        tree.insert(7, 7 + "");
        tree.insert(1, 1 + "");
        tree.insert(12, 12 + "");
        tree.insert(10, 10 + "");
        tree.insert(14, 14 + "");
        //1,3,4,5,7,10,12,14
    }

    public static void someSequentialDelete(ConcurrentBST<Integer, Object> tree) {
        tree.delete(1);
        tree.delete(1);
        tree.delete(5);
        tree.delete(5);
        tree.delete(1);
        tree.delete(5);
        tree.delete(6);
        tree.delete(1);
        tree.delete(3);
        tree.delete(2);
        tree.delete(10);
        tree.delete(12);
        tree.delete(14);
        //1,2,3,5,6,10,12,14
    }

    public static void startConcurrentTest(final ConcurrentBST<Integer, Object> tree) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<TaskReport>> callables = new ArrayList<>();

        //prepare 10 insert operations
        for (int i = 1; i <= 10; i++) {
            Callable<TaskReport> c = new Operation(tree, OperationType.INSERT, i);
            callables.add(c);
        }
        //prepare 5 delete operations (for instance deleting odd keys)
        for (int i=1; i<=10; i=i+2) {
            Callable<TaskReport> c = new Operation(tree, OperationType.DELETE, i);
            callables.add(c);
        }
        //prepare 5 find operations (looking for elements witk 5<=element.key<15)
        for (int i=1; i<=5; i++) {
            Callable<TaskReport> c = new Operation(tree, OperationType.FIND, i);
            callables.add(c);
        }
        //randomize order of operations before submitting them all together
        //Collections.shuffle(callables);
        List<Future<TaskReport>> futures = executorService.invokeAll(callables);

//        for (Future<String> future : futures) {
//            System.out.println("future.get = " + future.get());
//        }
        executorService.shutdown();
    }

}
