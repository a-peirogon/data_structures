/** find and ouput a shortest wire path in a grid */

package unal.applications;

import unal.datastructures.*;
import java.util.*;

public class WireRouter
{
   // fields
   static int[][] grid;
   static int size;               // number of rows and columns in the grid
   static Position start, finish; // both end points of wire
   static Position[] path;        // the shortest path

   // methods
   /** input the wire routing data */
   private static void inputData( )
   {
      // define the input stream to be the standard input stream
      Scanner s = new Scanner( System.in );

      System.out.println( "Enter grid size" );
      size = s.nextInt( );

      System.out.println( "Enter the start position" );
      start = new Position( s.nextInt( ), s.nextInt( ) );
      System.out.println( "Enter the finish position" );
      finish = new Position( s.nextInt( ), s.nextInt( ) );

      // create and input the wiring grid array
      grid = new int [ size + 2 ][ size + 2 ];
      System.out.println( "Enter the wiring grid in row-major order" );
      for( int i = 1; i <= size; i++ )
         for( int j = 1; j <= size; j++ )
            grid[ i ][ j ] = s.nextInt( );
   }

   /** find a shortest path from start to finish
    * @return true if successful, false if impossible */
   private static boolean findPath( )
   {
      if( ( start.row == finish.row ) && ( start.col == finish.col ) )
         return true;

      // initialize offsets
      Position[] offset = new Position[ 4 ];
      offset[ 0 ] = new Position( 0, 1 );   // right
      offset[ 1 ] = new Position( 1, 0 );   // down
      offset[ 2 ] = new Position( 0, -1 );  // left
      offset[ 3 ] = new Position( -1, 0 );  // up

      // initialize wall of blocks around the grid
      for( int i = 0; i <= size + 1; i++ )
      {
         grid[ 0 ][ i ] = grid[ size + 1 ][ i ] = 1; // bottom and top
         grid[ i ][ 0 ] = grid[ i ][ size + 1 ] = 1; // left and right
      }

      Position here = new Position( start.row, start.col );
      grid[ start.row ][ start.col ] = 2; // block
      int numOfNbrs = 4; // neighbors of a grid position

      // label reachable grid positions
      ArrayQueue<Position> q = new ArrayQueue<>( );
      Position nbr = new Position( );
      do
      {  // label neighbors of here
         for( int i = 0; i < numOfNbrs; i++ )
         {  // check out neighbors of here
            nbr.row = here.row + offset[ i ].row;
            nbr.col = here.col + offset[ i ].col;
            if( grid[ nbr.row ][ nbr.col ] == 0 )
            {  // unlabeled nbr, label it
               grid[ nbr.row ][ nbr.col ] = grid[ here.row ][ here.col ] + 1;
               if( ( nbr.row == finish.row ) && ( nbr.col == finish.col ) )
                  break; // done
               // put on queue for later expansion
               q.put( new Position( nbr.row, nbr.col ) );
            }
         }

         // have we reached finish?
         if( ( nbr.row == finish.row ) && ( nbr.col == finish.col ) )
            break; // done

         // finish not reached, can we move to a nbr?
         if( q.isEmpty( ) ) return false;          // no path
         here = q.remove( );  // get next position
      } while( true );

      // construct path
      int pathLength = grid[ finish.row ][ finish.col ] - 2;
      path = new Position[ pathLength ];

      // trace backwards from finish
      here = finish;
      for( int j = pathLength - 1; j >= 0; j-- )
      {
         path[ j ] = here;
         // find predecessor position
         for( int i = 0; i < numOfNbrs; i++ )
         {
            nbr.row = here.row + offset[ i ].row;
            nbr.col = here.col + offset[ i ].col;
            if( grid[ nbr.row ][ nbr.col ] == j + 2 ) break;
         }
         here = new Position( nbr.row, nbr.col ); // move to predecessor
      }

      return true;
   }

   /** output path to exit */
   private static void outputPath( )
   {
      System.out.println( "The wire path is" );
      for( Position x : path )
         System.out.println( x );
   }

   /** entry point for wire routing program */
   public static void main( String[] args )
   {
      inputData( );
      if( findPath( ) ) outputPath( );
      else System.out.println( "There is no wire path" );
   }
}

class Position
{
   // fields
   int row;  // row number of the position
   int col;  // column number of the position

   // constructors
   Position( )
   {
      this( 0, 0 );
   }

   Position( int row, int col )
   {
      this.row = row;
      this.col = col;
   }

   // convert to string suitable for output
   @Override
   public String toString( )
   {
      return new String( row + " " + col );
   }
}
