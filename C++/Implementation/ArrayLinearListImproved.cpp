#include <iostream>
#include "ArrayLinearListImproved.hpp"
using namespace std;

int main()
{
    cout << boolalpha << endl;
    ArrayLinearListImproved<int> *naturals = new ArrayLinearListImproved<int>();
    int j = 0;
    for( int i = 20; i != -1; i-- )
        naturals->add( j++, i );
    cout << "The elements are: " << *naturals << endl;
    cout << "Removing all elements ..." << endl;
    naturals->save( "dataFile.jet" );
    while( !naturals->isEmpty() )
        naturals->remove(0);
    cout << "is Empty? " << naturals->isEmpty() << endl;
    cout << "Loading all elements from a file ... "  << endl;
    naturals->load( "dataFile.jet" );
    cout << "The elements are: " << *naturals << endl;
    cout << "is Empty? " << naturals->isEmpty() << endl;
    cout << "Elements sorted: ";
    naturals->sortAll();
    cout << *naturals << endl;
    cout <<  noboolalpha << endl;
    delete naturals;
    return 0;
}
