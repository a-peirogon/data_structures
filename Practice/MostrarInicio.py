import tkinter as tk
from tkinter import messagebox
from tkinter import ttk
from ttkthemes import ThemedTk
from PIL import Image, ImageTk
import Inicio

class Ventana:
    def __init__(self):
        self.inicio = Inicio.Inicio()
        self.inicio.cargar_usuarios()
        self.usuario = None

    def volver_al_menu_principal(self, ventana):
        ventana.destroy()
        self.crear_ventana_principal()

    def mostrar_log_in(self):
        root.destroy()

        # Crear la ventana principal
        root3 = ThemedTk(theme="radiance")  # Selecciona un tema de ttkthemes
        root3.title("Iniciar sesión")
        root3.configure(background="white")  # Establecer el fondo blanco

        # Crear las etiquetas y campos de entrada para la entrada del usuario
        usuario_label = ttk.Label(root3, text="Usuario:")
        usuario_entry = ttk.Entry(root3, style="Custom.TEntry")
        usuario_entry.insert(0, "Ingrese su usuario")  # Placeholder

        contraseña_label = ttk.Label(root3, text="Contraseña:")
        contraseña_entry = ttk.Entry(root3, show="*", style="Custom.TEntry")
        contraseña_entry.insert(0, "Ingrese su contraseña")  # Placeholder

        # Estilo personalizado para el campo de entrada
        style = ttk.Style()
        style.configure("Custom.TEntry", fieldbackground="white", borderwidth=1, relief="solid", bordercolor="#a0a0a0", padding=5)
        style.map("Custom.TEntry", fieldbackground=[("focus", "white")])

        # Crear el botón de inicio de sesión
        inicio_sesion_button = ttk.Button(root3, text="Iniciar sesión",
                                          command=lambda: self.iniciar_sesion(usuario_entry.get(),
                                                                              contraseña_entry.get(),
                                                                              root3))

        # Botón para volver al menú principal
        boton_volver = ttk.Button(root3, text="Volver al menú principal",
                                  command=lambda: self.volver_al_menu_principal(root3))

        # Colocar los widgets usando el diseño de cuadrícula con un espacio reducido
        usuario_label.grid(row=0, column=0, padx=5, pady=5)
        usuario_entry.grid(row=0, column=1, padx=5, pady=5)
        contraseña_label.grid(row=1, column=0, padx=5, pady=5)
        contraseña_entry.grid(row=1, column=1, padx=5, pady=5)
        inicio_sesion_button.grid(row=2, columnspan=2, padx=5, pady=5)
        boton_volver.grid(row=6, columnspan=2, padx=5, pady=5)

        root3.mainloop()


    def iniciar_sesion(self, usuario, contraseña, ventana):
        ventana.destroy()
        self.usuario = usuario
        self.inicio.log_in(usuario, contraseña)

    def mostrar_sing_up(self):
        root.destroy()

        # Crear la ventana principal
        root2 = ThemedTk(theme="radiance")  # Selecciona un tema de ttkthemes
        root2.title("Registrarse")
        root2.configure(background="white")  # Establecer el fondo blanco

        # Crear las etiquetas y campos de entrada para la entrada del usuario
        usuario_label = ttk.Label(root2, text="Usuario:")
        usuario_entry = ttk.Entry(root2)
        contraseña_label = ttk.Label(root2, text="Contraseña:")
        contraseña_entry = ttk.Entry(root2, show="*")
        apellidos_label = ttk.Label(root2, text="Apellidos:")
        apellidos_entry = ttk.Entry(root2)
        nombres_label = ttk.Label(root2, text="Nombres:")
        nombres_entry = ttk.Entry(root2)
        edad_label = ttk.Label(root2, text="Edad:")
        edad_entry = ttk.Entry(root2)

        # Crear el botón de registro
        sign_up_button = ttk.Button(root2, text="Registrarse",
                                    command=lambda: self.inicio.sing_up(usuario_entry.get(),
                                                                        contraseña_entry.get(),
                                                                        apellidos_entry.get(),
                                                                        nombres_entry.get(),
                                                                        edad_entry.get()))
        # Botón para volver al menú principal
        boton_volver = ttk.Button(root2, text="Volver al menú principal",
                                  command=lambda: self.volver_al_menu_principal(root2))

        # Colocar los widgets usando el diseño de cuadrícula con un espacio reducido
        usuario_label.grid(row=0, column=0, padx=5, pady=5)
        usuario_entry.grid(row=0, column=1, padx=5, pady=5)
        contraseña_label.grid(row=1, column=0, padx=5, pady=5)
        contraseña_entry.grid(row=1, column=1, padx=5, pady=5)
        apellidos_label.grid(row=2, column=0, padx=5, pady=5)
        apellidos_entry.grid(row=2, column=1, padx=5, pady=5)
        nombres_label.grid(row=3, column=0, padx=5, pady=5)
        nombres_entry.grid(row=3, column=1, padx=5, pady=5)
        edad_label.grid(row=4, column=0, padx=5, pady=5)
        edad_entry.grid(row=4, column=1, padx=5, pady=5)
        sign_up_button.grid(row=5, columnspan=2, padx=5, pady=5)
        boton_volver.grid(row=6, columnspan=2, padx=5, pady=5)

        root2.mainloop()


    def crear_ventana_principal(self):
        global root

        # Crear la ventana principal
        root = ThemedTk(theme="radiance")  # Selecciona un tema de ttkthemes
        root.geometry("400x350")  # Aumenta la altura de la ventana para acomodar el logo más grande
        root.title("Inicio")
        root.configure(background="white")  # Establecer el fondo blanco

        # Cargar la imagen del logo
        logo_image = Image.open("logo.png")
        logo_image = logo_image.resize((250, 250))  # Ajusta el tamaño de la imagen
        logo_photo = ImageTk.PhotoImage(logo_image)

        # Crear el widget de la imagen del logo
        logo_label = ttk.Label(root, image=logo_photo, background="white")
        logo_label.pack(pady=1)  # Aumenta el valor de pady para dar espacio entre el logo y los botones

        # Crear los botones para las opciones de inicio de sesión
        iniciar_sesion_button = ttk.Button(root, text="Iniciar sesión", command=self.mostrar_log_in)
        registrarse_button = ttk.Button(root, text="Registrarse", command=self.mostrar_sing_up)

        # Colocar los widgets usando el diseño de cuadrícula
        iniciar_sesion_button.pack(padx=20, pady=5)  # Ajusta los valores de padx y pady para reducir el espacio
        registrarse_button.pack(padx=20, pady=5)  # Ajusta los valores de padx y pady para reducir el espacio

        root.mainloop()
