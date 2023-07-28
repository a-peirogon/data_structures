/** singly linked circular list with header node */

package unal.datastructures;

import java.util.*;

public class CircularWithHeader<T> implements LinearList<T>
{
   // fields
   protected ChainNode<T> headerNode;
   protected int size;

   // constructor
   /** create a circular list that is empty */
   public CircularWithHeader( )
   {
      headerNode = new ChainNode<T>( );
      headerNode.next = headerNode;
      size = 0;
   }

   // methods
   /** @return true iff list is empty */
   public boolean isEmpty( )
   {
      return size == 0;
   }

   /** @return current number of elements in list */
   public int size( )
   {
      return size;
   }

   /** @throws IndexOutOfBoundsException when
    * index is not between 0 and size - 1 */
   void checkIndex( int index )
   {
      if( index < 0 || index >= size )
         throw new IndexOutOfBoundsException
            ( "index = " + index + "  size = " + size );
   }

   /** @return element with specified index
    * @throws IndexOutOfBoundsException when
    * index is not between 0 and size - 1 */
   public T get( int index )
   {
      checkIndex( index );

      // move to desired node
      ChainNode<T> currentNode = headerNode.next;
      for( int i = 0; i < index; i++ )
         currentNode = currentNode.next;

      return currentNode.element;
   }

   /** @return index of first occurrence of theElement,
    * return -1 if theElement not in list */
   public int indexOf( T theElement )
   {
      // put theElement in header node
      headerNode.element = theElement;

      // search the list for theElement
      ChainNode<T> currentNode = headerNode.next;
      int index = 0;  // index of currentNode
      while( !currentNode.element.equals( theElement ) )
      {
         // move to next node
         currentNode = currentNode.next;
         index++;
      }

      // make sure we found matching element
      if( currentNode == headerNode )
         return -1;
      else
         return index;
   }

   /** Remove the element with specified index.
    * All elements with higher index have their
    * index reduced by 1.
    * @throws IndexOutOfBoundsException when
    * index is not between 0 and size - 1
    * @return removed element */
   public T remove( int index )
   {
      checkIndex( index );

      T removedElement;

      // use q to get to predecessor of desired node
      ChainNode<T> q = headerNode;
      for( int i = 0; i < index; i++ )
         q = q.next;

      removedElement = q.next.element;
      q.next = q.next.next; // remove desired node

      size--;
      return removedElement;
   }

   /** Insert an element with specified index.
    * All elements with equal or higher index
    * have their index increased by 1.
    * @throws IndexOutOfBoundsException when
    * index is not between 0 and size */
   public void add( int index, T theElement )
   {
      if( index < 0 || index > size )
         // invalid list position
         throw new IndexOutOfBoundsException
            ( "index = " + index + "  size = " + size );

      // find predecessor of new element
      ChainNode<T> p = headerNode; // Fixed YP
      for( int i = 0; i < index; i++ )
         p = p.next;

      // insert after p
      p.next = new ChainNode<T>( theElement, p.next );

      size++;
   }

   /** convert to a string */
   @Override
   public String toString( )
   {
      StringBuilder s = new StringBuilder( "[" );

      // put elements into the buffer
      ChainNode<T> currentNode = headerNode.next;
      while( currentNode != headerNode )
      {
         s.append( Objects.toString( currentNode.element ) + ", " );
         currentNode = currentNode.next;
      }
      if( size > 0 )
         s.setLength( s.length( ) - 2 );  // remove last ", "
      s.append( "]" );

      // create equivalent String
      return new String( s );
   }

   /** test program */
   public static void main( String[] args )
   {
      CircularWithHeader<Integer> x = new CircularWithHeader<>( );

      for( int i = 0; i < 10; i++ )
         x.add( i, new Integer( i ) );
      System.out.println( "List=" + x );

      for( int i = 0; i < 5; i++ )
         x.remove( 2 );
      System.out.println( "List=" + x );

      for(int i = 0; i < 10; i++)
         System.out.println( i + " is element " +
                             x.indexOf( new Integer( i ) ) );
   }
}
