// $(document).ready()의 약식
$(function () {
// 날짜에 대한 처리
    if ($(".date").length > 0) {

        var today = new Date();
        var range = today.getFullYear() - 100 + ":" + today.getFullYear();//"1990 : 2023"; //지금으로부터 100년전
        $.datepicker.setDefaults({
            dateFormat: "yy-mm-dd",
            changeYear: true,
            changeMonth: true,
            showMonthAfterYear: true,
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
            monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
            maxDate: today,
            yearRange: range,
        });
    }
    ;
    $(".date").datepicker();
    $(".date").attr("readonly", true);

    // 날짜 선택시 삭제 보이게
    $(".date").change(function () {
        $(this).next(".date-delete").css("display", "inline");
    });
    // 삭제 클릭시 날짜 없애기
    $(".date-delete").click(function () {
        $(this).prev(".date").val("");
        $(this).css("display", "none");
    });

//     파일 선택시
    $("input#file-single").change(function () {
        // console.log($(this))
        // console.log(this.files[0]);//속성 '값'을 꺼낼 때는 제이쿼리 x

        var _preview = $(this).closest(".file-info").find(".file-preview");
        var _delete = $(this).closest(".file-info").find(".file-delete");
        var _name = $(this).closest(".file-info").find(".file-name");
        var attached = this.files[0];

        // if(attached != "undefined")
        if (attached) {
            // console.log(attached)
            // console.log('name>', attached.name);
            //     파일 크기 제한을 두고자 한다면
            if (rejectFile(attached, $(this))) return;

            _name.text(attached.name);
            _delete.removeClass("d-none"); // 삭제 보이게

            //     이미지만 첨부 해야 한다면
            if (isImg(attached.name)) {

                if (_preview.length > 0) {
                    _preview.html("<img>");// img 요소 추가

                    var reader = new FileReader();
                    reader.readAsDataURL(attached);
                    reader.onload = function (e) {
                        // console.log(e.target.result)
                        // console.log(this.result)
                        _preview.children("img").attr("src", this.result);
                    }
                }
            } else {
                console.log($(this).val())
                if ($(this).hasClass("image-only")) {

                    initFileInfo($(this))
                }
                // _preview.empty();
                // _delete.addClass("d-none");
                // _preview.children("img").remove();
                // $(this).val("");
            }
            console.log('attached>', attached)
        }
    });
    $(".file-delete").click(function () {
        // var _info =$(this).closest(".file-info")
        // _info.find(".file-preview").empty();
        // _info.find("input[type=file]").val("");
        // $(this).addClass("d-none");
        initFileInfo($(this))
    });

    $(".file-drag")
        .on("dragover dragleave drop", function (e) {
            e.preventDefault();// 드롭을 허용하기 위해 기본 동작 취소
            //드래그 오버시 입력태그에 커서 있을 때 처럼 적용하기
            if (e.type == "dragover") $(this).addClass("drag-over");
            else $(this).removeClass("drag-over");
        })
        .on("drop", function (e) {
            // console.log("e>", e);
            // console.log("e>", e.originalEvent.dataTransfer.files);
            var files = filterFolder(e.originalEvent.dataTransfer);

            $(files).each(function () {
                fileList.setFile(this);
            });
            // console.log('fileList>', fileList);
            fileList.showFile();
        })
    $("body").on("dragover dragleave drop", function (e) {
        e.preventDefault();
    });

    $("#file-multiple").on("change", function () {
        var files = this.files;
        $(files).each(function () {
            fileList.setFile(this)
        })
        fileList.showFile();
    });

})

