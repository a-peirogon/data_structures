import csv
import shutil
import re
import os
import tkinter as tk
from tkinter import ttk
from tkinter import messagebox
from tkinter import Tk
from tkinter import filedialog
from tkinter.filedialog import askopenfile
from PIL import Image, ImageTk
import Usuario
import Articulo
import DashBoard as DB

class CrearArticulo:
    def __init__(self, master, id_usuario:int):
        self.master = master
        self.dash_board = DB.DashBoard(id_usuario=id_usuario)
        self.image = None

        self.master.title('Articulos')
        
        # Crear un marco contenedor
        frame = tk.LabelFrame(self.master, text='Registrar nuevo producto')
        frame.grid(row=0, column=0, columnspan=3, pady=20)

        # Entrada para el nombre
        tk.Label(frame, text='Nombre: ').grid(row=1, column=0)
        self.name = tk.Entry(frame)
        self.name.focus()
        self.name.grid(row=1, column=1)

        # Entrada para el precio
        tk.Label(frame, text='Precio: ').grid(row=2, column=0)
        self.price = tk.Entry(frame)
        self.price.grid(row=2, column=1)

        # Botón para agregar imagen
        ttk.Button(frame, 
                   text='Agregar Imagen', 
                   command=self.add_image).grid(row=3, columnspan=2, sticky=tk.W + tk.E, pady=2)

        tk.Label(frame, text=self.image).grid(row=4, column=0)

        # Botón para agregar el producto
        ttk.Button(frame, 
                   text='Guardar Producto', 
                   command=self.add_product).grid(row=5, columnspan=2, sticky=tk.W + tk.E, pady=2)


        # Mensajes de salida
        self.message = tk.Label(self.master, text='', fg='red')
        self.message.grid(row=1, column=0, columnspan=3)

        # Tabla
        self.tree = ttk.Treeview(self.master, height=10, columns=('Nombre', 'Precio'))
        self.tree.grid(row=5, column=0, columnspan=2)
        self.tree.heading('#0', text='ID', anchor=tk.CENTER)
        self.tree.heading('Nombre', text='Nombre', anchor=tk.CENTER)
        self.tree.heading('Precio', text='Precio', anchor=tk.CENTER)

        # Botones
        ttk.Button(self.master, 
                   text='ELIMINAR', 
                   command=self.delete_product).grid(row=6, 
                                                     columnspan=2, 
                                                     sticky=tk.W + tk.E)
        
        #ttk.Button(master, 
        #           text='EDITAR', 
        #           command=self.edit_product).grid(row=6, column=1, sticky=tk.W + tk.E)

        
        tk.Label(frame, text=' ').grid(row=7, column=0)
        
        # Botón para volver
        ttk.Button(self.master, 
                   text='VOLVER', 
                   command=self.close_product_app).grid(row=8, 
                                                        columnspan=2, 
                                                        sticky=tk.W + tk.E)

        # Rellenar las filas
        self.get_products()

    def close_product_app(self):
        self.master.destroy()

    def get_products(self):
        # Limpiar la tabla
        records = self.tree.get_children()
        for element in records:
            self.tree.delete(element)
        
        lista_articulos = self.dash_board.cargar_ventas_pendientes()
        
        for articulo in lista_articulos:
            id = articulo.id
            name = articulo.nombre
            price = articulo.precio
            self.tree.insert('', 'end', text=id, values=(name, price,))

    def add_product(self):
        name = self.name.get()
        image = self.image
        price = self.price.get()
        
        # Validar que se haya ingresado un nombre y un precio
        if self.verificar_campos_articulo(nombre=name, foto=image, precio=int(price)):
            
            self.crear_publicacion(name, image, int(price))
                
            self.message['text'] = 'Producto agregado correctamente.'
            self.name.delete(0, 'end')
            self.price.delete(0, 'end')
            self.get_products()
        else:
            self.message['text'] = 'Por favor, ingresa un nombre y un precio.'        
    
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
                                         vendedor=self.dash_board.usuario.usuario)
            articulo.guardar_articulo()
    
    def delete_product(self):
        selected_item = self.tree.selection()
        if selected_item:
            id_articulo = self.tree.item(selected_item)['text']
            self.dash_board.eliminar_articulo(id_articulo=id_articulo)
            self.message['text'] = 'Producto eliminado correctamente.'
            self.get_products()
        else:
            self.message['text'] = 'Por favor, selecciona un producto.'

    def edit_product(self):
        selected_item = self.tree.selection()
        if selected_item:
            name = self.tree.item(selected_item)['text']
            with open('database/articulos.csv', 'r') as file:
                reader = csv.reader(file)
                rows = list(reader)
            with open('database/articulos.csv', 'w', newline='') as file:
                writer = csv.writer(file)
                writer.writerow(rows[0])
                for row in rows[1:]:
                    if row[1] == name:
                        # Obtener el nuevo nombre desde la interfaz
                        new_name = self.name.get()
                        # Obtener el nuevo precio desde la interfaz
                        new_price = self.price.get()
                        # Asignar el nuevo nombre y el nuevo precio a las posiciones adecuadas
                        row[1] = new_name
                        row[3] = new_price
                    writer.writerow(row)
            self.message['text'] = 'Producto editado correctamente.'
            self.get_products()
        else:
            self.message['text'] = 'Por favor, selecciona un producto.'
    
    def add_image(self):
        self.image = None
        my_w = tk.Toplevel()
        my_w.geometry("400x300")  # Size of the window 
        my_w.title('Seleccionar Imagen')
        my_font1=('times', 18, 'bold')
        l1 = tk.Label(my_w,text='  ',width=30,font=my_font1)  
        l1.grid(row=1,column=1)
        b1 = tk.Button(my_w, text='Subir Archivo', width=20,command = lambda:self.upload_file(my_w))
        b1.grid(row=2,column=1)
        b2 = tk.Button(my_w, text='Aceptar', width=20,command = lambda:self.accept(my_w))
        b2.grid(row=4,column=1) 
    
    def upload_file(self, my_w):
        global img
        f_types = [("Image File","*.jpg"),("Image File","*.png")]
        filename = filedialog.askopenfilename(filetypes=f_types)
        self.image = filename
        img=Image.open(filename)
        img_resized=img.resize((400,200)) # new width & height
        img=ImageTk.PhotoImage(img_resized)
        b3 =tk.Button(my_w,image=img) # using Button 
        b3.grid(row=3,column=1)
        my_w.mainloop()  # Keep the window open

    def accept(self, my_w):
        original = self.image
        index = re.search('/', self.image[::-1]).span()[0]
        name_image = self.image[::-1][:index][::-1]
        target = f'imagenes/{name_image}'
        shutil.copyfile(original, target)

        id_image = self.obtener_id()
        index = re.search(r'\.', name_image).span()[0]
        name_image = f'{name_image[:index]}_{self.obtener_id()}{name_image[index:]}'
        
        new_name = f'imagenes/{name_image}'
        old_name = target        
        os.rename(old_name, new_name)
        
        self.image = name_image
        my_w.destroy()
    
    def obtener_id(self):
        
        directorio = 'database/id_imagen.csv'
        
        with open(directorio, 'r') as csvfile:
            reader = csv.reader(csvfile)
            id = next(reader)[0]
            
        with open(directorio, 'w') as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow([str(int(id)+1)])
        
        return id
