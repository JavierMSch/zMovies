package org.example.zmovies.Vistas;

import org.example.zmovies.Modelos.VideoClub;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que representa la interfaz de usuario por consola.
 * Permite interactuar con el sistema de VideoClub.
 */
public class InterfazUsuarioConsola {
    private final Scanner scanner;
    private final VideoClub videoClub;
    // Atributos no deben modificarse, por lo que no tiene getters y setters.

    /**
     * Constructor de la clase.
     * @param scanner Scanner para leer datos de la consola.
     * @param videoClub VideoClub para interactuar con el sistema.
     */
    public InterfazUsuarioConsola(Scanner scanner, VideoClub videoClub) {
        this.scanner = scanner;
        this.videoClub = videoClub;
    }

    /**
     * Inicia la interfaz de usuario.
     */
    public void start() {
        try {
            videoClub.start();
        } catch (Exception e) {
            System.out.println("Error al iniciar el sistema");
            e.printStackTrace();
            return;
        }
        menuPrincipal();
    }

    /**
     * Lee datos de la consola.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return String con la respuesta del usuario.
     */
    private String input(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Muestra el menú principal.
     */
    private void menuPrincipal() {
        while (true) {
            System.out.println("Menú principal");
            System.out.println("1.- Menú Rentas");
            System.out.println("2.- Menú Administración de Bases de Datos");
            System.out.println("3.- Salir\n");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    menuRentas();
                    break;
                case 2:
                    menuBasesDatos();
                    break;
                case 3:
                    try {
                        videoClub.insertarDatos();
                    } catch (Exception e) {
                        System.out.println("Error al guardar datos");
                        e.printStackTrace();
                    }
                    System.out.println("Hasta luego");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
            System.out.println();
        }
    }

    /**
     * Muestra el menú de Rentas.
     */
    private void menuRentas() {
        while (true) {
            System.out.println("Menú Rentas");
            System.out.println("1.- Rentar película");
            System.out.println("2.- Devolver película");
            System.out.println("3.- Recomendar película");
            System.out.println("4.- Listar películas pendientes a devolver");
            System.out.println("5.- Volver al menú principal");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    rentarPelicula();
                    break;
                case 2:
                    devolverPelicula();
                    break;
                case 3:
                    recomendarPelicula();
                    break;
                case 4:
                    listarPendientes();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
            System.out.println();
        }
    }

    /**
     * Muestra el menú de Bases de Datos.
     */
    private void menuBasesDatos() {
        while (true) {
            System.out.println("Menú Bases de Datos");
            System.out.println("1.- Administrar Películas");
            System.out.println("2.- Administrar Clientes");
            System.out.println("3.- Volver al menú principal");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    administrarPeliculas();
                    break;
                case 2:
                    administrarClientes();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
            System.out.println();
        }
    }

    /**
     * Muestra el menú de Administrar Películas.
     */
    private void administrarPeliculas() {
        while (true) {
            System.out.println("Menú Administrar Películas");
            System.out.println("1.- Agregar película");
            System.out.println("2.- Listar películas");
            System.out.println("3.- Mostrar detalles de una película");
            System.out.println("4.- Agregar género");
            System.out.println("5.- Listar géneros");
            System.out.println("6.- Volver al menú de Bases de Datos");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    agregarPelicula();
                    break;
                case 2:
                    listarPeliculas();
                    break;
                case 3:
                    detallesPelicula();
                    break;
                case 4:
                    agregarGenero();
                    break;
                case 5:
                    listarGeneros();
                    break;
                case 6:
                    System.out.println("Volviendo al menú de Bases de Datos");
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            System.out.println();
        }
    }

    /**
     * Muestra el menú de Administrar Clientes.
     */
    private void administrarClientes() {
        while (true) {
            System.out.println("Menú Administrar Clientes");
            System.out.println("1.- Agregar Cliente");
            System.out.println("2.- Mostrar detalles de cliente");
            System.out.println("3.- Volver al menú de Bases de Datos");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    detallesCliente();
                    break;
                case 3:
                    System.out.println("Volviendo al menú de Bases de Datos");
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            System.out.println();
        }
    }

    /**
     * Permite rentar una película a través de la interfaz.
     */
    private void rentarPelicula() {
        String rut = input("Ingrese el rut del cliente (ej.: 12.345.678-9) o salir para salir: ");

        if (rut.equalsIgnoreCase("salir")) {
            return;
        }

        if (!videoClub.existeCliente(rut)) {
            System.out.println("Cliente no encontrado, debe ser agregado");
            agregarCliente();
            System.out.println();
        }

        String titulo;
        while (true) {
            String input = input("Ingrese nombre de película a rentar: ");

            if (videoClub.existePelicula(input)) {
                titulo = input;
                break;
            } else {
                System.out.println("Película no encontrada");
                String opcion = input("Presione enter para reintentar/ingresar otra película o salir para salir: ");

                if (opcion.equalsIgnoreCase("salir")) {
                    return;
                }
            }
        }

        System.out.println("La película: " + titulo + " tiene un precio semanal de $" + videoClub.precioPelicula(titulo));
        int semanas = Integer.parseInt(input("Semanas a rentar (número mayor a 0): "));

        System.out.println("Se rentará la película: " + titulo + " por " + semanas +
                " semana(s) por un precio total de $" + videoClub.precioPelicula(titulo, semanas));
        String confirmar = input("Confirmar (si/no): ");

        if (confirmar.equalsIgnoreCase("si") || confirmar.equalsIgnoreCase("sí")) {
            videoClub.rentarPelicula(rut, titulo, semanas);
            System.out.println("Película fue rentada");
        } else {
            System.out.println("Renta cancelada, volviendo al menú");
        }

        System.out.println("Película agregada a venta");
    }

    /**
     * Permite devolver una película a través de la interfaz.
     */
    private void devolverPelicula() {
        String rut = input("Ingrese el rut del cliente (ej.: 12.345.678-9) o salir para salir: ");

        if (rut.equalsIgnoreCase("salir")) {
            return;
        }
        if (!videoClub.existeCliente(rut)) {
            System.out.println("Cliente no encontrado, volviendo al menú");
            return;
        }

        String pendientes = videoClub.obtenerListaRentasPendientes(rut);
        if (pendientes == null) {
            System.out.println("Cliente no posee películas pendientes a devolver");
            return;
        }

        System.out.println("Películas pendientes a devolver:");
        System.out.println(pendientes);

        int id = Integer.parseInt(input("Ingrese ID de renta a devolver (valores numéricos dentro de la lista): "));

        videoClub.devolverPelicula(id);
        System.out.println("Devolución realizada");
    }

    /**
     * Permite recomendar una película a un cliente a través de la interfaz.
     */
    private void recomendarPelicula() {
        String rut = input("Ingrese el rut del cliente (ej.: 12.345.678-9) o salir para salir: ");

        if (rut.equalsIgnoreCase("salir")) {
            return;
        }
        if (!videoClub.existeCliente(rut)) {
            System.out.println("Cliente no encontrado, volviendo al menú");
            return;
        }

        String pelicula = videoClub.recomendarPelicula(rut);
        if (pelicula == null) {
            System.out.println("No ha sido posible recomendar una película al cliente");
            return;
        }

        System.out.println("Película recomendada: " + pelicula);
    }

    /**
     * Permite listar las películas pendientes a devolver a través de la interfaz.
     */
    private void listarPendientes() {
        String input = input("Ingrese el rut del cliente (ej.: 12.345.678-9), 'todas' para listar todas las pendientes o salir para salir: ");

        if (input.equalsIgnoreCase("salir")) {
            return;
        }

        if (input.equalsIgnoreCase("todas")) {
            String pendientes = videoClub.obtenerListaRentasPendientes();
            if (pendientes == null) {
                System.out.println("No hay películas pendientes a devolver");
            } else {
                System.out.println(pendientes);
            }
            return;
        }

        if (!videoClub.existeCliente(input)) {
            System.out.println("Cliente no encontrado, volviendo al menú");
            return;
        }

        String pendientes = videoClub.obtenerListaRentasPendientes(input);
        if (pendientes == null) {
            System.out.println("Cliente no posee películas pendientes a devolver");
        } else {
            System.out.println(pendientes);
        }
    }

    /**
     * Permite agregar una película a través de la interfaz.
     */
    private void agregarPelicula() {
        String titulo = input("Ingrese el título de la película: ");

        if (videoClub.existePelicula(titulo)) {
            System.out.println("Película ya existe en el sistema, volviendo al menú");
            return;
        }

        String nombreGenero;
        while (true) {
            String input = input("Ingrese el nombre del género o salir para salir: ");

            if (input.equalsIgnoreCase("salir")) {
                return;
            }

            if (!videoClub.existeGenero(input)) {
                System.out.println("Género no encontrado, debe ser agregado");
                agregarGenero();
                System.out.println("\nAgregar película " + titulo + ":");
            } else {
                nombreGenero = input;
                break;
            }
        }

        int precioSemanal = Integer.parseInt(input("Ingrese precio semanal de la película (valor numérico): "));

        videoClub.agregarPelicula(titulo, nombreGenero, precioSemanal);
        System.out.println("Película agregada al sistema");
    }

    /**
     * Permite listar las películas a través de la interfaz.
     */
    private void listarPeliculas() {
        String input = input("Ingrese género, 'todas' para listar todas las películas o salir para salir: ");

        if (input.equalsIgnoreCase("salir")) {
            return;
        }

        if (input.equalsIgnoreCase("todas")) {
            String peliculas = videoClub.obtenerStringPeliculas();
            if (peliculas == null) {
                System.out.println("No hay películas en el sistema");
            } else {
                System.out.println(peliculas);
            }
            return;
        }

        if (!videoClub.existeGenero(input)) {
            System.out.println("Género no existe en el sistema, volviendo al menú");
            return;
        }

        String peliculas = videoClub.obtenerStringPeliculasGenero(input);
        if (peliculas == null) {
            System.out.println("No hay películas del género en el sistema");
        } else {
            System.out.println(peliculas);
        }
    }

    /**
     * Permite ver los detalles de una película a través de la interfaz.
     */
    private void detallesPelicula() {
        String titulo = input("Ingrese título: ");

        if (!videoClub.existePelicula(titulo)) {
            System.out.println("Película no se encuentra en el sistema, volviendo al menú");
            return;
        }

        System.out.println(videoClub.detallesPelicula(titulo));
    }

    /**
     * Permite ver los detalles de un cliente a través de la interfaz.
     */
    private void agregarGenero() {
        System.out.println("Agregar género:");
        String nombreGenero = input("Ingrese nombre para el género (sin tildes): ");
        if (videoClub.existeGenero(nombreGenero)) {
            System.out.println("Género ya registrado en el sistema, volviendo al menú");
            return;
        }

        videoClub.agregarGenero(nombreGenero);
        System.out.println("Género agregado al sistema");
    }

    /**
     * Permite listar los géneros a través de la interfaz.
     */
    private void listarGeneros() {
        List<String> generos = videoClub.obtenerListaStringGeneros();
        if (generos.isEmpty()) {
            System.out.println("No hay géneros en el sistema");
        } else {
            for (String genero : generos) {
                System.out.println(genero);
            }
        }
    }

    /**
     * Permite ver los detalles de un cliente a través de la interfaz.
     */
    private void detallesCliente() {
        String rut = input("Ingrese rut (ej.: 12.345.678-9): ");

        if (!videoClub.existeCliente(rut)) {
            System.out.println("Cliente no encontrado, volviendo al menú");
            return;
        }

        System.out.println(videoClub.detallesCliente(rut));
    }

    /**
     * Permite agregar un cliente a través de la interfaz.
     */
    private void agregarCliente() {
        System.out.println("Agregar cliente:");
        String rut = input("Ingrese rut del nuevo cliente (ej.: 12.345.678-9): ");

        if (videoClub.existeCliente(rut)) {
            System.out.println("Cliente ya registrado, volviendo");
            return;
        }

        String nombre = input("Ingrese nombre completo: ");

        String correo;
        while (true) {
            String input = input("Ingrese correo: ");
            if (input.contains("@")) {
                correo = input;
                break;
            } else {
                System.out.println("Correo no válido, ingrese otro:");
            }
        }

        String telefono = input("Ingrese número de teléfono: ");

        videoClub.agregarCliente(rut, nombre, correo, telefono);
        System.out.println("Cliente agregado al sistema");
    }
}
