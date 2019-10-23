package com.example.todoList.Service;

import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
           allOrderByIndexOrder.stream().map(
                    item -> {
                        if (item.getIndexOrder() == startIndex) {
                            item.setIndexOrder(endIndex);
                            todoItemRepository.save(item);
                        } else if (item.getIndexOrder() > startIndex) {
                            item.setIndexOrder(item.getIndexOrder() - 1);
                            todoItemRepository.save(item);
                        }
                        return item;
                    }
            ).collect(Collectors.toList());
        }else if(startIndex > endIndex){
              allOrderByIndexOrder.stream().map(
                    item -> {
                        if (item.getIndexOrder() == startIndex) {
                            item.setIndexOrder(endIndex);
                            todoItemRepository.save(item);
                        } else if (item.getIndexOrder() >= endIndex) {
                            item.setIndexOrder(item.getIndexOrder() + 1);
                            todoItemRepository.save(item);
                        }
                        return item;
                    }
            ).collect(Collectors.toList());
        }
        return todoItemRepository.findAllByOrderByIndexOrder();
    }
}
