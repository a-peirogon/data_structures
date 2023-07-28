/** a linked stack class */

package unal.datastructures;

import java.util.*;

public class LinkedStack<T> implements Stack<T>
{
   // fields
   protected ChainNode<T> topNode;

   // constructor
   public LinkedStack( )
   {
      topNode = null;
   }

   // methods
   /** @return true iff stack is empty */
   public boolean isEmpty( )
   {
      return topNode == null;
   }

   /** @return top element of stack
    * @throws EmptyStackException when the stack is empty */
   public T peek( )
   {
      if( isEmpty( ) ) throw new EmptyStackException( );
      return topNode.element;
   }

   /** add theElement to the top of the stack */
   public void push( T theElement )
   {
      topNode = new ChainNode<T>( theElement, topNode );
   }

   /** remove top element of stack and return it
    * @throws EmptyStackException when the stack is empty */
   public T pop( )
   {
      if( isEmpty( ) ) throw new EmptyStackException( );
      T topElement = topNode.element;
      topNode = topNode.next;
      return topElement;
   }

   /** test program */
   public static void main( String[] args )
   {
      LinkedStack<Integer> s = new LinkedStack<>( );

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
