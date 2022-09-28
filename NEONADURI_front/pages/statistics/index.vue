<template>
  <div class="statistics">
    <div class="banner-container">
      <img src="/banner/statistics-logo-star.png" alt="banner" class="banner" />
    </div>
    <div class="white-back slide-in-right">
      <b-form-select
        v-model="statisticsClass"
        :options="statisticsOption"
        class="selected"
        @change="statisticsChange"
      ></b-form-select>
      <div
        v-if="statisticsClass === 'sightNum'"
        id="myMap"
        style="width: 800px; height: 800px"
      >
        <button @click="mapopen()">얹어보자</button>
      </div>
      <div v-if="statisticsClass === 'object'" id="wordCloud">
        <!-- <div id="mainCloud"></div> -->
        <vue-word-cloud
          style="height: 40vh; width: 80vw"
          :words="words"
          :color="([, selPercent]) => colorPick(selPercent)"
          font-family="GmarketSansMedium"
          font-size-ratio="5"
        />
      </div>
      <div v-if="statisticsClass === 'satisfaction'">
        <div id="main"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts' // echart를 전역으로 불러옴
// import eword from 'echarts-wordcloud'
import VueWordCloud from 'vuewordcloud'
import axios from 'axios'
// import krJson from '~/static/map/koreaMap.json'
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
      priceList: [],
      foodList: [],
      natureList: [],

      colorIndex: 4,

      // ////
      // province: undefined,
      // currentProvince: undefined,
      // mapArea: MAP_AREA,

      // localSeatInfo : null,
    }
  },
  computed: {
    ...mapState('statistics', ['words', 'regionList', 'satList']),
  },
  created() {
    this.callSatList()
    this.callSelList()
    this.callVisitedList()
    // this.test()
  },
  mounted() {
    // Initialize the echarts instance based on the prepared dom
    this.mapopen()
    this.statisticsChange()
    // this.visitedWords();
    // this.drawMap();
  },
  methods: {
    ...mapActions('statistics', [
      'callSatList',
      'callSelList',
      'callVisitedList',
    ]),
    statisticsChange() {
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
        const myChart = echarts.init(document.getElementById('main'), null, {
          width: 1000,
          height: 500,
        })
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
      }
    },
    async mapopen() {
      const chartDom2 = document.getElementById('myMap')
      const myChart2 = echarts.init(chartDom2)
      let geoJson

      myChart2.showLoading()

      await axios.get('/data/asset/geo/USA.json').then(function (usaJson) {
        geoJson = usaJson.data
      })
      console.log(geoJson)
      myChart2.hideLoading()
      echarts.registerMap('USA', geoJson, {
        Alaska: {
          left: -131,
          top: 25,
          width: 15,
        },
        Hawaii: {
          left: -110,
          top: 28,
          width: 5,
        },
        'Puerto Rico': {
          left: -76,
          top: 26,
          width: 2,
        },
      })

      const option = {
        title: {
          text: 'USA Population Estimates (2012)',
          subtext: 'Data from www.census.gov',
          sublink: 'http://www.census.gov/popest/data/datasets.html',
          left: 'right',
        },
        tooltip: {
          trigger: 'item',
          showDelay: 0,
          transitionDuration: 0.2,
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
              '#a50026',
            ],
          },
          text: ['High', 'Low'],
          calculable: true,
        },
        toolbox: {
          show: true,
          left: 'left',
          top: 'top',
          feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {},
          },
        },
        series: [
          {
            name: 'USA PopEstimates',
            type: 'map',
            roam: true,
            map: 'USA',
            emphasis: {
              label: {
                show: true,
              },
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
              { name: 'Puerto Rico', value: 3667084 },
            ],
          },
        ],
      }

      // const confirmMap=echarts.getMap('USA').geoJSON;

      // console.log(JSON.stringify(confirmMap,null,2))
      myChart2.setOption(option)

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
  width: 30%;
  margin-bottom: -2vh;
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
  background-image: url('/banner/statistics-background.png');
  background-position-y: 100%;
  background-position-x: 20px;
  background-repeat: no-repeat;
  background-size: 100% 130%;
  width: 100%;
  height: 70vh;
}

.map {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50%;
}

#main {
  position: absolute;
  bottom: 5vh;
  right: 35vh;
}
#wordCloud {
  position: absolute;
  width: 40vw;
  bottom: 20vh;
  left: 20vh;
}
.map-wrapper {
  position: relative;
  text-align: center;
}
.background {
  /* fill: #021019; */
  fill: transparent;
  pointer-events: all;
}

.map-layer {
  fill: #08304b;
  stroke: #021019;
  stroke-width: 1px;
}
</style>
