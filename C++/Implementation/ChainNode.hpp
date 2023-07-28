
template <class T> class ChainNode
{
public:
    T element;
    ChainNode<T> *next;
    ChainNode()
    {
        next = NULL;
    }

    ChainNode( T element, ChainNode<T> *next = NULL )
    {
        this->element = element;
        this->next = next;
    }
};
