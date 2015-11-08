/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myUtil;

import concurrentbst.ConcurrentBST;
import dataStrucutres.Leaf;
import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;

/**
 *
 * @author lorenzo
 */
public class Operation implements Callable<TaskReport> {

    final ConcurrentBST<Integer, Object> tree;
    final OperationType type;
    final Integer key;

    public Operation(ConcurrentBST<Integer, Object> tree, OperationType type, Integer key) {
        this.tree = tree;
        this.type = type;
        this.key = key;
    }

    @Override
    public TaskReport call() throws Exception {
        TaskReport retval = new TaskReport();
        boolean opretval;
        retval.setMillisStart(System.currentTimeMillis());
        retval.setNanoStart(System.nanoTime());
        switch (type) {
            case INSERT:
                opretval = tree.insert(key, "" + key);
                retval.retvalues.put("OpDescription",
                        "INSERT(" + key + "):" + opretval);
                retval.retvalues.put("result", opretval);
                break;
            case DELETE:
                opretval = tree.delete(key);
                retval.retvalues.put("OpDescription",
                        "DELETE(" + key + "):" + opretval);
                retval.retvalues.put("result", opretval);
                break;
            case FIND:
                Leaf out = tree.find(key);
                retval.retvalues.put("OpDescription",
                        "FIND(" + key + "):" + (out.getKey() == key));
                break;
            default:
                break;
        }
        retval.setMillisEnd(System.currentTimeMillis());
        retval.setNanoEnd(System.nanoTime());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
        retval.retvalues.put("StartNanoSec", df.format(retval.getMillisStart()) + ":" + retval.getNanoStart());
        retval.retvalues.put("EndNanoSec", df.format(retval.getMillisEnd()) + ":" + retval.getNanoEnd());
        retval.retvalues.put("key", key);
        
        return retval;

    }

}
