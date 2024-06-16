import os
import csv
import getpass
from tkinter import messagebox
import ArbolAVL as AVL
import TablaHash as TB
import Usuario
import MostrarDashBoard as MDB

class Inicio:
    def __init__(self):
        pass
       
    def csv_esta_vacio(self, directorio):
        if os.stat(directorio).st_size == 0:
            return True
        else:
            return False
        
    def cargar_usuarios(self):
        
        # Carga el árbol
        arbol = AVL.ArbolAVL()
        
        directorio = 'database/usuarios.csv'
        
        if (not os.path.exists(directorio)) or (self.csv_esta_vacio(directorio)):
            with open(directorio, 'w') as f:
                pass
            return None
        else:
            with open(directorio, 'r') as f:
                lines = f.readlines()
                for line in lines:
                    line = line.split(',')
                    arbol.insertar(line[1])
        
            return arbol
    
    def verificar_campos_usuario(self, datos:list):
        
        try:
            datos[-1] = int(datos[-1])
        except:
            print(f'Error al escribir edad')
            return False
        
        # Validar el usuario
        arbol = self.cargar_usuarios()
        
        if arbol:
            if arbol.existe(datos[1]):
                print('El usuario ya existe, ingrese otro usuario')           
                return False
        
        # Valida los campos
        campos = ['usuario', 'contraseña', 'apellidos', 'nombres', 'edad']
        tipos = [str, str, str, str, int]
        
        validacion = list(map(lambda x, y: type(x) == y, datos, tipos))
        
        flag = True
        
        for i, valor, in enumerate(validacion):
            if not valor:
                print(f'Error al escribir {campos[i]}.')
                flag = False
        
        return flag
    
    def guardar_usuario(self, usuario:Usuario):

        directorio = 'database/usuarios.csv'
        
        datos = list(usuario.__dict__.values())[:7]
        
        with open(directorio, 'a') as archivo_csv:
            writer = csv.writer(archivo_csv)
            writer.writerow(datos)
        
    def sing_up(self, usuario, contraseña, apellidos, nombres, edad):

        datos = [usuario, contraseña, apellidos, nombres, edad]
            
        # Valida los campos para creación del usuario
        validacion = self.verificar_campos_usuario(datos=datos)
        
        # Crea el hash
        tabla_hash  = TB.TablaHash()
        contraseña_hash = tabla_hash.calcular_hash(datos[1])
        
        # Si la validación es correcta crea el objeto usuario y lo guarda en el CSV
        if validacion:
            usuario = Usuario.Usuario(usuario=datos[0],
                                      contraseña=contraseña_hash,
                                      apellidos=datos[2],
                                      nombres=datos[3],
                                      edad=int(datos[4]),
                                      estado=True)
            
            guardar_usuario = self.guardar_usuario(usuario)
            
            #print(f'\nSe guardó el usuario {usuario.usuario}')
            messagebox.showinfo("Éxito", f"Se guardó el usuario {usuario.usuario}")
            return True
        
        else:
            #print('\nError al ingresar los datos\nIntente de nuevo')
            messagebox.showerror("Error", "Error al ingresar los datos\nIntente de nuevo")
            return False
    
    def log_in(self, usuario:str, contraseña:str):
        
        # Carga los usuarios y hash
        tabla_hash = TB.TablaHash()
        tabla_hash.cargar_usuarios_hash()

        # Valida la contraseña
        contraseña_valida = tabla_hash.verificar_contraseña(usuario, contraseña)
        
        if contraseña_valida:
            
            # Crea el usuario
            directorio = 'database/usuarios.csv'
                
            with open(directorio, 'r') as f:
                lines = f.readlines()
                for line in lines:
                    line = line.split(',')
                    if usuario == line[1]:
                        id_usuario = int(line[0])
                        break
            
            MDB.MostrarDashBoard(id_usuario=id_usuario)
            
            return 
        
        else:
            #print('El usuario o la contraseña no son correctas')
            messagebox.showinfo("Error", f"El usuario o la contraseña no son correctas")
            
            return None
            
            
    