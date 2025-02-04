package ToDoAPI.controller;

import ToDoAPI.dto.TaskPostDto;
import ToDoAPI.dto.TaskPutDto;
import ToDoAPI.dto.TaskPutStatusDto;
import ToDoAPI.model.TaskModel;
import ToDoAPI.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskModel> save(@RequestBody TaskPostDto taskPostDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskPostDto));
    }

    @PutMapping("/update")
    public ResponseEntity<TaskModel> update(@RequestBody TaskPutDto taskPutDto) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(taskPutDto));
    }

    @PutMapping("/update/status")
    public ResponseEntity<TaskModel> updateStatus(@RequestBody TaskPutStatusDto taskPutStatusDto) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateStatus(taskPutStatusDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable(name = "id") UUID id) {
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }
}
