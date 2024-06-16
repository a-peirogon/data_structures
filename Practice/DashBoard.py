import Usuario
import Articulo
import ArbolAVL as AVL
import os
import csv

class DashBoard:
    def __init__(self, id_usuario:int):
        self.id_usuario = id_usuario
        self.usuario = None
        self.arbol_articulos = None
        self.cargar_usuario()
        self.cargar_articulos()
        print(self.usuario)
    
    def csv_esta_vacio(self, directorio):
        if os.stat(directorio).st_size == 0:
            return True
        else:
            return False
    
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

    def cargar_articulos(self):

        # Carga el árbol
        arbol = AVL.ArbolAVL()

        directorio = 'database/articulos.csv'

        if (not os.path.exists(directorio)) or (self.csv_esta_vacio(directorio)):
            with open(directorio, 'w') as f:
                pass
            return None
        else:
            with open(directorio, 'r') as f:
                lines = f.readlines()
                for line in lines:
                    line = line.split(',')
                    
                    if line[0] == 'id':
                        continue
                    
                    for i, text in enumerate(line):
                        if text == 'None' or text == '':
                            line[i] = None
                        elif text == 'True' or text == 'True\n':
                            line[i] = True
                        elif text == 'False' or text == 'False\n':
                            line[i] = False
                            
                    if line[0] == '\n':
                        break
                        
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
                    
    def verificar_campos_articulo(self, nombre:str, foto:str, precio:int):
        flag = True
        dir_foto = os.path.join('imagenes', foto)
        if not os.path.exists(dir_foto):
            print('Imagen no encontrada.')
            flag = False
        if type(nombre) != str:
            print('Error al ingresar el nombre.')
            flag = False
        if type(precio) != int:
            print('Error al ingresar el precio.')
            flag = False
        
        return flag
    
    def crear_publicacion(self, nombre:str, foto:str, precio:int):
        
        if self.verificar_campos_articulo(nombre=nombre, foto=foto, precio=precio):
            
            articulo = Articulo.Articulo(nombre=nombre, 
                                         foto=foto, 
                                         precio=precio, 
                                         vendedor=self.usuario.usuario)
            articulo.guardar_articulo()
        
    def cargar_historial_venta(self):
        self.cargar_articulos()
        
        filtro = {'vendedor': self.usuario.usuario, 
                  'activo': False}
        busqueda = self.arbol_articulos.buscar_arbol_filtro(filtro)
        return self.arbol_articulos.lista_busqueda

    def cargar_historial_compra(self):
        self.cargar_articulos()
        
        filtro = {'comprador': self.usuario.usuario, 
                  'activo': False}
        busqueda = self.arbol_articulos.buscar_arbol_filtro(filtro)
        return self.arbol_articulos.lista_busqueda
    
    def cargar_ventas_pendientes(self):
        self.cargar_articulos()
        
        filtro = {'vendedor': self.usuario.usuario,
                  'check_vendedor': None,
                  'activo': True}
        self.arbol_articulos.buscar_arbol_filtro(filtro)
        return self.arbol_articulos.lista_busqueda
    
    def marcar_venta_realizada(self, id_articulo:int):
        
        articulo = self.arbol_articulos.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.check_vendedor = True
        
        self.guardar_modificacion_articulo(articulo=articulo)
        
    def reportar_problema_venta(self, id_articulo:int):
        
        articulo = self.arbol_articulos.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.check_vendedor = False
        
        self.guardar_modificacion_articulo(articulo=articulo)
        
    def cargar_compras_pendientes(self):
        self.cargar_articulos()
        
        filtro = {'comprador': self.usuario.usuario, 
                  'check_comprador': None,
                  'activo': False}
        busqueda = self.arbol_articulos.buscar_arbol_filtro(filtro)
        return self.arbol_articulos.lista_busqueda
    
    def marcar_compra_recibida(self, id_articulo:int):
        
        articulo = self.arbol_articulos.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.check_comprador = True
        
        self.guardar_modificacion_articulo(articulo=articulo)
    
    def reportar_problema_compra(self, id_articulo:int):
        
        articulo = self.arbol_articulos.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.check_comprador = False
        
        self.guardar_modificacion_articulo(articulo=articulo)
        
    def eliminar_articulo(self, id_articulo:int):
        
        articulo = self.arbol_articulos.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.activo = False
        
        self.guardar_modificacion_articulo(articulo=articulo)
        
    def guardar_modificacion_articulo(self, articulo:Articulo):
        
        directorio = 'database/articulos.csv'
        
        with open(directorio, 'r') as file:
            reader = csv.reader(file)
            rows = list(reader)[1:]
            
        os.remove(directorio)
        
        columnas = ['id', 'nombre', 'foto', 'precio', 'vendedor', 
                    'comprador', 'carrito', 'check_vendedor', 
                    'check_comprador', 'activo']
        
        with open(directorio, 'w', newline= '') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=columnas)
            is_file_empty = csvfile.tell() == 0
            
            if is_file_empty:
                writer.writeheader()
            
        # Escribir las filas actualizadas en el archivo CSV
        with open(directorio, 'a', newline= '') as file:
            writer = csv.writer(file)

            for row in rows:
                if row == '/n':
                    None
                elif row[0] == 'id':
                    None
                elif int(row[0]) == articulo.id:
                    writer.writerow([articulo.id,
                                     articulo.nombre,
                                     articulo.foto,
                                     articulo.precio,
                                     articulo.vendedor,
                                     articulo.comprador,
                                     articulo.carrito,
                                     articulo.check_vendedor,
                                     articulo.check_comprador,
                                     articulo.activo])
                else:
                    writer.writerow(row)
                    
        self.cargar_articulos()
    
    def cerrar_sesion(self):
        pass
    
    
