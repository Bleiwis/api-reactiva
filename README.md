# API Reactiva con Spring WebFlux

Este proyecto es una API REST reactiva desarrollada con **Spring WebFlux**. Implementa operaciones CRUD para una entidad llamada `Product` y utiliza almacenamiento en memoria para simplificar la persistencia de datos.

## Características

- **Programación Reactiva**: Uso de `Mono` y `Flux` de Reactor para manejar datos de forma asíncrona y no bloqueante.
- **Operaciones CRUD**: Crear, Leer, Actualizar y Eliminar productos.
- **Pruebas Unitarias**: Verificación de la lógica de negocio con **JUnit 5** y **Mockito**.
- **Pruebas de Integración**: Validación de los endpoints de la API con **WebTestClient**.

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/api_reactiva/
│   │   ├── ApiReactivaApplication.java  # Clase principal de la aplicación
│   │   ├── controller/                  # Controladores REST
│   │   │   └── ProductController.java
│   │   ├── model/                       # Modelos de datos
│   │   │   └── Product.java
│   │   ├── repository/                  # Repositorios
│   │   │   └── ProductRepository.java
│   │   └── service/                     # Servicios
│   │       └── ProductService.java
│   └── resources/
│       └── application.properties       # Configuración de la aplicación
└── test/
    ├── java/com/example/api_reactiva/
    │   ├── ApiReactivaApplicationTests.java  # Pruebas generales
    │   ├── controller/                      # Pruebas de integración
    │   │   └── ProductControllerIntegrationTest.java
    │   └── service/                         # Pruebas unitarias
    │       └── ProductServiceTest.java
```

## Endpoints de la API

### Crear un Producto
- **POST** `/products`
- **Body**:
  ```json
  {
    "id": "1",
    "name": "Producto de prueba",
    "price": 10.0
  }
  ```
- **Respuesta**: `201 Created`

### Obtener Todos los Productos
- **GET** `/products`
- **Respuesta**: `200 OK`

### Obtener un Producto por ID
- **GET** `/products/{id}`
- **Respuesta**: `200 OK` o `404 Not Found`

### Eliminar un Producto por ID
- **DELETE** `/products/{id}`
- **Respuesta**: `204 No Content`

### Suscribirse a Actualizaciones de Productos
- **GET** `/products/subscribe`
- **Descripción**: Permite a los clientes suscribirse a actualizaciones de productos en tiempo real mediante Server-Sent Events (SSE).
- **Respuesta**: `200 OK`

### Publicar una Actualización de Producto
- **POST** `/products/update`
- **Body**:
  ```json
  {
    "id": "1",
    "name": "Producto Actualizado",
    "price": 15.0
  }
  ```
- **Descripción**: Publica una actualización de producto para notificar a los suscriptores.
- **Respuesta**: `200 OK`

### Manejo de Errores en Servicios de Suscripciones

Los servicios de suscripciones y publicación de actualizaciones implementan un manejo de errores robusto para garantizar la estabilidad de la aplicación:

- **Suscribirse a Actualizaciones de Productos**:
  - Si ocurre un error durante la emisión de actualizaciones, este se registra en los logs.
  - Se proporciona un flujo alternativo vacío (`Flux.empty()`) como fallback para evitar interrupciones en los suscriptores.

- **Publicar una Actualización de Producto**:
  - Si no se puede emitir una actualización, el error se registra en los logs para facilitar la depuración.

Estos mecanismos aseguran que los errores no afecten negativamente la experiencia del usuario y que la aplicación pueda continuar funcionando de manera estable.

## Pruebas

### Pruebas Unitarias
Las pruebas unitarias verifican la lógica de negocio en la clase `ProductService` utilizando **Mockito**.

### Pruebas de Integración
Las pruebas de integración validan los endpoints de la API utilizando **WebTestClient**.

Para ejecutar todas las pruebas:
```bash
./mvnw test
```

## Requisitos

- **Java 17** o superior
- **Maven**

## Ejecución

Para compilar y ejecutar la aplicación:
```bash
./mvnw clean package
java -jar target/api-reactiva-0.0.1-SNAPSHOT.jar
```

## Notas

Este proyecto utiliza almacenamiento en memoria, por lo que los datos se perderán al reiniciar la aplicación. Es ideal para propósitos de aprendizaje y pruebas.

---

Si tienes preguntas o necesitas ayuda, no dudes en contactarme.
