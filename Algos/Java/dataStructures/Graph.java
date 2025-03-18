package dataStructures;

import java.util.Map;
import java.util.LinkedList;

public interface Graph<T>
{
    boolean isEmpty( );
    void makeLink( T originNode, T destinationNode );
    void makeDoubleLink( T nodeOne, T nodeTwo );
    int connectedComp( T initialNode );
    Map<T, T> bfsIterative( T initialNode );
    LinkedList<T> path( T nodeOne, T nodeTwo );
    int lengthPath( T nodeOne, T nodeTwo );
    String toString( );
}
