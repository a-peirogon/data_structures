#include <iostream>
#include <stdexcept> // Exceptions
#include <sstream>   // Stringstream
#include "ChainNode.hpp"
#include "abstract.hpp"

using namespace std;

template <class T> class CircularWithHeader : public LinearList<T>
{
protected:
    ChainNode<T> *header;
    int size;

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
    CircularWithHeader()
    {
        size = 0;
        header = new ChainNode<T>( );
        header->next = header;
    }

    // Mthods
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
        ChainNode<T> *auxiliary = header->next;
        for( int i = 0; i < index; i++ )
            auxiliary = auxiliary->next;
        return auxiliary->element;
    }

    int indexOf( T theElement )
    {
        ChainNode<T> *auxiliary = header->next;
        int i = 0;

        while( auxiliary->next != header )
        {
            if( auxiliary->element == theElement )
                return i;
            i++;
            auxiliary = auxiliary->next;
        }
        if( auxiliary == NULL )
            return -1;
    }

    T remove( int index )
    {
        checkIndex( index );
        T removedElement;

        ChainNode<T> *auxiliary = header;
        for( int i = 0; i < index; i++ )
            auxiliary = auxiliary->next;

        removedElement = auxiliary->next->element;
        auxiliary->next = auxiliary->next->next;
        size--;
        return removedElement;
    }

    void add( int index, T theElement )
    {
        if( index < 0 || index > size )
        {
            cerr << "Index is invalid. Index: " << index << " size: " << size << endl;
            throw new out_of_range( "" );
        }

        ChainNode<T> *auxliary = header;
        for( int i = 0; i < index; i++ )
            auxliary = auxliary->next;

        auxliary->next = new ChainNode<T>( theElement, auxliary->next );
        size++;
    }

    string toString()
    {
        stringstream straux;
        straux << *this;
        return straux.str();
    }

    friend ostream& operator << (ostream &os, const CircularWithHeader<T> &p )
    {
        // ChainNode<T> *currentNode = p.header->next;
        // os << "[ " << currentNode->element;
        // while( currentNode->next != currentNode )
        // {
        //     currentNode = currentNode->next;
        //     os << ", " << currentNode->element;
        // }
        os << "[ ";
        if( p.size > 0 )
        {
            ChainNode<T> *current = p.header->next;
            os << current->element;
            for( int i = 1; i < p.size; i++ )
            {
                current = current->next;
                os << ", " << current->element;
            }
        }
        os << " ]";
        return os;
    }

};
