# Parcial 2 - Programación III

## Alumna

Marina Giselle Cordero

## Descripción del Proyecto

Aplicación de consola desarrollada en Java utilizando JPA (Jakarta Persistence API), Hibernate y base de datos H2.

El sistema permite administrar categorías y productos mediante operaciones ABM (Alta, Baja lógica, Modificación y Listado), implementando el patrón Repository para desacoplar la lógica de persistencia de la lógica de presentación.

Además, incorpora una consulta JPQL personalizada para obtener los productos activos pertenecientes a una categoría determinada.

---

## Funcionalidades Implementadas

### Gestión de Categorías

- Alta de categorías.
- Modificación de categorías.
- Baja lógica de categorías.
- Listado de categorías activas.

### Gestión de Productos

- Alta de productos asociados a una categoría.
- Modificación de productos.
- Baja lógica de productos.
- Listado de productos activos.

### Reportes

- Consulta de productos por categoría mediante JPQL.
- Ordenamiento alfabético de productos utilizando Streams.
- Transformación de entidades a DTOs mediante Streams y la operación map().

---

## Tecnologías Utilizadas

- Java
- JPA (Jakarta Persistence API)
- Hibernate
- H2 Database
- Gradle
- Lombok

---

## Conceptos Aplicados

- Programación Orientada a Objetos (POO)
- Herencia
- Generics
- Optional
- Repository Pattern
- JPQL
- DTOs
- Streams
- Expresiones Lambda
- Builder Pattern
- Lombok
- Baja lógica
- Validaciones de entrada

---
## Estructura General

```text
src/main/java/com/utn
│
├── dtos
├── entities
├── enums
├── repository
├── util
└── Main.java
```

---

## Ejecución del Proyecto

### Requisitos

- JDK 17 o superior
- Gradle (o utilizar el Wrapper incluido)

### Pasos

1. Clonar el repositorio:

```bash
git clone <url-del-repositorio>
```

2. Abrir el proyecto en IntelliJ IDEA.

3. Esperar la sincronización de Gradle.

4. Ejecutar la clase:

```text
Main.java
```

5. Utilizar el menú de consola para gestionar categorías y productos.

---

## Decisiones de Diseño

- Se implementó el patrón Repository para separar la lógica de persistencia de la lógica de presentación.
- Se utilizó un repositorio genérico (`BaseRepository<T>`) para reutilizar operaciones CRUD comunes.
- Se implementó baja lógica mediante el atributo `eliminado`, preservando el historial de registros.
- Las consultas específicas se resolvieron mediante JPQL tipado (TypedQuery) dentro de los repositorios para mantener desacoplada la lógica de acceso a datos.
- En el reporte de productos por categoría se utilizaron Streams, Lambdas y DTOs para transformar y presentar los datos de forma desacoplada.
- Se incorporaron métodos auxiliares y validaciones para mejorar la experiencia de usuario y reducir la repetición de código.

## Mejoras Incorporadas

Durante el desarrollo se incorporaron mejoras adicionales respecto de la implementación mínima requerida:

- Métodos auxiliares para reutilizar lógica de lectura y validación.
- Validaciones de datos de entrada.
- Cancelación de operaciones mediante la opción `0`.
- Ordenamiento alfabético de resultados utilizando Streams.
- Uso de DTOs en la capa de presentación del reporte de productos por categoría.

---

## Consideraciones sobre DTOs

Se implementó un DTO específico para la presentación de datos en el reporte de productos por categoría.

No se utilizaron DTOs para las operaciones de alta y modificación debido al alcance reducido del proyecto y a que las entidades involucradas (`Producto` y `Categoría`) no contienen información sensible. En una aplicación de mayor tamaño podrían incorporarse DTOs específicos de entrada para aumentar el desacoplamiento entre la capa de presentación y la capa de persistencia.

---
Repositorio desarrollado para la evaluación parcial de la materia Programación III - Tecnicatura Universitaria en Programación a Distancia (UTN).
