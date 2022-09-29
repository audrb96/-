<template>
  <div class="statistics">
    <div class="banner-container">
      <img src="/banner/statistics-logo2.png" alt="banner" class="banner" />
    </div>
    {{statisticsClass}}
    <div class="white-back slide-in-right">
      <b-form-select
        v-model="statisticsClass"
        :options="statisticsOption"
        class="selected"
        @change="statisticsChange"
      ></b-form-select>
      <div
        v-show="statisticsClass === 'sightNum'"
        id="myMap"
        style="width: 780px; height: 700px; left:25%"
      >

      <!-- <button @click="mapopen()">얹어보자 </button> -->
      </div>
      <div v-show="statisticsClass === 'object'" id="wordCloud">
        <vue-word-cloud
          style="height: 40vh; width: 70vw"
          :words="words"
          :color="([, selPercent]) => colorPick(selPercent)"
          font-family="GmarketSansMedium"
          font-size-ratio="5"
        />
      </div>
      <div v-show="statisticsClass === 'satisfaction'">
        <div id="main"></div>
      </div>
      <div class="buttonPlace">
        <div class="buttons">
          <b-button class="changeButton" variant="primary" size="lg" @click="changePage(0)">관광 여행 횟수</b-button>
        </div>
        <div class="buttons">
          <b-button class="changeButton" variant="primary" size="lg" @click="changePage(1)">관광 목적</b-button>
        </div>
        <div class="buttons">
          <b-button class="changeButton" variant="primary" size="lg" @click="changePage(2)">만족도</b-button>
        </div>
      </div>
    </div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
import * as echarts from 'echarts' // echart를 전역으로 불러옴
import VueWordCloud from 'vuewordcloud'
import { mapActions, mapState } from 'vuex'

