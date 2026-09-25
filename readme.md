# Laboratorio: Backend CRUD de Tareas con Clean Architecture y Spring Boot

Este proyecto es un laboratorio práctico diseñado para estudiantes de segundo semestre (Análisis y Diseño de Sistemas). Su objetivo es introducir los conceptos fundamentales de **Clean Architecture** y el desacoplamiento de capas utilizando **Spring Boot**, sin la complejidad de bases de datos relacionales o interfaces gráficas pesadas.

---

## 🏗️ Estructura del Proyecto (Clean Architecture)

El proyecto sigue estrictamente la regla de dependencia: **las capas externas conocen a las internas, pero el núcleo no sabe nada de los frameworks externos.**

```text
com.universidad.tareas
│
├── domain/                    (Núcleo: Java puro, sin frameworks)
│   ├── model/
│   │   └── Task.java
│   └── repository/
│       └── TaskRepository.java (Interfaz)
│
├── usecase/                   (Lógica de Negocio: Java puro)
│   ├── CrearTareaUseCase.java
│   ├── ListarTareasUseCase.java
│   ├── BuscarTareaPorIdUseCase.java
│   ├── ActualizarTareaUseCase.java
│   └── EliminarTareaUseCase.java
│
└── infrastructure/            (El Exterior: Spring Boot y Adaptadores)
    ├── config/
    │   └── BeanConfiguration.java
    ├── persistence/
    │   └── InMemoryTaskRepository.java (Lista en memoria `List<Task>`)
    └── rest/
        └── TaskController.java     (Endpoints REST JSON)
```

---

## 🚀 ¿Cómo Ejecutar el Proyecto?

Abre tu terminal en la raíz del proyecto y utiliza el **Maven Wrapper** incluido:

*   **En Mac / Linux:**
    ```bash
    ./mvnw spring-boot:run
    ```
*   **En Windows:**
    ```cmd
    mvnw.cmd spring-boot:run
    ```

También puedes abrir la clase principal en tu IDE favorito (IntelliJ IDEA, Eclipse, STS) que contenga la anotación `@SpringBootApplication` y presionar el botón **Play / Run**.

La aplicación iniciará en el puerto por defecto: `http://localhost:8080`

---

## 🧪 Endpoints Disponibles para Probar (Postman / Insomnia)

Puedes consumir la API REST enviando peticiones en formato JSON:

### 1. Crear Tarea (POST)
*   **URL:** `http://localhost:8080/api/tasks`
*   **Método:** `POST`
*   **Headers:** `Content-Type: application/json`
*   **Body (JSON):**
    ```json
    {
      "titulo": "Estudiar Clean Architecture"
    }
    ```

### 2. Listar Todas las Tareas (GET)
*   **URL:** `http://localhost:8080/api/tasks`
*   **Método:** `GET`

### 3. Buscar Tarea por ID (GET)
*   **URL:** `http://localhost:8080/api/tasks/{id}`
*   **Método:** `GET`

### 4. Actualizar Tarea (PUT)
*   **URL:** `http://localhost:8080/api/tasks/{id}`
*   **Método:** `PUT`
*   **Headers:** `Content-Type: application/json`
*   **Body (JSON):**
    ```json
    {
      "titulo": "Estudiar Clean Architecture (Completado)",
      "completada": true
    }
    ```

### 5. Eliminar Tarea (DELETE)
*   **URL:** `http://localhost:8080/api/tasks/{id}`
*   **Método:** `DELETE`

---

## 💡 Conceptos Clave para la Clase
1. **Independencia del Framework:** Las carpetas `domain` y `usecase` no tienen ninguna anotación de Spring (`@Service`, `@RestController`, etc.). Si mañana se decide cambiar Spring Boot por otro framework, la lógica de negocio permanece intacta.
2. **Inversión de Dependencias:** La interfaz `TaskRepository` es propiedad del dominio, pero es implementada en la infraestructura (`InMemoryTaskRepository`) e inyectada mediante configuración explícita en `BeanConfiguration.java`.
3. **Persistencia en Memoria:** Al no usar bases de datos reales, los datos se almacenan temporalmente en una estructura `List<Task>` en RAM mientras la aplicación esté ejecutándose.