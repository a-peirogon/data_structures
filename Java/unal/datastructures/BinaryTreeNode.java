/** class for nodes used in a binary tree */

package unal.datastructures;

public class BinaryTreeNode<T>
{
   // package visible fields
   T element;
   BinaryTreeNode<T> leftChild;  // left subtree
   BinaryTreeNode<T> rightChild; // right subtree

   // constructors
   public BinaryTreeNode( ) { }

   public BinaryTreeNode( T theElement )
   {
      element = theElement;
   }

   public BinaryTreeNode( T theElement,
                          BinaryTreeNode<T> theleftChild,
                          BinaryTreeNode<T> therightChild )
   {
      element = theElement;
      leftChild = theleftChild;
      rightChild = therightChild;
   }

   // accessor methods
   public BinaryTreeNode<T> getLeftChild( )
   {
      return leftChild;
   }

   public BinaryTreeNode<T> getRightChild( )
   {
      return rightChild;
   }

   public T getElement( )
   {
      return element;
   }

   // mutator methods
   public void setLeftChild( BinaryTreeNode<T> theLeftChild )
   {
      leftChild = theLeftChild;
   }

   public void setRightChild( BinaryTreeNode<T> theRightChild )
   {
      rightChild = theRightChild;
   }

   public void setElement( T theElement )
   {
      element = theElement;
   }

   @Override
   public String toString( )
   {
      return element.toString();
   }
}
