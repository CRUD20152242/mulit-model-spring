var count = 2;
$("#addIteam").click(addkey)
$("#createPath").click(createPath)
$("#submitData").click(submitData)
$("#deleteIteam").click(deleteIteam)

function createPath() {
    var path = $("#inputPath").val();
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
    if (count>29){
        alert("key 的个数太多了")
        return
    }
    count++;
   var res = "<div class='input-group' id='keyGroup"+count+"'>" +
       "                        <span class='input-group-addon' >key</span>" +
       "                        <input type='text' class='form-control' id='key"+count+"' aria-label='...'>" +
       "                    </div>"+
       "    <br class='keyGroup"+count+"'/>"

    $("#addKey").append(res)

    var addValue = "    <div class='input-group' id='valueGroup"+count+"'>" +
        "                        <span class='input-group-addon value1'>value</span>" +
        "                        <input type='text' class='form-control' id='value"+count+"' aria-label='...'>" +
        "                    </div>" +
        "    <br class='valueGroup"+count+"'/>"

    $("#addValue").append(addValue)

    var addTips = "<div class=\"input-group\" id='tipsGroup"+count+"'>" +
        "                        <span class=\"input-group-addon\">说明：</span>" +
        "                        <input type=\"text\" class=\"form-control\" aria-label=\"...\" id='tips"+count+"'>" +
        "                    </div>"+
        "    <br class='valueGroup"+count+"'/>"

    $("#addTips").append(addTips);
}

//提交数据
//todo 添加路径与判空操作
function submitData() {
    if(!confirm("创建节点时请先创建path 若path已存在则会覆盖原来的数据 是否继续？")){
        return
    }
    var iteam="";
var first = "{";
var  content = "";
var last = "}";
var path = $("#inputPath").val();
        for (var index = 1;index <count;index++) {
            var key = $("#key"+index).val()
            if (key =="") {
                break;
            }
            var value=$("#value"+index).val()
            var tips =$("#tips"+index).val();

                iteam = "\""+key+"\":[\""+value+"\",\""+tips+"\"],"



            content +=iteam
        }
    var key = $("#key"+count).val()
    if (key =="") {
        alert("有key为null  请删除或者填入")
    }
    var value=$("#value"+count).val()
    var tips =$("#tips"+count).val();
        iteam = "\""+key+"\":[\""+value+"\",\""+tips+"\"]"
    content+=iteam
        var datas = first+content+last;

    $.ajax({
        url : "http://localhost:8080/config/addData",
        type : "post",
        data : {
            "datas":datas,
            "path":path,
        },
        dataType:"json",
        success : addSucess,
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060"+f)
        }
    });
// 拼接数据的格式时  {"默认的key":["默认的value","默认测试数据 添加数据时会覆盖"],"默认的key2":["默认的value","默认测试数据 添加数据时会覆盖"]}
    
}

function addSucess(f) {
    alert(f.mesaage)
}

function deleteIteam() {
    $(".keyGroup"+count).remove()
    $(".valueGroup"+count).remove()
    $("#keyGroup"+count).remove()
    $("#valueGroup"+count).remove()
    $("#tipsGroup"+count).remove()
    $("#tips"+count).remove()

    count--

    if(count<3){
        count=1;
    }


}