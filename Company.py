class Postulante:
    def __init__(self, competencias):
        self.competencias = competencias
        self.siguiente = None

class Empresa:
    def __init__(self, competencias_requeridas):
        self.cabeza = None
        self.competencias_requeridas = competencias_requeridas

    def insertar_postulante(self, competencias):
        nuevo_postulante = Postulante(competencias)
        if not self.cabeza:
            self.cabeza = nuevo_postulante
        else:
            actual = self.cabeza
            while actual.siguiente:
                actual = actual.siguiente
            actual.siguiente = nuevo_postulante

    def buscar_postulantes(self):
        contador = 0
        actual = self.cabeza
        while actual:
            if set(actual.competencias).issuperset(self.competencias_requeridas):
                contador += 1
            actual = actual.siguiente
        return contador

# Lectura de la entrada
m = int(input())
competencias = list(map(int, input().split()))
n = int(input())

# Creación de la lista enlazada con los postulantes
empresa = Empresa(competencias)
for i in range(n):
    postulante = list(map(int, input().split()))
    empresa.insertar_postulante(postulante)

# Contar los postulantes que cumplen las competencias
busc_postulantes = empresa.buscar_postulantes()
print(busc_postulantes, end='')
