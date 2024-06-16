class Nodo:
    def __init__(self, valor):
        self.valor = valor
        self.siguiente = None

class ListaEnlazada:
    def __init__(self):
        self.cabeza = None
    
    def __iter__(self):
        self.actual = self.cabeza
        return self

    def __next__(self):
        if self.actual:
            valor = self.actual.valor
            self.actual = self.actual.siguiente
            return valor
        else:
            raise StopIteration
        
    def vaciar_lista(self):
        self.cabeza = None
    
    def esta_vacia(self):
        return self.cabeza is None
    
    def agregar_elemento(self, valor):
        nuevo_nodo = Nodo(valor)
        if self.esta_vacia():
            self.cabeza = nuevo_nodo
        else:
            nodo_actual = self.cabeza
            while nodo_actual.siguiente is not None:
                nodo_actual = nodo_actual.siguiente
            nodo_actual.siguiente = nuevo_nodo
    
    def imprimir_lista(self):
        if self.esta_vacia():
            print("La lista está vacía.")
        else:
            nodo_actual = self.cabeza
            while nodo_actual is not None:
                print(nodo_actual.valor)
                nodo_actual = nodo_actual.siguiente
