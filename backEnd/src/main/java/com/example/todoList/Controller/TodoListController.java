package com.example.todoList.Controller;


import com.example.todoList.Service.TodoListService;
import com.example.todoList.domain.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo/list")
@CrossOrigin
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @PostMapping
    public ResponseEntity create_todo_list(@RequestParam String name){

        TodoList todoList1 = todoListService.create(name);
        return ResponseEntity.status(200).body(todoList1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete_todo_list(@PathVariable Integer id){
        boolean delete = todoListService.delete(id);
        return delete? ResponseEntity.status(200).build():ResponseEntity.status(404).build();

    }

    @GetMapping
    public ResponseEntity get_todo_list_all(){
        List<TodoList> all = todoListService.getAll();
        return ResponseEntity.ok(all);
    }
}
