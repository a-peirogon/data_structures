/** a stack class derived from ArrayLinearList */

package unal.datastructures;

import java.util.*;

public class DerivedArrayStack<T> extends ArrayLinearList<T>
   implements Stack<T>
{
   // constructors
   /** create a stack with the given initial capacity */
   public DerivedArrayStack( int initialCapacity )
   {
      super( initialCapacity );
   }

   /** create a stack with initial capacity 10 */
   public DerivedArrayStack( )
   {
      this( 10 );
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
      if( isEmpty( ) )
         throw new EmptyStackException( );
      return get( size( ) - 1 );
   }

   /** add theElement to the top of the stack */
   public void push( T theElement )
   {
      add( size( ), theElement );
   }

   /** remove top element of stack and return it
    * @throws EmptyStackException when the stack is empty */
   public T pop( )
   {
      if( isEmpty( ) )
         throw new EmptyStackException( );
      return remove( size( ) - 1 );
   }

   /** test program */
   public static void main( String[] args )
   {
      int x;
      DerivedArrayStack<Integer> s = new DerivedArrayStack<>( 3 );

      // add a few elements
      s.push( new Integer( 1 ) );
      s.push( new Integer( 2 ) );
      s.push( new Integer( 3 ) );
      s.push( new Integer( 4 ) );

      // delete all elements
      while( !s.isEmpty( ) )
      {
         System.out.println( "Top element is " + s.peek( ) );
         System.out.println( "Removed the element " + s.pop( ) );
      }
   }
}
