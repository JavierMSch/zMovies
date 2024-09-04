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
            System.out.println("3.- Menú de Análisis de Datos del VideoClub");
            System.out.println("4.- Salir\n");

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
                    menuAnalisisDatos();
                    break;
                case 4:
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
            System.out.println("1.- Rentar película(s)");
            System.out.println("2.- Devolución película");
            System.out.println("3.- Volver al menú principal");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    videoClub.rentarPelicula(input("Ingrese el rut del cliente: "), input("Ingrese el título de la película: "),
                            Integer.parseInt(input("Ingrese la cantidad de semanas a rentar: ")));
                    break;
                case 2:
                    videoClub.devolverPelicula(Integer.parseInt(input("Ingrese el id de la renta a devolver: ")));
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
            System.out.println("1.- Agregar Película");
            System.out.println("2.- Mostrar Películas");
            System.out.println("3.- Eliminar Película");
            System.out.println("4.- Mostrar detalles de una película");
            System.out.println("5.- Volver al menú de Bases de Datos");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    videoClub.agregarPelicula(input("Ingrese el título de la película: "), input("Ingrese el género de la película: "),
                            Integer.parseInt(input("Ingrese el precio semanal de la película: ")));
                    break;
                case 2:
                    // TODO: método de retornar el listado en VideoClub;
                    break;
                case 3:
                    // TODO eliminarPelicula();
                    break;
                case 4:
                    // TODO mostrarDetallePelicula();
                    break;
                case 5:
                    System.out.println("Volviendo al menú de Bases de Datos");
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    private void administrarClientes() {
        while (true) {
            System.out.println("Menú Administrar Clientes");
            System.out.println("1.- Agregar Cliente");
            System.out.println("2.- Mostrar Clientes");
            System.out.println("3.- Mostrar detalles de un cliente");
            System.out.println("4.- Volver al menú de Bases de Datos");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {
                case 1:
                    // TODO agregarCliente();
                    break;
                case 2:
                    // TODO mostrarClientes();
                    break;
                case 3:
                    // TODO mostrarDetalleCliente();
                    break;
                case 4:
                    System.out.println("Volviendo al menú de Bases de Datos");
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    private void menuAnalisisDatos() {
        while (true) {
            System.out.println("Menú de Análisis de Datos");
            System.out.println("1.- Pelicula mas rentada de los 3 meses");
            System.out.println("2.- Pelicula mas rentada de los 6 meses");
            System.out.println("3.- Pelicula menos rentada de los 3 meses");
            System.out.println("4.- Pelicula menos rentada de los 6 meses");
            System.out.println("5.- Cliente con mas rentas");
            System.out.println("6.- Cliente con mas dinero gastado");
            System.out.println("7.- Mes con mas rentas del ultimo año");
            System.out.println("8.- Volver al menú principal");

            int opcion = Integer.parseInt(input("Seleccione una opción: "));
            System.out.println();

            switch (opcion) {

                case 1:
                    // peliculasMasRentadas(3);
                    break;
                case 2:
                    // peliculasMasRentadas(6);
                    break;
                case 3:
                    // peliculasMenosRentadas(3);
                    break;
                case 4:
                    // peliculasMenosRentadas(6);
                    break;
                case 5:
                    // TODO clienteConMasRentas();
                    break;
                case 6:
                    // TODO clienteConMasDineroGastado();
                    break;
                case 7:
                    // TODO mesConMasRentasUltimoAño();
                    break;
                case 8:
                    System.out.println("Volviendo al menú principal");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
            System.out.println();
        }
    }

    private void rentarPeliculas() {
        while (true) {
            String input = input("Ingrese el rut del cliente o salir para salir: ");

            if (input.equalsIgnoreCase("salir")) {
                return;
            }

            if (!videoClub.existeCliente(input)) {
                System.out.println("Cliente no encontrado, debe ser agregado");
                videoClub.agregarCliente(input, input("Ingrese nombre y apellido: "), input("Ingrese correo: "),
                        input("Ingrese teléfono: "));
                System.out.println("Rentar películas:");
            } else {
                // videoClub.iniciarBoletaTemp(input);
                break;
            }
        }

        while (true) {
            peliculaABoleta();
            if (videoClub.cantidadPeliculasTemp() == 0) {
                System.out.println("No se ha ingresado ninguna película a la venta, volviendo al menú");
                // videoClub.limpiarBoletaTemp();
                return;
            }

            System.out.println(videoClub.cantidadPeliculasTemp() + " película(s) en la venta actual");
            String opcion = input("Agregar otra película? (si/no): ");
            if (opcion.equalsIgnoreCase("no")) {
                break;
            }
        }

        System.out.println(videoClub.detallesBoletaTemp());
        String opcion = input("Confirmar renta de película(s)? (si/no): ");
        if (opcion.equalsIgnoreCase("no")) {
            System.out.println("Renta de película(s) cancelada, volviendo al menú");
            return;
        }
        videoClub.confirmarBoletaTemp();
        System.out.println("Renta realizada, volvuendo al menú");
    }

    private void peliculaABoleta() {
        while (true) {
            String titulo = input("Ingrese nombre de película a rentar: ");

            if (!videoClub.existePelicula(titulo)) {
                System.out.println("Película no encontrada");
                String opcion = input("Presione enter para reintentar/ingresar otra película o ingrese volver para volver: ");

                if (opcion.equalsIgnoreCase("volver")) {
                    return;
                }
            } else if (!videoClub.existePelicula(titulo)) {
                System.out.println("Película sin stock");
                String opcion = input("Presione enter para ingresar otra película o ingrese volver para volver: ");

                if (opcion.equalsIgnoreCase("volver")) {
                    return;
                }
            } else {
                //TODO: manejar entrada no válida????
                int semanas = Integer.parseInt(input("Semanas a rentar (número mayor a 0): "));
                videoClub.agregarPeliculaTemp(titulo, semanas);
                System.out.println("Película agregada a venta");
            }
        }
    }
}

/*
public void menuClientes() {
    while (true) {
        int opcion = 0;
        System.out.println("1. Mostrar Clientes");
        System.out.println("2. Mostrar detalles de un cliente");
        System.out.println("3. Menu Estadisticas Clientes");
        System.out.println("4. Recomendar peliculas");
        System.out.println("5. Volver");
        opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1:
                videoClub.mostrarClientes();
                break;
            case 2:
                videoClub.mostrarDetalleCliente();
                break;
            case 3:
                menuEstadisticasClientes();
                break;
            case 4:
                videoClub.recomendarPeliculas();
                break;
            case 5:
                start();
                break;
        }
    }
}

public void menuEstadisticasClientes() {
    while (true) {
        int opcion = 0;
        System.out.println("1. Clientes con mas rentas");
        System.out.println("2. Clientes con menos rentas");
        System.out.println("3. Volver");
        opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1:
                videoClub.clientesConMasRentas();
                break;
            case 2:
                videoClub.clientesConMenosRentas();
                break;
            case 3:
                menuClientes();
                break;
        }
    }
}

public void menuEstadisticasPeliculas() {
    while (true) {
        int opcion = 0;
        System.out.println("1. Peliculas mas rentadas");
        System.out.println("2. Peliculas menos rentadas");
        System.out.println("3. Peliculas mas populares");
        System.out.println("4. Peliculas menos populares");
        System.out.println("5. Volver");
        opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1:
                videoClub.peliculasMasRentadas();
                break;
            case 2:
                videoClub.peliculasMenosRentadas();
                break;
            case 3:
                videoClub.peliculasMasPopulares();
                break;
            case 4:
                videoClub.peliculasMenosPopulares();
                break;
            case 5:
                menuPelicula();
                break;
        }

public void menuPelicula() {
    while (true) {
        int opcion = 0;
        System.out.println("1. Agregar Pelicula");
        System.out.println("2. Mostrar Peliculas");
        System.out.println("3. Mostrar detalles de una pelicula");
        System.out.println("4. Menu Estadisticas Generales");
        System.out.println("5. Volver");
        opcion = scanner.nextInt();
        scanner.nextLine();
    }
}

public void menuRentar() {
    while (true) {
        int opcion = 0;
        System.out.println("1. Rentar Pelicula");
        System.out.println("2. Devolver Pelicula");
        System.out.println("3. Volver");
        opcion = scanner.nextInt();
        scanner.nextLine();
    }
}
}


*/
