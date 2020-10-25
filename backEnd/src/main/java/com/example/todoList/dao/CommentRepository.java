package com.example.todoList.dao;


import com.example.todoList.domain.Comment;
import com.example.todoList.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByTodoItem(TodoItem todoItem);
}
