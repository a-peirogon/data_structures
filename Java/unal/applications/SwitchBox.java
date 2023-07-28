/** switch box routing */

package unal.applications;

import unal.datastructures.*;
import java.util.*;

public class SwitchBox
{
   /** determine whether the switch box is routable
    * @param net array of pin to net assignments */
   public static boolean checkBox( int[] net )
   {
      ArrayStack<Integer> s = new ArrayStack<>( );
      for( int i = 0; i < net.length; i++ )
         if( !s.isEmpty( ) )
            // check with top net
            if( net[ i ] == net[ s.peek( ) ] )
               // net[ i ] is routable, delete from stack
               s.pop( );
            else s.push( i );
         else s.push( i );

      // any unrouted nets left?
      if( s.isEmpty( ) )
      {   // no nets remain
         System.out.println( "Switch box is routable" );
         return true;
      }

      System.out.println( "Switch box is not routable" );

      return false;
   }

   /** test program */
   public static void main( String[] args )
   {
      // define the input stream to be the standard input stream
      Scanner s = new Scanner( System.in );

      // input the number of pins and their net assignment
      System.out.println( "Type number of pins in switch box" );
      int n = s.nextInt( );

      // create net assignment array
      int[] net = new int[ n ];

      // input the net assignments
      System.out.println( "Type net numbers for pins 1 through " + n );
      for( int i = 0; i < n; i++ )
         net[ i ] = s.nextInt( );

      // see if the switch box is routable
      checkBox( net );
   }
}
