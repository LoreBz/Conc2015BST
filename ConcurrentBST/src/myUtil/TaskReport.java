/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author lorenzo
 */
public class TaskReport {

    long start, end;

    public TaskReport() {
        start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(start);
        System.out.println(sdf.format(resultdate));
        //JOptionPane.showMessageDialog(null, sdf.format(resultdate));
    }

}
