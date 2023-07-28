#include <algorithm>
#include <fstream>
#include "ArrayLinearList.hpp"

// there is a problem with the inheritance in the methods LinearList and LinearListImproved
template <class T> class ArrayLinearListImproved : public LinearListImproved<T>, public ArrayLinearList<T>
{
public:

    void save( string adress )
    {
        ofstream os( adress );
        os.write( (char *)&this->size, sizeof(int) );
        for( int i = 0; i < this->size; i++ )
            os.write( (char *)&this->element[ i ], sizeof(T) );
        os.close();
    }

    void load( string adress )
    {
        ifstream is( adress );
        is.read( (char *)&this->size, sizeof(int) );
        for( int i = 0; i < this->size; i++ )
            is.read( (char *)&this->element[ i ], sizeof(T) );
        is.close();
    }

    void sortAll()
    {
        sort( this->element, this->element + this->size );
    }

    bool isEmpty()
    {
        return ArrayLinearList<T>::isEmpty();
    }

    int length()
    {
        return ArrayLinearList<T>::length();
    }

    T get( int index )
    {
        return ArrayLinearList<T>::get( index );
    }

    int indexOf( T theElement )
    {
        return ArrayLinearList<T>::indexOf( theElement );
    }

    void add( int index, T theElement )
    {
        return ArrayLinearList<T>::add( index, theElement );
    }

    T remove( int index )
    {
        return ArrayLinearList<T>::remove( index );
    }

    string toString()
    {
        return ArrayLinearList<T>::toString();
    }

};
