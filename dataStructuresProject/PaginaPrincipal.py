import tkinter as tk
from tkinter import ttk, messagebox
from ttkthemes import ThemedTk
from PIL import ImageTk, Image
import MostrarDashBoard as Dbm
import Articulo
import Usuario
import os
import csv
import ArbolAVL as AVL
import DashBoard as DBoard
import PP as PP


class PaginaPrincipalGUI:
    def __init__(self, id_usuario: int):
        self.id_usuario = id_usuario
        self.dashboard = DBoard.DashBoard(id_usuario)
        self.pagina_principal = PP.PaginaPrincipal(id_usuario)
        self.arbol = self.pagina_principal.arbol_articulos
        self.usuario = self.pagina_principal.usuario
        self.id_actual = 0
        self.foto = None

        self.root = ThemedTk(theme="radiance")
        self.root.title("Página Principal")

        # Configurar estilo para el frame principal
        style = ttk.Style(self.root)
        style.configure("White.TFrame", background="white")

        # Frame principal
        main_frame = ttk.Frame(self.root, style="White.TFrame", padding=10)
        main_frame.pack()

        # Frame para la imagen del producto
        imagen_frame = ttk.LabelFrame(main_frame, text="Imagen del producto")
        imagen_frame.pack(side="left", padx=10)

        # Etiqueta para la foto del artículo
        self.foto_label = tk.Label(imagen_frame)
        self.foto_label.pack()

        # Etiqueta para la información del artículo
        self.info_label = tk.Label(main_frame, font=("Arial", 12))
        self.info_label.pack(pady=10)

        # Contenedor para los botones
        botones_frame = ttk.LabelFrame(main_frame, text="Opciones", padding=10)
        botones_frame.pack(pady=5)

        # Botón para comprar
        self.comprar_button = ttk.Button(botones_frame, text="Comprar", command=self.comprar_articulo)
        self.comprar_button.pack(side="left", padx=5)

        # Botón para ver el siguiente artículo
        self.siguiente_button = ttk.Button(botones_frame, text="Siguiente", command=self.ver_siguiente_articulo)
        self.siguiente_button.pack(side="left", padx=5)

        # Botón para ver el carrito
        self.carrito_button = ttk.Button(botones_frame, text="Ver Carrito", command=self.ver_carrito)
        self.carrito_button.pack(side="left", padx=5)

        # Botón para volver al menú principal
        boton_volver = ttk.Button(botones_frame, text="Volver al menú principal",
                                  command=lambda: self.volver_al_menu_principal(self.root))
        boton_volver.pack(side="left", padx=5)

        # Eventos de teclado para activar los botones
        self.root.bind('a', lambda event: self.comprar_articulo())
        self.root.bind('d', lambda event: self.ver_siguiente_articulo())

        self.mostrar_articulo_actual()

        self.root.mainloop()


    def volver_al_menu_principal(self, ventana):
        ventana.destroy()
        self.dbm = Dbm.MostrarDashBoard(self.id_usuario)

    def mostrar_articulo_actual(self):
        articulo = self.arbol.buscar(self.id_actual)

        if articulo:
            if articulo.objeto.vendedor == self.obtener_usuario_por_id(self.id_usuario) or articulo.objeto.activo == False:
                self.ver_siguiente_articulo()
            else:
                ruta_foto = os.path.join('imagenes', articulo.objeto.foto)
                self.mostrar_foto(ruta_foto)
                self.info_label.config(
                    text=f"Nombre: {articulo.objeto.nombre}\nPrecio: {articulo.objeto.precio}\nVendedor: {articulo.objeto.vendedor}")
        else:
            self.id_actual = 0
            self.mostrar_articulo_actual()

    def mostrar_foto(self, ruta_foto):
        if ruta_foto:
            imagen = Image.open(ruta_foto)
            imagen = imagen.resize((200, 200), Image.ANTIALIAS)
            foto = ImageTk.PhotoImage(image=imagen, master=self.foto_label)
            self.foto_label.config(image=foto)
            self.foto_label.image = foto
        else:
            self.foto_label.config(image=None)

    def comprar_articulo(self):
        articulo = self.arbol.buscar(self.id_actual)
        if articulo:
            self.arbol.agregar_al_carrito(self.id_actual)
            self.modificar_comprador(self.id_usuario, self.id_actual)
            self.ver_siguiente_articulo()

    def modificar_comprador(self, id_usuario: int, id_articulo: int):
        articulo = self.arbol.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.comprador = self.obtener_usuario_por_id(id_usuario)
        self.dashboard.guardar_modificacion_articulo(articulo=articulo)

    def obtener_usuario_por_id(self, id_usuario: int) -> str:
        directorio = 'database/usuarios.csv'
        with open(directorio, 'r') as archivo_csv:
            reader = csv.reader(archivo_csv)
            for fila in reader:
                if fila[0] == str(id_usuario):
                    return fila[1]
        return None

    def ver_siguiente_articulo(self):
        self.id_actual += 1
        self.mostrar_articulo_actual()

    def cargar_cosas_carrito(self):
        filtro = {'comprador': self.usuario.usuario, 'carrito': True, 'activo': True}
        self.arbol.buscar_arbol_filtro(filtro)
        return self.arbol.lista_busqueda

    def marcar_compra(self, id_articulo: int):
        articulo = self.arbol.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.activo = False
        articulo.carrito = False
        self.dashboard.guardar_modificacion_articulo(articulo=articulo)

    def eliminar_articulo(self, id_articulo: int):
        articulo = self.arbol.buscar(valor=id_articulo)
        articulo = articulo.objeto
        articulo.carrito = False
        articulo.comprador = None
        self.dashboard.guardar_modificacion_articulo(articulo=articulo)

    def ver_carrito(self):
        cosas_carrito = self.cargar_cosas_carrito()

        if cosas_carrito:
            # Crear una nueva ventana para mostrar las ventas pendientes
            carrito_window = tk.Toplevel(self.root)
            carrito_window.title('Carrito')

            # Etiqueta de título
            title_label = tk.Label(carrito_window, text="Carrito de compras", font=("Arial", 16, "bold"))
            title_label.pack(pady=10)

            # Marco para la lista de ventas
            frame = tk.LabelFrame(carrito_window, text='Lista de Articulos')
            frame.pack(padx=20, pady=10)

            # Crear un widget de desplazamiento
            scrollbar = tk.Scrollbar(frame)
            scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

            # Crear una lista para mostrar las ventas pendientes
            listbox = tk.Listbox(frame, width=50, yscrollcommand=scrollbar.set)
            listbox.pack()

            # Agregar las ventas pendientes a la lista
            for articulo in cosas_carrito:
                articulo_str = f"ID: {articulo.id} - Producto: {articulo.nombre} - Precio: {articulo.precio}"
                listbox.insert(tk.END, articulo_str)

            # Configurar el desplazamiento de la lista
            scrollbar.config(command=listbox.yview)

            # Botones Recibido y Problema
            btn_comprar = ttk.Button(carrito_window, text="Comprar artículo",
                                     command=lambda: self.compra_realizada(carrito_window, cosas_carrito, listbox))
            btn_comprar.pack(pady=10)

            btn_eliminar = ttk.Button(carrito_window, text="Eliminar artículo",
                                      command=lambda: self.articulo_eliminado(carrito_window, cosas_carrito, listbox))
            btn_eliminar.pack(pady=10)
        else:
            print("No hay cosas en el carrito.")

    def compra_realizada(self, ventana, cosas_carrito, listbox):
        seleccionado = listbox.curselection()
        if seleccionado:
            for i, articulo in enumerate(cosas_carrito):
                if i == int(seleccionado[0]):
                    id_articulo = articulo.id

            self.marcar_compra(id_articulo=id_articulo)

            messagebox.showinfo("Éxito", f"Se compró el artículo")
            # Actualizar la lista de ventas pendientes en la ventana
            ventana.destroy()
            self.ver_carrito()
        else:
            messagebox.showwarning("Error", "Por favor, selecciona un artículo.")

    def articulo_eliminado(self, ventana, cosas_carrito, listbox):
        seleccionado = listbox.curselection()
        if seleccionado:
            for i, articulo in enumerate(cosas_carrito):
                if i == int(seleccionado[0]):
                    id_articulo = articulo.id

            self.eliminar_articulo(id_articulo=id_articulo)
            messagebox.showinfo("Éxito", f"Artículo eliminado del carrito")
            # Actualizar la lista de ventas pendientes en la ventana
            ventana.destroy()
            self.ver_carrito()
        else:
            messagebox.showwarning("Error", "Por favor, selecciona un artículo.")
