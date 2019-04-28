var count = 2;
$("#addIteam").click(addkey)
$("#createPath").click(createPath)
$("#submitData").click(submitData)
$("#deleteIteam").click(deleteIteam)

function createPath() {
    var path = $("#inputPath").val();
    alert("创建的path 是"+path)
    $.ajax({
        url : "http://localhost:8080/config/createNode",
        type : "post",
        data : {
            "path":path,
        },
        dataType:"json",
        success : createSucess,
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060"+f)
        }
    });


}

function createSucess(flag) {
    alert(flag.mesaage)
}
function addkey() {
    count++;
   var res = "<div class='input-group' id='keyGroup"+count+"'>" +
       "                        <span class='input-group-addon' >key = </span>" +
       "                        <input type='text' class='form-control' id='key"+count+"' aria-label='...'>" +
       "                    </div>"+
       "    <br class='keyGroup"+count+"'/>"

    $("#addKey").append(res)

    var addValue = "    <div class='input-group' id='valueGroup"+count+"'>" +
        "                        <span class='input-group-addon value1'>value = </span>" +
        "                        <input type='text' class='form-control' id='value"+count+"' aria-label='...'>" +
        "                    </div>" +
        "    <br class='valueGroup"+count+"'/>"

    $("#addValue").append(addValue)
}


function submitData() {


    
}

function deleteIteam() {
    $(".keyGroup"+count).remove()
    $(".valueGroup"+count).remove()
    $("#keyGroup"+count).remove()
    $("#valueGroup"+count).remove()

    count--

    if(count<3){
        count=1;
    }


}