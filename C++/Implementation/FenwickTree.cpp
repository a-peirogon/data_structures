#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

class FenwickTree
{
private:
    vector<int> ft;

    int LSOne( int a )
    {
        return ( a & (-a) );
    }

public:
    FenwickTree( int n )
    {
        ft.assign( n + 1, 0 );
    }

    int rsq( int b )
    {
        int sum = 0;
        while( b )
        {
            sum += ft[b];
            b -= LSOne(b);
        }
        return sum;
    }

    int rsq( int a, int b )
    {
        return rsq(b) - ( a == 1 ? 0 : rsq( a - 1 ) );
    }

    void adjust( int k, int v )
    {
        while( k < ft.size() )
        {
            ft[k] += v;
            k += LSOne(k);
        }
    }
};

int main()
{
    ios_base::sync_with_stdio( false );
    cin.tie( NULL );

    int f[] = { 2, 4, 5, 5, 6, 6, 6, 7, 7, 8, 9 };
    FenwickTree ft(10);

    for( int i = 0; i < 11; i++ )
        ft.adjust( f[i], 1 );

    cout << ft.rsq(1, 1) << '\n';
    cout << ft.rsq(1, 2) << '\n';
    cout << ft.rsq(1, 6) << '\n';
    cout << ft.rsq(1, 10) << '\n';
    cout << ft.rsq(3, 6) << '\n';
    ft.adjust( 5, 2 );
    cout << ft.rsq(1, 10) << '\n';

    return 0;
}
