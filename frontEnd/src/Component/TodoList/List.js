import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import Item from '../TodoItem/Item';
import './list.css';
import update from 'immutability-helper'

const List = ({ remove, title, items = [], listId, refresh }) => {
    const list = (items.filter(item => item.status === "pending")).concat(items.filter(item => item.status === "finish"))
    const [item, setItem] = useState(list);
    const [addValue, setAddValue] = useState("");

    useEffect(() => {
        setItem(list)
    }, [items])
    const saveItem = (desc) => {
        axios({
            method: 'post',
            url: 'http://localhost:8080/api/one',
            params: {
                description: desc,
                todoListId: listId
            }
        }).then((res) => {
            refresh()
        }).catch((error) => {
            console.log(error)
        });
    }
    const removeItem = (index) => {
        axios({
            method: 'delete',
            url: 'http://localhost:8080/api/one',
            params: {
                id: index
            }
        }).then((res) => {
            refresh()
        }).catch((error) => {
            console.log(error)
        });
    }
    const changeStatus = (item, status) => {
        axios({
            method: 'patch',
            url: `${"http://localhost:8080/api/status/" + item.id}`,
            params: {
                status: status
            }
        }).then((res) => {
            refresh()
        }).catch((error) => {
            console.log(error)
        });
    }

    const changeIndexOrder = (startIndex, endIndex) => {
        axios({
            method: 'patch',
            url: "http://localhost:8080/api/indexOrder",
            params: {
                startIndex: startIndex,
                endIndex: endIndex
            }
        }).then((res) => {
            refresh()
        }).catch((error) => {
            console.log(error)
        });
    }
    const changeText = (e) => {
        setAddValue(e.target.value)
    }

    const addList = () => {
        saveItem(addValue);
        setAddValue("");
    }
    const moveItem = useCallback(
        (dragIndex, hoverIndex) => {
            const dragItem = item[dragIndex]
            setItem(
                update(item, {
                    $splice: [[dragIndex, 1], [hoverIndex, 0, dragItem]],
                }),
            )
            changeIndexOrder(item[dragIndex].indexOrder, item[hoverIndex].indexOrder)
        }, [item],
    )
    const todoList = item.map((item, index) => {
        return (
            <Item key={index}
                index={index}
                item={item}
                id={item.id}
                moveItem={moveItem}
                removeItem={() => { removeItem(item.id) }}
                markTodoDone={() => { changeStatus(item, item.status === "finish" ? "pending" : "finish") }} />
        );
    });

    return (
        <div className="item">
            <header className="listHeader">{title}</header>
            <button className="deleteList" onClick={remove}>删除todoList</button>
            <ul className="list-group"> {todoList} </ul>
            <input type="text" onChange={changeText} value={addValue} className="add-item" placeholder="add a new todo item..." />
            <button onClick={addList} className="btn">添加Item</button>
        </div>
    )
}


export default List;