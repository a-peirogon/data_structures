import java.util.ArrayList;

public class InsertionSort
{
    public static ArrayList<Integer> insertionSort( ArrayList<Integer> elements )
    {
        int j, aux;
        for( int i = 1; i < elements.size(); i++ )
		{
			j = i;
			aux = elements.get(j);
			while( j > 0 && elements.get(j-1) > aux )
			{
				aux = elements.get(j);
				elements.set( j, elements.get(j-1) );
				elements.set( j-1, aux );

				j--;
			}
		}
        return elements;
    }

	public static void main( String[] args )
	{
        int n = 11;
		ArrayList<Integer> elements = new ArrayList<>( n );
        while( n-- > 0 )
            elements.add( (int)(Math.random()*100) );
		System.out.println( "The elements are:\t\t" + elements );
        System.out.println( "The elements sorted are:\t" + insertionSort(elements) );
	}
}
