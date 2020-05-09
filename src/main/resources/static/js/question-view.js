//提交评论
function post() {
    var question_id = $("#question_id").val();
    var content = $("#content").val();
    if (!content){
        alert("评论不能为空~");
        return;
    }
    $.ajax({
        type: "POST",
        contentType:"application/json",
        url: "/comment",
        data: JSON.stringify({
            "parent_id":question_id,
            "content":content,
            "type":1,
            "question_id":question_id
        }),
        success: function (data) {
            if (data > 0) {
                alert('提交成功');
                /*window.location.href = "to_supplyexaminelist";*/
                setTimeout(function () {
                    parent.window.location.reload();
                },1000);
            }
        },
        dataType: "json"
    });
}

//获取二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments=$("#comment-"+id);

    var collapse = e.getAttribute("data-collapse");
    if (collapse){
            comments.removeClass("in");
            e.removeAttribute("data-collapse");
        } else{
            var subCommentContainer = $("#comment-"+id);
            if (subCommentContainer.children().length != 1){
                comments.addClass("in");
                e.setAttribute("data-collapse","in");
            }else{
                $.getJSON( "/getseccomments/"+id, function( data ) {
                    $.each(data.reverse(),function(index,comment) {
                        if (comment.user.avatar!=null){
                            var mediaLeftElement = $("<div/>",{
                                "class" : "media-left"
                            }).append($("<img/>",{
                                "class" : "media-object img-rounded",
                                "src" : comment.user.avatar
                            }));
                        } else {
                            var mediaLeftElement = $("<div/>",{
                                "class" : "media-left"
                            }).append($("<img/>",{
                                "class" : "media-object img-rounded",
                                "src" : "../static/images/touxiang2.jpg"
                            }));
                        }

                        var mediaBodyElement = $( "<div/>",{
                            "class": "media-body"
                        }).append($( "<h5/>",{
                            "class": "media-heading",
                            "html" : comment.user.username
                        })).append($( "<div/>",{
                            "html": comment.content
                        })).append($( "<div/>",{
                            "class": "menu"
                        }).append($( "<span/>",{
                            "class": "pull-right",
                            "html" : moment(comment.gmt_create).format('YYYY-MM-DD')
                        })));

                        var mediaElement = $( "<div/>",{
                            "class": "media",
                        }).append(mediaLeftElement).append(mediaBodyElement);

                        var commentElement = $( "<div/>",{
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                        }).append(mediaElement);

                        subCommentContainer.prepend(commentElement);
                    });
                    comments.addClass("in");
                    e.setAttribute("data-collapse","in");
                });
            }
        }

}

//提交二级评论
function postsecond(e) {
    var commentId = e.getAttribute("data-id");
    var question_id = $("#question_id").val();
    var content = $("#reply-" + commentId).val();
    if (!content){
        alert("评论不能为空~");
        return;
    }
    $.ajax({
        type: "POST",
        contentType:"application/json",
        url: "/secondcomment",
        data: JSON.stringify({
            "parent_id":question_id,
            "content":content,
            "type":2,
            "secomment_id":commentId
        }),
        success: function (data) {
            if (data > 0) {
                alert('提交成功');
                /*window.location.href = "to_supplyexaminelist";*/
                setTimeout(function () {
                    parent.window.location.reload();
                },1000);
            } else {
                var accept = confirm("该操作需要登录，是否马上登录？");
                if (accept) {
                    window.open("/to_login");
                    window.localStorage.setItem("closable",true);
                }
            }
        },
        dataType: "json"
    });
}
function del(id) {
    var question_id = $("#question_id").val();
    var accept = confirm("确认删除？");
    if (accept) {
        $.ajax({
            dataType: "json",
            type: "POST",
            url: "/commentdelete",
            data: {
                "cid":id,
                "qid":question_id
            },
            success: function (data) {
                if (data > 0) {
                    alert('删除成功');
                    setTimeout(function () {
                        parent.window.location.reload();
                    },1000);
                } else {
                    alert('删除失败');
                    /*window.location.href = "to_supplyexaminelist";*/
                    setTimeout(function () {
                        parent.window.location.reload();
                    },1000);
                }
            }
        });
    }
}