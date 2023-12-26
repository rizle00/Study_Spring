

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
        })
    }
    $(".date").datepicker();
    $(".date").attr("readonly",true);
})