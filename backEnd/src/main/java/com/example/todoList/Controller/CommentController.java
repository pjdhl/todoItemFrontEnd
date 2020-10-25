package com.example.todoList.Controller;


import com.example.todoList.Service.CommentService;
import com.example.todoList.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{todoItemId}/comments")
    public ResponseEntity getAllCommentByTodoItemId(@PathVariable Integer todoItemId){

        List<Comment> allComment = commentService.getAllByTodoItemId(todoItemId);
        return ResponseEntity.status(200).body(allComment);

    }

    @PostMapping("/{todoItemId}/comments")
    public ResponseEntity createCommentByTodoItemId(@PathVariable Integer todoItemId,
                                                    @RequestBody Comment comment){
        commentService.create(todoItemId,comment);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{commentId}/comment")
    public ResponseEntity deleteCommentById(@PathVariable Integer commentId){
        boolean delete = commentService.delete(commentId);
        return delete? ResponseEntity.status(200).build(): ResponseEntity.status(404).build();


    }
}
