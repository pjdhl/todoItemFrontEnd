/* eslint-disable jsx-a11y/accessible-emoji */
import React, { useRef, useState } from 'react';
import { useDrag, useDrop } from 'react-dnd'
import './Item.css';
import { parseDate, formatDateTime } from "../../Util/Date"
import Comment from '../Comment/comment';
const Item = ({ item, id, markTodoDone, index, removeItem, moveItem }) => {
    const [visible, setVisible] = useState("none");
    const todoClass = item.status === "pending" ? "undone" : "done";
    const ref = useRef(null);
    const [{ isOver }, drop] = useDrop({
        accept: "item",
        collect: monitor => ({
            isOver: monitor.isOver()
        }),
        hover(item, monitor) {
            if (!ref.current) {
                return
            }
            const dragIndex = item.index
            const hoverIndex = index
            // Don't replace items with themselves
            if (dragIndex === hoverIndex) {
                return
            }
            // Determine rectangle on screen
            const hoverBoundingRect = ref.current.getBoundingClientRect()
            // Get vertical middle
            const hoverMiddleY =
                (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2
            // Determine mouse position
            const clientOffset = monitor.getClientOffset()
            // Get pixels to the top
            const hoverClientY = clientOffset.y - hoverBoundingRect.top
            // Only perform the move when the mouse has crossed half of the items height
            // When dragging downwards, only move when the cursor is below 50%
            // When dragging upwards, only move when the cursor is above 50%
            // Dragging downwards
            if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
                return
            }
            // Dragging upwards
            if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
                return
            }
            if (isOver) {
                moveItem(dragIndex, hoverIndex)
                item.index = hoverIndex
            }
        },

    })

    const [{ }, drag] = useDrag({
        item: { type: "item", id, index },
        collect: monitor => ({
            isDragging: monitor.isDragging(),
        }),
    })
    drag(drop(ref))

    const foldComment = () => {
        if (visible === "none") {
            setVisible("block")
        } else {
            setVisible("none")
        }
    }
    const commentStyle = {
        display: visible,
    }
    return (
        <div>
            <li className="list-group-item">
                {
                    item.status === "pending" ?
                        <div className={todoClass} ref={ref}>
                            <span className="icon" onClick={markTodoDone}>✓{item.description}</span>
                            <span className="time">{formatDateTime(parseDate(item.createTime))}</span>
                        </div>
                        : <div className={todoClass}>
                            <span className="icon" onClick={markTodoDone}>✓{item.description}</span>
                            <span className="time">{formatDateTime(parseDate(item.createTime))}</span>
                        </div>
                }
                <button type="button" className="close" onClick={removeItem}>✖️</button>
                <button type="button" className="foldBtn" onClick={foldComment}>展开评论</button>
            </li>
            <div style={commentStyle}>
                <Comment itemId={id}></Comment>
            </div>
        </div>
    );
}


export default Item;