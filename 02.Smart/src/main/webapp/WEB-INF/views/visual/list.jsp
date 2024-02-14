<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<style>
    #legend span { width: 44px; height: 17px; margin-right: 5px;}
    #legend li {display: flex; align-items: center}
</style>
<link href="<c:url value='/css/yearpicker.css'/>" rel="stylesheet">
<body>


<h3 class="mb-4"> 사원 정보 분석 </h3>
<%--부서별 사원 수--%>
<%--채용 인원 수--%>
<ul class="nav nav-tabs">
    <li class="nav-item">
        <a class="nav-link active">부서원 수</a>
    </li>
    <li class="nav-item">
        <a class="nav-link">채용 인원 수</a>
    </li>
    <li class="nav-item">
        <a class="nav-link">Link</a>
    </li>
</ul>

<div id="tab-content" class="py-3" style="height:450px">
    <div class="tab text-center mb-3">
        <div class="form-check form-check-inline">
            <label class="form-check-label" >
                <input class="form-check-input" type="radio" name="chart" value="bar" checked>막대그래프
            </label>
        </div>
        <div class="form-check form-check-inline">
            <label class="form-check-label" >
                <input class="form-check-input" type="radio" name="chart" value="doughnut" >도넛그래프
            </label>
        </div>
    </div>
    <div class="tab text-center mb-3">
        <div class="form-check form-check-inline">
            <label class="form-check-label" >
                <input class="form-check-input" type="checkbox" id="top3">TOP3부서
            </label>
        </div>
        <div class="form-check form-check-inline">
            <label class="form-check-label" >
                <input class="form-check-input" type="radio" name="unit" value="year" checked>년도별
            </label>
        </div>
        <div class="form-check form-check-inline">
            <label class="form-check-label" >
                <input class="form-check-input" type="radio" name="unit" value="month" >월별
            </label>
        </div>
        <div class="d-inline-block year-range">
            <div class="d-flex gap-2">
                <input type="text" class="form-control year" id="begin" readonly>
                <span>~</span>
                <input type="text" class="form-control year" id="end" readonly>
            </div>
        </div>
    </div>

    <canvas id="chart" class="m-auto" style="height:100%"></canvas>
