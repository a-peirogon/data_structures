/** linked binary trees */

package unal.datastructures;

import java.lang.reflect.*;

public class LinkedBinaryTree<T> implements BinaryTree<T>
{
   // instance fields
   BinaryTreeNode<T> root;   // root node

   // class fields
   static Method visit;      // visit method to use during a traversal
   static Method theAdd1;    // method to increment count by 1
   static Method theOutput;  // method to output node element
   static int count;         // counter

   // method to initialize class fields
   static
   {
      try
      {
         Class<LinkedBinaryTree> lbt = LinkedBinaryTree.class;
         theAdd1 = lbt.getMethod( "add1", BinaryTreeNode.class );
         theOutput = lbt.getMethod( "output", BinaryTreeNode.class );
      }
      catch( Exception e )
      {
         // exception not possible
      }
   }

   // constructor
   public LinkedBinaryTree( )
   {
      root = null;
   }

   // class methods
   /** visit method that outputs element */
   public static <T> void output( BinaryTreeNode<T> t )
   {
      System.out.print( t.element + " " );
   }

   /** visit method to count nodes */
   public static <T> void add1( BinaryTreeNode<T> t )
   {
      count++;
   }

   // instance methods
   /** @return true iff tree is empty */
   public boolean isEmpty( )
   {
      return root == null;
   }

   /** @return root element if tree is not empty
    * @return null if tree is empty */
   public T root( )
   {
      return ( root == null ) ? null : root.element;
   }

   /** set this to the tree with the given root and subtrees
    * CAUTION: does not clone left and right */
   public void makeTree( T root, BinaryTree<T> left, BinaryTree<T> right )
   {
      this.root = new BinaryTreeNode<T>( root,
         ((LinkedBinaryTree<T>) left).root,
         ((LinkedBinaryTree<T>) right).root );
   }

   /** remove the left subtree
    * @throws IllegalArgumentException when tree is empty
    * @return removed subtree */
   public BinaryTree<T> removeLeftSubtree( )
   {
      if( root == null )
         throw new IllegalArgumentException( "tree is empty" );

      // detach left subtree and save in leftSubtree
      LinkedBinaryTree<T> leftSubtree = new LinkedBinaryTree<T>( );
      leftSubtree.root = root.leftChild;
      root.leftChild = null;

      return ( BinaryTree<T> ) leftSubtree;
   }

   /** remove the right subtree
    * @throws IllegalArgumentException when tree is empty
    * @return removed subtree */
   public BinaryTree<T> removeRightSubtree( )
   {
      if( root == null )
         throw new IllegalArgumentException( "tree is empty" );

      // detach right subtree and save in rightSubtree
      LinkedBinaryTree<T> rightSubtree = new LinkedBinaryTree<T>( );
      rightSubtree.root = root.rightChild;
      root.rightChild = null;

      return ( BinaryTree<T> ) rightSubtree;
   }

   /** preorder traversal */
   public void preOrder( Method visit )
   {
      LinkedBinaryTree.visit = visit;
      thePreOrder( root );
   }

   /** actual preorder traversal method */
   static <T> void thePreOrder( BinaryTreeNode<T> t )
   {
      if( t != null )
      {
         try
         {
            visit.invoke( null, t );  // visit tree root
         }
         catch ( Exception e )
         {
            System.out.println( e );
         }
         thePreOrder( t.leftChild );  // do left subtree
         thePreOrder( t.rightChild ); // do right subtree
      }
   }

   /** inorder traversal */
   public void inOrder( Method visit )
   {
      LinkedBinaryTree.visit = visit;
      theInOrder( root );
   }

   /** actual inorder traversal method */
   static <T> void theInOrder( BinaryTreeNode<T> t )
   {
      if( t != null )
      {
         theInOrder( t.leftChild ); // do left subtree
         try
         {
            visit.invoke( null, t ); // visit tree root
         }
         catch( Exception e )
         {
            System.out.println( e );
         }
         theInOrder( t.rightChild ); // do right subtree
      }
   }

   /** postorder traversal */
   public void postOrder( Method visit )
   {
      LinkedBinaryTree.visit = visit;
      thePostOrder( root );
   }

   /** actual postorder traversal method */
   static <T> void thePostOrder( BinaryTreeNode<T> t )
   {
      if( t != null )
      {
         thePostOrder( t.leftChild ); // do left subtree
         thePostOrder( t.rightChild ); // do right subtree
         try
         {
            visit.invoke( null, t ); // visit tree root
         }
         catch ( Exception e )
         {
            System.out.println( e );
         }
      }
   }

   /** level order traversal */
   public void levelOrder( Method visit )
   {
      ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>( );
      BinaryTreeNode<T> t = root;
      while( t != null )
      {
         try
         {
            visit.invoke( null, t ); // visit tree root
         }
         catch ( Exception e )
         {
            System.out.println( e );
         }

         // put t's children on queue
         if( t.leftChild != null )
            q.put( t.leftChild );
         if( t.rightChild != null )
            q.put( t.rightChild );

         // get next node to visit
         t = ( BinaryTreeNode<T> ) q.remove( );
      }
   }

   /** output elements in preorder */
   public void preOrderOutput( )
   {
      preOrder( theOutput );
   }

   /** output elements in inorder */
   public void inOrderOutput( )
   {
      inOrder( theOutput );
   }

   /** output elements in postorder */
   public void postOrderOutput( )
   {
      postOrder( theOutput );
   }

   /** output elements in level order */
   public void levelOrderOutput( )
   {
      levelOrder( theOutput );
   }

   /** count number of nodes in tree */
   public int size( )
   {
      count = 0;
      preOrder( theAdd1 );
      return count;
   }

   /** @return tree height */
   public int height( )
   {
      return theHeight( root );
   }

   /** @return height of subtree rooted at t */
   static <T> int theHeight( BinaryTreeNode<T> t )
   {
      if( t == null ) return 0;
      int hl = theHeight( t.leftChild );  // height of left subtree
      int hr = theHeight( t.rightChild ); // height of right subtree
      if( hl > hr ) return ++hl;
      else return ++hr;
   }

   /** test program */
   public static void main( String[] args )
   {
      LinkedBinaryTree<Integer> a = new LinkedBinaryTree<>( ),
         x = new LinkedBinaryTree<>( ),
         y = new LinkedBinaryTree<>( ),
         z = new LinkedBinaryTree<>( );
      y.makeTree( new Integer( 1 ), a, a );
      z.makeTree( new Integer( 2 ), a, a );
      x.makeTree( new Integer( 3 ), y, z );
      y.makeTree( new Integer( 4 ), x, a );

      System.out.println( "Preorder sequence is " );
      y.preOrderOutput( );
      System.out.println( );

      System.out.println( "Inorder sequence is " );
      y.inOrderOutput( );
      System.out.println( );

      System.out.println( "Postorder sequence is " );
      y.postOrderOutput( );
      System.out.println( );

      System.out.println( "Level order sequence is " );
      y.levelOrderOutput( );
      System.out.println( );

      System.out.println( "Number of nodes = " + y.size( ) );

      System.out.println( "Height = " + y.height( ) );
   }
}
