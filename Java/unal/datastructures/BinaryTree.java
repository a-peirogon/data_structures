/** binary tree interface */

package unal.datastructures;

import java.lang.reflect.*;

interface BinaryTree<T>
{
   boolean isEmpty( );
   T root( );
   void makeTree( T root, BinaryTree<T> left, BinaryTree<T> right );
   BinaryTree<T> removeLeftSubtree( );
   BinaryTree<T> removeRightSubtree( );
   void preOrder( Method visit );
   void inOrder( Method visit );
   void postOrder( Method visit );
   void levelOrder( Method visit );
}