</div>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-autocolors"></script>
<script src="<c:url value='/js/yearpicker.js'/>"></script>
<script>
    // 바차트 색에 대한 정보 입력
    function makeLegend() {
        var li = "";
        var i =0;
        for( i =0; i<=5; i++){
            li +=`<li class="col-auto">
                     <span></span><font>\${i*10}~\${i*10+9}명</font>
                  </li>`;
        }
        li +=`<li class="col-auto">
                     <span></span><font>\${i*10}명이상</font>
                  </li>`;
        var tag = `
        <ul class="row d-flex justify-content-center mt-5" id="legend">
            \${li}
        </ul>
        `;
        $("#tab-content").after(tag);
        $("#legend span").each(function (idx) {
            $(this).css("background-color", colors[idx]);
        })
    }
    // 샘플 차트
    function sampleChart() {
        // const ctx = $("#chart");


        new Chart($("#chart"), {

            type: 'bar',
            data: {
                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    Chart.defaults.font.size = 20;
    Chart.defaults.plugins.legend.position = "bottom";
    Chart.register(ChartDataLabels);
    Chart.defaults.set('plugins.datalabels', {
        color: '#FE777B',
        anchor: 'end',
        font: {weight: "bold"}
    });
    const autocolors = window['chartjs-plugin-autocolors'];
    Chart.register(autocolors);
    var colors =['#36A2EB',
        '#9BD0F5',
        '#FF6384',
        '#FFB1C1','#cc65fe', '#ffce56', '#948b74'];
    // 바 차트 생성
    function barChart(info) {
        console.log(info);
        new Chart($("#chart"), {

            type: 'bar',
            data: {
                labels: info.category,
                datasets: [{
                    label: '부서원 수',
                    data: info.datas,
                    borderWidth: 1,
                    barPercentage: 0.5,
                    backgroundColor: info.colors
                }]
            },
            options: {
                responsive: false,
                maintainAspectRatio: false,
                plugins: {
                    datalabels: {
                        formatter: function (value, context) {
                            return value + "명";
                        }
                    },
                    legend: {display: false},
                    autocolors: {
                        mode: 'data'
                    },
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            color: 'red',
                            display: true,
                            text: '부서별 사원수'
                        }
                    }
                }
            }
        });
        makeLegend();
    }
    // 라인 차트 생성
    function lineChart(info) {
        new Chart($("#chart"), {

            type: 'line',
            data: {
                labels: info.category,
                datasets: [{
                    label: '부서원 수',
                    data: info.datas,
                    borderColor: '#006fff',
                    pointRadius: 5,
                    tension: 0.1,
                    pointBackgroundColor: 'rgba(255,0,242,0.6)',
                }]
            },
            options: {
                responsive: false,
                maintainAspectRatio: false,
                plugins: {
                    datalabels: {
                        formatter: function (value, context) {
                            return value + "명";
                        }
                    },
                    legend: {display: false},

                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            color: 'red',
                            display: true,
                            text: '부서별 사원수'
                        }
                    }
                }
            }
        });
    }
    //부서 정보 조회
    function department() {
        // sampleChart();
        //if( typeof visual != "undefined" )  visual.destroy(); //그래프를 아예 바꾸는것에 제대로 영향을 미치지 못함
        initChart();
        $.ajax({
            url: "department"
        }).done(function (resp) {
            console.log(resp);

            var info = {};
            info.category = [];
            info.datas = [];
            info.colors = [];
            $(resp).each(function () {
                info.datas.push(this.COUNT);
                info.category.push(this.DEPARTMENT_NAME);
                info.colors.push(colors[Math.floor(this.COUNT/10)]);

            })
            // lineChart(info);
            // barChart(info);
            //lineChart( info );
            if( $("[name=chart]:checked").val()=="bar" )
                barChart( info );
            else
                doughnutChart( info );

            //visual.update(); 그래프를 아예 바꾸는것에 제대로 영향을 미치지 못함
        })

    }
    var visual;
    function doughnutChart( info ){
        //각 데이터에 대한 백분율 구하기
        var sum = 0;
        $(info.datas).each( function(){
            sum += this;
        })
        info.pct = info.datas.map( function( value ){
            return Math.round(value/sum*10000)/100
        } )
        console.log( sum, info )

        visual = new Chart( $("#chart"), {
            type: 'doughnut',
            data: {
                labels: info.category,
                datasets: [{
                    label: "부서원수",
                    data: info.datas,
                    hoverOffset: 6, //마우스올렸을때 데이터조각이 offset되는 정도
                }]
            },
            options: {
                responsive: false,
                maintainAspectRatio: false,
                cutout: "60%",  //내부원을 얼마나 잘라낼것인지 (0:파이그래프가 됨)
                plugins: {
                    autocolors: { mode: "data" },
                    datalabels: {
                        formatter: function(v, item){
                            //return v+"명 " ;
                            //return `\${v}명\n(\${info.pct[item.dataIndex]}%)` ;
                            return `\${info.pct[item.dataIndex]}%` ;
                        },
                        anchor: "middle",
                    }
                }
            }
        } )

    }

    function unitChart( info ){
        new Chart( $("#chart"), {
            type: "bar",
            data: {
                labels: info.category,
                datasets: [{
                    data: info.datas,
                    barPercentage: 0.5,
                    backgroundColor: info.colors,
                }]
            },
            options: {
                plugins: {
                    legend: { display:false },
                },
                reponsive: false,
                maintainAspectRatio: false,
                scales:{
                    y: {
                        title: { text: info.title,  display: true },
                    }
                }
            }
        } )

        makeLegend();
    }


    function hirement() {
        initChart();

        var unit = $("[name=unit]:checked").val();

        $.ajax({
            url: "hirement/" + unit,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify( { begin: $("#begin").val(), end: $("#end").val() } ),
        }).done(function( response ){
            console.log( response )

            var info = {};
            info.datas = [], info.category = [], info.colors = [];

            $(response).each(function(){
                info.datas.push( this.COUNT );
                info.category.push( this.UNIT );
                info.colors.push( colors[ Math.floor(this.COUNT/10) ] );
            })

            info.title = (unit == "year" ? "년도별 " : "월별 " ) + "채용인원수"
            unitChart( info );
        })

    }
    //탭 선택시 액션
    $("ul.nav-tabs li").on({
        "click": function () {
            $("ul.nav-tabs li a").removeClass("active");
            $(this).children("a").addClass("active");

            var idx = $(this).index();

            $("#tab-content .tab").addClass("d-none");
            $("#tab-content .tab").eq(idx).removeClass("d-none");

            if (idx == 0) department();
            else if (idx == 1) hirement();
        },
        "mouseover": function () {
            $(this).addClass("shadow");
        },
        "mouseleave": function () {
            $(this).removeClass("shadow")
        }
    })
    //부서원 수 탭 자동 클릭
    $(function () {
        $("ul.nav-tabs li").eq(0).trigger("click");
    })

    //막대/도넛 그래프 종류 라디오 변경시
    $("[name=chart]").change(function(){
        department();
    })

    //년도별/월별 라디오 변경시
    //TOP3부서 선택/해제시
    $("[name=unit], #top3").change(function(){

        if( $("[name=unit]:checked").val()=="year" ){
            $(".year-range").removeClass("d-none");
        }else{
            $(".year-range").addClass("d-none");
        }
        hirement_info();
    })

    function hirement_top3(){
        initChart();

        var unit = $("[name=unit]:checked").val();
        $.ajax( {
            url: "hirement/top3/"+ unit,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify( { begin:$("#begin").val(), end:$("#end").val() } )
        }).done(function(response){
            console.log(response )

            var info = {};
            info.category = response.unit;
            info.type = unit == "year" ? "bar" : "line";
            info.datas = [], info.label= [];

            $(response.list).each(function(idx, dept ){
                info.label.push( this.DEPARTMENT_NAME );
                console.log(this.DEPARTMENT_NAME )
                var datas = info.category.map( function(item ){
                    console.log(item )
                    console.log(dept[item] )
                    return dept[item];
                })
                info.datas.push(datas);
            })
            info.title = `상위3위 부서의 \${unit == 'year' ? "년도별": "월별"} 채용인원수`;
            console.log( info )
            top3Chart( info );
        })

    }

    function top3Chart( info ){
        var datas = [];
        for( var idx=0; idx<info.datas.length; idx++ ){
            var department = {};
            department.data = info.datas[idx];
            department.label = info.label[idx];
            department.backgroundColor = colors[idx]; //막대그래프용
            department.borderColor = colors[idx]; //선그래프용
            datas.push( department );
        }

        new Chart( $("#chart"), {
            type: info.type,
            data: {
                labels: info.category,
                datasets: datas, // [ {}, {}, {}, {}  ]
            },
            options: {
                scales: {
                    y: {
                        title: { text: info.title, display: true }
                    }
                },
                responsive: false,
                maintainAspectRatio: false,
            }


        } )

    }

    function initChart(){
        $("#legend").remove();
        $("canvas#chart").remove();
        $("#tab-content").append( `<canvas id="chart" class="m-auto" style="height:100%"></canvas>` );
    }

    var thisYear = new Date().getFullYear();
    $("#end").yearpicker({
        year: thisYear, //선택된 년도
        endYear: thisYear, //선택가능 끝년도
        startYear: thisYear-50, //선택가능 시작년도
    })
    $("#begin").yearpicker({
        year: thisYear-9,
        endYear: thisYear,
        startYear: thisYear-50, //선택가능 시작년도
    })

    $(document)
        .on("click", ".yearpicker-items", function(){
            //범위를 잘못 선택하지 않도록 하기
            if( $("#begin").val() > $("#end").val() )  $("#begin").val( $("#end").val() );
            hirement_info();
        })

    function hirement_info(){
        if( $("#top3").prop("checked") )  hirement_top3();
        else hirement();
    }
</script>
</body>
</html>