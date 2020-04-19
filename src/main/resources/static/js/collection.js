function f(id) {
    var str = document.getElementById("sc").getAttribute("class")
    if (str == "glyphicon glyphicon-star-empty") {
        //如果是空心，则表示是未收藏，则执行函数 fa（），实现收藏
        fa(id)
    } else {
        //如果是实心，则是已经收藏，点击则执行函数 cancel(),取消收藏
        cancel(id)
    }
}

function fa(i) {
    $.ajax({
        url:'/collection',
        type:'get',
        data:{'id':i},
        success:function (data) {
            if (data=="收藏成功~"){
                document.getElementById("sc").setAttribute("class","glyphicon glyphicon-star");
                // document.getElementById("sc").innerText="已收藏";
                alert(data);
            }else if(data=="这篇文章已经收藏啦，快去收藏夹看看吧~"){
                alert(data);
            }else if (data == "收藏失败~发生了一点小错误~") {
                alert(data);
            }
        }
    });
}

function cancel(id) {
    $.ajax({
        url:'/cancelcollectioned',
        type:'get',
        data:{'id':id},
        success:function (data) {
            if(data=="已取消收藏~"){
                //修改样式为 空心 ，字样修改为 ‘收藏’
                document.getElementById("sc").setAttribute("class","glyphicon glyphicon-star-empty");
                // document.getElementById("sc").innerText="收藏";
                alert(data);
            }else if (data=="发生了一点小错误~请稍后再试"){
                alert(data);
            }
        }
    });
}


