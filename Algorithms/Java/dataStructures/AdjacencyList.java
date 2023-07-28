package dataStructures;
import java.util.*;

public class AdjacencyList<T> implements Graph<T>
{
    public  Map< T, LinkedList<T> > data;
    private Map< T, String > color;

    // constructor
    public AdjacencyList()
    {
        data = new HashMap<>();
        color = new HashMap<>();
    }

    // methods

    public boolean isEmpty()
    {
        return data.size() == 0;
    }

    public void makeLink( T originNode, T destinationNode )
    {
        if( !data.containsKey( originNode ) )
        {
            data.put( originNode, new LinkedList<T>() );
            color.put( originNode, new String() );
        }
        data.get( originNode ).add( destinationNode );
    }

    public void makeDoubleLink( T nodeOne, T nodeTwo )
    {
        makeLink( nodeOne, nodeTwo );
        makeLink( nodeTwo, nodeOne );
    }

    public int connectedComp( T initialNode )
    {
        for( T node: data.keySet() )
            color.replace( node, "white" );
        return dfsVisit( initialNode );
    }

    private int dfsVisit( T node )
    {
        int totalMarquet = 1;
        color.replace( node, "gray" );
        for( T neighbor: data.get( node ) )
            if( color.get( neighbor ).equals( "white" ) )
                totalMarquet += dfsVisit( neighbor );
        color.replace( node, "black" );
        return totalMarquet;
    }

    public Map<T, T> dfsIterative( T initialNode )
    {
        for( T node: data.keySet() )
            color.replace( node, "white" );
        color.replace( initialNode, "gray" );
        Map<T, T> path = new HashMap<>();
        path.put( initialNode, null );
        Stack<T> nodeList = new Stack<>();
        nodeList.push( initialNode );
        T lastElement;
        while( !nodeList.isEmpty() )
        {
            lastElement = nodeList.pop();
            for( T neighbor: data.get( lastElement ) )
            {
                if( color.get( neighbor ).equals( "white" ) )
                {
                    color.replace( neighbor, "gray" );
                    path.put( neighbor, lastElement );
                    nodeList.push( neighbor );
                }
                color.replace( lastElement, "black" );
            }
        }
        return path;
    }

    public Map<T, T> bfsIterative( T initialNode )
    {
        for( T node: data.keySet() )
            color.replace( node, "white" );
        color.replace( initialNode, "gray" );
        Map<T, T> treeDistance = new HashMap<>();
        treeDistance.put( initialNode, null );
        Queue<T> nodeList = new LinkedList<>();
        nodeList.add( initialNode );
        T firstElement;
        while( !nodeList.isEmpty() )
        {
            firstElement = nodeList.remove();
            for( T neighbor: data.get( firstElement ) )
            {
                if( color.get( neighbor ).equals( "white" ) )
                {
                    color.replace( neighbor, "gray" );
                    treeDistance.put( neighbor, firstElement );
                    nodeList.add( neighbor );
                }
                color.replace( firstElement, "black" );
            }
        }
        return treeDistance;
    }

    public LinkedList<T> path( T nodeOne, T nodeTwo )
    {
        Map<T, T> treeDistance = bfsIterative( nodeOne );
        LinkedList<T> path = new LinkedList<>();
        path.addFirst( nodeTwo );
        T auxiliary = nodeTwo;
        while( treeDistance.get( auxiliary ) != null )
        {
            auxiliary = treeDistance.get( auxiliary );
            path.addFirst( auxiliary );
        }
        return path;
    }

    public int lengthPath( T nodeOne, T nodeTwo )
    {
        return path( nodeOne, nodeTwo ).size();
    }

    @Override
    public String toString()
    {
        return data.toString();
    }

    public boolean isConnected()
    {
        return data.size() == connectedComp( data.keySet().iterator().next() );
    }

    public static void main(String[] args)
    {
        AdjacencyList<Integer> city = new AdjacencyList<>();
        city.makeDoubleLink( 1, 2 );
        city.makeDoubleLink( 2, 3 );
        city.makeDoubleLink( 2, 4 );
        city.makeDoubleLink( 4, 3 );
        city.makeDoubleLink( 7, 8 );
        System.out.println();
        System.out.println( "the graph is:\t " + city );
        System.out.println( "Number of component connected with node 1:\t " + city.connectedComp(1) );
        System.out.println( "This graph is connected?\t" + city.isConnected() );
        city.makeDoubleLink( 1, 8 );
        System.out.println( "We are conneting the graph ... " );
        System.out.println( "This graph is connected?\t" + city.isConnected() );
        System.out.println( city );
        System.out.println( "dfs iterative with node 1:\t" + city.dfsIterative(1) );
        System.out.println( "dfs iterative with node 1:\t" + city.bfsIterative(1) );
        System.out.println( "the path between 4 and 7 is:\t" + city.path( 4, 7 ) + " length:\t" + city.lengthPath(4,7) );
        System.out.println();
    }
}
