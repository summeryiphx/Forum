$("#form-btn").click(function() {
    var form = new FormData();
    form.append("file", document.getElementById("file").files[0]);
    $.ajax({
        "url":"/file/avatar",
        "data":form,
        "processData":false,
        "contentType":false,
        "type":"POST",
        "success":function(data) {
            if (data>0) {
                alert("上传成功！");
                window.location.href = "/updateAva";
            } else {
                alert("上传失败！");
            }
        }
    });
});

document.getElementById("file").addEventListener("change",function(e){
    var files = this.files;

    var img = new Image();
    var render  = new FileReader();
    render.readAsDataURL(files[0]);
    render.onloadstart = function(){
//				alert("start")
    };
    render.onload = function(){
        img.src = this.result;
        img.style.height = "38px";
        img.style.width = "38px";
        img.className = "img-circle";
        var avad = document.getElementById("ava-d");
        var ava = document.getElementById("ava");
        avad.style.display = "none";
        ava.style.display = "none";
        var oDiv = document.getElementById("oDiv");
        oDiv.innerHTML = "";

        oDiv.appendChild(img);
//				alert("success");
    };
    render.onloadend = function(){
//				alert("end");
    }
});