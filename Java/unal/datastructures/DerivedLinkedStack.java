/** a stack class derived from Chain */

package unal.datastructures;

import java.util.*;

public class DerivedLinkedStack<T> extends Chain<T> implements Stack<T>
{
   // constructor
   public DerivedLinkedStack( )
   {
      super( );
   }

   // methods
   /** @return true iff stack is empty */
   public boolean isEmpty( )
   {
      return super.isEmpty( );
   }

   /** @return top element of stack
    * @throws EmptyStackException when the stack is empty */
   public T peek( )
   {
      if ( isEmpty( ) ) throw new EmptyStackException( );
      return get( 0 );
   }

   /** add theElement to the top of the stack */
   public void push( T theElement )
   {
      add( 0, theElement );
   }

   /** remove top element of stack and return it
    * @throws EmptyStackException when the stack is empty */
   public T pop( )
   {
      if ( isEmpty( ) ) throw new EmptyStackException( );
      return remove( 0 );
   }

   /** test program */
   public static void main( String[] args )
   {
      int x;
      DerivedLinkedStack<Integer> s = new DerivedLinkedStack<>( );

      // add a few elements
      s.push( new Integer( 1 ) );
      s.push( new Integer( 2 ) );
      s.push( new Integer( 3 ) );
      s.push( new Integer( 4 ) );

      // delete all elements
      while ( !s.isEmpty( ) )
      {
         System.out.println( "Top element is " + s.peek( ) );
         System.out.println( "Removed the element " + s.pop( ) );
      }
   }
}
