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

      <div class="buttonBoxes">
        <div class="types reInput">
          <div class="buttons fontSize" @click="toggle(0)">시작점</div>
        </div>
        <div class="types reInput">
          <div class="buttons fontSize" @click="toggle(1)">도착점</div>
        </div>
        <!-- <div class="types reInput">
                <div class="buttons fontSize" @click="toggle(2)">
                    경로보기
                </div>
            </div> -->
      </div>

      <div class="content">
        <div class="inputBox">
          <b-input-group>
            <b-form-input
              id="searchKeyword"
              placeholder="시작점 검색"
              @keyup.enter="findTarget()"
            ></b-form-input>
            <b-input-group-append>
              <b-button variant="outline-primary" @click="findTarget()"
                >검색</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </div>

        <!-- 3가지 형태()-->
        <div class="stopover">
          <div v-show="findStartPoint" style="border-right: 1px solid black">
            <ul id="searchResult" name="searchResult">
              <div v-for="(item, index) in searchResult" :key="index">
                <img :src="item.src" style="vertical-align: middle" /><span>{{
                  item.name
                }}</span>
                <b-button
                  variant="success"
                  name="sendBtn"
                  @click="selected(item, 0, index)"
                  >시작점 설정</b-button
                >
              </div>
            </ul>
          </div>
          <div v-show="findEndPoint">
            <ul id="searchResult" name="searchResult">
              <div v-for="(item, index) in searchResult" :key="index">
                <img :src="item.src" style="vertical-align: middle" /><span>{{
                  item.name
                }}</span>
                <b-button
                  variant="warning"
                  name="sendBtn"
                  @click="selected(item, 1, index)"
                  >도착점 설정</b-button
                >
              </div>
            </ul>
          </div>
        </div>

        <div class="itemList">
          <!-- {{startPointObject.name}} -->
          <img
            v-show="Object.keys(startPointObject).length != 0"
            src="http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png"
            style="vertical-align: middle"
          /><span>{{ startPointObject.name }}</span>
          <hr />
          <p id="result"></p>
          <select id="selectLevel">
            <option value="0" selected="selected">교통최적+추천</option>
            <option value="1">교통최적+무료우선</option>
            <option value="2">교통최적+최소시간</option>
            <option value="3">교통최적+초보</option>
          </select>
          <!-- <button id="btn_select" type="button"  class="btn btn-primary" >적용하기</button> -->
          <!-- <button @click="findRouteTmap()">눌러주세용ㅇㅇㅇㅇ</button> -->
          <b-button
            :disabled="
              Object.keys(startPointObject).length0 == 0 ||
              Object.keys(endPointObject).length == 0
            "
            @click="findRouteTmap()"
            >검색</b-button
          >
          <hr />
          <div
            v-for="(item, index) in stopOverList"
            :key="index"
            style="display: flex; justify-content: space-between"
          >
            <i class="fi fi-sr-Cross-circle"></i>
            <span style="font: 25px">
              {{ item.name }}
            </span>
            <img
              src="../../static/icon/cross.png"
              style="width: 20px; height: 20px"
              @click="deleteStopOver(index)"
            />
          </div>
          <hr />
          <img
            v-show="Object.keys(endPointObject).length != 0"
            src="http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png"
            style="vertical-align: middle"
          /><span>{{ endPointObject.name }}</span>
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


export default {
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
      stopOverList: [
        {
          name: 'test1',
          lat: 37.56626352138058,
          lon: 126.98735015742581,
        },
        {
          name: 'test2',
          lat: 37.56568310756034,
          lon: 127.00221495976581,
        },
        {
          name: 'test3',
          lat: 37.570369,
          lon: 126.992153,
        },
        {
          name: 'test4',
          lat: 37.56335290252303,
          lon: 127.00352387777271,
        },
        {
          name: 'test5',
          lat: 37.570721714117965,
          lon: 127.00186090818215,
        },
        {
          name: 'test6',
          lat: 37.56515390827723,
          lon: 126.99066536776698,
        },
      ],
      stopOverObjectList: [],

      resultInfoArr: [],
      markerArr: [],
      labelArr: [],
      resultMarkerArr: [],

      changedStart:Object,
      changedEnd:Object,
    }
  },
  mounted() {
    this.makeMap()
    this.pickStopOver()
  },
  methods: {
    // 경유지 찍기
    pickStopOver() {
      for (let i = 0; i < this.stopOverList.length; i++) {
        const mapInstance = new Tmapv2.Marker({
          position: new Tmapv2.LatLng(
            this.stopOverList[i].lat,
            this.stopOverList[i].lon
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
          success (response) {
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
          error (request, status, error) {
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
        success (response) {
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
            const projectionCng = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
              pointCng
            )

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
        error (request, status, error) {
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

      for(let i in this.markerArr){
        this.markerArr[i].setMap(null);
      }

      
      if(this.resultInfoArr.length > 0){
        this.changedStart.setMap(null);
        this.changedEnd.setMap(null);
      }
      
       this.changedStart=new Tmapv2.Marker({
          position: new Tmapv2.LatLng(
            this.startPointObject.lat,
            this.startPointObject.lon
          ),
          icon:
            'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_g_m_s.png',
          iconSize: new Tmapv2.Size(24, 38),
          map: this.map,
        })

        this.changedEnd=new Tmapv2.Marker({
          position: new Tmapv2.LatLng(
            this.endPointObject.lat,
            this.endPointObject.lon
          ),
          icon:
            'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_g_m_e.png',
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
      let resultData = null;
      let resultFeatures = null;
      await axios.post('https://apis.openapi.sk.com/tmap/routes/routeSequential30?version=1&format=json',JSON.stringify(dataInfo),{headers})
      .then(function(response){
        resultData=response.data.properties
        resultFeatures = response.data.features
      })

      console.log(JSON.stringify(resultData,null,2))
      console.log(JSON.stringify(resultFeatures,null,2))

      // 결과 출력
      const tDistance =
        '총 거리 : ' +
        (resultData.totalDistance / 1000).toFixed(1) +
        'km,  '
      const tTime =
        '총 시간 : ' + (resultData.totalTime / 60).toFixed(0) + '분'
      $('#result').text(tDistance + tTime)

      // 기존의 길과 포인트들 전부 삭제
      if (this.resultInfoArr.length > 0) {
        for (let i in this.resultInfoArr) {
          this.resultInfoArr[i].setMap(null)
        }
        this.resultInfoArr = []
      }

      if (this.resultMarkerArr.length > 0) {
        for (let i in this.resultMarkerArr) {
          this.resultMarkerArr[i].setMap(null)
        }
        this.resultMarkerArr = []
      }

      // 루트 그림 그리는 포인트를 담는 배열
      const drawInfoArr = []

      for (let i in resultFeatures) {
        const geometry = resultFeatures[i].geometry
        const properties = resultFeatures[i].properties
        let polyline_;

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
          const convertPoint =
            new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(latlon)

          const marker_p = new Tmapv2.Marker({
            position: new Tmapv2.LatLng(
              convertPoint._lat,
              convertPoint._lng
            ),
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
.test {
  background-color: black;
}

.findRoute {
  background-color: #eaf2f9;
  overflow: auto;
  width: 100%;
  height: 100vh;
}

.container {
  display: inline-flex;
  flex-direction: column;
  width: 80%;
  padding-left: 20%;
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
  height: 55%;
  margin-left: -10%;
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
</style>
