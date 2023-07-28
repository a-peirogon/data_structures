/** a linked queue class */

package unal.datastructures;

public class LinkedQueue<T> implements Queue<T>
{
   // fields
   protected ChainNode<T> front;
   protected ChainNode<T> rear;

   // constructor
   /** create an empty queue */
   public LinkedQueue( )
   {
      front = rear = null;
   }

   // methods
   /** @return true iff queue is empty */
   public boolean isEmpty( )
   {
      return front == null;
   }

   /** @return the element at the front of the queue
    * @return null if the queue is empty */
   public T getFrontElement( )
   {
      return isEmpty( ) ? null : front.element;
   }

   /** @return the element at the rear of the queue
    * @return null if the queue is empty */
   public T getRearElement( )
   {
      return isEmpty( ) ? null : rear.element;
   }

   /** insert theElement at the rear of the queue */
   public void put( T theElement )
   {
      ChainNode<T> p = new ChainNode<T>( theElement, null );

      if( front == null ) front = p; // empty queue
      else rear.next = p; // nonempty queue

      rear = p;
   }

   /** remove an element from the front of the queue
    * @return removed element
    * @return null if the queue is empty */
   public T remove( )
   {
      if( isEmpty( ) ) return null;
      T frontElement = front.element;
      front = front.next;
      if( isEmpty( ) ) rear = null; // enable garbage collection

      return frontElement;
   }

   /** test program */
   public static void main( String[] args )
   {
      int x;
      LinkedQueue<Integer> q = new LinkedQueue<>( );

      // add a few elements
      q.put( new Integer( 1 ) );
      q.put( new Integer( 2 ) );
      q.put( new Integer( 3 ) );
      q.put( new Integer( 4 ) );

      // delete all elements
      while ( !q.isEmpty( ) )
      {
         System.out.println( "Rear element is " + q.getRearElement( ) );
         System.out.println( "Front element is " + q.getFrontElement( ) );
         System.out.println( "Removed the element " + q.remove( ) );
      }
   }
}
