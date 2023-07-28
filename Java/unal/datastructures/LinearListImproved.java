/** extending interface linear list */

package unal.datastructures;

import java.util.*;
import java.io.*;

public interface LinearListImproved<T> extends LinearList<T>
{
   void save( String fn );
   void load( String fn );
   void sort( );
   void sort( Comparator<T> c );
}
