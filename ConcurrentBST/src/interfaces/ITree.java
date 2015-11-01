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

    public Leaf find(Integer key);

    public boolean insert(Integer key, Object value);

    public boolean delete(Integer key);

}
