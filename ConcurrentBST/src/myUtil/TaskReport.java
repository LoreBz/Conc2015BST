/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lorenzo
 */
public class TaskReport implements Comparable<TaskReport> {

    Long millisStart, millisEnd;
    Long nanoStart, nanoEnd;
    Map<String, Object> retvalues;

    public TaskReport() {
        this.retvalues = new HashMap<>();
    }

    public Long getMillisStart() {
        return millisStart;
    }

    public void setMillisStart(Long millisStart) {
        this.millisStart = millisStart;
    }

    public Long getMillisEnd() {
        return millisEnd;
    }

    public void setMillisEnd(Long millisEnd) {
        this.millisEnd = millisEnd;
    }

    public Long getNanoStart() {
        return nanoStart;
    }

    public void setNanoStart(Long nanoStart) {
        this.nanoStart = nanoStart;
    }

    public Long getNanoEnd() {
        return nanoEnd;
    }

    public void setNanoEnd(Long nanoEnd) {
        this.nanoEnd = nanoEnd;
    }

    public Map<String, Object> getRetvalues() {
        return retvalues;
    }

    @Override
    public int compareTo(TaskReport o) {
        if (this.millisStart.equals(o.getMillisStart())) {
            return this.nanoStart.compareTo(o.getNanoStart());
        } else {
            return this.millisStart.compareTo(o.getMillisStart());
        }

    }

}
