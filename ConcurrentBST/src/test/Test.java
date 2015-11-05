/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import concurrentbst.ConcurrentBST;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import myUtil.Operation;
import myUtil.OperationType;
import myUtil.TaskReport;
import myUtil.WizardTest;

/**
 *
 * @author Lorenzo
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        setLookAndFeel();
        final ConcurrentBST<Integer, Object> tree = new ConcurrentBST<>();
        //sequential test
        //     try {
//            someSequentialInsert(tree);
//            printTree(tree, "insertion");
//            someSequentialDelete(tree);
//            printTree(tree, "deletion");

//concurrent test
//            startConcurrentTest(tree);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WizardTest wizardTest = new WizardTest(tree);
                wizardTest.setLocationRelativeTo(null);
                wizardTest.setVisible(true);
            }
        });

        // printTree(tree, "afterConcurrentTest");
    }

    public static void printTree(ConcurrentBST<Integer, Object> tree, String fileTitle) throws InterruptedException {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        int hours = time.getHours();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();
        String filename = fileTitle + hours + "h" + minutes + "m" + seconds + "s_" + "BST";
        try {

            tree.printTree2DotFile(filename);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        String OS = System.getProperty("os.name").toLowerCase();
        Desktop desktop = Desktop.getDesktop();
//
//        try {
//            desktop.open(new File(filename));
//        } catch (IOException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }

        try {
            if (OS.indexOf("win") >= 0) {
                //Windows OS
//                System.out.println("cmd /C: set PATH=%PATH%;Graphviz2.38\\bin");
//                Runtime.getRuntime().exec("set PATH=%PATH%;Graphviz2.38\\bin");
                System.out.println(
                        "> .\\Graphviz2.38\\bin\\dot -Tpdf " + filename + ".dot -o " + filename + ".pdf");
                Runtime.getRuntime().exec(
                        ".\\Graphviz2.38\\bin\\dot -Tpdf " + filename + ".dot -o " + filename + ".pdf");

                Thread.sleep(500);

                desktop.open(new File(filename + ".pdf"));
            } else {
                desktop.open(new File(filename + ".dot"));
            }

        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            if (OS.indexOf("win") >= 0) {
                System.out.println(
                        "There are problem in printing the tree\nUnder Windows in order to render the tree and save it into a pdf file you need graphviz\nAvailable from here http://graphviz.org/Download.php");
            }
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
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        List<Callable<TaskReport>> callables = new ArrayList<>();

        //prepare 10 insert operations
        for (int i = 1; i <= 10; i++) {
            Callable<TaskReport> c = new Operation(tree, OperationType.INSERT, i);
            callables.add(c);
        }
        //prepare 5 delete operations (for instance deleting odd keys)
        for (int i = 1; i <= 10; i = i + 2) {
            Callable<TaskReport> c = new Operation(tree, OperationType.DELETE, i);
            callables.add(c);
        }
        //prepare 5 find operations (looking for elements witk 5<=element.key<15)
        for (int i = 1; i <= 5; i++) {
            Callable<TaskReport> c = new Operation(tree, OperationType.FIND, i);
            callables.add(c);
        }
        //randomize order of operations before submitting them all together
        Collections.shuffle(callables);
        List<Future<TaskReport>> futures = executorService.invokeAll(callables);

//        for (Future<String> future : futures) {
//            System.out.println("future.get = " + future.get());
//        }
        executorService.shutdown();
    }

    public static void startConcurrentTest(ConcurrentBST<Integer, Object> tree, List<Operation> tasks, Integer numbThreads) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException, ExecutionException, IOException {
        String reportFilename = "report.csv";
        PrintWriter reportWriter = new PrintWriter(reportFilename, "UTF-8");
        reportWriter.println("Description,start,end,nanoStart,nanoEnd");
        System.out.println("Preparing " + tasks.size() + " operations to be executed with a pool of " + numbThreads + " threads"
        );
        Collections.shuffle(tasks);
        ExecutorService executorService = Executors.newFixedThreadPool(numbThreads);
        List<Future<TaskReport>> futures = executorService.invokeAll(tasks);

        executorService.awaitTermination(4, TimeUnit.SECONDS);

        List<TaskReport> reports = new ArrayList<>();
        for (Future<TaskReport> future : futures) {
            reports.add(future.get());
        }
        Collections.sort(reports);
        for (TaskReport tr : reports) {
//            Date dateStart = new Date(tr.getStart());
//            Date dateEnd = new Date(tr.getEnd());
            //SimpleDateFormat dfFormat = new SimpleDateFormat("HH:mm:ss:SSSSSSS");
            //String dateTextStart = new DecimalFormat("#.##########").format(dateStart)
            //String dateTextEnd = new DecimalFormat("#.##########").format(dateEnd)
//            String dateTextStart = dfFormat.format(dateStart);
//            String dateTextEnd = dfFormat.format(dateEnd);
            //System.out.println("solution Time : " + new DecimalFormat("#.##########").format(seconds) + " Seconds");
            Map<String, Object> param = tr.getRetvalues();
            reportWriter.println(param.get("OpDescription") + "," + /*param.get("StartNanoSec") + "," + param.get("EndNanoSec") + "," +*/ tr.getNanoStart() + "," + tr.getNanoEnd());
        }
        reportWriter.close();
        Thread.sleep(500);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(reportFilename));

    }

    private static boolean setLookAndFeel() {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
