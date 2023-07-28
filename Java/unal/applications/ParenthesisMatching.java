/** match parentheses */

package unal.applications;

import unal.datastructures.*;
import java.util.*;

public class ParenthesisMatching
{
   public static void printMatchedPairs( String expr )
   {
      ArrayStack<Integer> s = new ArrayStack<>( );
      for( int i = 0; i < expr.length( ); i++ )
         if( expr.charAt( i ) == '(' )
            s.push( i );
         else if( expr.charAt( i ) == ')' )
            try
            {  // remove location of matching '( ' from stack
               System.out.println( s.pop( ) + "  " + i );
            }
            catch ( Exception e )
            {  // stack was empty, no match exists
               System.out.println( "No match for right parenthesis at " + i );
            }

      // remaining '( ' in stack are unmatched
      while( !s.isEmpty( ) )
         System.out.println( "No match for left parenthesis at " + s.pop( ) );
   }

   /** test program */
   public static void main( String[] args )
   {
      Scanner s = new Scanner( System.in );

      // input the expression
      System.out.println( "Type an expression with no spaces" );
      String expression = s.nextLine(  );

      // output the pairs of matched parentheses
      System.out.println( "The pairs of matching parentheses in" );
      System.out.println( expression );
      System.out.println( "are ( indexing begins at 0 )" );
      printMatchedPairs( expression );
   }
}
