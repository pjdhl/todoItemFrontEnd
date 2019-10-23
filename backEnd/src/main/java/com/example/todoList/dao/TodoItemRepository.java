package com.example.todoList.dao;

import com.example.todoList.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {

    List<TodoItem> findAllById(Iterable<Integer> integers);

    List<TodoItem> findAllByOrderByIndexOrder();
}
