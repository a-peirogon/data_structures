/** hash tables using linear open addressing and division */

package unal.datastructures;

import java.util.*;

public class HashTable<K extends Comparable<? super K>, E>
   implements Dictionary<K, E>
{
   // fields
   protected int divisor;             // hash function divisor
   protected DataDict<K, E>[] table; // hash table array
   protected boolean[] neverUsed;     // parallel array
   protected int size;                // number of elements in table

   // constructor
   @SuppressWarnings( "unchecked" )
   public HashTable( int theDivisor )
   {
      divisor = theDivisor;
      table = new DataDict[ divisor ];
      neverUsed = new boolean[ divisor ];
      Arrays.fill( neverUsed, true );
      size = 0;
   }

   // methods
   /** @return true iff the table is empty */
   public boolean isEmpty( )
   {
      return size == 0;
   }

   /** @return current number of elements in the table */
   public int size( )
   {
      return size;
   }

   /** search an open addressed hash table for an element with
    * key theKey
    * @return location of matching element if found, otherwise
    * return location where an element with key theKey may be
    * inserted provided the hash table is not full */
   private int search( K theKey )
   {
      int i = Math.abs( theKey.hashCode( ) ) % divisor;
      int j = i; // start at home bucket
      do
      {
         if( neverUsed[ j ] || ( table[ j ] != null && table[ j ].key.equals( theKey ) ) )
            return j;
         j = ( j + 1 ) % divisor; // next bucket
      } while( j != i ); // returned to home bucket?

      return j; // table full
   }

   /** @return element with specified key
    * @return null if no matching element */
   public E get( K theKey )
   {
      // search the table
      int b = search( theKey );

      // see if a match was found at table[ b ]
      if( neverUsed[ b ] || !table[ b ].key.equals( theKey ) )
         return null; // no match

      return table[ b ].element; // matching element
   }

   /** insert an element with the specified key
    * overwrite old element if there is already an
    * element with the given key
    * @throws IllegalArgumentException when the table is full
    * @return old element ( if any ) with key theKey */
   public E put( K theKey, E theElement )
   {
      // search the table for a matching element
      int b = search( theKey );

      // check if matching element found
      if( neverUsed[ b ] )
      {
         // no matching element and table not full
         table[ b ] = new DataDict<K, E>( theKey, theElement );
         neverUsed[ b ] = false;
         size++;
         return null;
      }
      else
      {  // check if duplicate or table full
         if( table[ b ].key.equals( theKey ) )
         {  // duplicate, change table[ b ].element
            E elementToReturn = table[ b ].element;
            table[ b ].element = theElement;
            return elementToReturn;
         }
         else throw new IllegalArgumentException( "table is full" );
      }
   }

   /** remove from the hash table
    * @return removed element */
   public E remove( K theKey )
   {
      // search the table for a matching element
      int b = search( theKey );

      if( neverUsed[ b ] )
         return null; // no matching element and table not full
      if( table[ b ].key.equals( theKey ) )
      {
         E elementToReturn = table[ b ].element;
         table[ b ] = null;
         size--;
         return elementToReturn;
      }
      else
         return null;
   }

   /** convert to a string */
   @Override
   public String toString( )
   {
      StringBuilder s = new StringBuilder( "\n[" );

      // put elements into the buffer
      for( int i = 0; i < divisor; i++)
         s.append( "{" + Objects.toString( table[ i ] ) +
                   "," + ( neverUsed[ i ] ? "T" : "F" ) + "}, " );

      if( size > 0 )
         s.setLength( s.length( ) - 2 ); // remove last ", "

      s.append( "]\n" );

      // create equivalent String
      return new String( s );
   }

   /** test method */
   public static void main( String[] args )
   {
      HashTable<Integer, Integer> h = new HashTable<>( 11 );

      h.put( 80, 180 ); h.put( 40, 140 ); h.put( 65, 165 );
      h.put( 58, 158 ); h.put( 24, 124 ); h.put( 2, 102 );
      h.put( 13, 113 ); h.put( 46, 146 ); h.put( 16, 116);
      h.put( 7, 107 ); h.put( 21, 121);
      System.out.println( h );

      try
      {
         h.put( 99, 99 );
      }
      catch( Exception e )
      {
         System.out.println( "No memory for 99" );
      }

      // update element
      h.put( 7, 29 );
      System.out.println( h );
      System.out.println( "Element " + h.get( 2 ) + " found" );
      System.out.println( "Element " + h.remove( 58 ) + " removed" );
      System.out.println( h );
      System.out.println( "Element " + h.get( 2 ) + " found" );
   }
}
