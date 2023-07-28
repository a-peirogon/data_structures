/** Node class used by linked structures.
 * This class and its fields are
 * visible only within the package dataStructures. */

package unal.datastructures;

class ChainNode<T>
{
   // package visible fields
   T element;
   ChainNode<T> next;

   // package visible constructors
   ChainNode( )
   {
      this( null, null );
   }

   ChainNode( T element )
   {
      this( element, null );
   }

   ChainNode( T element, ChainNode<T> next )
   {
      this.element = element;
      this.next = next;
   }
}
