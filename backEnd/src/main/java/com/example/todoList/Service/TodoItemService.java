package com.example.todoList.Service;

import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoItem>  getAll() {
        return todoItemRepository.findAllByOrderByIndexOrder();
    }

    public TodoItem create(String description) {
        int count = todoItemRepository.findAll().size();
        TodoItem list = new TodoItem(description, Status.pending,count);
        return todoItemRepository.save(list);
    }


    public boolean update(Integer id,String status) {
        TodoItem one = todoItemRepository.findById(id).get();
        if (one.equals(null)) {
            return false;
        }else{
            if (status.equals(Status.pending.toString()) || status.equals(Status.finish.toString())) {
                one.setStatus(Status.valueOf(status));
                todoItemRepository.save(one);
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean delete(Integer id) {
        Optional<TodoItem> itemOption = todoItemRepository.findById(id);
        if (!itemOption.isPresent()) {
            return false;
        }else {
            int indexOrder = itemOption.get().getIndexOrder();
            todoItemRepository.deleteById(id);
            List<TodoItem> allByOrderByIndexOrder = todoItemRepository.findAllByOrderByIndexOrder();
            for (TodoItem todoItem : allByOrderByIndexOrder) {
                if(todoItem.getIndexOrder() > indexOrder){
                    todoItem.setIndexOrder(todoItem.getIndexOrder()-1);
                    todoItemRepository.save(todoItem);
                }
            }
        }
        return true;
    }

    public List<TodoItem> updateIndex(Integer startIndex, Integer endIndex) {
        List<TodoItem> allOrderByIndexOrder = todoItemRepository.findAllByOrderByIndexOrder();
        if(startIndex < endIndex){
            changeIndexOrder(startIndex, endIndex, allOrderByIndexOrder);
        }else if(startIndex > endIndex){
            for (TodoItem todoItem: allOrderByIndexOrder) {
                if(startIndex > allOrderByIndexOrder.size() || endIndex > allOrderByIndexOrder.size()){
                    throw new IllegalArgumentException("index order has over list size");
                }else if (todoItem.getIndexOrder() == startIndex){
                    todoItem.setIndexOrder(endIndex);
                    todoItemRepository.save(todoItem);
                }else if(todoItem.getIndexOrder() >= endIndex){
                    todoItem.setIndexOrder(todoItem.getIndexOrder() + 1);
                    todoItemRepository.save(todoItem);
                }
            }
        }
        return todoItemRepository.findAllByOrderByIndexOrder();
    }

    private void changeIndexOrder(Integer startIndex, Integer endIndex, List<TodoItem> allOrderByIndexOrder) {
        for (TodoItem todoItem: allOrderByIndexOrder) {
            if(startIndex > allOrderByIndexOrder.size() || endIndex > allOrderByIndexOrder.size()){
                throw new IllegalArgumentException("index order has over list size");
            }else if (todoItem.getIndexOrder() == startIndex){
                todoItem.setIndexOrder(endIndex);
                todoItemRepository.save(todoItem);
            }else if(todoItem.getIndexOrder() > startIndex){
                todoItem.setIndexOrder(todoItem.getIndexOrder() - 1);
                todoItemRepository.save(todoItem);
            }
        }
    }
}
