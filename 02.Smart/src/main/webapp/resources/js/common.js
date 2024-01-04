

$(function (){

    if($(".date").length>0){

        var today = new Date();
        var range = today.getFullYear()-100+":"+today.getFullYear();//"1990 : 2023"; //지금으로부터 100년전
        $.datepicker.setDefaults({
            dateFormat: "yy-mm-dd",
            changeYear: true,
            changeMonth: true,
            showMonthAfterYear: true,
            dayNamesMin:["일","월","화","수","목","금","토"],
            monthNamesShort:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
            maxDate: today,
            yearRange: range,
        });
    };
    $(".date").datepicker();
    $(".date").attr("readonly",true);

    // 날짜 선택시 삭제 보이게
    $(".date").change(function (){
        $(this).next(".date-delete").css("display","inline");
    });
    // 삭제 클릭시 날짜 없애기
    $(".date-delete").click(function (){
        $(this).prev(".date").val("");
        $(this).css("display","none");
    });

//     파일 선택시
    $("input#file-single").change(function (){
        // console.log($(this))
        // console.log(this.files[0]);//속성 '값'을 꺼낼 때는 제이쿼리 x

        var _preview = $(this).closest(".file-info").find(".file-preview");
        var _delete = $(this).closest(".file-info").find(".file-delete");
        var attached = this.files[0];

        // if(attached != "undefined")
        if(attached ){
            // console.log(attached)
            // console.log('name>', attached.name);
        //     파일 크기 제한을 두고자 한다면
            if(rejectFile(attached, $(this))) return;



        //     이미지만 첨부 해야 한다면
            if(isImg(attached.name)){
                _delete.removeClass("d-none"); // 삭제 보이게

               if(_preview.length>0){
                   _preview.html("<img>");// img 요소 추가
                   
                   var reader = new FileReader();
                   reader.readAsDataURL(attached);
                   reader.onload = function (e){
                       // console.log(e.target.result)
                       // console.log(this.result)
                       _preview.children("img").attr("src",this.result);
                   }
               }
            } else {
                console.log($(this).val())
                initFileInfo($(this))
                // _preview.empty();
                // _delete.addClass("d-none");
                // _preview.children("img").remove();
               // $(this).val("");
            }
            console.log('attached>', attached)
        }
    })
    $(".file-delete").click(function (){
        // var _info =$(this).closest(".file-info")
        // _info.find(".file-preview").empty();
        // _info.find("input[type=file]").val("");
        // $(this).addClass("d-none");
        initFileInfo($(this))
    })

})

//이미지 판단
function isImg (filename){
//     abc.png, abc.jpg....
    var imgs = ["png", "jpg", "jpeg",  "gif", "bmp", "webp"]
    var ext = filename.substr(filename.lastIndexOf(".")+1);
//     substr(start, n개), substring(start, finish)
    return imgs.indexOf(ext) == -1 ? false : true;
}

function rejectFile( fileInfo, tag){
    // 1K = 1024 , 1M = 1024*1024 , 1G = 1024*1024*1024
    if(fileInfo.size >10*1024*1024){//15M
        alert("10Mb를 넘는 파일은 첨부할 수 없습니다")
        // tag.val("");
        // tag.closest(".file-info").find(".file-preview").empty();
        // tag.closest(".file-info").find(".file-delete").addClass("d-none");
        initFileInfo(tag)
        return true;
    } else
        return false;
}

function initFileInfo(tag){
    var _info =tag.closest(".file-info")
    _info.find(".file-preview").empty();
    _info.find("input[type=file]").val("");
    _info.find(".file-delete").addClass("d-none");
}