import os
import hashlib
import csv

class TablaHash:
    def __init__(self):
        self.tabla = {}
    
    def csv_esta_vacio(self, directorio):
        if os.stat(directorio).st_size == 0:
            return True
        else:
            return False
        
    def cargar_usuarios_hash(self):
        
        directorio = 'database/usuarios.csv'
        
        if (not os.path.exists(directorio)) or (self.csv_esta_vacio(directorio)):
            with open(directorio, 'w') as f:
                pass
        else:
            with open(directorio, 'r') as f:
                lines = f.readlines()
                for line in lines:
                    line = line.split(',')
                    self.tabla[line[1]] = line[2]
        
    def verificar_contraseña(self, usuario, contraseña):
        if usuario in self.tabla:
            hash_contraseña = self.calcular_hash(contraseña)
            if self.tabla[usuario] == hash_contraseña:
                return True
            else:
                print("Contraseña incorrecta")
                return False
        else:
            print("Usuario no encontrado")
            return False

    def calcular_hash(self, texto):
        sha256 = hashlib.sha256()
        sha256.update(texto.encode('utf-8'))
        return sha256.hexdigest()
