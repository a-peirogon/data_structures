package unal.datastructures;

import java.util.*;

class DataDict<K extends Comparable<? super K>, E>
{
   // fields
   K key;      // its key
   E element;  // element in node

   // constructor
   DataDict( )
   {
      key = null;
      element = null;
   }

   DataDict( K theKey, E theElement )
   {
      key = theKey;
      element = theElement;
   }

   @Override
   public String toString( )
   {
      return "[" + Objects.toString( element ) +
         ", key=" + Objects.toString( key ) + "]";
   }
}
