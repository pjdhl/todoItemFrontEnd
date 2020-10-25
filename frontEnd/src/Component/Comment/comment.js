import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import "./comment.css"
import { parseDate, formatDateTime } from "../../Util/Date"
const Comment = ({ itemId }) => {
    const [comment, setComment] = useState([]);
    const [addValue, setAddValue] = useState([]);
    useEffect(() => {
        getComment();
    }, [])
    const getComment = () => {
        axios.get("http://localhost:8080/api/" + itemId + "/comments")
            .then((res) => {
                console.log(res)
                setComment(res.data);
            })
    }
    const removeComment = (id) => {
        axios({
            method: 'delete',
            url: 'http://localhost:8080/api/' + id + '/comment',
        }).then((res) => {
            getComment()
        }).catch((error) => {
            console.log(error)
        });
    }
    const saveComment = (text) => {
        axios({
            method: 'post',
            url: 'http://localhost:8080/api/' + itemId + '/comments',
            data: {
                text: text
            }
        }).then((res) => {
            getComment();
        }).catch((error) => {
            console.log(error)
        });
    }
    const changeText = (e) => {
        setAddValue(e.target.value)
    }
    const addComment = () => {
        saveComment(addValue);
        setAddValue("");
    }
    const commentItem = comment.map((item, index) => {
        return (
            <li key={index} className="list-group-item">
                <div>
                    <span className="icon">{item.text}</span>
                    <span className="time">{formatDateTime(parseDate(item.createTime))}</span>
                </div>
                <button type="button" className="close" onClick={() => { removeComment(item.id) }}>✖️</button>

            </li>
        )
    })
    return (
        <div className="comment">
            {commentItem}
            <div className="addValue">
                <input type="text" onChange={changeText} value={addValue} className="add-item" placeholder="add ...." />
                <button onClick={addComment} className="btn">添加Comment</button>
            </div>
        </div>

    )
}

export default Comment;