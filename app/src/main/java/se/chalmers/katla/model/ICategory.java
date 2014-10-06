package se.chalmers.katla.model;

import java.util.Iterator;

/**
 * Created by Anna on 2014-10-06.
 */
public interface ICategory {

    /**
     *
     * @return the name of this category
     */
    public String getName();

    /**
     * Adds a composite to the list of composites that belongs to this category
     * @param composite the composite you want to add
     */
    public void addComposite(IComposite composite);

    /**
     *
     * @return an iterator over this categorys composites
     */
    public Iterator<IComposite> getComposites();

}
