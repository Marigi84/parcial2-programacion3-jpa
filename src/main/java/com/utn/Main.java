package com.utn;

import com.utn.dtos.ProductoReporteDTO;
import com.utn.repository.CategoriaRepository;
import com.utn.repository.ProductoRepository;
import com.utn.util.JPAUtil;
import com.utn.entities.Categoria;
import com.utn.entities.Producto;

import java.util.Comparator;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepository = new CategoriaRepository();
    private static final ProductoRepository productoRepository = new ProductoRepository();

    public static void main(String[] args) {

        cargarDatosInicialesSiNoExisten();

        menuPrincipal();
        JPAUtil.cerrar();
        scanner.close();
    }


    private static void menuPrincipal() {
        int opcion;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gestion de categorias");
            System.out.println("2. Gestion de productos");
            System.out.println("3. Reportes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuReportes();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void menuCategorias() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE CATEGORIAS =====");
            System.out.println("1. Alta de categoria");
            System.out.println("2. Modificar categoria");
            System.out.println("3. Baja logica de categoria");
            System.out.println("4. Listar categorias activas");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> altaCategoria();
                case 2 -> modificarCategoria();
                case 3 -> bajaCategoria();
                case 4 -> listarCategorias();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void menuProductos() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Alta de producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Baja logica de producto");
            System.out.println("4. Listar productos activos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> altaProducto();
                case 2 -> modificarProducto();
                case 3 -> bajaProducto();
                case 4 -> listarProductos();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void menuReportes() {
        int opcion;

        do {
            System.out.println("\n===== REPORTES =====");
            System.out.println("1. Productos por categoria");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> productosPorCategoria();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void cargarDatosInicialesSiNoExisten() {
        var categorias = categoriaRepository.listarActivos();

        if (!categorias.isEmpty()) {
            return;
        }

        categoriaRepository.guardar(
                Categoria.builder()
                        .nombre("Bebidas")
                        .descripcion("Gaseosas, jugos y aguas")
                        .build()
        );

        categoriaRepository.guardar(
                Categoria.builder()
                        .nombre("Comidas")
                        .descripcion("Hamburguesas, pizzas y sandwiches")
                        .build()
        );

        categoriaRepository.guardar(
                Categoria.builder()
                        .nombre("Postres")
                        .descripcion("Helados, tortas y dulces")
                        .build()
        );

        System.out.println("Datos iniciales de categorias cargados correctamente.");
    }

//--------------HELPERS---------------------

    private static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private static String leerTextoObligatorioCancelable(String mensaje) {
        String texto;

        do {
            System.out.print(mensaje + " (0 para cancelar): ");
            texto = scanner.nextLine().trim();

            if ("0".equals(texto)) {
                return null;
            }

            if (texto.isBlank()) {
                System.out.println("Error: el valor no puede estar vacio.");
            }

        } while (texto.isBlank());

        return texto;
    }

    private static double leerDoublePositivo(String mensaje) {
        double valor;

        while (true) {
            try {
                System.out.print(mensaje);
                valor = Double.parseDouble(scanner.nextLine().trim());

                if (valor > 0) {
                    return valor;
                }

                System.out.println("Error: el valor debe ser mayor a 0.");

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero valido.");
            }
        }
    }

    private static int leerEnteroNoNegativo(String mensaje) {
        int valor;

        while (true) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine().trim());

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("Error: el valor no puede ser negativo.");

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero entero valido.");
            }
        }
    }

    private static Long leerLongCancelable(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " (0 para cancelar): ");
                Long valor = Long.parseLong(scanner.nextLine().trim());

                if (valor == 0) {
                    return null;
                }

                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un ID valido.");
            }
        }
    }


    private static com.utn.entities.Categoria seleccionarCategoriaActiva() {
        var categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas. Debe crear una categoria antes de cargar productos.");
            return null;
        }

        while (true) {
            System.out.println("\n===== CATEGORIAS DISPONIBLES =====");

            categorias.forEach(categoria ->
                    System.out.println("ID: " + categoria.getId()
                            + " | Nombre: " + categoria.getNombre()
                            + " | Descripcion: " + categoria.getDescripcion())
            );

            System.out.print("Ingrese ID de categoria (0 para cancelar): ");

            Long id;
            try {
                id = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un ID valido.");
                continue;
            }

            if (id == 0) {
                System.out.println("Operacion cancelada.");
                return null;
            }

            var categoriaOpt = categoriaRepository.buscarPorId(id);

            if (categoriaOpt.isPresent() && !categoriaOpt.get().isEliminado()) {
                return categoriaOpt.get();
            }

            System.out.println("Error: no existe una categoria activa con ese ID.");
        }
    }

    // ===== METODOS DE CATEGORIAS =====

    private static void altaCategoria() {
        System.out.println("\n===== ALTA DE CATEGORIA =====");

        String nombre = leerTextoObligatorioCancelable("Ingrese nombre");

        if (nombre == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        System.out.print("Ingrese descripcion (opcional): ");
        String descripcion = scanner.nextLine().trim();

        var categoria = Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();

        categoria = categoriaRepository.guardar(categoria);

        System.out.println("Categoria guardada correctamente.");
        System.out.println("ID generado: " + categoria.getId());
    }

    private static void modificarCategoria() {
        System.out.println("\n===== MODIFICAR CATEGORIA =====");

        listarCategorias();

        Long id = leerLongCancelable("Ingrese ID de la categoria a modificar");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var categoriaOpt = categoriaRepository.buscarPorId(id);

        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: no existe una categoria activa con ese ID.");
            return;
        }

        var categoria = categoriaOpt.get();

        System.out.println("Valores actuales:");
        System.out.println("Nombre: " + categoria.getNombre());
        System.out.println("Descripcion: " + categoria.getDescripcion());

        System.out.print("Nuevo nombre (Enter para conservar): ");
        String nuevoNombre = scanner.nextLine().trim();

        if (!nuevoNombre.isBlank()) {
            categoria.setNombre(nuevoNombre);
        }

        System.out.print("Nueva descripcion (Enter para conservar): ");
        String nuevaDescripcion = scanner.nextLine().trim();

        if (!nuevaDescripcion.isBlank()) {
            categoria.setDescripcion(nuevaDescripcion);
        }

        categoriaRepository.guardar(categoria);

        System.out.println("Categoria modificada correctamente.");
    }

    private static void bajaCategoria() {
        System.out.println("\n===== BAJA LOGICA DE CATEGORIA =====");

        listarCategorias();

        Long id = leerLongCancelable("Ingrese ID de la categoria a dar de baja");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var categoriaOpt = categoriaRepository.buscarPorId(id);

        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: no existe una categoria activa con ese ID.");
            return;
        }

        String nombreCategoria = categoriaOpt.get().getNombre();

        boolean eliminada = categoriaRepository.eliminarLogico(id);

        if (eliminada) {
            System.out.println("Categoria dada de baja correctamente: " + nombreCategoria);
        } else {
            System.out.println("Error: no se pudo dar de baja la categoria.");
        }
    }

    private static void listarCategorias() {
        var categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }

        System.out.println("\n===== CATEGORIAS ACTIVAS =====");

        categorias.forEach(categoria ->
                System.out.println("ID: " + categoria.getId()
                        + " | Nombre: " + categoria.getNombre()
                        + " | Descripcion: " + categoria.getDescripcion())
        );
    }

    // ===== METODOS DE PRODUCTOS =====

    private static void altaProducto() {
        System.out.println("\n===== ALTA DE PRODUCTO =====");

        Categoria categoria = seleccionarCategoriaActiva();

        if (categoria == null) {
            return;
        }

        String nombre = leerTextoObligatorioCancelable("Ingrese nombre");

        if (nombre == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        double precio = leerDoublePositivo("Ingrese precio: ");

        System.out.print("Ingrese descripcion (opcional): ");
        String descripcion = scanner.nextLine().trim();

        int stock = leerEnteroNoNegativo("Ingrese stock: ");

        Producto producto = Producto.builder()
                .nombre(nombre)
                .precio(precio)
                .descripcion(descripcion)
                .stock(stock)
                .imagen(null)
                .disponible(true)
                .categoria(categoria)
                .build();

        producto = productoRepository.guardar(producto);

        System.out.println("Producto guardado correctamente.");
        System.out.println("ID generado: " + producto.getId());
    }

    private static void modificarProducto() {
        System.out.println("\n===== MODIFICAR PRODUCTO =====");

        listarProductos();

        Long id = leerLongCancelable("Ingrese ID del producto a modificar");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var productoOpt = productoRepository.buscarPorId(id);

        if (productoOpt.isEmpty() || productoOpt.get().isEliminado()) {
            System.out.println("Error: no existe un producto activo con ese ID.");
            return;
        }

        var producto = productoOpt.get();

        System.out.println("\nValores actuales:");
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Precio: $" + producto.getPrecio());
        System.out.println("Stock: " + producto.getStock());

        System.out.print("Nuevo nombre (Enter para conservar): ");
        String nuevoNombre = scanner.nextLine().trim();

        if (!nuevoNombre.isBlank()) {
            producto.setNombre(nuevoNombre);
        }

        System.out.print("Nuevo precio (Enter para conservar): ");
        String nuevoPrecioTexto = scanner.nextLine().trim();

        if (!nuevoPrecioTexto.isBlank()) {
            try {
                double nuevoPrecio = Double.parseDouble(nuevoPrecioTexto);

                if (nuevoPrecio <= 0) {
                    System.out.println("Error: el precio debe ser mayor a 0.");
                    return;
                }

                producto.setPrecio(nuevoPrecio);

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un precio valido.");
                return;
            }
        }

        System.out.print("Nuevo stock (Enter para conservar): ");
        String nuevoStockTexto = scanner.nextLine().trim();

        if (!nuevoStockTexto.isBlank()) {
            try {
                int nuevoStock = Integer.parseInt(nuevoStockTexto);

                if (nuevoStock < 0) {
                    System.out.println("Error: el stock no puede ser negativo.");
                    return;
                }

                producto.setStock(nuevoStock);

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un stock valido.");
                return;
            }
        }

        productoRepository.guardar(producto);

        System.out.println("Producto modificado correctamente.");
    }

    private static void bajaProducto() {
        System.out.println("\n===== BAJA LOGICA DE PRODUCTO =====");

        listarProductos();

        Long id = leerLongCancelable("Ingrese ID del producto a dar de baja");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var productoOpt = productoRepository.buscarPorId(id);

        if (productoOpt.isEmpty() || productoOpt.get().isEliminado()) {
            System.out.println("Error: no existe un producto activo con ese ID.");
            return;
        }

        String nombreProducto = productoOpt.get().getNombre();

        boolean eliminado = productoRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Producto dado de baja correctamente: " + nombreProducto);
        } else {
            System.out.println("Error: no se pudo dar de baja el producto.");
        }
    }

    private static void listarProductos() {
        var productos = productoRepository.listarActivosConCategoria();

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }

        System.out.println("\n===== PRODUCTOS ACTIVOS =====");

        productos.forEach(producto ->
                System.out.println("ID: " + producto.getId()
                        + " | Nombre: " + producto.getNombre()
                        + " | Precio: $" + producto.getPrecio()
                        + " | Stock: " + producto.getStock()
                        + " | Categoria: " + producto.getCategoria().getNombre())
        );
    }

    // ===== METODOS DE REPORTES =====

    private static void productosPorCategoria() {
        System.out.println("\n===== PRODUCTOS POR CATEGORIA =====");

        Categoria categoria = seleccionarCategoriaActiva();

        if (categoria == null) {
            return;
        }

        var productos = productoRepository.buscarPorCategoria(categoria.getId());

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos para la categoria: "
                    + categoria.getNombre());
            return;
        }

        var productosDTO = productos.stream()
                .sorted(Comparator.comparing(producto -> producto.getNombre()))
                .map(producto -> new ProductoReporteDTO(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock()
                ))
                .toList();

        System.out.println("\nProductos activos de la categoria: "
                + categoria.getNombre());

        productosDTO.forEach(dto ->
                System.out.println("ID: " + dto.id()
                        + " | Nombre: " + dto.nombre()
                        + " | Precio: $" + dto.precio()
                        + " | Stock: " + dto.stock())
        );
    }
}