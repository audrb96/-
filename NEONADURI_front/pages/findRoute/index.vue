<template>
  <div class="findRoute">
    <div class="container">
      <!-- <div class="banner">
              <img src="/banner/banner.png" style="width: 50%;">
          </div> -->
      <div class="map">
        <div id="map_wrap" class="map_wrap">
          <div id="map_div"></div>
        </div>
      </div>
      <div class="search-wrapper">
        <div class="search-container">
          <input
            id="searchKeyword"
            type="text"
            :placeholder="
              findStartPoint
                ? '출발지를 입력해주세요.'
                : '도착지를 입력해주세요'
            "
            @keyup.enter="findTarget()"
          />
          <img
            class="search-icon"
            src="http://www.endlessicons.com/wp-content/uploads/2012/12/search-icon.png"
            @click="findTarget(e)"
          />
        </div>
      </div>
      <div class="main-container">
        <div class="middle">
          <label>
            <input type="radio" name="radio" checked @click="toggle(0)" />
            <div class="front-end box">
              <span>출발지 검색</span>
            </div>
          </label>

          <label>
            <input type="radio" name="radio" @click="toggle(1)" />
            <div class="back-end box">
              <span>도착지 검색</span>
            </div>
          </label>
        </div>
        <div class="search-result">
          <div v-show="findStartPoint">
            <div
              v-for="(item, index) in searchResult"
              :key="index"
              class="result-item"
            >
              <img
                :src="item.src"
                style="vertical-align: middle; margin-right: 10px"
              /><span>{{ item.name }}</span>
              <b-button
                variant="success"
                class="ml-3"
                name="sendBtn"
                @click="selected(item, 0, index)"
                >시작점 설정</b-button
              >
            </div>
          </div>
          <div v-show="findEndPoint">
            <div
              v-for="(item, index) in searchResult"
              :key="index"
              class="result-item"
            >
              <img
                :src="item.src"
                style="vertical-align: middle; margin-right: 10px"
              /><span>{{ item.name }}</span>
              <b-button
                variant="warning"
                class="ml-3"
                name="sendBtn"
                @click="selected(item, 1, index)"
                >도착점 설정</b-button
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xx9b31967c4bc2496f8dde1d66747658c9"></script>
<script>
// import {mapState} from 'vuex'
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  head: {
    script: [
      {
        src: 'https://kit.fontawesome.com/301da58328.js',
      },
    ],
  },
  computed: {
    ...mapState('route', ['routeList']),
  },
  data() {
    return {
      searchResult: [],

      startPointObject: {},
      startIndex: -1,
      startMarker: Object,

      endPointObject: {},
      endIndex: -1,
      endMarker: Object,
      // 페이지 보이기
      findStartPoint: true,
      findEndPoint: false,
      findRoute: false,
      // input 데이터값
      startPoint: '',
      endPoint: '',
      // 실제 지도 객체
      map: Object,
      // 출발점과 도착점 객체
      marker_s: Object,
      marker_e: Object,
      // 경유지 test case
      stopOverList: [],
      stopOverObjectList: [],

      resultInfoArr: [],
      markerArr: [],
      labelArr: [],
      resultMarkerArr: [],
    }
  },
  mounted() {
    this.makeMap()
    this.pickStopOver()
  },
  methods: {
    // 경유지 찍기
    pickStopOver() {
      for (let i = 0; i < this.routeList.length; i++) {
        const mapInstance = new Tmapv2.Marker({
          position: new Tmapv2.LatLng(
            this.routeList[i].lat,
            this.routeList[i].lng
          ),
          icon:
            'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_g_m_' +
            i +
            '.png',
          iconSize: new Tmapv2.Size(24, 38),
          map: this.map,
        })

        this.stopOverObjectList.push(mapInstance)
      }
    },
    // 시작점, 도착점 찍기
    selected(item, idx, index) {
      const map = this.map
      const startPointObject = this.startPointObject

      if (idx === 0) {
        if (Object.keys(startPointObject).length !== 0) {
          this.startMarker.setMap(null)
        }

        this.startPointObject = item
        this.startMarker = new Tmapv2.Marker({
          position: new Tmapv2.LatLng(item.lat, item.lon),
          icon: 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png',
          iconSize: new Tmapv2.Size(24, 38),
          map,
        })
        this.startIndex = index
      } else {
        if (Object.keys(this.endPointObject).length !== 0) {
          this.endMarker.setMap(null)
        }

        this.endPointObject = item
        this.endMarker = new Tmapv2.Marker({
          position: new Tmapv2.LatLng(item.lat, item.lon),
          icon: 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png',
          iconSize: new Tmapv2.Size(24, 38),
          map,
        })
        this.endIndex = index
      }
    },
    // 시작점, 도착점 선택창 열기
    toggle(num) {
      if (num === 0) {
        this.findStartPoint = true
        this.findEndPoint = false
      } else {
        this.findStartPoint = false
        this.findEndPoint = true
      }
    },
    resetBoxes() {
      this.startPoint = ''
      this.endPoint = ''
    },
    // 지도 그리기
    makeMap() {
      let map
      // 1. 지도 띄우기
      map = new Tmapv2.Map('map_div', {
        center: new Tmapv2.LatLng(37.56701114710962, 126.9973611831669),
        width: '70vw',
        height: '50vh',
        zoom: 15,
        zoomControl: true,
        scrollwheel: true,
      })

      this.map = map
    },
    initTmap() {
      const map = this.map
      let markerArr = this.markerArr
      let labelArr = this.labelArr

      // 2. POI 통합 검색 API 요청
      $('#btn_select').click(function () {
        const searchKeyword = $('#searchKeyword').val() // 검색 키워드

        $.ajax({
          method: 'GET', // 요청 방식
          url: 'https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result', // url 주소
          async: false, // 동기설정
          data: {
            // 요청 데이터 정보
            appKey: 'l7xx9b31967c4bc2496f8dde1d66747658c9', // 발급받은 Appkey
            searchKeyword, // 검색 키워드
            resCoordType: 'EPSG3857', // 요청 좌표계
            reqCoordType: 'WGS84GEO', // 응답 좌표계
            count: 10, // 가져올 갯수
          },
          success(response) {
            const resultpoisData = response.searchPoiInfo.pois.poi

            // 2. 기존 마커, 팝업 제거
            if (markerArr.length > 0) {
              for (var i in markerArr) {
                markerArr[i].setMap(null)
              }
              markerArr = []
            }

            if (labelArr.length > 0) {
              for (var i in labelArr) {
                labelArr[i].setMap(null)
              }
              labelArr = []
            }

            let innerHtml = '' // Search Reulsts 결과값 노출 위한 변수
            // 맵에 결과물 확인 하기 위한 LatLngBounds객체 생성
            const positionBounds = new Tmapv2.LatLngBounds()
            let marker
            // 3. POI 마커 표시
            for (const k in resultpoisData) {
              // POI 마커 정보 저장
              const noorLat = Number(resultpoisData[k].noorLat)
              const noorLon = Number(resultpoisData[k].noorLon)
              const name = resultpoisData[k].name

              // POI 정보의 ID
              const id = resultpoisData[k].id

              // 좌표 객체 생성
              const pointCng = new Tmapv2.Point(noorLon, noorLat)

              // EPSG3857좌표계를 WGS84GEO좌표계로 변환
              const projectionCng =
                new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(pointCng)

              const lat = projectionCng._lat
              const lon = projectionCng._lng

              // 좌표 설정
              const markerPosition = new Tmapv2.LatLng(lat, lon)

              // Marker 설정
              marker = new Tmapv2.Marker({
                position: markerPosition, // 마커가 표시될 좌표
                // icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
                icon:
                  'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_' +
                  k +
                  '.png', // 아이콘 등록
                iconSize: new Tmapv2.Size(24, 38), // 아이콘 크기 설정
                title: name, // 마커 타이틀
                map, // 마커가 등록될 지도 객체
              })

              // 결과창에 나타날 검색 결과 html
              innerHtml +=
                "<li><div><img src='http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" +
                k +
                ".png' style='vertical-align:middle;'/><span>" +
                name +
                "</span>  <button type='button' name='sendBtn' @click='poiDetail(" +
                id +
                ");'>상세보기</button></div></li>"
              // 마커들을 담을 배열에 마커 저장
              markerArr.push(marker)
              positionBounds.extend(markerPosition) // LatLngBounds의 객체 확장
            }

            $('#searchResult').html(innerHtml) // searchResult 결과값 노출
            map.panToBounds(positionBounds) // 확장된 bounds의 중심으로 이동시키기
            map.zoomOut()
          },
          error(request, status, error) {
            console.log(
              'code:' +
                request.status +
                '\n' +
                'message:' +
                request.responseText +
                '\n' +
                'error:' +
                error
            )
          },
        })
      })
    },

    // 검색결과
    findTarget() {
      this.searchResult = []
      const map = this.map
      const markerArr = this.markerArr
      const searchResult = this.searchResult
      const searchKeyword = $('#searchKeyword').val() // 검색 키워드

      $.ajax({
        method: 'GET', // 요청 방식
        url: 'https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result', // url 주소
        async: false, // 동기설정
        data: {
          // 요청 데이터 정보
          appKey: 'l7xx9b31967c4bc2496f8dde1d66747658c9', // 발급받은 Appkey
          searchKeyword, // 검색 키워드
          resCoordType: 'EPSG3857', // 요청 좌표계
          reqCoordType: 'WGS84GEO', // 응답 좌표계
          count: 10, // 가져올 갯수
        },
        success(response) {
          const resultpoisData = response.searchPoiInfo.pois.poi
          // 2. 기존 마커, 팝업 제거
          if (markerArr.length > 0) {
            for (const i in markerArr) {
              markerArr[i].setMap(null)
            }
            // markerArr.clear();
            this.markerArr = []
          }

          // 맵에 결과물 확인 하기 위한 LatLngBounds객체 생성
          const positionBounds = new Tmapv2.LatLngBounds()
          let marker
          // searchResult=[];
          // 3. POI 마커 표시
          for (const k in resultpoisData) {
            // POI 마커 정보 저장
            const noorLat = Number(resultpoisData[k].noorLat)
            const noorLon = Number(resultpoisData[k].noorLon)
            const name = resultpoisData[k].name

            // POI 정보의 ID
            const id = resultpoisData[k].id

            // 좌표 객체 생성
            const pointCng = new Tmapv2.Point(noorLon, noorLat)

            // EPSG3857좌표계를 WGS84GEO좌표계로 변환
            const projectionCng =
              new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(pointCng)

            const lat = projectionCng._lat
            const lon = projectionCng._lng

            // 좌표 설정
            const markerPosition = new Tmapv2.LatLng(lat, lon)

            // Marker 설정
            marker = new Tmapv2.Marker({
              position: markerPosition, // 마커가 표시될 좌표
              // icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
              icon:
                'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_' +
                k +
                '.png', // 아이콘 등록
              iconSize: new Tmapv2.Size(24, 38), // 아이콘 크기 설정
              title: name, // 마커 타이틀
              map, // 마커가 등록될 지도 객체
            })

            // 결과창에 나타날 검색 결과 html
            searchResult.push({
              id,
              src: `http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_${k}.png`,
              name,
              lat,
              lon,
            })
            // 마커들을 담을 배열에 마커 저장
            markerArr.push(marker)
            positionBounds.extend(markerPosition) // LatLngBounds의 객체 확장
          }

          // searchResult 결과값 노출
          map.panToBounds(positionBounds) // 확장된 bounds의 중심으로 이동시키기
          map.zoomOut()
        },
        error(request, status, error) {
          console.log(
            'code:' +
              request.status +
              '\n' +
              'message:' +
              request.responseText +
              '\n' +
              'error:' +
              error
          )
        },
      })
    },

    // 길찾기
    async findRouteTmap() {
      for (let i in this.markerArr) {
        this.markerArr[i].setMap(null)
      }

      new Tmapv2.Marker({
        position: new Tmapv2.LatLng(
          this.startPointObject.lat,
          this.startPointObject.lon
        ),
        icon: 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_g_m_s.png',
        iconSize: new Tmapv2.Size(24, 38),
        map: this.map,
      })

      new Tmapv2.Marker({
        position: new Tmapv2.LatLng(
          this.endPointObject.lat,
          this.endPointObject.lon
        ),
        icon: 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_g_m_e.png',
        iconSize: new Tmapv2.Size(24, 38),
        map: this.map,
      })

      const headers = {}
      headers.appKey = '	l7xx9b31967c4bc2496f8dde1d66747658c9'
      headers['Content-Type'] = 'application/json'

      const dataInfo = {
        reqCoordType: 'WGS84GEO',
        resCoordType: 'EPSG3857',
        startName: '출발',
        startX: `${this.startPointObject.lon}`,
        startY: `${this.startPointObject.lat}`,
        startTime: '201711121314',
        endName: '도착',
        endX: `${this.endPointObject.lon}`,
        endY: `${this.endPointObject.lat}`,
        searchOption: '0',
      }

      const viaPoints = []

      for (const i in this.stopOverList) {
        const via = {
          viaPointId: 'test' + i,
          viaPointName: `${this.stopOverList[i].name}`,
          viaX: `${this.stopOverList[i].lon}`,
          viaY: `${this.stopOverList[i].lat}`,
        }

        viaPoints.push(via)
      }

      dataInfo.viaPoints = viaPoints
      let resultData = null
      let resultFeatures = null
      await axios
        .post(
          'https://apis.openapi.sk.com/tmap/routes/routeSequential30?version=1&format=json',
          JSON.stringify(dataInfo),
          { headers }
        )
        .then(function (response) {
          resultData = response.data.properties
          resultFeatures = response.data.features
        })

      console.log(JSON.stringify(resultData, null, 2))
      console.log(JSON.stringify(resultFeatures, null, 2))

      // 결과 출력
      const tDistance =
        '총 거리 : ' + (resultData.totalDistance / 1000).toFixed(1) + 'km,  '
      const tTime = '총 시간 : ' + (resultData.totalTime / 60).toFixed(0) + '분'
      $('#result').text(tDistance + tTime)

      // 기존의 길과 포인트들 전부 삭제
      if (this.resultInfoArr.length > 0) {
        for (let i in this.resultInfoArr) {
          console.log('타입 : ')
          console.log(typeof this.resultInfoArr[i])
          console.log(this.resultInfoArr.length)
          this.resultInfoArr[i].setMap(null)
        }
      }
      this.resultInfoArr = []

      if (this.resultMarkerArr.length > 0) {
        for (let i in this.resultMarkerArr) {
          this.resultMarkerArr[i].setMap(null)
        }
      }
      this.resultMarkerArr = []

      // 루트 그림 그리는 포인트를 담는 배열
      const drawInfoArr = []

      for (let i in resultFeatures) {
        const geometry = resultFeatures[i].geometry
        const properties = resultFeatures[i].properties
        let polyline_

        this.resultInfoArr.push(this.startPointObject)
        this.resultInfoArr.push(this.endPointObject)

        for (const k in this.stopOverObjectList) {
          this.resultInfoArr.push(this.stopOverObjectList[k])
        }

        if (geometry.type == 'LineString') {
          for (const j in geometry.coordinates) {
            // 경로들의 결과값(구간)들을 포인트 객체로 변환
            const latlng = new Tmapv2.Point(
              geometry.coordinates[j][0],
              geometry.coordinates[j][1]
            )
            // 포인트 객체를 받아 좌표값으로 변환
            const convertPoint =
              new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(latlng)
            // 포인트객체의 정보로 좌표값 변환 객체로 저장
            const convertChange = new Tmapv2.LatLng(
              convertPoint._lat,
              convertPoint._lng
            )

            drawInfoArr.push(convertChange)
          }

          polyline_ = new Tmapv2.Polyline({
            path: drawInfoArr,
            strokeColor: '#FF0000',
            strokeWeight: 6,
            map: this.map,
          })
          this.resultInfoArr.push(polyline_)
        } else {
          let markerImg = ''
          let size = '' // 아이콘 크기 설정합니다.

          if (properties.pointType == 'S') {
            // 출발지 마커
            markerImg =
              'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png'
            size = new Tmapv2.Size(24, 38)
          } else if (properties.pointType == 'E') {
            // 도착지 마커
            markerImg =
              'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png'
            size = new Tmapv2.Size(24, 38)
          } else {
            // 각 포인트 마커
            markerImg = 'http://topopen.tmap.co.kr/imgs/point.png'
            size = new Tmapv2.Size(8, 8)
          }

          // 경로들의 결과값들을 포인트 객체로 변환
          const latlon = new Tmapv2.Point(
            geometry.coordinates[0],
            geometry.coordinates[1]
          )
          // 포인트 객체를 받아 좌표값으로 다시 변환
          const convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
            latlon
          )

          const marker_p = new Tmapv2.Marker({
            position: new Tmapv2.LatLng(convertPoint._lat, convertPoint._lng),
            icon: markerImg,
            iconSize: size,
            map: this.map,
          })

          this.resultMarkerArr.push(marker_p)
        }
      }
    },
    // 경유지 지우기
    deleteStopOver(index) {
      // 먼저 그려져있던 포인트들 전부 지우기
      for (const i in this.stopOverObjectList) {
        this.stopOverObjectList[i].setMap(null)
      }
      // 이전 포인트들 담아두었던 배열 초기화
      this.stopOverObjectList = []

      // 지울 부분을 리스트에서 제거
      this.stopOverList.splice(index, 1)

      // 포인트를 그려주는 메소드 호출
      this.pickStopOver()
    },
  },
}
</script>

