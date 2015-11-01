/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dataStrucutres.Leaf;

/**
 *
 * @author Lorenzo
 */
public interface ITree {

    public Leaf find(Comparable key);

    public boolean insert(Comparable key, Object value);

    public boolean delete(Comparable key);

}
