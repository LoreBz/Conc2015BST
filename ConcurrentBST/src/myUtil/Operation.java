/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myUtil;

import concurrentbst.ConcurrentBST;
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
        retval.setStart(System.currentTimeMillis());
        switch (type) {
            case INSERT:
                tree.insert(key, "" + key);
                break;
            case DELETE:
                tree.delete(key);
                break;
            case FIND:
                tree.find(key);
                break;
            default:
                break;
        }
        retval.setEnd(System.currentTimeMillis());
        return retval;

    }

}
