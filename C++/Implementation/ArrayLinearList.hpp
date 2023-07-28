#include <stdexcept> // Exceptions
#include <sstream>   // stringstream
#include "abstract.hpp"

template <class T> class ArrayLinearList : public LinearList<T>
{
protected:
    T *element;
    int size, scope;

    void checkIndex( int index )
    {
        if( index < 0 || index >= size )
        {
            cerr << "Index is invalid. Index: " << index << " size: " << size << endl;
            throw new out_of_range( "" );
        }
    }

public:
    // Constructor
    ArrayLinearList( int initialSize = 8 )
    {
        if( initialSize < 0 )
        {
            cerr << "size must be greater or equals that 1" << endl;
            throw new invalid_argument( "" );
        }
        scope = initialSize;
        element = new T[ scope ];
        size = 0;
    }

    ~ArrayLinearList()
    {
        delete[] element;
    }


    //Methods
    bool isEmpty()
    {
        return size == 0;
    }

    int length()
    {
        return size;
    }

    T get( int index )
    {
        checkIndex( index );
        return element[ index ];
    }

    int indexOf( T theElement )
    {
        for( int i = 0; i < size; i++ )
            if( element[ i ] == theElement )
                return i;
        return -1;
    }

    void add( int index, T theElement )
    {
        if( index < 0 || index > size )
        {
            cerr << "Index is invalid. Index: " << index << " size: " << size << endl;
            throw new out_of_range( "" );
        }
        if( size + 1 == scope )
        {
            T *auxliary = element;
            scope *= 2;
            element = new T[ scope ];
            for( int i = 0; i < size; i++ )
                element[ i ] = auxliary[ i ];
        }
        for( int i = size - 1; i >= index; i-- )
            element[ i + 1 ] = element[ i ];
        element[ index ] = theElement;
        size++;
    }

    T remove( int index )
    {
        checkIndex( index );
        T elementRemoved  = element[ index ];
        for( int i = index; i < size - 1; i++ )
            element[ i ] = element[ i + 1 ];
        size--;
        return elementRemoved;
    }

    string toString()
    {
        stringstream straux;
        straux << *this;
        return straux.str();
    }

    friend ostream& operator << (ostream &os, const ArrayLinearList<T> &p )
    {
        os << "[ ";
        if( p.size > 0 )
        {
            os << p.element[ 0 ];
            for( int i = 1; i < p.size; i++ )
                os << ", " << p.element[ i ];
        }
        os << " ]";
        return os;
    }
};
