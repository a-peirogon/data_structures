#include <stdexcept> // Exceptions
#include <sstream>   // Stringstream
#include "abstract.hpp"
#include "ChainNode.hpp"

template <class T> class Chain : public LinearList<T>
{
    protected:
        ChainNode<T> *firstNode;
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
        Chain()
        {
            firstNode = NULL;
            size = 0;
        }

        // Methods
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
            ChainNode<T> *current = firstNode;
            for( int i = 0; i < index; i++ )
                current = current->next;
            return current->element;
        }

        int indexOf( T theElement )
        {
            ChainNode<T> *current = firstNode;
            for( int i = 0; current != NULL; i++  )
            {
                if( theElement == current->element )
                    return i;
                current = current->next;
            }
            return -1;
        }

        void add( int index, T theElement )
        {
            if( index < 0 || index > size )
            {
                cerr << "Index is invalid. Index: " << index << " size: " << size << endl;
                throw new out_of_range( "" );
            }
            if( size == 0 )
                firstNode = new ChainNode<T>( theElement );
            else
            {
                ChainNode<T> *current = firstNode;
                for( int i = 1; i < index; i++ )
                    current = current->next;
                current->next = new ChainNode<T>( theElement, current->next );
            }
            size++;
        }

        T remove( int index )
        {
            checkIndex( index );
            ChainNode<T> *current = firstNode;
            for( int i = 1; i < index; i++ )
                current = current->next;
            T elementRemoved = current->next->element;
            current->next = current->next->next;
            size--;
            return elementRemoved;
        }

        string toString( )
        {
            stringstream straux;
            straux << *this;
            return straux.str( );
        }

        // overloading of the operator <<
        friend ostream& operator << (ostream &os, const Chain<T> &p )
        {
            os << "[ ";
            if( p.size > 0 )
            {
                ChainNode<T> *current = p.firstNode;
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
