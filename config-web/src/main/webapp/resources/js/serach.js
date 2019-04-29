$(function () {
    $("[data-toggle='popover']").popover()
})
var keys = "";
var flags = "xq"
var firstSize = 0;
//提交查询请求
$('#searchId').click(submit);
//提交修改删除增加请求
$('#submitAll').click(submitAll);

//修改数据  算法思想是 记录所有的操作 然后后台对比
function submitAll() {
    if(flags=="xq"){
        return
    }
    if (!confirm("是否保存更改?")) {
        return
    }
//    获取所有删除的key
        var delDatas = keys
//    获取所有增加的key和value与tips
    var first = "{";
    var  content = "";
    var last = "}";
    var path =getPath();
    for (var index = 1;index <pageSize;index++) {
        if(pageSize==1){
            return
        }
        if(index<=firstSize){
            var key = $("#key"+index).text()
            var value=$("#value"+index).val()
            var tips =$("#tips"+index).text();
            var iteam="";
            iteam = "\""+key+"\":[\""+value+"\",\""+tips+"\"],"
            content +=iteam
        }else {
            var key = $("#key"+index).val()
            var value=$("#value"+index).val()
            var tips =$("#tips"+index).val()
            var iteam="";
            iteam = "\""+key+"\":[\""+value+"\",\""+tips+"\"],"
            content +=iteam
        }


    }
    if(pageSize>firstSize){
        var key = $("#key"+pageSize).val()
        var value=$("#value"+pageSize).val()
        var tips =$("#tips"+pageSize).val()
        iteam = "\""+key+"\":[\""+value+"\",\""+tips+"\"]"
    }else {
        var key = $("#key"+pageSize).text()
        var value=$("#value"+pageSize).val()
        var tips =$("#tips"+pageSize).text()
        iteam = "\""+key+"\":[\""+value+"\",\""+tips+"\"]"
    }



    content+=iteam;
    var datas = first+content+last;

    $.ajax({
        url : "http://localhost:8080/config/updateDatas",
        type : "post",
        data : {
            "path":path,
            "datas":datas,
            "delDatas":delDatas
        },
        dataType:"json",
        success : function (f) {
            alert(f.mesaage)
        },
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060")
        }
    });
    flags="xq"
}

function submit() {

    var path = $("#pathId").val();
    var selectScope = $("#selectScope").val();
    $.ajax({
        url : "http://localhost:8080/config/allPaths",
        type : "post",
        data : {
            "path":path,
            "scope":selectScope
        },
        dataType:"json",
        success : showPaths,
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060")
        }
    });
}
//渲染path页面
function showPaths(flag) {
    var listSize = flag.length;
    var results="            ";
    var leftResult="            ";
    //渲染右侧
    for (var i = 1; i < listSize; i=i+2) {
        var message = "\'要删除的序号是"+i+"\'";
        results =results+
            "<br/>"+
            "<div class='panel panel-default'>" +
                "<div class='panel-heading  panel-primary'>" +
                    "<h3 class='panel-title'>path</h3>" +
                "</div>" +
                 "<div class='panel-body'>" +
                    flag[i]+
                "</div>" +
                "<div class='panel-info'>" +
                    "<div class='btn-group btn-group-justified' role='group' aria-label='...'>" +
                        "<div class='btn-group' role='group'>" +
                             "<button type='button' class='btn btn-default' id='del"+i+"' title='删除path 同时删除值 父节点有子节点则父节点不能删除'"+
        "onclick=\"deletePath(\'"+flag[i]+"\')\">删除</button>" +
                        "</div>" +
                        "<div class='btn-group' role='group'>" +
                             "<button type='button' class='btn btn-default'  data-toggle=\"modal\" data-target=\"#myModal\" id='showData"+i+"' " +
            "  onclick=\"showDatas(\'"+flag[i]+"\')\"> 详情</button>" +
                        "</div>" +
                         "<div class='btn-group' role='group'>" +
                                "<button type='button' class='btn btn-default' data-toggle=\"modal\" data-target=\"#myModal\"   id='updateData"+i+"' title='修改本path的数据' " +
            "onclick=\"updateData(\'"+ flag[i]+"\')\">修改</button>" +
                         "</div>" +
                    "</div>" +
            "</div>" +
         "</div>"
    }
    $(".rightPath").html(results)
    //渲染左侧
    for (var i = 0; i < listSize; i=i+2) {
        leftResult =leftResult+
            "<br/>"+
            "<div class='panel panel-default'>" +
            "<div class='panel-heading  panel-primary'>" +
            "<h3 class='panel-title'>path</h3>" +
            "</div>" +
            "<div class='panel-body'>" +
            flag[i]+
            "</div>" +
            "<div class='panel-info'>" +
            "<div class='btn-group btn-group-justified' role='group' aria-label='...'>" +
            "<div class='btn-group' role='group'>" +
            "<button type='button' class='btn btn-default' id='del"+i+"' title='删除path 同时删除值 父节点有子节点则父节点不能删除' " +
            "onclick=\"deletePath(\'"+flag[i]+"\')\">删除</button>" +
            "</div>" +
            "<div class='btn-group' role='group'>" +
            "<button type='button' class='btn btn-default' data-toggle=\"modal\" data-target=\"#myModal\"  id='showData"+i+"' " +
            "onclick=\"showDatas(\'"+flag[i]+"\')\"> 详情</button>" +
            "</div>" +
            "<div class='btn-group' role='group'>" +
            "<button type='button' class='btn btn-default' data-toggle=\"modal\" data-target=\"#myModal\"  id='updateData"+i+"' title='修改本path的数据' " +
            "onclick=\"updateData(\'"+ flag[i]+"\')\">修改</button>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>"
        $(".leftPath").html(leftResult)
    }
}