<style>
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.result-item {
  font-family: 'GmarketSansMedium';
  margin-top: 15px;
  margin-left: 10px;
}

.main-container {
  display: flex;
  justify-content: flex-start;
  width: 100vw;
}

.search-result {
  position: relative;
  background: #fff;
  border-radius: 15px;
  width: 50%;
  height: 98%;
}

.search-wrapper {
  margin-top: 20px;
  width: 100vw;
  display: flex;
  justify-content: center;
}

.test {
  background-color: black;
}

.findRoute {
  background-color: #eaf2f9;
  overflow: auto;
  width: 100vw;
  height: 100vh;
}

.container {
  display: inline-flex;
  flex-direction: column;
  width: 1vw;
  height: 100%;
  /* padding-left: 20%; */
}

.banner {
  display: flex;
  align-items: center;
  justify-content: center;
}

.input-form {
  width: 18vw;
  height: 6vh;
  font-size: larger;
  margin-top: 3vh;
  margin-bottom: 3vh;
  margin-left: 4vw;
  margin-right: 4vw;
}

.search {
  display: flex;
  align-items: center;
  justify-content: center;
}

.box {
  width: 30%;
}

.my-button {
  display: flex;
  align-items: center;
  justify-content: center;
}

.types {
  width: 18vw;
  height: 6vh;
  margin-top: 1vh;
  margin-bottom: 3vh;
  margin-left: 4vw;
  margin-right: 4vw;
  font-size: 2vh;
}

