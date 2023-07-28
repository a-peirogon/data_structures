/** binary search tree */

package unal.datastructures;

import java.util.*;

public class BST<K extends Comparable<? super K>, E>
   extends LinkedBinaryTree<DataDict<K, E>> implements Dictionary<K, E>
{
   // constructor
   public BST( )
   {
      super( );
   }

   // methods
   /** @return element with specified key
    * @return null if no matching element */
   public E get( K theKey )
   {
      // pointer p starts at the root and moves through
      // the tree looking for an element with key theKey
      BinaryTreeNode<DataDict<K, E>> p = root;
      while( p != null )
         if( theKey.compareTo( p.element.key ) < 0 )
            p = p.leftChild;
         else
            if( theKey.compareTo( p.element.key ) > 0 )
               p = p.rightChild;
            else // found matching element
               return p.element.element;
      // no matching element
      return null;
   }

   /** insert an element with the specified key
    * overwrite old element if there is already an
    * element with the given key
    * @return old element (if any) with key theKey */
   public E put( K theKey, E theElement )
   {
      BinaryTreeNode<DataDict<K, E>> p = root, // search pointer
         pp = null; // parent of p
      // find place to insert theElement
      while( p != null )
      {  // examine p.element.key
         pp = p;
         // move p to a child
         if( theKey.compareTo( p.element.key ) < 0 )
            p = p.leftChild;
         else if( theKey.compareTo( p.element.key ) > 0 )
            p = p.rightChild;
         else
         {  // overwrite element with same key
            E elementToReturn = p.element.element;
            p.element.element = theElement;
            return elementToReturn;
         }
      }

      // get a node for theElement and attach to pp
      BinaryTreeNode<DataDict<K, E>> r = new BinaryTreeNode<>
         ( new DataDict<K, E>( theKey, theElement ) );
      if( root != null )
         // the tree is not empty
         if( theKey.compareTo( pp.element.key ) < 0 )
            pp.leftChild = r;
         else
            pp.rightChild = r;
      else // insertion into empty tree
         root = r;
      return null;
   }

   /** @return matching element and remove it
    * @return null if no matching element */
   public E remove( K theKey )
   {
      // set p to point to node with key searchKey
      BinaryTreeNode<DataDict<K, E>> p = root, // search pointer
         pp = null; // parent of p
      while( p != null && !p.element.key.equals( theKey ) )
      {  // move to a child of p
         pp = p;
         if( theKey.compareTo( p.element.key ) < 0 )
            p = p.leftChild;
         else
            p = p.rightChild;
      }

      if( p == null ) // no element with key searchKey
         return null;

      // save element to be removed
      E theElement = p.element.element;

      // restructure tree
      // handle case when p has two children
      if( p.leftChild != null && p.rightChild != null )
      {  // two children
         // convert to zero or one child case
         // find element with largest key in left subtree of p
         BinaryTreeNode<DataDict<K, E>> s = p.leftChild,
            ps = p; // parent of s
         while( s.rightChild != null )
         {  // move to larger element
            ps = s;
            s = s.rightChild;
         }

         // move largest element from s to p
         p.element = s.element;
         p = s;
         pp = ps;
      }

      // p has at most one child, save this child in c
      BinaryTreeNode<DataDict<K, E>> c;
      if( p.leftChild == null )
         c = p.rightChild;
      else
         c = p.leftChild;

      // remove node p
      if( p == root ) root = c;
      else
      {  // is p left or right child of pp?
         if( p == pp.leftChild )
            pp.leftChild = c;
         else
            pp.rightChild = c;
      }

      return theElement;
   }

   /** output elements in ascending order of key */
   public void ascend()
   {
      inOrderOutput();
   }

   /** test program */
   public static void main( String[] args )
   {
      BST<Integer, Character> y = new BST<>( );

      // insert a few elements
      y.put( new Integer( 1 ), new Character( 'a' ) );
      y.put( new Integer( 6 ), new Character( 'c' ) );
      y.put( new Integer( 4 ), new Character( 'b' ) );
      y.put( new Integer( 8 ), new Character( 'd' ) );

      System.out.println( "Elements in ascending order are" );
      y.ascend( );
      System.out.println( );

      // remove an element
      System.out.println( "Removed element " +
                          y.remove( new Integer( 4 ) ) + " with key 4" );
      System.out.println( "Elements in ascending order are");
      y.ascend( );
      System.out.println( );

      // remove another element
      System.out.println( "Removed element " +
                          y.remove( new Integer( 8 ) ) + " with key 8" );
      System.out.println( "Elements in ascending order are" );
      y.ascend( );
      System.out.println( );

      // remove yet another element
      System.out.println( "Removed element " +
                          y.remove( new Integer( 6 ) ) + " with key 6" );
      System.out.println( "Elements in ascending order are" );
      y.ascend( );
      System.out.println( );

      // try to remove a nonexistent element
      System.out.println( "Removed element " +
                          y.remove( new Integer( 6 ) ) + " with key 6" );
      System.out.println( "Elements in ascending order are" );
      y.ascend( );
      System.out.println( );
   }
}