//修改 添加 删除path上的数据
function updateData(path) {
    flags=4
    setPath(path)

    //先显示所有的数据
    $.ajax({
        url : "http://localhost:8080/config/getData",
        type : "post",
        data : {
            "path":path
        },
        dataType:"json",
        success :updateDatas,
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060" +f)
        }
    });
}
function getPath() {
    return path;
}
var pageSize = 0;
function updateDatas(str) {
    pageSize=0;
    firstSize = 0;
    var content = "";
    for (var key in str){
        pageSize++;
        firstSize++;
        content += "<tr>" +
            "              <td id='key"+pageSize+"'>"+key+"</td>" +
            "              <td><input  id='value"+pageSize+"' value='"+str[key][0]+"'></td>" +
            "              <td id='tips"+pageSize+"'>"+str[key][1]+"</td>" +
            "               <td>" +
            "                     <div class=\"btn-group\">" +
            "                        <button type=\"button\" class=\"btn btn-default \" id='del"+key+"' onclick=\"deleteOne('"+key+",')\">删除</button>" +
            "                        <button type=\"button\" class=\"btn btn-default \" id='add"+key+"' onclick=\"addOne()\">增加</button>" +
            "                     </div>" +
            "                </td>" +
            "           </tr>"
    }

    //todo 针对path多数据的显示在这里修改


    $("tbody").html(content)
}
var addIteam = "";
function addOne() {
    pageSize++;
    addIteam =  "<tr id='addIteam"+pageSize+"'>" +
        "              <td><input type='text' id='key"+pageSize+"'></td>" +
        "              <td><input type='text' id='value"+pageSize+"'></td>" +
        "              <td><input type='text' id='tips"+pageSize+"'></td>" +
        "               <td>" +
        "                     <div class=\"btn-group\">" +
        "                        <button type=\"button\" class=\"btn btn-default \" onclick=\"deleteAdd('addIteam"+pageSize+"')\" >删除</button>" +
        "                        <button type=\"button\" class=\"btn btn-default \" onclick=\"addOne()\" >增加</button>" +
        "                     </div>" +
        "                </td>" +
        "           </tr>"
    $("tbody").append(addIteam);
}
function deleteAdd(id) {
    $("#"+id).remove();
    pageSize--;
}
function deleteOne(key) {
    if(!confirm("正在删除key，不会立即生效 确定删除？")){
        return
    }
    keys+=key;
    if($.isEmptyObject(key)){
        return
    }
}

function editValue(path) {
    var path = path;
    var key   =$("#dataKey").html()
    var value  =$("#valuePath").val()
    alert("修改的数据是："+path +"key = " +key+ "value = " + value)
}
//查看path的数据详情 禁止修改
function showDatas(path) {

    $.ajax({
        url : "http://localhost:8080/config/getData",
        type : "post",
        data : {
            "path":path
        },
        dataType:"json",
        success :getDataSuccess,
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060" +f)
        }
    });

}

function getDataSuccess(data) {
    var content = "";
    for (var key in data){
        content += "<tr>" +
            "              <td>"+key+"</td>" +
            "              <td>"+data[key][0]+"</td>" +
            "              <td>"+data[key][1]+"</td>" +
            "               <td>" +
            "                     <div class=\"btn-group\">" +
            "                        <button type=\"button\" class=\"btn btn-default disabled\">详情页禁止修改数据</button>" +
            "                     </div>" +
            "                </td>" +
            "           </tr>"
  }


    $("tbody").html(content)
}

//删除path
function deletePath(path) {
    $.ajax({
        url : "http://localhost:8080/config/deletePath",
        type : "post",
        data : {
            "path":path,
        },
        dataType:"json",
        success : function (f) {
            //后端返回  删除成功/删除失败的原因
            alert(f.mesaage)
        },
        error:function (f) {
            alert("未知错误 联系管理员 qq 2314449060" +f)
        }
    });
}

var path;
function setPath(path1) {
    path = path1;
}