import java.util.Scanner;

public class InterfazUsuario {
    private Scanner scanner;
    private VideoClub videoClub;

    public InterfazUsuario(Scanner scanner, VideoClub videoClub) {
        this.scanner = scanner;
        this.videoClub = videoClub;
    }

    public void start() {
        menuPrincipal();
    }

    private String input(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

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
                    System.out.println("Hasta luego");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
            System.out.println();
        }
    }

    private void menuRentas() {
        while (true) {
            System.out.println("Menú Rentas");
            System.out.println("1.- Rentar película");
            System.out.println("2.- Devolver película");
            System.out.println("3.- Recomendar película");
            System.out.println("4.- Listar películas pendientes a devolver");
            System.out.println("5.- Película más rentada por cada género");
            System.out.println("6.- Volver al menú principal");

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
                    listarMasRentadasPorGenero();
                    break;
                case 6:
                    System.out.println("Volviendo al menú principal");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
            System.out.println();
        }
    }

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

    private void administrarPeliculas() {
        while (true) {
            System.out.println("Menú Administrar Películas");
            System.out.println("1.- Agregar película");
            System.out.println("2.- Listar películas");
            System.out.println("3.- Mostrar detalles de una película");
            System.out.println("4.- Agregar género");
            System.out.println("5.- Listar géneros");
            System.out.println("6.- Editar datos película");
            System.out.println("7.- Eliminar película");
            System.out.println("8.- Editar género");
            System.out.println("9.- Eliminar género");
            System.out.println("10.- Volver al menú de Bases de Datos");

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
                    editarPelicula();
                    break;
                case 7:
                    eliminarPelicula();
                    break;
                case 8:
                    editarGenero();
                    break;
                case 9:
                    eliminarGenero();
                    break;
                case 10:
                    System.out.println("Volviendo al menú de Bases de Datos");
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            System.out.println();
        }
    }

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

    private void rentarPelicula() {
        String rut;
        while (true) {
            String input = input("Ingrese el rut del cliente (ej.: 12.345.678-9) o salir para salir: ");

            if (input.equalsIgnoreCase("salir")) {
                return;
            }

            if (!videoClub.existeCliente(input)) {
                System.out.println("Cliente no encontrado, debe ser agregado");
                agregarCliente();
                System.out.println("\nRentar película:");
            } else {
                rut = input;
                break;
            }
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

    private void listarMasRentadasPorGenero() {
        String listado = videoClub.obtenerListaMasRentadaGenero();

        if (listado == null) {
            System.out.println("No existen rentas en el sistema, volviendo al menú");
        }

        System.out.println("Películas más rentadas por cada género:");
        System.out.println(listado);
    }

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

    private void listarPeliculas() {
        String input = input("Ingrese género, 'todas' para listar todas las películas o salir para salir: ");

        if (input.equalsIgnoreCase("salir")) {
            return;
        }

        if (input.equalsIgnoreCase("todas")) {
            String peliculas = videoClub.obtenerListaPeliculas();
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

        String peliculas = videoClub.obtenerListaPeliculas(input);
        if (peliculas == null) {
            System.out.println("No hay películas del género en el sistema");
        } else {
            System.out.println(peliculas);
        }
    }

    private void detallesPelicula() {
        String titulo = input("Ingrese título: ");

        if (!videoClub.existePelicula(titulo)) {
            System.out.println("Película no se encuentra en el sistema, volviendo al menú");
            return;
        }

        System.out.println(videoClub.detallesPelicula(titulo));
    }

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

    private void listarGeneros() {
        String generos = videoClub.obtenerListaGeneros();
        if (generos == null) {
            System.out.println("No hay géneros en el sistema");
        } else {
            System.out.println(generos);
        }
    }

    private void editarPelicula() {
        String titulo = input("Ingrese título: ");

        if (!videoClub.existePelicula(titulo)) {
            System.out.println("Película no se encuentra en el sistema, volviendo al menú");
            return;
        }

        System.out.println(videoClub.detallesPelicula(titulo));

        String nuevoTitulo;
        while (true) {
            nuevoTitulo = input("Ingrese nuevo título o enter para mantener el actual: ");
            if (nuevoTitulo.isEmpty() || !videoClub.existePelicula(nuevoTitulo)) {
                break;
            }
            System.out.println("Ya existe película con ese título, debe ingresar otro");
        }

        String nuevoGenero = input("Ingrese nuevo género o enter para mantener el actual: ");
        if (!nuevoGenero.isEmpty() && !videoClub.existeGenero(nuevoGenero)) {
            System.out.println("Género no existe en el sistema, se creará");
        }

        String precioStr = input("Ingrese nuevo precio semanal (valor entero mayor a 0) o enter para mantener el actual: ");
        int nuevoPrecio = -1;
        if (!precioStr.isEmpty()) {
            nuevoPrecio = Integer.parseInt(precioStr);
        }

        videoClub.editarPelicula(titulo, nuevoTitulo, nuevoGenero, nuevoPrecio);
        System.out.println("Película editada exitosamente");
    }

    private void eliminarPelicula() {
        String titulo = input("Ingrese el título de la película a eliminar: ");

        if (!videoClub.existePelicula(titulo)) {
            System.out.println("Película no se encuentra en el sistema, volviendo al menú");
            return;
        }

        if (videoClub.peliculaTieneRentasActivas(titulo)) {
            System.out.println("Película tiene rentas sin devolver, aún no se puede eliminar, volviendo al menú");
            return;
        }

        String confirmacion = input("¿Está seguro de que desea eliminar la película '" + titulo + "'? (si/no): ");
        if (confirmacion.equalsIgnoreCase("si")) {
            videoClub.eliminarPelicula(titulo);
            System.out.println("Película eliminada exitosamente");
        } else {
            System.out.println("Eliminación cancelada");
        }
    }

    private void editarGenero() {
        String genero = input("Ingrese género a editar: ");

        if (!videoClub.existeGenero(genero)) {
            System.out.println("Género no se encuentra en el sistema, volviendo al menú");
            return;
        }

        String nuevo = input("Ingrese nuevo nombre de género: ");
        if (videoClub.existeGenero(nuevo)) {
            System.out.println("Género ya existe en el sistema, volviendo al menú");
            return;
        }

        if (nuevo == null || nuevo.isEmpty()) {
            System.out.println("Nombre no válido, volviendo al menú");
            return;
        }

        videoClub.editarGenero(genero, nuevo);
        System.out.println("Género editado exitosamente");
    }

    private void eliminarGenero() {
        String nombre = input("Ingrese el nombre del género a eliminar: ");

        if (!videoClub.existeGenero(nombre)) {
            System.out.println("Género no se encuentra en el sistema, volviendo al menú");
            return;
        }

        System.out.println("Las películas pertenecientes al género se moverán al género \"SIN GENERO\"");
        String confirmacion = input("¿Está seguro de que desea eliminar el género '" + nombre + "'? (si/no): ");
        if (confirmacion.equalsIgnoreCase("si")) {
            videoClub.eliminarGenero(nombre);
            System.out.println("Genero eliminado exitosamente");
        } else {
            System.out.println("Eliminación cancelada");
        }
    }

    private void detallesCliente() {
        String rut = input("Ingrese rut (ej.: 12.345.678-9): ");

        if (!videoClub.existeCliente(rut)) {
            System.out.println("Cliente no encontrado, volviendo al menú");
            return;
        }

        System.out.println(videoClub.detallesCliente(rut));
    }

    private void agregarCliente() {
        System.out.println("Agregar cliente:");
        String rut = input("Ingrese rut (ej.: 12.345.678-9): ");

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