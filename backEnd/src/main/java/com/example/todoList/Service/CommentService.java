package com.example.todoList.Service;


import com.example.todoList.dao.CommentRepository;
import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.domain.Comment;
import com.example.todoList.domain.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Comment create(Integer todoItemId,Comment comment) {
        return todoItemRepository.findById(todoItemId).map(todoItem ->{
            comment.setTodoItem(todoItem);
            comment.setCreateTime(new Date());
            return commentRepository.save(comment);
        }).orElseThrow(RuntimeException::new);
    }

    public List<Comment> getAllByTodoItemId(Integer todoItemId) {
        TodoItem todoItem = new TodoItem();
        todoItem.setId(todoItemId);
        return commentRepository.findByTodoItem(todoItem);
    }

    public boolean delete(Integer id) {
        Optional<Comment> byId = commentRepository.findById(id);
        if(byId.isPresent()){
            TodoItem todoItem = commentRepository.findById(id).get().getTodoItem();
            todoItemRepository.findById(todoItem.getId()).get().getComment()
                    .removeIf(a->a.getId() ==id);
            commentRepository.delete(byId.get());
            return true;
        }else{
            throw new IllegalArgumentException("id is not exist");
        }
    }
}
