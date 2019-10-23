package com.example.todoList.Service;

import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;

    public List<TodoList>  getAll() {
        return todoListRepository.findAll();
    }

    public TodoList create(String description) {
        int count = todoListRepository.findAll().size();
        int order_index = count ==0 ? count : count++;
        TodoList list = new TodoList(description, Status.pending,order_index);
        return todoListRepository.save(list);
    }


    public boolean update(Integer id,String status) {
        TodoList one = todoListRepository.findById(id).get();
        if (one.equals(null)) {
            return false;
        }else{
            if (status.equals(Status.pending.toString()) || status.equals(Status.finish.toString())) {
                one.setStatus(Status.valueOf(status));
                todoListRepository.save(one);
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean deleteList(Integer id) {
        TodoList list = todoListRepository.findById(id).get();
        int indexOrder = list.getIndexOrder();
        if (todoListRepository.findById(id).equals(Optional.empty())) {
            return false;
        }else if(indexOrder<todoListRepository.findAll().size()){
            todoListRepository.deleteById(list.getId());
            List<TodoList> allByOrderByIndexOrder = todoListRepository.findAllByOrderByIndexOrder();
            allByOrderByIndexOrder.stream().map(
                    item -> {
                        if(item.getIndexOrder() > indexOrder){
                            item.setIndexOrder(item.getIndexOrder()-1);
                            todoListRepository.save(item);
                        }
                        return item;
            }).collect(Collectors.toList());
        }
        todoListRepository.deleteById(list.getId());
        return true;
    }

    public List<TodoList> updateIndex(Integer startIndex, Integer endIndex) {
        List<TodoList> allOrderByIndexOrder = todoListRepository.findAllByOrderByIndexOrder();
        if(startIndex < endIndex){
           allOrderByIndexOrder.stream().map(
                    item -> {
                        if (item.getIndexOrder() == startIndex) {
                            item.setIndexOrder(endIndex);
                            todoListRepository.save(item);
                        } else if (item.getIndexOrder() > startIndex) {
                            item.setIndexOrder(item.getIndexOrder() - 1);
                            todoListRepository.save(item);
                        }
                        return item;
                    }
            ).collect(Collectors.toList());
        }else if(startIndex > endIndex){
              allOrderByIndexOrder.stream().map(
                    item -> {
                        if (item.getIndexOrder() == startIndex) {
                            item.setIndexOrder(endIndex);
                            todoListRepository.save(item);
                        } else if (item.getIndexOrder() >= endIndex) {
                            item.setIndexOrder(item.getIndexOrder() + 1);
                            todoListRepository.save(item);
                        }
                        return item;
                    }
            ).collect(Collectors.toList());
        }
        return todoListRepository.findAllByOrderByIndexOrder();
    }
}
