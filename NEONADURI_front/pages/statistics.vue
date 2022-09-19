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
            :words="wordList"
            :color="([, weight]) => weight > 10 ? 'black' : weight > 5 ? 'RoyalBlue' : 'Indigo'"
            font-family="Roboto"
          />

        </div>
        <div id="main" style="width:100%; height:100%;" ></div>
      </div>
    </div>

  </div>
</template>

<script>
  import * as echarts from "echarts"; // echart를 전역으로 불러옴
  import VueWordCloud from 'vuewordcloud';
  // import axios from 'axios';
  import { mapActions,mapState } from "vuex";


  export default {
  	name:'',
    components: {
        [VueWordCloud.name]: VueWordCloud,
    },
    data(){
      return{
        wordList:[['romance', 10.4], ['horror', 3], ['fantasy', 7], ['adventure', 3],['하이요',4],['히히히히히힣',11]],
        fontSizeMapper: word => Math.log2(word.value)*5 ,
      }
    },
    computed:{
      ...mapState('statistics',['words','priceList','foodList','natureList','regionList'])
    },
    created(){
      this.callSatList();
      this.callSelList();
      this.callVisitedList();
    },
    mounted(){
       // Initialize the echarts instance based on the prepared dom
       const myChart = echarts.init(document.getElementById('main'));

        // Specify the configuration items and data for the chart
        const option = {
          tooltip: {},
          legend: {
            data: ['물가','식당 및 음식','자연경관']
          },
          xAxis: {
            data: ['서울', '부산', '대구', '인천', '광주', '대전', '울산','세종','경기','강원','충북','충남','전북','전남','경북','경남','제주'],
            name:'도시'
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
              name: '물가',
              type: 'line',
              // data: [15, 70, 64, 57, 87, 100]
              data: this.priceList
            },
            {
              name: '식당 및 음식',
              type: 'line',
              // data: [10, 23, 5, 17, 3, 34]
              data: this.foodList
            },
            {
              name: '자연경관',
              type: 'line',
              // data: [43, 33,75, 67, 93, 64]
              data: this.natureList
            }
          ]
        };

        // Display the chart using the configuration items and data just specified.
        myChart.setOption(option);







      },
       methods:{
        ...mapActions('statistics',['callSatList','callSelList','callVisitedList'])
      },
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
