package com.example.todoList.Controller;

import com.example.todoList.Service.TodoListService;
import com.example.todoList.domain.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class TodoController {

    @Autowired
    private TodoListService todoListService;

    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<TodoList> all = todoListService.getAll();
        if (all.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(all);
        }
    }

    @PostMapping("/one")
    public ResponseEntity create(@RequestParam("description") String description){
        TodoList list = todoListService.create(description);
        return ResponseEntity.status(200).body(list);
    }

    @PatchMapping("/status/{listId}")
    public ResponseEntity updateStatus(@PathVariable Integer listId,@RequestParam("status") String status){
        return todoListService.update(listId,status)?
                ResponseEntity.ok().build():
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/one")
    public ResponseEntity deleteList(@RequestParam Integer id){
        if (todoListService.deleteList(id)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/indexOrder")
    public ResponseEntity updateOrderIndex(@RequestParam Integer startIndex,@RequestParam Integer endIndex){
        List<TodoList> todoLists = todoListService.updateIndex(startIndex, endIndex);
        return todoLists.isEmpty() ? ResponseEntity.status(404).build():ResponseEntity.status(200).body(todoLists);
    }



}
