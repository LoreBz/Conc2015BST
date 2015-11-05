package dataStrucutres;

/**
 *
 * @author Lorenzo
 */
public class Dinfo extends Info {

    public InternalNode gp, p;
    public Leaf l;
    public Update pupdate;

    public Dinfo(InternalNode gp, InternalNode p, Leaf l, Update pupdate) {
        this.gp = gp;
        this.p = p;
        this.l = l;
        this.pupdate = pupdate;
    }
    
    
}
