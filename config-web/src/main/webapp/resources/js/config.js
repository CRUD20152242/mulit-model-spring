$(
    function () {
        
    }
)
var count = 2;
$("#addIteam").click(addkey)
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
        "                        <input type='text' class='form-control' class='value"+count+"' aria-label='...'>" +
        "                    </div>" +
        "    <br class='valueGroup"+count+"'/>"

    $("#addValue").append(addValue)
}
$("#deleteIteam").click(deleteIteam)

function deleteIteam() {
    $(".keyGroup"+count).remove()
    $(".valueGroup"+count).remove()
    $("#keyGroup"+count).remove()
    $("#valueGroup"+count).remove()

    count--

    if(count<3){
        count=2;
    }


}