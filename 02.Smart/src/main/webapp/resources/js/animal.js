

$(document)
    .on("change","#sido", function () {
        animal_sigungu();
        animal_list(1);
    })
    .on("change","#sigungu", function () {
        animal_shelter();
        animal_list(1);
    })
    .on("change","#shelter", function () {
        animal_list(1);
    })
    .on("change","#upkind", function () {
        animal_kind();
        animal_list(1);
    })
    .on("change","#kind", function () {
        animal_list(1);
    })
// $("#sido").change(function () {
//     animal_sigungu();
//     animal_list(1);
// })

function animal_sigungu() {
    $("#sigungu").remove();
    $("#shelter").remove();
    //시도 코드가 있는 경우만 시군구 조회
    if($("#sido").val() == "") return;
    $.ajax({
        url: "animal/sigungu",
        data: {sido:$("#sido").val()}
    }).done(function (resp) {
        $("#sido").after(resp);
    })

}

// $("#sigungu").change(function () {
//     animal_list(1);
//     animal_shelter();
// })

function animal_shelter() {
    $("#shelter").remove();
    // 시군구 코드(입력시 데이터 o, 미 입력시 데이터 x)
    if($("#sigungu").val() == "") return;
    $.ajax({
        url: "animal/shelter",
        data: {sido:$("#sido").val(), sigungu:$("#sigungu").val()}
    }).done(function (resp) {
        $("#sigungu").after(resp);
    })
}

function animal_type() {
    var tag = `
    <select class="form-select w-px200" id="upkind">
    <option value="">축종 선택</option>
   
    <option value="417000">
        개
    </option>
    <option value="422400">
        고양이
    </option>
    <option value="429900">
        기타
    </option>
</select>`;
    $(".animal-top").append(tag);
}

function animal_kind() {
    $("#kind").remove();
    if($("#upkind").val()=="") return;
    $.ajax({
        url: "animal/kind",
        data: {upkind : $("#upkind").val()}
    }).done(function (resp) {
        $("#upkind").after(resp)
    });
}