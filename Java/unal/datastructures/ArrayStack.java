/** a stack class that uses a one-dimensional array */

package unal.datastructures;

import java.util.*;

public class ArrayStack<T> implements Stack<T>
{
   // fields
   int top;    // current top of stack
   T[] stack;  // element array

   // constructors
   /** create a stack with the given initial capacity
    * @throws IllegalArgumentException when initialCapacity < 1 */
   @SuppressWarnings( "unchecked" )
   public ArrayStack( int initialCapacity )
   {
      if( initialCapacity < 1 )
         throw new IllegalArgumentException
            ( "initialCapacity must be >= 1" );
      stack = ( T[] ) new Object[ initialCapacity ];
      top = -1;
   }

   /** create a stack with initial capacity 10 */
   public ArrayStack( )
   {
      this( 10 );
   }

   // methods
   /** @return true iff stack is empty */
   public boolean isEmpty( )
   {
      return top == -1;
   }

   /** @return top element of stack
    * @throws EmptyStackException when the stack is empty */
   public T peek( )
   {
      if( isEmpty( ) )
         throw new EmptyStackException( );
      return stack[ top ];
   }

   /** add theElement to the top of the stack */
   @SuppressWarnings( "unchecked" )
   public void push( T theElement )
   {
      // increase array size if necessary
      if( top == stack.length - 1 )
      {
         T[] old = stack;
         stack = ( T[] ) new Object[ 2 * stack.length ];
         System.arraycopy( old, 0, stack, 0, old.length );
      }

      // put theElement at the top of the stack
      stack[ ++top ] = theElement;
   }

   /** remove top element of stack and return it
    * @throws EmptyStackException when the stack is empty */
   public T pop( )
   {
      if( isEmpty( ) )
         throw new EmptyStackException( );
      T topElement = stack[ top ];
      stack[ top-- ] = null; // enable garbage collection
      return topElement;
   }

   /** test program */
   public static void main( String[] args )
   {
      int x;
      ArrayStack<Integer> s = new ArrayStack<>( 3 );

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
