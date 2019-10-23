package com.example.todoList.dao;

import com.example.todoList.domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TodoListRepository extends JpaRepository<TodoList, Integer> {

    List<TodoList> findAllById(Iterable<Integer> integers);

    List<TodoList> findAllByOrderByIndexOrder();
}
