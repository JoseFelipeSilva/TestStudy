<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estatísticas</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
		var totais = ${provasTotais};
		var esteAno = ${provasEsteAno};
		var maior = ${maiorQue50};
		var menor = ${menorQue50};
$(function () {
    // Create the chart
    Highcharts.chart('container', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Browser market shares. January, 2015 to May, 2015'
        },
        subtitle: {
            text: 'Click the columns to view versions. Source: <a href="http://netmarketshare.com">netmarketshare.com</a>.'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Notas'
            }

        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b> of total<br/>'
        },

        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: [{
                name: 'Provas feitas',
                y: totais,
            }, {
                name: 'Provas Feitas este ano',
                y: esteAno,
            }, {
                name: 'Quantidade de notas acima de 50',
                y: maior,
            },{
            	name: 'Quantidade de notas Abaixo de 50',
            	y:menor,
           
            }]
        }],
    });
});
		</script>
</head>
<body>
	<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
</body>
</html>