export default {
  name: '',
  components: {
    [VueWordCloud.name]: VueWordCloud,
  },
  data() {
    return {
      statisticsOption: [
        { value: 'sightNum', text: '관광여행횟수' },
        { value: 'object', text: '관광목적' },
        { value: 'satisfaction', text: '만족도' },
      ],
      statisticsClass: 'sightNum',
      fontSizeMapper: (word) => Math.log2(word.value) * 5,
      priceList:[],
      foodList:[],
      natureList:[],

      colorIndex: 4,

    }
  },
  computed: {
    ...mapState('statistics', [
      'words',
      'regionList',
      'satList',
      'koreaMap',
      'introData' // 지도의 범례를 위한 값. 최대값과 최솟값 가짐
    ]),
  },
  watch:{
    statisticsClass(newVal, oldVal){
      if(oldVal==='sightNum'){
        console.log('붕괴')
        // echarts.dispose('myChart2');
        // echarts.disconnect()
      }
      else if(newVal==='sightNum'){
        console.log('생성')
        this.mapopen();
      }
      else if(newVal==='satisfaction'){
        this.statisticsChange()
      }
    }
  },
  created() {
    },
  mounted() {
    // Initialize the echarts instance based on the prepared dom
    // this.callSatList()
    // this.callSelList()
    // this.callVisitedList()
    this.mapopen()
    this.statisticsChange()

  },
  methods: {
    ...mapActions('statistics', [
      'callSatList',
      'callSelList',
      'callVisitedList',
    ]),
    statisticsChange() {

      this.priceList=[]
      this.foodList=[]
      this.natureList=[]

      // data 분류
      this.satList.forEach((element) => {
        if (element.satType === '0') {
          this.priceList.push(element.satScore)
        } else if (element.satType === '1') {
          this.foodList.push(element.satScore)
        }
        if (element.satType === '2') {
          this.natureList.push(element.satScore)
        }
      })

      if (this.statisticsClass === 'satisfaction') {
        const myChart = echarts.init(document.getElementById('main'))
        // Specify the configuration items and data for the chart
        const option = {
          tooltip: {},
          legend: {
            data: ['물가', '식당 및 음식', '자연경관'],
          },
          xAxis: {
            data: [
              '서울',
              '부산',
              '대구',
              '인천',
              '광주',
              '대전',
              '울산',
              '세종',
              '경기',
              '강원',
              '충북',
              '충남',
              '전북',
              '전남',
              '경북',
              '경남',
              '제주',
            ],
            name: '도시',
          },
          yAxis: [
            {
              type: 'value',
              name: 'percent',
              min: 60,
              max: 100,
              position: 'left',
              axisLabel: {
                formatter: '{value} %',
              },
            },
          ],
          series: [
            {
              name: '물가',
              type: 'line',
              // data: [15, 70, 64, 57, 87, 100]
              data: this.priceList,
            },
            {
              name: '식당 및 음식',
              type: 'line',
              // data: [10, 23, 5, 17, 3, 34]
              data: this.foodList,
            },
            {
              name: '자연경관',
              type: 'line',
              // data: [43, 33,75, 67, 93, 64]
              data: this.natureList,
            },
          ],
        }

        // Display the chart using the configuration items and data just specified.
        myChart.setOption(option)

        window.onresize = function () {
          myChart.resize();
        };
      }
    },
    changePage(idx){
      if(idx===0){
        this.statisticsClass='sightNum'
      }else if(idx===1){
        this.statisticsClass='object'
      }else{
        this.statisticsClass='satisfaction'
      }
    },
    async mapopen(){

      await this.callSatList()
      await this.callSelList()
      await this.callVisitedList()

      const chartDom2 = document.getElementById('myMap');
      // if (myChart2 != null && myChart2 !== '' && myChart2 !== undefined) {
      //   myChart2.dispose();
      // }
      // echarts.dispose(chartDom2);
      const myChart2 = echarts.init(chartDom2);

      // const kr
      const geoJson=this.koreaMap;
      myChart2.showLoading()
      myChart2.hideLoading();
      echarts.registerMap('korea', geoJson);

      const option = {
        // title: {
        //   text: 'USA Population Estimates (2012)',
        //   subtext: 'Data from www.census.gov',
        //   sublink: 'http://www.census.gov/popest/data/datasets.html',
        //   left: 'right'
        // },
        tooltip: {
          trigger: 'item',
          showDelay: 0,
          transitionDuration: 0.2,
        },
        visualMap: {
          left: 'right',
          top:'center',
          min: 0,
          max: 45000,
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
              '#a50026',
            ],
          },
          text: ['High', 'Low'],
          calculable: true,
        },
        // toolbox: {
        //   show: true,
        //   left: 'left',
        //   top: 'top',
        //   feature: {
        //     dataView: { readOnly: false },
        //     restore: {},
        //     saveAsImage: {}
        //   }
        // },
        series: [
          {
            name: '방문횟수',
            type: 'map',
            roam: 'false',
            map: 'korea',
            emphasis: {
              label: {
                show: true
              }
            },
            data : this.regionList
          }
        ]
      };
      myChart2.setOption(option);

      option && myChart2.setOption(option);

      window.onresize = function () {
        myChart2.resize();
      };

      // console.log(JSON.stringify(myChart2.getOption(),null,2))
    },
    colorPick(selPercent) {
      // const colorArr=["#F24D98","#813B7C","#59D044","#F3A002","#F2F44D"]
      // const colorArr=["#3F6F76","#69B7CE","#C65840","#F4CE4B","#62496F"]
      // const colorArr=["#4368B6","#78A153","#DEC23B","#E4930A","#C53211"]
      // const colorArr=["#C1395E","#AEC17B","#F0CA50","#E07B42","#89A7C2"]
      // const colorArr=["#21344F","#8AAD05","#E2CE1B","#DF5D22","#E17976"]
      const colorArr = ['#C13E43', '#376EA5', '#565654', '#F9D502', '#E7CA6B']
      if (selPercent > 10) {
        this.colorIndex = (this.colorIndex + 1) % 5
      } else {
        this.colorIndex = (this.colorIndex + 1) % 5
      }
      return colorArr[this.colorIndex]
    },
  },
}
</script>

<style scoped>
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.banner-container {
  display: flex;
  justify-content: center;
}
.banner {
  margin-top: 1vh;
  width: 40%;
  margin-bottom: -1vh;
}
.slide-in-right {
  -webkit-animation: slide-in-right 1s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
  animation: slide-in-right 1s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
}
@-webkit-keyframes slide-in-right {
  0% {
    -webkit-transform: translateX(100%);
    transform: translateX(100%);
    opacity: 0;
  }
  100% {
    -webkit-transform: translateX(0);
    transform: translateX(0);
    opacity: 1;
  }
}
@keyframes slide-in-right {
  0% {
    -webkit-transform: translateX(100%);
    transform: translateX(100%);
    opacity: 0;
  }
  100% {
    -webkit-transform: translateX(0);
    transform: translateX(0);
    opacity: 1;
  }
}
.selected {
  position: absolute;
  top: 5vh;
  left: 20vh;
}

.custom-select {
  width: 10%;
}
.statistics {
  width: 100%;
  height: 100vh;
}

.white-back {
  position: relative;
  background-image: url('/banner/statistics-ticket-background.png');
  background-position-y: 100%;
  background-position-x: 20px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: 100%;
  height: 70vh;
}

.map {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50%;
}

.buttonPlace{
  position:absolute;
  top:1px;
  right:-2%;
  width:14%;
  height:73%;

  display:flex;
  flex-direction: column;
  justify-content: space-evenly;
}



#main {
  position: absolute;
  bottom: 5vh;
  right: 65vh;
  width: 600px;
  height: 600px;

}
#wordCloud {
  position: absolute;
  width: 40vw;
  bottom: 20vh;
  left: 20vh;
}

</style>
