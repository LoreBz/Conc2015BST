/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStrucutres;

/**
 *
 * @author Lorenzo
 */
public class Iinfo extends Info {

    public InternalNode p, newInternal;
    public Leaf l;

    public Iinfo(InternalNode p, InternalNode newInternal, Leaf l) {
        this.p = p;
        this.newInternal = newInternal;
        this.l = l;
    }

}