.reInput {
  display: flex;
  justify-content: flex-start;
}

.roadFinding {
  display: flex;
  justify-content: flex-end;
}

.buttons {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 5vw;
  border-radius: 20px;
  border: 1px solid white;
  float: left;
  box-shadow: 3px 3px 3px black;

  transition-duration: 0.3s;

  font-size: 100%;
}

.map {
  height: 50vh;
  margin-left: -10%;
  display: flex;
  width: 100vw;
  justify-content: center;
}

.buttonBoxes {
  display: flex;
  justify-content: center;
}

.content {
  width: 100%;
  height: 450px;
  display: flex;
  background-color: white;
}

.inputBox {
  border-right: 1px solid black;
  width: 25%;
}

.stopover {
  width: 35%;
  padding-left: 2%;
  background-color: white;
  overflow-y: scroll;
}

.itemList {
  width: 45%;
}

.buttons:active {
  margin-left: 5px;
  margin-top: 5px;
  box-shadow: none;
}

.fontSize {
  font-size: 2vh;
}

.search-container {
  font-family: 'GmarketSansMedium';
  width: 490px;
  display: block;
  margin: 0 auto;
  height: 8vh;
}

input#searchKeyword {
  margin: 0 auto;
  width: 100%;
  height: 45px;
  padding: 0 20px;
  font-size: 1rem;
  border: 1px solid #d0cfce;
  outline: none;
}
input#searchKeyword:focus {
  border: 1px solid #008abf;
  transition: 0.35s ease;
  color: #008abf;
}
input#searchKeyword:focus::-webkit-input-placeholder {
  transition: opacity 0.45s ease;
  opacity: 0;
}
input#searchKeyword:focus::-moz-placeholder {
  transition: opacity 0.45s ease;
  opacity: 0;
}
input#searchKeyword:focus:-ms-placeholder {
  transition: opacity 0.45s ease;
  opacity: 0;
}

