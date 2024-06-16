import csv
import Usuario

class Articulo:
    
    def __init__(self, nombre:str, foto:str, precio:int, vendedor:str, comprador=None, carrito=False, check_vendedor=None, check_comprador=None, activo=True, nuevo_articulo=True, id=None):
        if nuevo_articulo:
            self.id = self.obtener_id()
        else:
            self.id = id
        self.nombre = nombre
        self.foto = foto
        self.precio = precio
        self.vendedor = vendedor
        self.comprador = comprador
        self.carrito = carrito
        self.check_vendedor = check_vendedor
        self.check_comprador = check_comprador
        self.activo = activo
        
    def obtener_id(self):
        
        directorio = 'database/id_articulo.csv'
        
        with open(directorio, 'r') as csvfile:
            reader = csv.reader(csvfile)
            id = next(reader)[0]
            
        with open(directorio, 'w') as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow([str(int(id)+1)])
        
        return id
    
    def __str__(self):
        return str(self.__dict__)
            
    
    def guardar_articulo(self):
                
        columnas = ['id', 'nombre', 'foto', 'precio', 'vendedor', 'comprador', 'carrito', 'check_vendedor', 'check_comprador', 'activo']
        
        directorio = 'database/articulos.csv'
        
        with open(directorio, 'a') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=columnas)
            is_file_empty = csvfile.tell() == 0
            
            if is_file_empty:
                writer.writeheader()
                
            writer.writerow({'id':self.id,
                             'nombre': self.nombre,
                             'foto': self.foto,
                             'precio': self.precio,
                             'vendedor': self.vendedor,
                             'comprador': self.comprador,
                             'carrito': self.carrito,
                             'check_vendedor': self.check_vendedor,
                             'check_comprador': self.check_comprador,
                             'activo': self.activo})
        
        