package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import java.util.Set;

/**
 * Specifies a Set for a Geometry E
 */
public interface GeometrySet<E> extends Iterable<E> {
    /**
     * Must maintain Set property (any equal segments can't be in set together)
     * @param e: the given Geometry to add
     * @return Integer: id of segment
     */
    public Integer add(E e);

    /**
     * Checks if the Set contains the given Geometry
     * @param e: the given Geometry to check
     * @return Boolean: true if it does, false if not
     */
    public boolean contains(E e);

    /**
     * Gets the Geometry E from the Set given its id
     * @param id: the id of the Geometry
     * @return Geometry: Returns the geometry if it exists, else null.
     */
    public E get(Integer id);
}
