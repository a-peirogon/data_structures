/** linked implementation of LinearList */

package unal.datastructures;

import java.util.*;

public class Chain<T> implements LinearList<T>, Iterable<T>
{
   // fields
   protected ChainNode<T> firstNode;
   protected int size;

   // constructor
   /** create a list that is empty */
   public Chain( )
   {
      firstNode = null;
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
      ChainNode<T> currentNode = firstNode;
      for( int i = 0; i < index; i++ )
         currentNode = currentNode.next;

      return currentNode.element;
   }

   /** @return index of first occurrence of theElement,
    * return -1 if theElement not in list */
   public int indexOf( T theElement )
   {
      // search the chain for theElement
      ChainNode<T> currentNode = firstNode;
      int index = 0;  // index of currentNode
      while( currentNode != null &&
             !currentNode.element.equals( theElement ) )
      {
         // move to next node
         currentNode = currentNode.next;
         index++;
      }
      // make sure we found matching element
      if( currentNode == null )
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
      if( index == 0 ) // remove first node
      {
         removedElement = firstNode.element;
         firstNode = firstNode.next;
      }
      else
      {  // use q to get to predecessor of desired node
         ChainNode<T> q = firstNode;
         for( int i = 0; i < index - 1; i++ )
            q = q.next;

         removedElement = q.next.element;
         q.next = q.next.next; // remove desired node
      }
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

      if( index == 0 )
         // insert at front
         firstNode = new ChainNode<T>( theElement, firstNode );
      else
      {   // find predecessor of new element
         ChainNode<T> p = firstNode;
         for( int i = 0; i < index - 1; i++ )
            p = p.next;

         // insert after p
         p.next = new ChainNode<T>( theElement, p.next );
      }
      size++;
   }

   /** convert to a string */
   @Override
   public String toString( )
   {
      StringBuilder s = new StringBuilder( "[" );

      // put elements into the buffer
      for( T x : this )
         s.append( Objects.toString( x ) + ", " );

      if( size > 0 )
         s.setLength( s.length( ) - 2 ); // remove last ", "

      s.append( "]" );

      // create equivalent String
      return new String( s );
   }

   /** create and return an iterator */
   public Iterator<T> iterator( )
   {
      return new ChainIterator( );
   }

   /** chain iterator */
   private class ChainIterator implements Iterator<T>
   {
      // data member
      private ChainNode<T> nextNode;

      // constructor
      public ChainIterator( )
      {
         nextNode = firstNode;
      }

      // methods
      /** @return true iff list has a next element */
      public boolean hasNext( )
      {
         return nextNode != null;
      }

      /** @return next element in list
       * @throws NoSuchElementException
       * when there is no next element */
      public T next( )
      {
         if( nextNode != null )
         {
            T elementToReturn = nextNode.element;
            nextNode = nextNode.next;
            return elementToReturn;
         }
         else
            throw new NoSuchElementException( "No next element" );
      }

      /** unsupported method */
      public void remove( )
      {
         throw new UnsupportedOperationException
            ( "remove not supported" );
      }
   }

   /** test program */
   public static void main( String[] args )
   {
      // test default constructor
      Chain<Integer> x = new Chain<>( );

      // test size
      System.out.println( "Initial size is " + x.size( ) );

      // test isEmpty
      if( x.isEmpty( ) )
         System.out.println( "The list is empty" );
      else System.out.println( "The list is not empty" );

      // test put
      x.add( 0, new Integer( 2 ) );
      x.add( 1, new Integer( 6 ) );
      x.add( 0, new Integer( 1 ) );
      x.add( 2, new Integer( 4 ) );
      System.out.println( "List size is " + x.size( ) );

      // test toString
      System.out.println( "The list is " + x );

      // test indexOf
      int index = x.indexOf( new Integer( 4 ) );
      if( index < 0 )
         System.out.println( "4 not found" );
      else System.out.println( "The index of 4 is " + index );

      index = x.indexOf( new Integer( 3 ) );
      if( index < 0 )
         System.out.println( "3 not found" );
      else System.out.println( "The index of 3 is " + index );

      // test get
      System.out.println( "Element at 0 is " + x.get( 0 ) );
      System.out.println( "Element at 3 is " + x.get( 3 ) );

      // test remove
      System.out.println( x.remove( 1 ) + " removed" );
      System.out.println( "The list is " + x );
      System.out.println( x.remove( 2 ) + " removed" );
      System.out.println( "The list is " + x );

      if( x.isEmpty( ) )
         System.out.println( "The list is empty" );
      else System.out.println( "The list is not empty" );

      System.out.println( "List size is " + x.size( ) );

      // output using an iterator
      Iterator y = x.iterator( );
      System.out.print( "The list is " );
      while( y.hasNext( ) )
         System.out.print( y.next( ) + " " );
      System.out.println( );
   }
}
