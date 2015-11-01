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
public class SearchOutput {

    InternalNode gp, p;
    Leaf l;
    Update pupdate, gpupdate;

    public SearchOutput() {
    }

    public SearchOutput(InternalNode gp, InternalNode p, Leaf l, Update pupdate, Update gpupdate) {
        this.gp = gp;
        this.p = p;
        this.l = l;
        this.pupdate = pupdate;
        this.gpupdate = gpupdate;
    }

    public InternalNode getGp() {
        return gp;
    }

    public void setGp(InternalNode gp) {
        this.gp = gp;
    }

    public InternalNode getP() {
        return p;
    }

    public void setP(InternalNode p) {
        this.p = p;
    }

    public Leaf getL() {
        return l;
    }

    public void setL(Leaf l) {
        this.l = l;
    }

    public Update getPupdate() {
        return pupdate;
    }

    public void setPupdate(Update pupdate) {
        this.pupdate = pupdate;
    }

    public Update getGpupdate() {
        return gpupdate;
    }

    public void setGpupdate(Update gpupdate) {
        this.gpupdate = gpupdate;
    }

}
