#include <iostream>
#include "abstract.hpp"
#include "ChainNode.hpp"

using namespace std;

template <class T> class LinkedStack : public Stack<T>
{
private:
    ChainNode<T> *topNode;

public:
    LinkedStack()
    {
        topNode = NULL;
    }

    bool isEmpty()
    {
        return topNode == NULL;
    }

    T peek()
    {
        if( isEmpty() )
        {
            cerr << "The stack is Empty" << endl;
            throw new out_of_range( "" );
        }
        return topNode->element;
    }

    void push( T theElement )
    {
        topNode = new ChainNode<T>( theElement, topNode );
    }

    T pop()
    {
        if( isEmpty() )
        {
            cerr << "The stack is Empty" << endl;
            throw new out_of_range( "" );
        }
        T topElement = topNode->element;
        topNode = topNode->next;
        return topElement;
    }

};

int main()
{
    cout << boolalpha << endl;
    LinkedStack<int> *numbers = new LinkedStack<int>();
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
