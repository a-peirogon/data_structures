package unal.datastructures;

import java.util.*;
import java.io.*;

public class ArrayLinearListImproved
   <T extends Serializable & Comparable<? super T>> extends ArrayLinearList<T>
   implements LinearListImproved<T>
{
   // constructors
   public ArrayLinearListImproved(int initialCapacity)
   {
      super( initialCapacity );
   }

   public ArrayLinearListImproved( )
   {
      this( 10 );
   }

   // methods
   /** Save the list into a file */
   public void save( String fn )
   {
      try( ObjectOutputStream os = new
           ObjectOutputStream( new FileOutputStream( fn ) ) )
         {
            os.writeInt( size );
            for( T x : this ) os.writeObject( x );
         }
      catch( IOException e )
      {
         e.printStackTrace( );
      }
      System.out.println( "Save done" );
   }

   /** Load from a file into the list.
    * The list is not reset beforehand */
   @SuppressWarnings( "unchecked" )
   public void load( String fn )
   {
      try( ObjectInputStream is = new
           ObjectInputStream( new FileInputStream( fn ) ) )
         {
            int n = is.readInt( );
            for( int i = 0; i < n; i++ )
               add( i, ( T ) is.readObject( ) );
         }
      catch( IOException | ClassNotFoundException e )
      {
         e.printStackTrace( );
      }
      System.out.println( "Load done" );
   }

   /** sort the list using default compareTo */
   public void sort( )
   {
      Arrays.sort( element, 0, size );
   }

   /** sort the list using specific comparator */
   public void sort( Comparator<T> c )
   {
      Arrays.sort( element, 0, size, c );
   }

   /** test program */
   public static void main( String[] args )
   {
      Random r = new Random( new Date( ).getTime( ) );

      ArrayLinearListImproved<Student> x =
         new ArrayLinearListImproved<>( );
      ArrayLinearListImproved<Student> y =
         new ArrayLinearListImproved<>( );

      x.add( 0, new Student( r.nextInt( 999 ), "Ingrid" ) );
      x.add( 1, new Student( 333, "Zenon" ) );
      x.add( 2, new Student( r.nextInt( 999 ), "Mary" ) );
      x.add( 3, new Student( r.nextInt( 999 ), "Aiden" ) );

      System.out.println( "list is " + x );
      x.sort( );
      System.out.println( "by default " + x );
      x.sort( Student.BY_NAME );
      System.out.println( "by name " + x );
      x.sort( Student.BY_NAME_REV );
      System.out.println( "by name rev " + x );
      x.sort( Student.BY_CODE );
      System.out.println( "by code " + x );
      x.sort( Student.BY_CODE_REV );
      System.out.println( "by code rev " + x );
      x.save( "x.dat" );
      y.load( "x.dat" );
      for( Student s : y ) System.out.println( s );
      System.out.println( y.indexOf( new Student( 333 ) ) );
   }
}

class Student implements Serializable, Comparable<Student>
{
   private int code;
   private String name;

   public static final Comparator<Student> BY_NAME = new ByName( );
   public static final Comparator<Student> BY_NAME_REV = new ByNameRev( );
   public static final Comparator<Student> BY_CODE = new Bycode( );
   public static final Comparator<Student> BY_CODE_REV = new BycodeRev( );

   public Student( )
   {
      this( 0, "unknown" );
   }

   public Student( int c )
   {
      this( c, "unknown" );
   }

   public Student( int c, String n )
   {
      code = c; name = n;
   }

   public int getCode( )
   {
      return code;
   }

   public String getName( )
   {
      return name;
   }

   @Override
   public String toString( )
   {
      return "[" + code + ", " + name + "]";
   }

   @Override
   public boolean equals( Object o )
   {
      if( o == null ) return false;
      if( o == this ) return true;
      if( ! ( o instanceof Student ) ) return false;
      return this.code == ( ( Student ) o ).code;
   }

   @Override
   public int hashCode( )
   {
      return Objects.hash( code );
   }

   @Override
   public int compareTo( Student o )
   {
      return this.code - o.code;
   }

   private static class ByName implements Comparator<Student>{
      public int compare( Student a, Student b ) {
         return a.getName( ).compareTo( b.getName( ) );
      }
   }

   private static class ByNameRev implements Comparator<Student>{
      public int compare( Student a, Student b ) {
         return -1 * a.getName( ).compareTo( b.getName( ) );
      }
   }

   private static class Bycode implements Comparator<Student>{
      public int compare( Student a, Student b ) {
         return a.code - b.code;
      }
   }

   private static class BycodeRev implements Comparator<Student>{
      public int compare( Student a, Student b ) {
         return -1 * ( a.code - b.code );
      }
   }
}
