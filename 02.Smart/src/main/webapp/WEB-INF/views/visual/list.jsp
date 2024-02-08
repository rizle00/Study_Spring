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
    <canvas id="chart" class="m-auto" style="height:100%"></canvas>
</div>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-autocolors"></script>
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
        <ul class="row d-flex justify-content-center" id="legend">
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
            // barChart(info);
            lineChart(info);
        })

    }

    function hirement() {

    }
    //탭 선택시 액션
    $("ul.nav-tabs li").on({
        "click": function () {
            $("ul.nav-tabs li a").removeClass("active");
            $(this).children("a").addClass("active");

            var idx = $(this).index();
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
</script>
</body>
</html>