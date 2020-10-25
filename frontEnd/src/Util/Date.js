

const parseDate = (date) => {
    var arr = date.split("T");
    var d = arr[0];
    var darr = d.split('-');
    var t = arr[1];
    var tarr = t.split('.000');
    var marr = tarr[0].split(':');
    return parseInt(darr[0]) + "/" + parseInt(darr[1]) + "/" + parseInt(darr[2]) + " " + parseInt(marr[0]) + ":" + parseInt(marr[1]) + ":" + parseInt(marr[2]);
}

const formatDateTime = (date) => {
    let time = new Date(Date.parse(date));
    time.setTime(time.setHours(time.getHours() + 8));

    // alert(time);
    // if (!isNaN(time)) {
    //     return new Date(Date.parse(date.replace(/-/g, "/")));
    // } else {
    let Y = time.getFullYear() + '-';
    let M = addZero(time.getMonth() + 1) + '-';
    let D = addZero(time.getDate()) + ' ';
    let h = addZero(time.getHours()) + ':';
    let m = addZero(time.getMinutes()) + ':';
    let s = addZero(time.getSeconds());
    return Y + M + D + h + m + s;
    // }
}
// 数字补0操作
const addZero = (num) => {
    return num < 10 ? '0' + num : num;
}

module.exports = {
    parseDate,
    formatDateTime
}