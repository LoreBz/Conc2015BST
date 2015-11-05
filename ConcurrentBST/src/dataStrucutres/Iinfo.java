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