.search-icon {
  cursor: pointer;
  position: relative;
  float: right;
  width: 75px;
  height: 75px;
  top: -62px;
  right: -15px;
}

@import url("https://fonts.googleapis.com/css?family=Inter:400'");
html {
  background-color: #1a1a1a;
  overflow: hidden;
}

.middle {
  width: 14%;
  /* Made by */
}
.middle h1 {
  font-family: 'Inter', sans-serif;
  color: #fff;
}
.middle input[type='radio'] {
  display: none;
}
.middle input[type='radio']:checked + .box {
  background-color: #007e90;
}
.middle input[type='radio']:checked + .box span {
  color: white;
  transform: translateY(70px);
}
.middle input[type='radio']:checked + .box span:before {
  transform: translateY(0px);
  opacity: 1;
}
.middle .box {
  width: 200px;
  height: 300px;
  background-color: #fff;
  transition: all 250ms ease;
  will-change: transition;
  display: block;
  text-align: center;
  cursor: pointer;
  position: relative;
  font-family: 'Inter', sans-serif;
  font-weight: 900;
}
.middle .box:active {
  transform: translateY(10px);
}
.middle .box span {
  position: absolute;
  transform: translate(0, 60px);
  left: 0;
  right: 0;
  bottom: 185px;
  transition: all 300ms ease;
  font-size: 1.5em;
  user-select: none;
  color: #007e90;
}
.middle .box span:before {
  font-size: 1.2em;
  font-family: FontAwesome;
  display: block;
  transform: translateY(-80px);
  opacity: 0;
  transition: all 300ms ease-in-out;
  font-weight: normal;
  color: white;
}
.middle .front-end span:before {
  content: '\f124';
}
.middle .back-end span:before {
  content: '\f041';
}
.middle p {
  color: #fff;
  font-family: 'Inter', sans-serif;
  font-weight: 400;
}
.middle p a {
  text-decoration: underline;
  font-weight: bold;
  color: #fff;
}
.middle p span:after {
  content: '\f021';
  font-family: FontAwesome;
  color: yellow;
}
</style>
