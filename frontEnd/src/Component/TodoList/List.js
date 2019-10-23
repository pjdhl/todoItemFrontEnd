import React, { Component } from 'react';
import axios from 'axios';
import Item from '../TodoItem/Item';
import './list.css';

class List extends Component {
    constructor(props) {
        super(props)
        this.state = {
            todoList: [],
            addValue: ""
        }
    }
    componentDidMount() {
        this.getList();
    }
    getList() {
        axios.get("http://localhost:8080/api/all")
            .then(res => {
                this.setState({
                    todoList: res.data
                })
            }).catch(error => {
                console.log(error)
            });
    }

    saveItem(desc) {
        axios({
            method: 'post',
            url: 'http://localhost:8080/api/one',
            params: {
                description: desc,
            }
        }).then((res) => {
            this.getList();
        }).catch((error) => {
            console.log(error)
        });
    }
    removeItem(index) {
        axios({
            method: 'delete',
            url: 'http://localhost:8080/api/one',
            params: {
                id: index
            }
        }).then((res) => {
            this.getList();
        }).catch((error) => {
            console.log(error)
        });
    }
    changeStatus(item, status) {
        axios({
            method: 'patch',
            url: `${"http://localhost:8080/api/status/" + item.id}`,
            params: {
                status: status
            }
        }).then((res) => {
            this.getList();
        }).catch((error) => {
            console.log(error)
        });
    }

    changeText(e) {
        this.setState({
            addValue: e.target.value
        })
    }

    addList() {
        this.saveItem(this.state.addValue, 0);
        this.setState({
            addValue: ""
        })
    }

    render() {
        var items = this.state.todoList.map((item, index) => {
            return (
                <Item key={index} item={item} index={item.id} removeItem={this.removeItem.bind(this, item.id)} markTodoDone={this.changeStatus.bind(this, item, item.status === "finish" ? "pending" : "finish")} />
            );
        });
        return (
            <div className="list">
                <ul className="list-group"> {items} </ul>
                <input type="text" onChange={this.changeText.bind(this)} value={this.state.addValue} className="add-item" placeholder="add a new todo..." />
                <button onClick={this.addList.bind(this)} className="btn">Add</button>
            </div>
        );
    }
}


export default List;
