package com.utn;

import com.utn.dtos.ProductoReporteDTO;
import com.utn.dtos.UsuarioAltaDTO;
import com.utn.dtos.UsuarioListadoDTO;
import com.utn.dtos.UsuarioModificacionDTO;
import com.utn.dtos.ItemPedidoDTO;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.repository.CategoriaRepository;
import com.utn.repository.ProductoRepository;
import com.utn.repository.UsuarioRepository;
import com.utn.repository.PedidoRepository;
import com.utn.util.JPAUtil;
import com.utn.entities.Categoria;
import com.utn.entities.Producto;
import com.utn.entities.Usuario;
import com.utn.enums.Rol;

import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepository = new CategoriaRepository();
    private static final ProductoRepository productoRepository = new ProductoRepository();
    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static final PedidoRepository pedidoRepository = new PedidoRepository();

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
            System.out.println("3. Gestion de usuarios");
            System.out.println("4. Gestion de pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");


            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 5 -> menuReportes();
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

    private static void menuUsuarios() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE USUARIOS =====");
            System.out.println("1. Alta de usuario");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Baja logica de usuario");
            System.out.println("4. Listar usuarios activos");
            System.out.println("5. Buscar usuario por mail");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> altaUsuario();
                case 2 -> modificarUsuario();
                case 3 -> bajaUsuario();
                case 4 -> listarUsuarios();
                case 5 -> buscarUsuarioPorMail();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE PEDIDOS =====");
            System.out.println("1. Alta de pedido");
            System.out.println("2. Cambiar estado de pedido");
            System.out.println("3. Baja logica de pedido");
            System.out.println("4. Listar pedidos activos");
            System.out.println("5. Pedidos por usuario");
            System.out.println("6. Pedidos por estado");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> altaPedido();
                case 2 -> cambiarEstadoPedido();
                case 3 -> bajaPedido();
                case 4 -> listarPedidos();
                case 5 -> pedidosPorUsuario();
                case 6 -> pedidosPorEstado();
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

        if (categorias.isEmpty()) {

            Categoria bebidas = categoriaRepository.guardar(
                    Categoria.builder()
                            .nombre("Bebidas")
                            .descripcion("Gaseosas, jugos y aguas")
                            .build()
            );

            Categoria comidas = categoriaRepository.guardar(
                    Categoria.builder()
                            .nombre("Comidas")
                            .descripcion("Hamburguesas, pizzas y sandwiches")
                            .build()
            );

            Categoria postres = categoriaRepository.guardar(
                    Categoria.builder()
                            .nombre("Postres")
                            .descripcion("Helados, tortas y dulces")
                            .build()
            );

            productoRepository.guardar(
                    Producto.builder()
                            .nombre("Coca Cola")
                            .precio(2500.0)
                            .descripcion("Gaseosa cola")
                            .stock(20)
                            .disponible(true)
                            .categoria(bebidas)
                            .build()
            );

            productoRepository.guardar(
                    Producto.builder()
                            .nombre("Sprite")
                            .precio(2300.0)
                            .descripcion("Gaseosa lima limon")
                            .stock(15)
                            .disponible(true)
                            .categoria(bebidas)
                            .build()
            );

            productoRepository.guardar(
                    Producto.builder()
                            .nombre("Agua Mineral")
                            .precio(1500.0)
                            .descripcion("Agua sin gas")
                            .stock(30)
                            .disponible(true)
                            .categoria(bebidas)
                            .build()
            );

            productoRepository.guardar(
                    Producto.builder()
                            .nombre("Hamburguesa Completa")
                            .precio(8500.0)
                            .descripcion("Hamburguesa con papas")
                            .stock(10)
                            .disponible(true)
                            .categoria(comidas)
                            .build()
            );

            productoRepository.guardar(
                    Producto.builder()
                            .nombre("Pizza Muzzarella")
                            .precio(12000.0)
                            .descripcion("Pizza grande")
                            .stock(8)
                            .disponible(true)
                            .categoria(comidas)
                            .build()
            );

            System.out.println("Datos iniciales cargados correctamente.");
        }
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

    private static Rol seleccionarRol() {
        while (true) {
            System.out.println("\nSeleccione rol:");
            System.out.println("1. ADMIN");
            System.out.println("2. USUARIO");
            System.out.print("Opcion: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> {
                    return Rol.ADMIN;
                }
                case 2 -> {
                    return Rol.USUARIO;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static Estado seleccionarEstado() {
        while (true) {
            System.out.println("\nSeleccione estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. CONFIRMADO");
            System.out.println("3. TERMINADO");
            System.out.println("4. CANCELADO");
            System.out.print("Opcion: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> {
                    return Estado.PENDIENTE;
                }
                case 2 -> {
                    return Estado.CONFIRMADO;
                }
                case 3 -> {
                    return Estado.TERMINADO;
                }
                case 4 -> {
                    return Estado.CANCELADO;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static FormaPago seleccionarFormaPago() {
        while (true) {
            System.out.println("\nSeleccione forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");
            System.out.print("Opcion: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> {
                    return FormaPago.TARJETA;
                }
                case 2 -> {
                    return FormaPago.TRANSFERENCIA;
                }
                case 3 -> {
                    return FormaPago.EFECTIVO;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static int leerEnteroPositivo(String mensaje) {
        int valor;

        while (true) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine().trim());

                if (valor > 0) {
                    return valor;
                }

                System.out.println("El valor debe ser mayor a 0.");

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero valido.");
            }
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

    // ===== METODOS DE USUARIOS =====

    private static void altaUsuario() {
        System.out.println("\n===== ALTA DE USUARIO =====");

        String nombre = leerTextoObligatorioCancelable("Ingrese nombre");

        if (nombre == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        String apellido = leerTextoObligatorioCancelable("Ingrese apellido");

        if (apellido == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        String mail = leerTextoObligatorioCancelable("Ingrese mail");

        if (mail == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var usuarioExistente = usuarioRepository.buscarPorMail(mail);

        if (usuarioExistente.isPresent()) {
            System.out.println("No se puede guardar: ya existe un usuario activo con ese mail.");
            return;
        }

        System.out.print("Ingrese celular (opcional): ");
        String celular = scanner.nextLine().trim();

        String contrasena = leerTextoObligatorioCancelable("Ingrese contraseña");

        if (contrasena == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        Rol rol = seleccionarRol();

        UsuarioAltaDTO usuarioAltaDTO = new UsuarioAltaDTO(
                nombre,
                apellido,
                mail,
                celular,
                contrasena,
                rol
        );

        Usuario usuario = Usuario.builder()
                .nombre(usuarioAltaDTO.nombre())
                .apellido(usuarioAltaDTO.apellido())
                .mail(usuarioAltaDTO.mail())
                .celular(usuarioAltaDTO.celular())
                .contrasena(usuarioAltaDTO.contrasena())
                .rol(usuarioAltaDTO.rol())
                .build();

        usuario = usuarioRepository.guardar(usuario);

        System.out.println("Usuario guardado correctamente.");
        System.out.println("ID generado: " + usuario.getId());
    }

    private static void listarUsuarios() {
        var usuarios = usuarioRepository.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios activos.");
            return;
        }

        var usuariosDTO = usuarios.stream()
                .map(usuario -> new UsuarioListadoDTO(
                        usuario.getId(),
                        usuario.getNombre() + " " + usuario.getApellido(),
                        usuario.getMail(),
                        usuario.getRol()
                ))
                .toList();

        System.out.println("\n===== USUARIOS ACTIVOS =====");

        usuariosDTO.forEach(dto ->
                System.out.println("ID: " + dto.id()
                        + " | Nombre: " + dto.nombreCompleto()
                        + " | Mail: " + dto.mail()
                        + " | Rol: " + dto.rol())
        );
    }

    private static void buscarUsuarioPorMail() {
        System.out.println("\n===== BUSCAR USUARIO POR MAIL =====");

        String mail = leerTextoObligatorioCancelable("Ingrese mail");

        if (mail == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var usuarioOpt = usuarioRepository.buscarPorMail(mail);

        if (usuarioOpt.isEmpty()) {
            System.out.println("No existe un usuario activo con ese mail.");
            return;
        }

        var usuario = usuarioOpt.get();

        UsuarioListadoDTO usuarioDTO = new UsuarioListadoDTO(
                usuario.getId(),
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getMail(),
                usuario.getRol()
        );

        System.out.println("\nUsuario encontrado:");
        System.out.println("ID: " + usuarioDTO.id()
                + " | Nombre: " + usuarioDTO.nombreCompleto()
                + " | Mail: " + usuarioDTO.mail()
                + " | Rol: " + usuarioDTO.rol());
    }

    private static void bajaUsuario() {
        System.out.println("\n===== BAJA LOGICA DE USUARIO =====");

        listarUsuarios();

        Long id = leerLongCancelable("Ingrese ID del usuario a dar de baja");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var usuarioOpt = usuarioRepository.buscarPorId(id);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().isEliminado()) {
            System.out.println("No existe un usuario activo con ese ID.");
            return;
        }

        var usuario = usuarioOpt.get();
        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();

        boolean eliminado = usuarioRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Usuario dado de baja correctamente: " + nombreCompleto);
        } else {
            System.out.println("No se pudo dar de baja el usuario.");
        }
    }

    private static void modificarUsuario() {
        System.out.println("\n===== MODIFICAR USUARIO =====");

        listarUsuarios();

        Long id = leerLongCancelable("Ingrese ID del usuario a modificar");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var usuarioOpt = usuarioRepository.buscarPorId(id);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().isEliminado()) {
            System.out.println("No existe un usuario activo con ese ID.");
            return;
        }

        var usuario = usuarioOpt.get();

        System.out.println("\nValores actuales:");
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Apellido: " + usuario.getApellido());
        System.out.println("Mail: " + usuario.getMail());
        System.out.println("Celular: " + usuario.getCelular());
        System.out.println("Contrasena: ********");

        System.out.print("Nuevo nombre (Enter para conservar): ");
        String nuevoNombre = scanner.nextLine().trim();

        if (nuevoNombre.isBlank()) {
            nuevoNombre = usuario.getNombre();
        }

        System.out.print("Nuevo apellido (Enter para conservar): ");
        String nuevoApellido = scanner.nextLine().trim();

        if (nuevoApellido.isBlank()) {
            nuevoApellido = usuario.getApellido();
        }

        System.out.print("Nuevo mail (Enter para conservar): ");
        String nuevoMail = scanner.nextLine().trim();

        if (nuevoMail.isBlank()) {
            nuevoMail = usuario.getMail();
        } else if (!nuevoMail.equalsIgnoreCase(usuario.getMail())) {
            var usuarioConMismoMail = usuarioRepository.buscarPorMail(nuevoMail);

            if (usuarioConMismoMail.isPresent()
                    && !usuarioConMismoMail.get().getId().equals(usuario.getId())) {
                System.out.println("No se puede modificar: ya existe otro usuario activo con ese mail.");
                return;
            }
        }

        System.out.print("Nuevo celular (Enter para conservar): ");
        String nuevoCelular = scanner.nextLine().trim();

        if (nuevoCelular.isBlank()) {
            nuevoCelular = usuario.getCelular();
        }

        System.out.print("Nueva contrasena (Enter para conservar): ");
        String nuevaContrasena = scanner.nextLine().trim();

        if (nuevaContrasena.isBlank()) {
            nuevaContrasena = usuario.getContrasena();
        }

        UsuarioModificacionDTO usuarioModificacionDTO = new UsuarioModificacionDTO(
                nuevoNombre,
                nuevoApellido,
                nuevoMail,
                nuevoCelular,
                nuevaContrasena
        );

        usuario.setNombre(usuarioModificacionDTO.nombre());
        usuario.setApellido(usuarioModificacionDTO.apellido());
        usuario.setMail(usuarioModificacionDTO.mail());
        usuario.setCelular(usuarioModificacionDTO.celular());
        usuario.setContrasena(usuarioModificacionDTO.contrasena());

        usuarioRepository.guardar(usuario);

        System.out.println("Usuario modificado correctamente.");
    }

    // ===== METODOS DE PEDIDOS =====

    private static void listarPedidos() {
        var pedidos = pedidoRepository.listarActivosConUsuario();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos activos.");
            return;
        }

        System.out.println("\n===== PEDIDOS ACTIVOS =====");

        pedidos.forEach(pedido ->
                System.out.println("ID: " + pedido.id()
                        + " | Fecha: " + pedido.fecha()
                        + " | Estado: " + pedido.estado()
                        + " | Forma de pago: " + pedido.formaPago()
                        + " | Usuario: " + pedido.nombreUsuario()
                        + " | Total: $" + pedido.total())
        );
    }

    private static void pedidosPorUsuario() {
        System.out.println("\n===== PEDIDOS POR USUARIO =====");

        listarUsuarios();

        Long idUsuario = leerLongCancelable("Ingrese ID del usuario");

        if (idUsuario == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var usuarioOpt = usuarioRepository.buscarPorId(idUsuario);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().isEliminado()) {
            System.out.println("No existe un usuario activo con ese ID.");
            return;
        }

        var usuario = usuarioOpt.get();

        var pedidos = pedidoRepository.buscarPorUsuario(idUsuario);

        if (pedidos.isEmpty()) {
            System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellido()
                    + " no tiene pedidos activos.");
            return;
        }

        System.out.println("\nPedidos activos de: " + usuario.getNombre() + " " + usuario.getApellido());

        pedidos.forEach(pedido ->
                System.out.println("ID: " + pedido.getId()
                        + " | Fecha: " + pedido.getFecha()
                        + " | Estado: " + pedido.getEstado()
                        + " | Forma de pago: " + pedido.getFormaPago()
                        + " | Total: $" + pedido.getTotal())
        );
    }

    private static void pedidosPorEstado() {
        System.out.println("\n===== PEDIDOS POR ESTADO =====");

        Estado estado = seleccionarEstado();

        var pedidos = pedidoRepository.buscarPorEstado(estado);

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos activos con estado: " + estado);
            return;
        }

        System.out.println("\nPedidos activos con estado: " + estado);

        pedidos.forEach(pedido ->
                System.out.println("ID: " + pedido.getId()
                        + " | Fecha: " + pedido.getFecha()
                        + " | Estado: " + pedido.getEstado()
                        + " | Forma de pago: " + pedido.getFormaPago()
                        + " | Total: $" + pedido.getTotal())
        );
    }

    private static void cambiarEstadoPedido() {
        System.out.println("\n===== CAMBIAR ESTADO DE PEDIDO =====");

        listarPedidos();

        Long id = leerLongCancelable("Ingrese ID del pedido");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var pedidoOpt = pedidoRepository.buscarPorId(id);

        if (pedidoOpt.isEmpty() || pedidoOpt.get().isEliminado()) {
            System.out.println("No existe un pedido activo con ese ID.");
            return;
        }

        var pedido = pedidoOpt.get();

        System.out.println("Estado actual: " + pedido.getEstado());

        Estado nuevoEstado = seleccionarEstado();

        pedido.setEstado(nuevoEstado);

        pedidoRepository.guardar(pedido);

        System.out.println("Estado del pedido actualizado correctamente.");
        System.out.println("Pedido ID: " + pedido.getId() + " | Nuevo estado: " + nuevoEstado);
    }

    private static void bajaPedido() {
        System.out.println("\n===== BAJA LOGICA DE PEDIDO =====");

        listarPedidos();

        Long id = leerLongCancelable("Ingrese ID del pedido a dar de baja");

        if (id == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var pedidoOpt = pedidoRepository.buscarPorId(id);

        if (pedidoOpt.isEmpty() || pedidoOpt.get().isEliminado()) {
            System.out.println("No existe un pedido activo con ese ID.");
            return;
        }

        var pedido = pedidoOpt.get();
        Double total = pedido.getTotal();

        boolean eliminado = pedidoRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Pedido dado de baja correctamente.");
            System.out.println("ID: " + id + " | Total: $" + total);
        } else {
            System.out.println("No se pudo dar de baja el pedido.");
        }
    }

    private static List<ItemPedidoDTO> cargarItemsPedido() {
        List<ItemPedidoDTO> items = new ArrayList<>();

        String continuar = "S";

        do {
            System.out.println("\n===== SELECCION DE PRODUCTOS =====");

            listarProductos();

            Long idProducto = leerLongCancelable("Ingrese ID del producto a agregar");

            if (idProducto == null) {
                System.out.println("Carga de producto cancelada.");
                break;
            }

            var productoOpt = productoRepository.buscarPorId(idProducto);

            if (productoOpt.isEmpty() || productoOpt.get().isEliminado()) {
                System.out.println("No existe un producto activo con ese ID.");
                continue;
            }

            var producto = productoOpt.get();

            if (!Boolean.TRUE.equals(producto.getDisponible())) {
                System.out.println("El producto no esta disponible.");
                continue;
            }

            if (producto.getStock() <= 0) {
                System.out.println("El producto no tiene stock disponible.");
                continue;
            }

            int cantidad = leerEnteroPositivo("Ingrese cantidad: ");

            int cantidadYaAgregada = items.stream()
                    .filter(item -> item.productoId().equals(idProducto))
                    .mapToInt(ItemPedidoDTO::cantidad)
                    .sum();

            int cantidadTotalSolicitada = cantidadYaAgregada + cantidad;

            if (cantidadTotalSolicitada > producto.getStock()) {
                System.out.println("Stock insuficiente.");
                System.out.println("Stock disponible: " + producto.getStock());
                System.out.println("Cantidad ya agregada al pedido: " + cantidadYaAgregada);
                return items;
            }

            items.add(new ItemPedidoDTO(idProducto, cantidad));

            System.out.println("Producto agregado temporalmente al pedido.");

            System.out.print("Desea agregar otro producto? (S/N): ");
            continuar = scanner.nextLine().trim();

        } while (continuar.equalsIgnoreCase("S"));

        return items;
    }

    private static void altaPedido() {
        System.out.println("\n===== ALTA DE PEDIDO =====");

        listarUsuarios();

        Long idUsuario = leerLongCancelable("Ingrese ID del usuario");

        if (idUsuario == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        var usuarioOpt = usuarioRepository.buscarPorId(idUsuario);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().isEliminado()) {
            System.out.println("No existe un usuario activo con ese ID.");
            return;
        }

        var usuario = usuarioOpt.get();

        FormaPago formaPago = seleccionarFormaPago();

        var items = cargarItemsPedido();

        if (items.isEmpty()) {
            System.out.println("El pedido debe tener al menos un producto.");
            return;
        }

        try {
            var pedido = pedidoRepository.registrarPedido(idUsuario, formaPago, items);

            System.out.println("\nPedido registrado correctamente.");
            System.out.println("ID generado: " + pedido.getId());
            System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
            System.out.println("Fecha: " + pedido.getFecha());
            System.out.println("Estado: " + pedido.getEstado());
            System.out.println("Forma de pago: " + pedido.getFormaPago());
            System.out.println("Total: $" + pedido.getTotal());

        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar el pedido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrio un problema inesperado al registrar el pedido.");
        }
    }
}