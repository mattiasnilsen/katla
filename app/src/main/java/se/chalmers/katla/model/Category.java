package se.chalmers.katla.model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Anton on 2014-10-06.
 */
public class Category implements ICategory {
    private String name;
    private List<IComposite> composites;

    public Category (String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addComposite(IComposite composite) {
        if(!composites.contains(composite)) {
            composites.add(composite);
        }
    }

    @Override
    public void removeComposite(IComposite composite) {
        composites.remove(composite);
    }

    @Override
    public void moveComposite(IComposite composite, int index) {
        composites.remove(composite);
        composites.add(index ,composite);
    }

    @Override
    public Iterator<IComposite> getComposites() {
        return composites.listIterator();
    }
}
