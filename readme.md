# 🚀 Laboratorio: Clean Architecture con Spring Boot (CRUD en Memoria + Docker)

Este proyecto es una guía práctica diseñada para estudiantes de semestres tempranos de ingeniería con el fin de introducir los conceptos clave de **Clean Architecture** utilizando **Spring Boot**, sin la complejidad de bases de datos externas o interfaces gráficas pesadas.

---

## 🏛️ Estructura del Proyecto (Clean Architecture)

El código está estrictamente dividido en tres capas para demostrar el desacoplamiento y la independencia de frameworks:

```text
com.universidad.tareas
│
├── domain/                    (Cero frameworks, Java puro - Reglas de negocio y entidades)
│   ├── model/
│   │   └── Task.java
│   └── repository/
│       └── TaskRepository.java (Interfaz)
│
├── usecase/                   (Lógica de la aplicación - Casos de uso independientes)
│   ├── CrearTareaUseCase.java
│   ├── ListarTareasUseCase.java
│   ├── BuscarTareaPorIdUseCase.java
│   ├── ActualizarTareaUseCase.java
│   └── EliminarTareaUseCase.java
│
└── infrastructure/            (Adaptadores, Configuración de Beans y controladores REST)
    ├── config/
    │   └── BeanConfiguration.java
    ├── persistence/
    │   └── InMemoryTaskRepository.java (Simulación de BD con ArrayList)
    └── rest/
        └── TaskController.java     (Endpoints HTTP / JSON)
```

---

## 💻 Implementación de las Capas (Código del Laboratorio)

### 1. Capa `domain` (Núcleo Puro)
```java
// domain/model/Task.java
package com.universidad.tareas.domain.model;

public class Task {
    private Long id;
    private String titulo;
    private boolean completada;

    public Task(Long id, String titulo, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.completada = completada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
}
```

```java
// domain/repository/TaskRepository.java
package com.universidad.tareas.domain.repository;

import com.universidad.tareas.domain.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task guardar(Task task);
    List<Task> listarTodas();
    Optional<Task> buscarPorId(Long id);
    void eliminar(Long id);
}
```

### 2. Capa `usecase` (Lógica de Negocio)
```java
// usecase/CrearTareaUseCase.java
package com.universidad.tareas.usecase;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.domain.repository.TaskRepository;

public class CrearTareaUseCase {
    private final TaskRepository taskRepository;

    public CrearTareaUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task ejecutar(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        Long id = System.currentTimeMillis();
        Task nuevaTask = new Task(id, titulo, false);
        return taskRepository.guardar(nuevaTask);
    }
}
```

### 3. Capa `infrastructure` (Adaptadores, Configuración y REST)
```java
// infrastructure/config/BeanConfiguration.java
package com.universidad.tareas.infrastructure.config;

import com.universidad.tareas.domain.repository.TaskRepository;
import com.universidad.tareas.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CrearTareaUseCase crearTareaUseCase(TaskRepository repo) { return new CrearTareaUseCase(repo); }
    @Bean
    public ListarTareasUseCase listarTareasUseCase(TaskRepository repo) { return new ListarTareasUseCase(repo); }
    @Bean
    public BuscarTareaPorIdUseCase buscarTareaPorIdUseCase(TaskRepository repo) { return new BuscarTareaPorIdUseCase(repo); }
    @Bean
    public ActualizarTareaUseCase actualizarTareaUseCase(TaskRepository repo) { return new ActualizarTareaUseCase(repo); }
    @Bean
    public EliminarTareaUseCase eliminarTareaUseCase(TaskRepository repo) { return new EliminarTareaUseCase(repo); }
}
```

---

## 🐳 Guía Completa de Ejecución con Docker

Para empaquetar y ejecutar este proyecto utilizando contenedores, sigue estos pasos al pie de la letra:

### Paso 1: Crear el archivo `Dockerfile`
En la raíz de tu proyecto (al mismo nivel que `pom.xml`), asegúrate de tener un archivo llamado exactamente `Dockerfile` con el siguiente contenido de compilación por fases (*multistage build*):

```dockerfile
# Fase 1: Compilación de la aplicación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Fase 2: Ejecución de la aplicación con Java 17 JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Paso 2: Construir la imagen de Docker
Abre tu terminal en la raíz del proyecto y ejecuta el siguiente comando para construir la imagen (asegúrate de incluir el punto `.` al final, que indica el directorio actual):

```bash
docker build -t api-tareas-clean .
```

### Paso 3: Ejecutar el contenedor
Una vez finalizada la compilación, pon en marcha el contenedor mapeando el puerto `8080` de tu máquina local al puerto `8080` del contenedor:

```bash
docker run -p 8080:8080 api-tareas-clean
```

Al ver el banner de Spring Boot y el mensaje `Started TareasApplication`, tu aplicación estará corriendo exitosamente dentro de Docker.

---

## 🌐 Endpoints de la API (Pruebas)

Una vez que la aplicación esté corriendo en Docker, puedes consumirla en la siguiente URL base:
`http://localhost:8080/api/tasks`

| Operación | Método HTTP | Ruta | Cuerpo de la Petición (JSON) |
| :--- | :--- | :--- | :--- |
| **Crear Tarea** | `POST` | `/api/tasks` | `{"titulo": "Estudiar Clean Architecture"}` |
| **Listar Todas** | `GET` | `/api/tasks` | *Ninguno* |
| **Buscar por ID** | `GET` | `/api/tasks/{id}` | *Ninguno* |
| **Actualizar** | `PUT` | `/api/tasks/{id}` | `{"titulo": "Tarea Actualizada", "completada": true}` |
| **Eliminar** | `DELETE` | `/api/tasks/{id}` | *Ninguno* |