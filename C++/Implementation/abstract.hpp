using namespace std;

template <class T> class LinearList
{
public:
    virtual bool isEmpty() = 0;
    virtual int length() = 0;
    virtual T get( int index ) = 0;
    virtual int indexOf( T theElement ) = 0;
    virtual void add( int index, T theElement ) = 0;
    virtual T remove( int index ) = 0;
    virtual string toString() = 0;
};

template <class T> class LinearListImproved : public LinearList<T>
{
public:
    virtual void save( string adress ) = 0;
    virtual void load( string adress ) = 0;
    virtual void sortAll() = 0;
};

template <class T> class Stack
{
public:
    virtual bool isEmpty() = 0;
    virtual T peek() = 0;
    virtual void push( T theElement ) = 0;
    virtual T pop() = 0;
};

template <class T> class Queue
{
public:
    virtual bool isEmpty() = 0;
    virtual T getFrontElement() = 0;
    virtual T getRearElement() = 0;
    virtual void put( T theElement ) = 0;
    virtual T remove() = 0;
};
