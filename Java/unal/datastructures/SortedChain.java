package unal.datastructures;

import java.util.*;

public class SortedChain<K extends Comparable<? super K>, E>
   implements Dictionary<K, E>, Iterable<E>
{
   // top-level nested class
   protected static class SortedChainNode<K extends Comparable<? super K>, E> extends DataDict<K, E>
   {
      // fields
      protected SortedChainNode<K, E> next;

      // constructors
      protected SortedChainNode( )
      {
         super( );
         next = null;
      }

      protected SortedChainNode( K theKey, E theElement )
      {
         super( theKey, theElement );
         next = null;
      }

      protected SortedChainNode( K theKey, E theElement,
                                 SortedChainNode<K, E> theNext )
      {
         super( theKey, theElement );
         next = theNext;
      }
   }

   // fields of SortedChain
   protected SortedChainNode<K, E> firstNode;
   protected int size;

   // constructor
   /** create an empty sorted chain */
   public SortedChain( )
   {
      firstNode = null;
      size = 0;
   }

   // methods
   /** @return true iff the chain is empty */
   public boolean isEmpty( )
   {
      return size == 0;
   }

   /** @return current number of elements in list */
   public int size( )
   {
      return size;
   }

   /** @return element with specified key
    * @return null if there is no matching element */
   public E get( K theKey )
   {
      SortedChainNode<K, E> currentNode = firstNode;

      // search for match with theKey
      while( currentNode != null &&
             currentNode.key.compareTo( theKey ) < 0 )
         currentNode = currentNode.next;

      // verify match
      if( currentNode != null && currentNode.key.equals( theKey ) )
         // yes, found match
         return currentNode.element;

      // no match
      return null;
   }

   /** insert an element with the specified key
    * overwrite old element if there is already an
    * element with the given key
    * @return old element ( if any ) with key theKey */
   public E put( K theKey, E theElement )
   {
      SortedChainNode<K, E> p = firstNode,
         tp = null; // tp trails p

      // move tp so that theElement can be inserted after tp
      while( p != null && p.key.compareTo( theKey ) < 0 )
      {
         tp = p;
         p = p.next;
      }

      // check if there is a matching element
      if( p != null && p.key.equals( theKey ) )
      {  // replace old element
         E elementToReturn = p.element;
         p.element = theElement;
         return elementToReturn;
      }

      // no match, set up node for theElement
      SortedChainNode<K, E> q =
         new SortedChainNode<K, E>( theKey, theElement, p );

      // insert node just after tp
      if( tp == null ) firstNode = q;
      else tp.next = q;

      size++;

      return null;
   }

   /** @return matching element and remove it
    * @return null if no matching element */
   public E remove( K theKey )
   {
      SortedChainNode<K, E> p = firstNode,
         tp = null; // tp trails p

      // search for match with theKey
      while( p != null && p.key.compareTo( theKey ) < 0 )
      {
         tp = p;
         p = p.next;
      }

      // verify match
      if( p != null && p.key.equals( theKey ) )
      {  // found a match
         E e = p.element;  // the matching element

         // remove p from the chain
         if( tp == null ) firstNode = p.next;  // p is first node
         else tp.next = p.next;

         size--;

         return e;
      }

      // no matching element to remove
      return null;
   }

   /** convert to a string */
   @Override
   public String toString( )
   {
      StringBuilder s = new StringBuilder( "[" );
      if( firstNode != null )
      {  // nonempty chain
         // do first element
         s.append( Objects.toString( firstNode.element ) );
         // do remaining elements
         SortedChainNode<K, E> currentNode = firstNode.next;
         while( currentNode != null )
         {
            s.append( ", " + Objects.toString( currentNode.element ) );
            currentNode = currentNode.next;
         }
      }
      s.append( "]" );

      // create equivalent String
      return new String( s );
   }

   /** create and return an iterator */
   public Iterator<E> iterator( )
   {
      return new SortedChainIterator( );
   }

   /** sorted chain iterator */
   private class SortedChainIterator implements Iterator<E>
   {
      // fields
      private SortedChainNode<K, E> nextNode;

      // constructor
      public SortedChainIterator( )
      {
         nextNode = firstNode;
      }

      // methods
      /** @return true iff list has more elements */
      public boolean hasNext( )
      {
         return nextNode != null;
      }

      /** @return next element in list
       * @throws NoSuchElementException
       * if there is no next element */
      public E next( )
      {
         if( nextNode != null )
         {
            E obj = nextNode.element;
            nextNode = nextNode.next;
            return obj;
         }
         else throw new NoSuchElementException
                 ( "No next element" );
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
      SortedChain<Integer, Integer> x = new SortedChain<>( );

      // test put
      x.put( new Integer( 2 ), new Integer( 12 ) );
      System.out.println( "The list is " + x );
      x.put( new Integer( 6 ), new Integer( 16 ) );
      System.out.println( "The list is " + x );
      x.put( new Integer( 1 ), new Integer( 11 ) );
      System.out.println( "The list is " + x );
      x.put( new Integer( 4 ), new Integer( 14 ) );
      System.out.println( "The list is " + x );
      x.put( new Integer( 6 ), new Integer( 26 ) );
      System.out.println( "The list is " + x );

      // test iterator
      for( Integer r : x ) System.out.println( r);

      // test get
      System.out.println( "element " +
                          x.get( new Integer( 2 ) ) + " has key 2" );
      System.out.println( "element " +
                          x.get( new Integer( 1 ) ) + " has key 1" );
      System.out.println( "element " +
                          x.get( new Integer( 6 ) ) + " has key 6" );

      // test remove
      System.out.println( "removed element " +
                          x.remove( new Integer( 2 ) ) + " with key 2" );
      System.out.println( "The list is " + x );
      System.out.println( "removed element " +
                          x.remove( new Integer( 1 ) ) + " with key 1" );
      System.out.println( "The list is " + x );
      System.out.println( "removed element " +
                          x.remove( new Integer( 6 ) ) + " with key 6" );
      System.out.println( "The list is " + x );
   }
}
