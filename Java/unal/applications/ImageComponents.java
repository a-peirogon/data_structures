/** image component labeling */

package unal.applications;

import unal.datastructures.*;
import java.util.*;

public class ImageComponents
{
   // fields
   static int[][] pixel;
   static int size; // number of rows and columns in the image

   // methods
   /** input the image */
   private static void inputImage( )
   {
      // define the input stream to be the standard input stream
      Scanner s = new Scanner( System.in );

      System.out.println( "Enter image size" );
      size = s.nextInt( );

      // create and input the pixel array
      pixel = new int[ size + 2 ][ size + 2 ];
      System.out.println( "Enter the pixel array in row-major order" );
      for( int i = 1; i <= size; i++ )
         for( int j = 1; j <= size; j++ )
            pixel[ i ][ j ] = s.nextInt( );
   }

   /** label the components */
   private static void labelComponents( )
   {
      // initialize offsets
      Position[] offset = new Position[ 4 ];
      offset[ 0 ] = new Position( 0, 1 );   // right
      offset[ 1 ] = new Position( 1, 0 );   // down
      offset[ 2 ] = new Position( 0, -1 );  // left
      offset[ 3 ] = new Position( -1, 0 );  // up

      // initialize wall of 0 pixels
      for( int i = 0; i <= size + 1; i++ )
      {
         pixel[ 0 ][ i ] = pixel[ size + 1 ][ i ] = 0; // bottom and top
         pixel[ i ][ 0 ] = pixel[ i ][ size + 1 ] = 0; // left and right
      }

      int numOfNbrs = 4; // neighbors of a pixel position
      ArrayQueue<Position> q = new ArrayQueue<>( );
      Position nbr = new Position( );
      int id = 1;  // component id

      // scan all pixels labeling components
      for( int r = 1; r <= size; r++ )      // row r of image
         for( int c = 1; c <= size; c++ )   // column c of image
            if( pixel[ r ][ c ] == 1 )
            {  // new component
               pixel[ r ][ c ] = ++id; // get next id
               Position here = new Position( r, c );
               do
               {  // find rest of component
                  for( int i = 0; i < numOfNbrs; i++ )
                  {  // check all neighbors of here
                     nbr.row = here.row + offset[ i ].row;
                     nbr.col = here.col + offset[ i ].col;
                     if( pixel[ nbr.row ][ nbr.col ] == 1 )
                     {  // pixel is part of current component
                        pixel[ nbr.row ][ nbr.col ] = id;
                        q.put( new Position( nbr.row, nbr.col ) );
                     }
                  }
                  // any unexplored pixels in component?
                  here = q.remove( ); // a component pixel
               } while( here != null );
            } // end of if, for c, and for r
   }

   /** output labeled image */
   private static void outputImage( )
   {
      System.out.println( "The labeled image is" );
      for( int i = 1; i <= size; i++ )
      {
         for( int j = 1; j <= size; j++ )
            System.out.print( pixel[ i ][ j ] + "  " );
         System.out.println( );
      }
   }

   /** entry point for component labeling program */
   public static void main( String[] args )
   {
      inputImage( );
      labelComponents( );
      outputImage( );
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
