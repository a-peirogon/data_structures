import Usuario
import Articulo
import ListaEnlazada as LE

class Nodo:
    def __init__(self, valor, objeto=None):
        self.valor = valor
        self.objeto = objeto
        self.izquierda = None
        self.derecha = None
        self.altura = 1

class ArbolAVL:
    def __init__(self):
        self.raiz = None
        self.lista_busqueda = LE.ListaEnlazada()

    def insertar(self, valor, objeto=None):
        if self.raiz == None:
            self.raiz = Nodo(valor, objeto)
        else:
            self.raiz = self._insertar_recursivo(valor, objeto, self.raiz)

    def _insertar_recursivo(self, valor, objeto, nodo_actual):
        if nodo_actual is None:
            return Nodo(valor, objeto)
        
        if valor < nodo_actual.valor:
            nodo_actual.izquierda = self._insertar_recursivo(valor, objeto, nodo_actual.izquierda)
        else:
            nodo_actual.derecha = self._insertar_recursivo(valor, objeto, nodo_actual.derecha)

        nodo_actual.altura = 1 + max(self._obtener_altura(nodo_actual.izquierda), self._obtener_altura(nodo_actual.derecha))

        balance = self._obtener_balance(nodo_actual)

        # Caso de rotación a la izquierda
        if balance > 1 and valor < nodo_actual.izquierda.valor:
            return self._rotar_derecha(nodo_actual)

        # Caso de rotación a la derecha
        if balance < -1 and valor > nodo_actual.derecha.valor:
            return self._rotar_izquierda(nodo_actual)

        # Caso de rotación doble a la izquierda
        if balance > 1 and valor > nodo_actual.izquierda.valor:
            nodo_actual.izquierda = self._rotar_izquierda(nodo_actual.izquierda)
            return self._rotar_derecha(nodo_actual)

        # Caso de rotación doble a la derecha
        if balance < -1 and valor < nodo_actual.derecha.valor:
            nodo_actual.derecha = self._rotar_derecha(nodo_actual.derecha)
            return self._rotar_izquierda(nodo_actual)

        return nodo_actual

    def _obtener_altura(self, nodo):
        if nodo is None:
            return 0
        return nodo.altura

    def _obtener_balance(self, nodo):
        if nodo is None:
            return 0
        return self._obtener_altura(nodo.izquierda) - self._obtener_altura(nodo.derecha)

    def _rotar_derecha(self, nodo_z):
        nodo_y = nodo_z.izquierda
        nodo_t2 = nodo_y.derecha

        nodo_y.derecha = nodo_z
        nodo_z.izquierda = nodo_t2

        nodo_z.altura = 1 + max(self._obtener_altura(nodo_z.izquierda), self._obtener_altura(nodo_z.derecha))
        nodo_y.altura = 1 + max(self._obtener_altura(nodo_y.izquierda), self._obtener_altura(nodo_y.derecha))

        return nodo_y

    def _rotar_izquierda(self, nodo_z):
        nodo_y = nodo_z.derecha
        nodo_t2 = nodo_y.izquierda

        nodo_y.izquierda = nodo_z
        nodo_z.derecha = nodo_t2

        nodo_z.altura = 1 + max(self._obtener_altura(nodo_z.izquierda), self._obtener_altura(nodo_z.derecha))
        nodo_y.altura = 1 + max(self._obtener_altura(nodo_y.izquierda), self._obtener_altura(nodo_y.derecha))

        return nodo_y

    def imprimir_arbol(self):
        if self.raiz is not None:
            self._imprimir_recursivo(self.raiz)

    def _imprimir_recursivo(self, nodo_actual):
        if nodo_actual is not None:
            self._imprimir_recursivo(nodo_actual.izquierda)
            print(nodo_actual.objeto)
            self._imprimir_recursivo(nodo_actual.derecha)
    
    def imprimir_arbol_filtro(self, *kwargs):
        if self.raiz != None:
            self._imprimir_recursivo_filtro(self.raiz, *kwargs)

    def _imprimir_recursivo_filtro(self, nodo_actual, *kwargs):
        if nodo_actual != None:
            self._imprimir_recursivo_filtro(nodo_actual.izquierda, *kwargs)

            campos = nodo_actual.objeto.__dict__
            flag = False

            for key, value in kwargs[0].items():
                if key in list(campos.keys()):
                    flag = True
                    if campos[key] != value:
                        flag = False
                        break
                else:
                    flag = False
                    print('Se ingresó un campo que no existe')
                    
            if flag:
                print(nodo_actual.objeto)

            self._imprimir_recursivo_filtro(nodo_actual.derecha, *kwargs)
    
    def buscar_arbol_filtro(self, *kwargs):
        self.lista_busqueda.vaciar_lista()
        if self.raiz != None:
            self._buscar_recursivo_filtro(self.raiz, *kwargs)

    def _buscar_recursivo_filtro(self, nodo_actual, *kwargs):
        if nodo_actual != None:
            self._buscar_recursivo_filtro(nodo_actual.izquierda, *kwargs)

            campos = nodo_actual.objeto.__dict__
            flag = False

            for key, value in kwargs[0].items():
                if key in list(campos.keys()):
                    flag = True
                    if campos[key] != value:
                        flag = False
                        break
                else:
                    flag = False
                    print('Se ingresó un campo que no existe')
                    
            if flag:
                self.lista_busqueda.agregar_elemento(nodo_actual.objeto)

            self._buscar_recursivo_filtro(nodo_actual.derecha, *kwargs)
    
    def buscar(self, valor):

        return self._buscar_recursivo(self.raiz, valor)

    def _buscar_recursivo(self, nodo, valor):
        if nodo is None or nodo.valor == valor:
            return nodo

        if valor < nodo.valor:
            return self._buscar_recursivo(nodo.izquierda, valor)
        else:
            return self._buscar_recursivo(nodo.derecha, valor)
    
    def existe(self, valor):
        return self._existe_recursivo(self.raiz, valor)

    def _existe_recursivo(self, nodo, valor):
        if nodo == None:
            return False

        if nodo.valor == valor:
            return True
        elif valor < nodo.valor:
            return self._existe_recursivo(nodo.izquierda, valor)
        else:
            return self._existe_recursivo(nodo.derecha, valor)
    
    def agregar_al_carrito(self, valor):
        return self._agregar_al_carrito_recursivo(self.raiz, valor)

    def _agregar_al_carrito_recursivo(self, nodo, valor):
        if nodo is None:
            pass
        elif nodo.objeto.id == valor:

            nodo.objeto.carrito = True
        elif valor < nodo.valor:
            self._agregar_al_carrito_recursivo(nodo.izquierda, valor)
        else:
            self._agregar_al_carrito_recursivo(nodo.derecha, valor)    
    def guardar_arbol(self):
        if self.raiz is not None:
            self._guardar_ArbolRecursivo(self.raiz)

    def _guardar_ArbolRecursivo(self, nodo_actual):
        if nodo_actual is not None:
            self._guardar_ArbolRecursivo(nodo_actual.izquierda)
            nodo_actual.objeto.guardar_articulo()
            self._guardar_ArbolRecursivo(nodo_actual.derecha)