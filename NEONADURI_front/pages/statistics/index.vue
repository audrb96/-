<template>
  <div class="statistics">
    <div class="banner-container">
      <img src="/banner/statistics-banner.png" alt="banner" class="banner" />
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
        style="width: 600px; height: 400px"
      >
        지도 넣을겁니다
      </div>
      <div v-if="statisticsClass === 'object'" id="wordCloud">
        <vue-word-cloud
          style="height: 40vh; width: 80vw"
          :words="wordList"
          :color="
            ([, weight]) =>
              weight > 10 ? 'black' : weight > 5 ? 'RoyalBlue' : 'Indigo'
          "
          font-family="Roboto"
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
import VueWordCloud from 'vuewordcloud'
// import axios from 'axios';
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
      wordList: [
        ['romance', 10.4],
        ['horror', 3],
        ['fantasy', 7],
        ['adventure', 3],
        ['하이요', 4],
        ['히히히히히힣', 11],
      ],
      fontSizeMapper: (word) => Math.log2(word.value) * 5,
    }
  },
  computed: {
    ...mapState('statistics', [
      'words',
      'priceList',
      'foodList',
      'natureList',
      'regionList',
    ]),
  },
  created() {
    this.callSatList()
    this.callSelList()
    this.callVisitedList()
  },
  mounted() {
    // Initialize the echarts instance based on the prepared dom
  },
  methods: {
    ...mapActions('statistics', [
      'callSatList',
      'callSelList',
      'callVisitedList',
    ]),
    statisticsChange() {
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
              min: 0,
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
  },
}
</script>

<style scoped>
.banner-container {
  display: flex;
  justify-content: center;
}
.banner {
  width: 24%;
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
  height: 80vh;
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
</style>
