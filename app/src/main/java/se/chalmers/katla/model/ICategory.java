package se.chalmers.katla.model;

import java.util.Iterator;

/**
 * Created by Anton on 2014-10-06.
 */
public interface ICategory {

    /**
     * Gets the name of this category
     * @return the name of this category
     */
    public String getName();

    /**
     * Adds a composite to the list of composites that belongs to this category
     * @param composite the composite to add
     */
    public void addComposite(IComposite composite);

    /**
     * Removes a composite from the list if found
     * @param composite the composite to remove
     */
    public void removeComposite(IComposite composite);

    /**
     * Moves the composite to a specific index in the list of composites
     * @param composite the composite to move
     * @param index the unique index to move the composite to
     */
    public void moveComposite(IComposite composite, int index);

    /**
     *  Gets an interator over this categorys composites
     * @return an iterator over this categorys composites
     */
    public Iterator<IComposite> getComposites();

}
