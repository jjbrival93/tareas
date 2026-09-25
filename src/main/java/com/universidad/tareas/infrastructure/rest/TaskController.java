// infrastructure/rest/TaskController.java
package com.universidad.tareas.infrastructure.rest;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.usecase.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final CrearTareaUseCase crearUseCase;
    private final ListarTareasUseCase listarUseCase;
    private final BuscarTareaPorIdUseCase buscarUseCase;
    private final ActualizarTareaUseCase actualizarUseCase;
    private final EliminarTareaUseCase eliminarUseCase;

    public TaskController(CrearTareaUseCase crear, ListarTareasUseCase listar, BuscarTareaPorIdUseCase buscar, ActualizarTareaUseCase actualizar, EliminarTareaUseCase eliminar) {
        this.crearUseCase = crear;
        this.listarUseCase = listar;
        this.buscarUseCase = buscar;
        this.actualizarUseCase = actualizar;
        this.eliminarUseCase = eliminar;
    }

    // CREATE: POST /api/tasks
    @PostMapping
    public Task crear(@RequestBody TaskRequest request) {
        return crearUseCase.ejecutar(request.getTitulo());
    }

    // READ (Todos): GET /api/tasks
    @GetMapping
    public List<Task> listar() {
        return listarUseCase.ejecutar();
    }

    // READ (Por ID): GET /api/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarPorId(@PathVariable Long id) {
        return buscarUseCase.ejecutar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE: PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public Task actualizar(@PathVariable Long id, @RequestBody TaskRequest request) {
        return actualizarUseCase.ejecutar(id, request.getTitulo(), request.isCompletada());
    }

    // DELETE: DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }

    // DTO auxiliar interno para recibir los datos JSON limpiamente
    public static class TaskRequest {
        private String titulo;
        private boolean completada;

        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        
        public boolean isCompletada() { return completada; }
        public void setCompletada(boolean completada) { this.completada = completada; }
    }
}