$(function () {
    $("[data-toggle='popover']").popover()
})
//提交查询请求
$('#searchId').click(submit);
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
    alert(flag)
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
    //先显示所有的数据
    var content = "<tr>" +
        "              <td id='dataKey'>data.key</td>" +
        "              <td ><input type='text' value='数据' id='valuePath'></td>" +
        "              <td>"+path+"</td>" +
        "               <td>" +
        "                     <div class=\"btn-group\">" +
        "                        <button type=\"button\" class=\"btn btn-default \"  id='updateValue' " +
        "onclick=\"editValue(\'"+path+"\')\">修改</button>" +
        "                        <button type=\"button\" class=\"btn btn-default disabled\">删除</button>" +
        "                     </div>" +
        "                </td>" +
        "           </tr>"


    var footer = "<tr>" +
        "                            <td>" +
        "                                <div class=\"btn-group\">" +
        "                                    <button type=\"button\" class=\"btn btn-default\">增加</button>" +
        "                                </div>" +
        "                            </td>" +
        "                        </tr>"
    $("tbody").html(content+content)
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
            "path":path,
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
            "              <td>"+data[key]+"</td>" +
            "               <td>" +
            "                     <div class=\"btn-group\">" +
            "                        <button type=\"button\" class=\"btn btn-default disabled\">详情页禁止修改数据</button>" +
            "                     </div>" +
            "                </td>" +
            "           </tr>"
  }

    //todo 针对path多数据的显示在这里修改


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