#include <iostream>
#include <vector>

using namespace std;

class UnionFind
{
private:
    vector<int> p, rank;
public:
    UnionFind( int n )
    {
        p.assign( n, 0 );
        rank.assign( n, 0 );
        for( int i = 0; i < n; i++ )
            p[i] = i;
    }

    int findSet( int i )
    {
        if( i == p[i] )
            return i;
        else {
            p[i] = findSet( p[i] );
            return p[i];
        }
    }

    bool isSameSet( int i, int j )
    {
        return findSet( i ) == findSet( j );
    }

    void unionSet( int i, int j )
    {
        if( !isSameSet(i, j) )
        {
            int x = findSet(i), y = findSet(j);
            if( rank[x] > rank[y] )
                p[y] = x;
            else{
                p[x] = y;
                if( rank[x] == rank[y] )
                    rank[y]++;
            }
        }
    }
};

int main()
{
    UnionFind netWork( 10 );
    netWork.unionSet( 0, 1 );
    netWork.unionSet( 8, 9 );
    netWork.unionSet( 9, 0 );
    if( netWork.isSameSet( 0, 9) )
        cout << "0 and 9 is in the same set\n";
    return 0;
}
