import React, { Component } from 'react';
import './Item.css';
class Item extends Component {

    render() {
        var todoClass = this.props.item.status === "pending" ?
            "undone" : "done";
        return (
            <li className="list-group-item">
                <div className={todoClass}>
                    <span className="icon" onClick={this.props.markTodoDone}>✓</span>
                    {this.props.item.description}
                    <button type="button" className="close" onClick={this.props.removeItem}>✖️</button>
                </div>
            </li>
        );
    }
}


export default Item;
