import csv
import sys
import MostrarInicio as Inicio
import tkinter as tk
from tkinter import ttk
from tkinter import messagebox
from tkinter import Tk
import Usuario
import DashBoard as DB
import CrearArticulo as CA
import PaginaPrincipal as PP2
from ttkthemes import ThemedTk
from PIL import Image, ImageTk

class MostrarDashBoard:
    def __init__(self, id_usuario: int):
        self.id_usuario = id_usuario
        self.wind = ThemedTk(theme="radiance")
        self.wind.title('Dashboard')
        self.dash_board = DB.DashBoard(id_usuario=id_usuario)
        self.pagina_principal = None
    
        # Margen exterior
        self.wind.grid_rowconfigure(0, weight=1)
        self.wind.grid_columnconfigure(0, weight=1)
        frame_margin = tk.Frame(self.wind, padx=20, pady=20, bg='white')
        frame_margin.grid(row=0, column=0, sticky="nsew")

        # Logo
        logo_image = Image.open("logo1.png")
        logo_image = logo_image.resize((300, 100))
        logo_image = ImageTk.PhotoImage(logo_image)
        logo_label = tk.Label(frame_margin, image=logo_image)
        logo_label.image = logo_image
        logo_label.grid(row=0, column=0, pady=10)

        # Contenedor
        frame = tk.LabelFrame(frame_margin, text='Dashboard', font=('Arial', 14, 'bold'), foreground='#333333')
        frame.grid(row=1, column=0, pady=20, ipadx=20, ipady=20)

        # Estilo de los botones
        style = ttk.Style(self.wind)
        style.theme_use("radiance")
        style.configure('TButton', font=('Arial', 12))

        # Botones
        btn_user_data = ttk.Button(frame, text="Datos de Usuario", command=self.ver_datos_usuario)
        btn_user_data.grid(row=0, column=0, padx=10, pady=(5, 2), sticky="ew")  # Ajusta el valor de pady para cambiar el espacio vertical
        
        btn_view_sales = ttk.Button(frame, text="Cerrar Sesión", command=self.cerrar_sesion)
        btn_view_sales.grid(row=1, column=0, padx=10, pady=(2, 2), sticky="ew")  # Ajusta el valor de pady para cambiar el espacio vertical
        
        btn_view_sales = ttk.Button(frame, text="Ventas Pendientes", command=self.ver_ventas_pendientes)
        btn_view_sales.grid(row=2, column=0, padx=10, pady=(2, 2), sticky="ew")  # Ajusta el valor de pady para cambiar el espacio vertical

        btn_view_purchases = ttk.Button(frame, text="Compras Pendientes", command=self.ver_compras_pendientes)
        btn_view_purchases.grid(row=3, column=0, padx=10, pady=(2, 2), sticky="ew")  # Ajusta el valor de pady para cambiar el espacio vertical
        
        btn_create_post = ttk.Button(frame, text="Crear Artículo", command=self.crear_articulo)
        btn_create_post.grid(row=4, column=0, padx=10, pady=(2, 2), sticky="ew")  # Ajusta el valor de pady para cambiar el espacio vertical
        
        btn_create_post = ttk.Button(frame, text="Página Principal", command=self.ver_pagina_principal)
        btn_create_post.grid(row=5, column=0, padx=10, pady=(2, 5), sticky="ew")  # Ajusta el valor de pady para cambiar el espacio vertical

        self.wind.mainloop()


        
    def ver_pagina_principal(self):
        self.wind.destroy()
        PP2.PaginaPrincipalGUI(self.id_usuario)

    def cerrar_sesion(self):
        self.wind.destroy()
        inicio = Inicio.Ventana()
        inicio.crear_ventana_principal()
        

    def volver_al_menu_principal(self, ventana):
        ventana.destroy()
        MostrarDashBoard(self.id_usuario)

    def ver_datos_usuario(self):

        # Carga el usuario
        self.dash_board.cargar_usuario()
        
        usuario = self.dash_board.usuario

        # Verificar si se encontraron datos de usuario
        if usuario:
            # Crear una ventana emergente para mostrar los datos del usuario
            user_data_window = tk.Toplevel(self.wind)
            user_data_window.title('Datos de Usuario')

            # Etiqueta de título
            title_label = tk.Label(user_data_window, text="Datos de Usuario", font=("Arial", 16, "bold"))
            title_label.pack(pady=10)

            # Marco para la lista de datos del usuario
            frame = tk.LabelFrame(user_data_window, text='Datos')
            frame.pack(padx=20, pady=10)

            # Crear un widget de desplazamiento
            scrollbar = tk.Scrollbar(frame)
            scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

            # Crear un listbox para mostrar los datos del usuario
            listbox = tk.Listbox(frame, width=50, yscrollcommand=scrollbar.set)
            listbox.pack()

            # Agregar los datos del usuario al listbox
            listbox.insert(tk.END, f"Usuario: {usuario.usuario}")
            listbox.insert(tk.END, f"Nombre: {usuario.nombres} {usuario.apellidos}")
            listbox.insert(tk.END, f"Edad: {usuario.edad}")

            # Configurar el desplazamiento del listbox
            scrollbar.config(command=listbox.yview)

        else:
            print("No se encontraron datos de usuario.")
    
    def ver_ventas_pendientes(self):
        ventas_pendientes = self.dash_board.cargar_ventas_pendientes()
        
        if ventas_pendientes:
            # Crear una nueva ventana para mostrar las ventas pendientes
            ventas_window = tk.Toplevel(self.wind)
            ventas_window.title('Ventas Pendientes')

            # Etiqueta de título
            title_label = tk.Label(ventas_window, text="Ventas Pendientes", font=("Arial", 16, "bold"))
            title_label.pack(pady=10)

            # Marco para la lista de ventas
            frame = tk.LabelFrame(ventas_window, text='Lista de Ventas')
            frame.pack(padx=20, pady=10)

            # Crear un widget de desplazamiento
            scrollbar = tk.Scrollbar(frame)
            scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

            # Crear una lista para mostrar las ventas pendientes
            listbox = tk.Listbox(frame, width=50, yscrollcommand=scrollbar.set)
            listbox.pack()

            # Agregar las ventas pendientes a la lista
            for venta in ventas_pendientes:
                venta_str = f"ID: {venta.id} - Producto: {venta.nombre} - Precio: {venta.precio}"
                listbox.insert(tk.END, venta_str)

            # Configurar el desplazamiento de la lista
            scrollbar.config(command=listbox.yview)

            # Botones Recibido y Problema
            btn_recibido = ttk.Button(ventas_window, 
                                      text="Enviado", 
                                      command=lambda: self.marcar_venta_realizada(ventas_window, 
                                                                                  ventas_pendientes, 
                                                                                  listbox))
            btn_recibido.pack(pady=10)

            btn_problema = ttk.Button(ventas_window,
                                      text="Reportar problema", 
                                      command=lambda: self.reportar_problema_venta(ventas_window,
                                                                                   ventas_pendientes, 
                                                                                   listbox))
            btn_problema.pack(pady=10)
        else:
            print("No hay ventas pendientes.")

    def marcar_venta_realizada(self, ventana, ventas_pendientes, listbox):
        seleccionado = listbox.curselection()
        if seleccionado:
            
            for i, venta in enumerate(ventas_pendientes):
                if i == int(seleccionado[0]):
                    id_articulo = venta.id
            
            self.dash_board.marcar_venta_realizada(id_articulo=id_articulo)
            
            messagebox.showinfo("Venta Recibida", f"La venta con ID {id_articulo} ha sido marcada como recibida.")
            # Actualizar la lista de ventas pendientes en la ventana
            ventana.destroy()
            self.ver_ventas_pendientes()
        else:
            messagebox.showwarning("Error", "Por favor, selecciona una venta.")
            

    def reportar_problema_venta(self, ventana, ventas_pendientes, listbox):
        seleccionado = listbox.curselection()
        if seleccionado:
            for i, venta in enumerate(ventas_pendientes):
                if i == int(seleccionado[0]):
                    id_articulo = venta.id
            
            self.dash_board.reportar_problema_venta(id_articulo=id_articulo)

            messagebox.showinfo("Problema Reportado", f"Se ha reportado un problema con la venta ID {id_articulo}.")
            # Actualizar la lista de ventas pendientes en la ventana
            ventana.destroy()
            self.ver_ventas_pendientes()
        else:
            messagebox.showwarning("Error", "Por favor, selecciona una venta.")

    def ver_compras_pendientes(self):
        compras_pendientes = self.dash_board.cargar_compras_pendientes()
        if compras_pendientes:
            # Crear una nueva ventana para mostrar las compras pendientes
            compras_window = tk.Toplevel(self.wind)
            compras_window.title('Compras Pendientes')

            # Etiqueta de título
            title_label = tk.Label(compras_window, text="Compras Pendientes", font=("Arial", 16, "bold"))
            title_label.pack(pady=10)

            # Marco para la lista de compras
            frame = tk.LabelFrame(compras_window, text='Lista de Compras')
            frame.pack(padx=20, pady=10)

            # Crear un widget de desplazamiento
            scrollbar = tk.Scrollbar(frame)
            scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

            # Crear una lista para mostrar las compras pendientes
            listbox = tk.Listbox(frame, width=50, yscrollcommand=scrollbar.set)
            listbox.pack()

            # Agregar las compras pendientes a la lista
            for compra in compras_pendientes:
                compra_str = f"ID: {compra.id} - Producto: {compra.nombre} - Precio: {compra.precio}"
                listbox.insert(tk.END, compra_str)

            # Configurar el desplazamiento de la lista
            scrollbar.config(command=listbox.yview)

            # Botones Recibido y Problema
            btn_recibido = ttk.Button(compras_window,
                                      text="Recibido", 
                                      command=lambda: self.marcar_compra_recibida(compras_window,
                                                                                  compras_pendientes,
                                                                                  listbox))
            btn_recibido.pack(pady=10)

            btn_problema = ttk.Button(compras_window, 
                                      text="Reportat problema", 
                                      command=lambda: self.reportar_problema_compra(compras_window,
                                                                                    compras_pendientes,
                                                                                    listbox))
            btn_problema.pack(pady=10)
        else:
            print("No hay compras pendientes.")

    def marcar_compra_recibida(self, ventana, compras_pendientes, listbox):
        seleccionado = listbox.curselection()
        if seleccionado:
            for i, compra in enumerate(compras_pendientes):
                if i == int(seleccionado[0]):
                    id_articulo = compra.id
            
            self.dash_board.marcar_compra_recibida(id_articulo=id_articulo)
            
            messagebox.showinfo("Compra Recibida", f"La compra con ID {id_articulo} ha sido marcada como recibida.")
            # Actualizar la lista de compras pendientes en la ventana
            ventana.destroy()
            self.ver_compras_pendientes()
        else:
            messagebox.showwarning("Error", "Por favor, selecciona una compra.")

    def reportar_problema_compra(self, ventana, compras_pendientes, listbox):
        seleccionado = listbox.curselection()
        if seleccionado:
            for i, compra in enumerate(compras_pendientes):
                if i == int(seleccionado[0]):
                    id_articulo = compra.id
            
            self.dash_board.reportar_problema_compra(id_articulo=id_articulo)            

            messagebox.showinfo("Problema Reportado", f"Se ha reportado un problema con la compra ID {id_articulo}.")
            # Actualizar la lista de compras pendientes en la ventana
            ventana.destroy()
            self.ver_compras_pendientes()
        else:
            messagebox.showwarning("Error", "Por favor, selecciona una compra.")


    # Crear productos
    def crear_articulo(self):
        product_window = tk.Toplevel(self.wind)
        product_app = CA.CrearArticulo(product_window, self.dash_board.usuario.id)
        
    def abrir_dashboard(self, id):
        MostrarDashBoard(id)
