import os
import csv

class Usuario:
    def __init__(self, usuario:str, contraseña:str, apellidos:str, nombres:str, edad:int, estado:bool, usuario_nuevo=True, id=None):
        if usuario_nuevo:
            self.id = self.obtener_id()
        else:
            self.id = id
        self.usuario = usuario
        self.contraseña = contraseña
        self.apellidos = apellidos
        self.nombres = nombres
        self.edad = edad
        self.estado = estado
        
    def csv_esta_vacio(self, directorio):
        if os.stat(directorio).st_size == 0:
            return True
        else:
            return False
    
    def obtener_id(self):
        
        directorio = 'database/id_usuario.csv'
        
        with open(directorio, 'r') as csvfile:
            reader = csv.reader(csvfile)
            id = next(reader)[0]
            
        with open(directorio, 'w') as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow([str(int(id)+1)])
        
        return id