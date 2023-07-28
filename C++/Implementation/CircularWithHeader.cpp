#include <iostream>
#include "CircularWithHeader.hpp"

using namespace std;

int main()
{
    cout << boolalpha << endl; //print true or false with bool
    CircularWithHeader<string> *names = new CircularWithHeader<string>();
    cout << "is Emply?\t" << names->isEmpty() << endl;
    names->add( 0, "karen" );
    names->add( 1, "karen" );
    names->add( 2, "rafael" );
    names->add( 3, "gabriel" );
    names->add( 4, "omar" );
    names->add( 0, "jesus" );
    names->add( 3, "aura" );
    cout << "to string:\t" << names->toString() << endl;
    cout << "Numbers of elements:\t" << names->length() << endl;
    cout << "is Empty?\t" << names->isEmpty( ) << endl;
    cout << "Index of \"jesus\":\t" << names->indexOf( "jesus" ) << endl;
    cout << "The element in the index 5 is:\t" << names->get( 5 ) << endl;
    cout << "Delete the position 4:\t" << names->remove( 4 ) << endl;
    cout << "The content of the array is:\t" << *names << endl;
    cout << noboolalpha << endl;
    delete names;
    return 0;
}
