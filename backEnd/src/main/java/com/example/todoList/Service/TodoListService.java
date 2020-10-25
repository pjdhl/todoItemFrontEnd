package com.example.todoList.Service;


import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoListService {
    @Autowired
    private TodoListRepository todoListRepository;

    public TodoList create(String name) {
        TodoList todoList = new TodoList();
        todoList.setName(name);
        TodoList save = todoListRepository.save(todoList);//TODO return directly
//        todoList.setName("testeest");持久化状态
        return save;
    }

    public boolean delete(Integer id) {
        Optional<TodoList> todoList = todoListRepository.findById(id);
        if(todoList.isPresent()){
            todoListRepository.delete(todoList.get());
            return true;
        }else{
            return false;
        }
    }

    public List<TodoList> getAll() {
        return todoListRepository.findAll();
    }
}

