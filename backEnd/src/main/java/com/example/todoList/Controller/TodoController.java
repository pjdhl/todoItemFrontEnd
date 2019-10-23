package com.example.todoList.Controller;

import com.example.todoList.Service.TodoItemService;
import com.example.todoList.domain.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class TodoController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<TodoItem> all = todoItemService.getAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/one")
    public ResponseEntity create(@RequestParam("description") String description){
        TodoItem list = todoItemService.create(description);
        return ResponseEntity.status(200).body(list);
    }

    @PatchMapping("/status/{listId}")
    public ResponseEntity updateStatus(@PathVariable Integer listId,@RequestParam("status") String status){
        return todoItemService.update(listId,status)?
                ResponseEntity.ok().build():
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/one")
    public ResponseEntity deleteList(@RequestParam Integer id){
        if (todoItemService.delete(id)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/indexOrder")
    public ResponseEntity updateOrderIndex(@RequestParam Integer startIndex,@RequestParam Integer endIndex){
        List<TodoItem> todoLists = todoItemService.updateIndex(startIndex, endIndex);
        return todoLists.isEmpty() ? ResponseEntity.status(404).build():ResponseEntity.status(200).body(todoLists);
    }



}
