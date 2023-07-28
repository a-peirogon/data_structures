/** heap sort */

package unal.applications;

import unal.datastructures.*;

public class HeapSort
{
   /** sort the elements a[0 : a.length - 1] using
    * the heap sort method */
   public static <T extends Comparable<? super T>> void heapSort( T[] a )
   {
      // create a max heap of the elements
      MaxHeap<T> h = new MaxHeap<>( );
      h.initialize( a );

      // extract one by one from the max heap
      for( int i = a.length - 1; i >= 0; i-- )
         a[ i ] = h.removeMax( );
   }

   /** test program */
   public static void main( String[] args )
   {
      Integer[] a = { new Integer( 3 ),
                      new Integer( 2 ),
                      new Integer( 4 ),
                      new Integer( 1 ),
                      new Integer( 6 ),
                      new Integer( 9 ),
                      new Integer( 8 ),
                      new Integer( 7 ),
                      new Integer( 5 ),
                      new Integer( 0 )};

      // output elements to be sorted
      System.out.println( "The elements are" );
      for( int i = 0; i < a.length; i++ )
         System.out.print( a[ i ] + " " );
      System.out.println( );

      // sort the elements
      heapSort( a );

      // output in sorted order
      System.out.println( "The sorted order is" );
      for( int i = 0; i < a.length; i++ )
         System.out.print( a[ i ] + " " );
      System.out.println( );
   }
}
