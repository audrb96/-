<template>
    
  <div class="statistics">
    <div class="white-back">
      <div class="map">
        <div id="myMap" style="width:600px; height:400px;">지도 넣을겁니다</div>
      </div>
      <div class="under">
        <div id="wordCloud">
          <vue-word-cloud
            style="
              height: 50vh;
              width: 40vw;
            "
            :words="[['romance', 19], ['horror', 3], ['fantasy', 7], ['adventure', 3],['하이요',4],['히히히히히힣',11]]"
            :color="([, weight]) => weight > 10 ? 'DeepPink' : weight > 5 ? 'RoyalBlue' : 'Indigo'"
            font-family="Roboto"
          />

        </div>
        <div id="main" style="width:100%; height:100%;" ></div>
      </div>
    </div>
      
  </div>
</template>

<script>
  import * as echarts from "echarts"; //echart를 전역으로 불러옴
  import VueWordCloud from 'vuewordcloud';
  import axios from 'axios';
  
  
  export default {
  	name:'',
    data(){
      return{
        words: [
            { text: 'Vue', value: 1000 },
            { text: 'js', value: 200 },
            { text: 'is', value: 800 },
            { text: 'very cool', value: 100000},
            { text: 'lunch', value: 100 },
        ],
        fontSizeMapper: word => Math.log2(word.value)*5 ,
      }
    },
    components: {
        [VueWordCloud.name]: VueWordCloud,
    },
    mounted(){
       // Initialize the echarts instance based on the prepared dom
       var myChart = echarts.init(document.getElementById('main'));

        // Specify the configuration items and data for the chart
        var option = {
          title: {
            text: 'ECharts Getting Started Example'
          },
          tooltip: {},
          legend: {
            data: ['sales','test']
          },
          xAxis: {
            data: ['Shirts', 'Cardigans', 'Chiffons', 'Pants', 'Heels', 'Socks']
          },
          yAxis: [
            {
              type: 'value',
              name: 'percent',
              min: 0,
              max: 100,
              position: 'left',
              axisLabel: {
                formatter: '{value} %'
              }
            }
          ],
          series: [
            {
              name: 'sales',
              type: 'line',
              data: [15, 70, 64, 57, 87, 100]
            },
            {
              name: 'test',
              type: 'line',
              data: [10, 23, 5, 17, 3, 34]
            }
          ]
        };

        // Display the chart using the configuration items and data just specified.
        myChart.setOption(option);

       

        /////////////////////////////
        var ROOT_PATH = 'https://echarts.apache.org/examples';

        var chartDom2 = document.getElementById('myMap');
        var myChart2 = echarts.init(chartDom2);
        var option2;

        myChart2.showLoading();
        axios.get(ROOT_PATH + '/data/asset/geo/USA.json')
        .then(usaJson=>{
          console.log("들어왔습니다!")
          myChart2.hideLoading();
          echarts.registerMap('USA', usaJson, {
            Alaska: {
              left: -131,
              top: 25,
              width: 15
            },
            Hawaii: {
              left: -110,
              top: 28,
              width: 5
            },
            'Puerto Rico': {
              left: -76,
              top: 26,
              width: 2
            }
          });
          option2 = {
            title: {
              text: 'USA Population Estimates (2012)',
              subtext: 'Data from www.census.gov',
              sublink: 'http://www.census.gov/popest/data/datasets.html',
              left: 'right'
            },
            tooltip: {
              trigger: 'item',
              showDelay: 0,
              transitionDuration: 0.2
            },
            visualMap: {
              left: 'right',
              min: 500000,
              max: 38000000,
              inRange: {
                color: [
                  '#313695',
                  '#4575b4',
                  '#74add1',
                  '#abd9e9',
                  '#e0f3f8',
                  '#ffffbf',
                  '#fee090',
                  '#fdae61',
                  '#f46d43',
                  '#d73027',
                  '#a50026'
                ]
              },
              text: ['High', 'Low'],
              calculable: true
            },
            toolbox: {
              show: true,
              //orient: 'vertical',
              left: 'left',
              top: 'top',
              feature: {
                dataView: { readOnly: false },
                restore: {},
                saveAsImage: {}
              }
            },
            series: [
              {
                name: 'USA PopEstimates',
                type: 'map',
                roam: true,
                map: 'USA',
                emphasis: {
                  label: {
                    show: true
                  }
                },
                data: [
                  { name: 'Alabama', value: 4822023 },
                  { name: 'Alaska', value: 731449 },
                  { name: 'Arizona', value: 6553255 },
                  { name: 'Arkansas', value: 2949131 },
                  { name: 'California', value: 38041430 },
                  { name: 'Colorado', value: 5187582 },
                  { name: 'Connecticut', value: 3590347 },
                  { name: 'Delaware', value: 917092 },
                  { name: 'District of Columbia', value: 632323 },
                  { name: 'Florida', value: 19317568 },
                  { name: 'Georgia', value: 9919945 },
                  { name: 'Hawaii', value: 1392313 },
                  { name: 'Idaho', value: 1595728 },
                  { name: 'Illinois', value: 12875255 },
                  { name: 'Indiana', value: 6537334 },
                  { name: 'Iowa', value: 3074186 },
                  { name: 'Kansas', value: 2885905 },
                  { name: 'Kentucky', value: 4380415 },
                  { name: 'Louisiana', value: 4601893 },
                  { name: 'Maine', value: 1329192 },
                  { name: 'Maryland', value: 5884563 },
                  { name: 'Massachusetts', value: 6646144 },
                  { name: 'Michigan', value: 9883360 },
                  { name: 'Minnesota', value: 5379139 },
                  { name: 'Mississippi', value: 2984926 },
                  { name: 'Missouri', value: 6021988 },
                  { name: 'Montana', value: 1005141 },
                  { name: 'Nebraska', value: 1855525 },
                  { name: 'Nevada', value: 2758931 },
                  { name: 'New Hampshire', value: 1320718 },
                  { name: 'New Jersey', value: 8864590 },
                  { name: 'New Mexico', value: 2085538 },
                  { name: 'New York', value: 19570261 },
                  { name: 'North Carolina', value: 9752073 },
                  { name: 'North Dakota', value: 699628 },
                  { name: 'Ohio', value: 11544225 },
                  { name: 'Oklahoma', value: 3814820 },
                  { name: 'Oregon', value: 3899353 },
                  { name: 'Pennsylvania', value: 12763536 },
                  { name: 'Rhode Island', value: 1050292 },
                  { name: 'South Carolina', value: 4723723 },
                  { name: 'South Dakota', value: 833354 },
                  { name: 'Tennessee', value: 6456243 },
                  { name: 'Texas', value: 26059203 },
                  { name: 'Utah', value: 2855287 },
                  { name: 'Vermont', value: 626011 },
                  { name: 'Virginia', value: 8185867 },
                  { name: 'Washington', value: 6897012 },
                  { name: 'West Virginia', value: 1855413 },
                  { name: 'Wisconsin', value: 5726398 },
                  { name: 'Wyoming', value: 576412 },
                  { name: 'Puerto Rico', value: 3667084 }
                ]
              }
            ]
          };
          myChart2.setOption(option2);
        
      })
          
          
        
        
        option2 && myChart2.setOption(option2);

        window.onresize = function() {
          myChart.resize();
          myChart2.resize();
        };


      }
}
</script>



<style>

    .statistics{
        background-color: #bdd9e5;
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .white-back{
        background-color: white;
        width: 90%;
        height: 90vh;
        border-radius: 30px;
    }

    .map{
      display: flex;
      justify-content: center;
      align-items: center;
      height: 50%;
    }

    .under{
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 50%;
    }

    #main{
      width: 60%;
    }

    #wordCloud{
      width: 50%;
    }


</style>