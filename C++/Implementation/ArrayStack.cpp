#include <iostream>
#include "abstract.hpp"

using namespace std;

template <class T> class ArrayStack : public Stack<T>
{
private:
    int top, scope;
    T* stack;

public:

    //Contructor
    ArrayStack( int initialCapacity = 8 )
    {
        if( initialCapacity < 0 )
        {
            cerr << "The initial capacity is invalid:" << initialCapacity;
            throw new out_of_range( "" );
        }
        scope = initialCapacity;
        stack = new T[ initialCapacity ];
        top = -1;
    }

    // Methods
    bool isEmpty()
    {
        return top == -1;
    }

    T peek()
    {
        if( isEmpty() )
        {
            cerr << "The stack is Empty" << endl;
            throw new out_of_range( "" );
        }
        return stack[top];
    }

    void push( T theElement )
    {
        if( top == scope - 1 )
        {
            T *old = stack;
            stack = new T[ 2 * scope ];
            for( int i = 0; i < scope; i++ )
                stack[ i ] = old[ i ];
            scope *= 2;
        }
        stack[ ++top ] = theElement;
    }
    T pop()
    {
        if( isEmpty() )
        {
            cerr << "The stack is Empty" << endl;
            throw new out_of_range( "" );
        }
        T topElement = stack[ top-- ];
        return topElement;
    }
};


int main()
{
    cout << boolalpha << endl;
    ArrayStack<int> *numbers = new ArrayStack<int>();
    cout << "is Empty? " << numbers->isEmpty() << endl;
    cout << "15 elements are inserted from 0 ..." << endl;
    int i;
    for( i = 0; i < 15; i++ )
        numbers->push( i );
    cout << "The element in top is: " << numbers->peek() << endl;
    cout << "5 elements are removed" << endl;
    i = 5;
    while( i-- )
        numbers->pop();
    cout << "5 elements are inserted from 15" << endl;
    for( i = 15; i < 20; i++ )
        numbers->push( i );
    cout << "All elements are removed ... " << endl;
    while( !numbers->isEmpty() )
        cout << numbers->pop() << " ";
    cout << endl;
    cout << noboolalpha << endl;
    delete numbers;
    return 0;
}