// 파일 관련 처리
function FileList() {
    this.files = [];
    this.info = {upload: [], id: [], remove: []};// 업로드 여부, 업로드 되어있는 파일 id, 삭제할 파일 id
    this.setFile = function (file, id) {
        // id 값이 있으면 이미 업료드 되어 있는 파일이므로 업로드 하지 않음 true, false
        this.info.upload.push(typeof id == "undefined");
        // 값이 있을 경우 id 값을 넘김
        if (typeof id != "undefined") this.info.id.push(id);
        this.files.push(file);
    }
    this.getFile = function () {
        return this.files;
    }
//     해당 파일항목 삭제
//     slice(시작, 끝) : 시작위치에서 끝위치-1 까지를 반화, 끝 파라미터 생략가능, 원래데이터는 그대로 유지
//     splice(시작, 갯수) : 시작 위치에서 지정 갯수 만큼 제거. 원래 데이터가 바뀜
    this.removeFile = function (i) {
        this.files.splice(i, 1);//1개씩 제거
        this.info.upload.splice(i, 1);

        //     이미 업로드 되어 있는 파일을 삭제한 경우 id를 remove로 옮기기
        if (typeof this.info.id[i] != "undefined") {
            this.info.remove.push(this.info.id[i]);//remove에 넣기
            this.info.id.splice(i, 1);//id에서 삭제
        }

    }
    this.showFile = function () {
        var tag = "";
        if (this.files.length > 0) {//파일 목록에 파일이 있는 경우
            for (i = 0; i < this.files.length; i++) {
                tag += `
                <div class="file-item d-flex gap-2 my-1">
                    <button type="button" class="btn-close small" data-seq="${i}"></button>
                    <span>${this.files[i].name}</span>
                </div>`;
            }
        } else {//없는 경우
            tag = `<div class="py-3 text-center">첨부할 파일을 마우스로 끌어오세요</div>`;
        }
        $(".file-drag").html(tag);
        console.log('>>', this)
    }
}

function multipleFileUpload() {
//     FileList 객체의 files의 파일정보를 input file태그에 넣기
    var transfer = new DataTransfer();
    var files = fileList.getFile();
    if (files.length > 0) {
        for (i = 0; i < files.length; i++) {
            if (fileList.info.upload[i]) transfer.items.add(files[i]);
        }
    }
    console.log('transfer>', transfer.files);
    $("#file-multiple").prop("files", transfer.files);
}

//폴더 제한
function filterFolder(transfer) {
    var files = [], folder = false;
    for (i = 0; i < transfer.items.length; i++) {
        var entry = transfer.items[i].webkitGetAsEntry();
        // console.log('idx>',entry);
        if (entry.isFile) files.push(transfer.files[i]);
        else folder = true;
    }
    if (folder) {
        alert("폴더는 첨부할 수 없습니다!");
    }
    return files;
}

$(document)// 삭제 처리
    .on("click", ".file-item .btn-close", function () {
        console.log($(this).data("seq"));
        fileList.removeFile($(this).data("seq"));
        fileList.showFile();
    })

//이미지 판단
function isImg(filename) {
//     abc.png, abc.jpg....
    var imgs = ["png", "jpg", "jpeg", "gif", "bmp", "webp"]
    var ext = filename.substr(filename.lastIndexOf(".") + 1);
//     substr(start, n개), substring(start, finish)
    return imgs.indexOf(ext) == -1 ? false : true;
}

function rejectFile(fileInfo, tag) {
    // 1K = 1024 , 1M = 1024*1024 , 1G = 1024*1024*1024
    if (fileInfo.size > 10 * 1024 * 1024) {//15M
        alert("10Mb를 넘는 파일은 첨부할 수 없습니다")
        // tag.val("");
        // tag.closest(".file-info").find(".file-preview").empty();
        // tag.closest(".file-info").find(".file-delete").addClass("d-none");
        initFileInfo(tag)
        return true;
    } else
        return false;
}

function initFileInfo(tag) {
    var _info = tag.closest(".file-info")
    _info.find(".file-name").empty();//파일명 안보이게
    _info.find(".file-preview").empty();// 미리보기 안보이게
    _info.find("input[type=file]").val("");//파일정보 초기화
    _info.find(".file-delete").addClass("d-none");// 삭제버튼 안보이게
}

//입력 여부 확인
function emptyCheck() {
    var ok = true;
    $(".check-empty").each(function () {
        if ($(this).val() == "") {
            alert($(this).attr("title") + " 입력하세요!");
            $(this).focus();
            ok = false;
            return ok;
        }
    })
    return ok;
}