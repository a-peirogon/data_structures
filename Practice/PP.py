import ArbolAVL as AVL

import Articulo
import Usuario
import os
import csv
import DashBoard as DB
import tkinter as tk
from tkinter import Label, messagebox
from PIL import ImageTk, Image


class PaginaPrincipal:
    def __init__(self, id_usuario: int):
        self.id_usuario = id_usuario
        self.usuario = None
        self.arbol_articulos = None
        self.lista_carrito = None
        self.cargar_usuario()
        self.cargar_articulos()
        print(self.usuario)

    def cargar_usuario(self):

        directorio = 'database/usuarios.csv'

        with open(directorio, 'r') as f:
            lines = f.readlines()
            for line in lines:
                line = line.split(',')

                if line[0] == '\n':
                    break

                if str(self.id_usuario) == line[0]:
                    usuario = Usuario.Usuario(id=int(line[0]),
                                              usuario=line[1],
                                              contraseña=line[2],
                                              apellidos=line[3],
                                              nombres=line[4],
                                              edad=int(line[5]),
                                              estado=bool(line[6]),
                                              usuario_nuevo=False)
                    break

        self.usuario = usuario
    def csv_esta_vacio(self, directorio):
        if os.stat(directorio).st_size == 0:
            return True
        else:
            return False

    def cargar_articulos(self):
        # Carga el árbol
        arbol = AVL.ArbolAVL()

        directorio = 'database/articulos.csv'

        if (not os.path.exists(directorio)) or (self.csv_esta_vacio(directorio)):
            with open(directorio, 'w') as f:
                pass
            self.arbol_articulos = None
        else:
            with open(directorio, 'r') as f:
                lines = f.readlines()
                for line in lines[1:]:
                    line = line.split(',')

                    for i, text in enumerate(line):
                        if text == 'None':
                            line[i] = None
                        elif text == 'True' or text == 'True\n':
                            line[i] = True
                        elif text == 'False' or text == 'False\n':
                            line[i] = False

                    if line[0] == '\n':
                        pass

                    else:
                        articulo = Articulo.Articulo(id=int(line[0]),
                                                     nombre=line[1],
                                                     foto=line[2],
                                                     precio=int(line[3]),
                                                     vendedor=line[4],
                                                     comprador=line[5],
                                                     carrito=line[6],
                                                     check_vendedor=line[7],
                                                     check_comprador=line[8],
                                                     activo=line[9],
                                                     nuevo_articulo=False)

                        arbol.insertar(valor=articulo.id, objeto=articulo)

            self.arbol_articulos = arbol
            print('cargados')

    # la función retorno 1a1 va mostrando los objetos y permite comprarlos o pasar al siguiente
    def retorno_1_a_1(self, comprador):

        directorio = 'database/id_articulo.csv'

        with open(directorio, 'r') as csvfile:
            reader = csv.reader(csvfile)
            ID = next(reader)[0]
        for i in range(int(ID)):
            articulo = self.arbol_articulos.buscar(i)
            if articulo.objeto.vendedor == "usuario" or articulo.objeto.activo == False:
                pass
            else:
                print(f'Artículo: {articulo.objeto.nombre}.')
                eleccion = input("Presione 'a' para comprar o 'd' para ver el siguiente articulo.\n")
                if eleccion == 'd':
                    pass
                elif eleccion == 'a':
                    self.arbol_articulos.agregar_al_carrito(i, comprador)
                if i == (int(ID) - 1):
                    if eleccion == 'a':
                        self.arbol_articulos.agregar_al_carrito(i, comprador)
        print("No hay más objetos para mostrar.")

    def reInicicarBDD(self):

        columnas = ['id', 'nombre', 'foto', 'precio', 'vendedor', 'comprador', 'carrito', 'check_vendedor',
                    'check_comprador', 'activo']

        directorio = 'database/articulos.csv'

        with open(directorio, 'w') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=columnas)
            writer.writeheader()
