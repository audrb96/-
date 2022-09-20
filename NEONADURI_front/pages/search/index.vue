<template>
  <div class="search">
    <div style="width: 100%">
      <div class="main-content fadeInUp">
        <div class="recommand">
          <div class="recommand-content">
            <div class="recommand-left">
              <img
                src="/review/deoksugung.jpg"
                alt="임시"
                class="recommand-img"
              />
              <div>
                <h2 style="font-family: 'SEBANG_Gothic_Bold'">동궁과 월지</h2>
                <br />
                <div class="recommand-des">
                  신라의 문무왕이 삼국을 통일한 후 궁궐을 넓게 확장해 태자가
                  거처하도록 한 게 동궁! 밤에 보니깐 더 화려해보이네요.
                </div>
                <div>#데이트 #경주</div>
              </div>
            </div>
            <div class="top-title">
              <img src="/icon/star.png" alt="star" class="star-icon" />
              <div>추천장소</div>
            </div>
          </div>
        </div>
      </div>
      <div class="option-content fadeInUp">
        <div class="option">
          <div class="search-bar">
            <h1 style="font-family: 'SEBANG_Gothic_Bold'">검색 :</h1>
            <input v-model="searchWord" type="text" class="search-input" />
          </div>
          <hr />
          <div class="search-bar">
            <h1 style="font-family: 'SEBANG_Gothic_Bold'">지역 :</h1>
            <b-form-select
              v-model="sido"
              :options="sidoSelect"
              class="selected"
              @change="sidoChange"
            ></b-form-select>
            <b-form-select
              v-model="sigungu"
              :options="sigunguSelect"
              class="selected"
              @change="myeonChange"
            ></b-form-select>
            <b-form-select
              v-model="myeon"
              :options="myeonSelect"
              class="selected"
            ></b-form-select>
          </div>
          <hr />
          <div>
            <div
              v-for="(option, index) in options"
              :key="index"
              class="option-row mb-3"
            >
              <div class="mr-3 mdClass-title">{{ option.mdClass }}</div>
              <div class="btn-group">
                <v-btn
                  v-for="(element, i) in option.smClass"
                  :key="i"
                  color="#98d4f3"
                  :value="element.name"
                  class="mr-3 class-btn"
                  :class="{ clicked: element.isSelected }"
                  @click="btnClick(element, $event)"
                  ><img
                    class="btn-icon"
                    :src="'/icon/' + element.icon"
                    alt=""
                  />{{ element.name }}</v-btn
                >
              </div>
            </div>
          </div>
          <div class="button-box">
            <button class="custom-btn btn-12" @click="moveSearchResult">
              <span>레츠고!</span><span>어디로 갈까요?</span>
            </button>
          </div>
        </div>
      </div>
    </div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'SearchPage',
  data() {
    return {
      options: [
        {
          mdClass: '자연',
          smClass: [
            {
              icon: 'forest.png',
              name: '숲',
              isSelected: false,
            },
            {
              icon: 'park.png',
              name: '관광공원/허브마을',
              isSelected: false,
            },
            {
              icon: 'lake.png',
              name: '대형호수/저수지',
              isSelected: false,
            },
            {
              icon: 'plant.png',
              name: '식물원',
              isSelected: false,
            },
            {
              icon: 'waterfall.png',
              name: '폭포/계곡',
              isSelected: false,
            },
            {
              icon: 'sea.png',
              name: '해수욕장',
              isSelected: false,
            },
            {
              icon: 'tree.png',
              name: '휴양림/수목원',
              isSelected: false,
            },
          ],
        },
        {
          mdClass: '관광',
          smClass: [
            {
              icon: 'sightseeing1.png',
              name: '유명관광지',
              isSelected: false,
            },
            {
              icon: 'sightseeing2.png',
              name: '일반관광지',
              isSelected: false,
            },
          ],
        },
        {
          mdClass: '체험',
          smClass: [
            {
              icon: 'zoo.png',
              name: '동물원',
              isSelected: false,
            },
            {
              icon: 'aquarium.png',
              name: '아쿠아리움/대형수족관',
              isSelected: false,
            },
            {
              icon: 'funfair.png',
              name: '일반유원지/일반놀이공원',
              isSelected: false,
            },
            {
              icon: 'theme.png',
              name: '테마공원/대형놀이공원',
              isSelected: false,
            },
          ],
        },
        {
          mdClass: '전통',
          smClass: [
            {
              icon: 'desk.png',
              name: '서원/향교/서당',
              isSelected: false,
            },
            {
              icon: 'temple1.png',
              name: '유명사찰',
              isSelected: false,
            },
            {
              icon: 'temple2.png',
              name: '지역사찰',
              isSelected: false,
            },
          ],
        },
        {
          mdClass: '쇼핑',
          smClass: [
            {
              icon: 'market.png',
              name: '시장',
              isSelected: false,
            },
            {
              icon: 'food.png',
              name: '먹거리/패션거리',
              isSelected: false,
            },
            {
              icon: 'souvenir.png',
              name: '토속/특산물/기념품매장',
              isSelected: false,
            },
          ],
        },
        {
          mdClass: '카지노',
          smClass: [
            {
              icon: '7.png',
              name: '세븐럭카지노',
              isSelected: false,
            },
            {
              icon: 'paradiseCasino.png',
              name: '파라다이스카지노',
              isSelected: false,
            },
            {
              icon: 'casino.png',
              name: '카지노',
              isSelected: false,
            },
          ],
        },
        {
          mdClass: '어린이',
          smClass: [
            {
              icon: 'pororo.png',
              name: '뽀로로',
              isSelected: false,
            },
            {
              icon: 'tayo.png',
              name: '타요',
              isSelected: false,
            },
            {
              icon: 'poly.png',
              name: '폴리',
              isSelected: false,
            },
            {
              icon: 'larva.png',
              name: '라바',
              isSelected: false,
            },
            {
              icon: 'guitar.png',
              name: '기타',
              isSelected: false,
            },
          ],
        },
      ],
      sido: 'all',
      sigungu: 'all',
      myeon: 'all',
      searchWord: '',
      selected: [],
    }
  },
  computed: {
    ...mapGetters('region', ['sidoSelect', 'sigunguSelect', 'myeonSelect']),
  },
  created() {
    this.callSidos()
  },
  methods: {
    ...mapActions('region', ['callSidos', 'callSigungus', 'callMyeons']),
    sidoChange() {
      this.callSigungus(this.sido)
    },
    myeonChange() {
      const location = { sido: this.sido, sigungu: this.sigungu }
      this.callMyeons(location)
    },
    btnClick(element) {
      if (this.selected.includes(element.name)) {
        this.selected = this.selected.filter((item) => {
          return item !== element.name
        })
      } else {
        this.selected.push(element.name)
      }
      element.isSelected = !element.isSelected

      console.log(this.selected)
    },
    moveSearchResult() {
      this.$router.push('search/searchResult')
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

@font-face {
  font-family: 'SEBANG_Gothic_Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2104@1.0/SEBANG_Gothic_Bold.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
.mdClass-title {
  width: 8%;
  font-size: 30px;
  font-family: 'SEBANG_Gothic_Bold';
  padding-left: 10px;
  height: 40px;
  border-left: 5px solid #02a6f8;
}

.class-btn {
  display: flex;
  align-items: center;
}

.btn-icon {
  width: 20px;
  margin-right: 10px;
}

.button-box {
  display: flex;
  justify-content: end;
}

.custom-btn {
  width: 130px;
  height: 40px;
  color: #fff;
  border-radius: 5px;
  padding: 10px 25px;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  outline: none;
}

.btn-12 {
  position: relative;
  right: 20px;
  bottom: 20px;
  border: none;
  box-shadow: none;
  width: 130px;
  height: 40px;
  line-height: 42px;
  -webkit-perspective: 230px;
  perspective: 230px;
}
.btn-12 span {
  background: rgb(0, 172, 238);
  background: linear-gradient(
    0deg,
    rgba(0, 172, 238, 1) 0%,
    rgba(2, 126, 251, 1) 100%
  );
  display: block;
  position: absolute;
  width: 130px;
  height: 40px;
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  margin: 0;
  text-align: center;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all 0.3s;
  transition: all 0.3s;
}
.btn-12 span:nth-child(1) {
  box-shadow: -7px -7px 20px 0px #fff9, -4px -4px 5px 0px #fff9,
    7px 7px 20px 0px #0002, 4px 4px 5px 0px #0001;
  -webkit-transform: rotateX(90deg);
  -moz-transform: rotateX(90deg);
  transform: rotateX(90deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12 span:nth-child(2) {
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12:hover span:nth-child(1) {
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
}
.btn-12:hover span:nth-child(2) {
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  color: transparent;
  -webkit-transform: rotateX(-90deg);
  -moz-transform: rotateX(-90deg);
  transform: rotateX(-90deg);
}
.search {
  font-family: 'GmarketSansMedium';
  height: 65vw;
}
.btn-group {
  width: 90%;
  display: flex;
}

.clicked {
  background-color: #02a6f8 !important;
}

.option-row {
  display: flex;
  align-items: center;
}
.selected {
  margin-left: 20px;
  margin-bottom: 10px;
}
.search-bar {
  display: flex;
  align-items: center;
}
.custom-select {
  width: 25%;
}
.search-input {
  margin-left: 20px;
  margin-bottom: 10px;
  width: 800px;
  border: none;
  border-radius: 20px;
  outline: none;
  padding: 10px;
  font-size: 1em;
  color: #676767;
  transition: border 0.5s;
  -webkit-transition: border 0.5s;
  -moz-transition: border 0.5s;
  -o-transition: border 0.5s;
  border: solid 3px #98d4f3;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.search-input:focus {
  border: solid 3px #02a6f8;
}
.option-content {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
.option {
  padding: 2%;
  border-radius: 15px;
  background-color: white;
  width: 90%;
}
.recommand-left {
  display: flex;
}
.star-icon {
  width: 20px;
  margin-right: 5%;
}

.recommand-img {
  margin-right: 1.5%;
  width: 300px;
  height: 150px;
}

.recommand-des {
  margin-bottom: 1%;
}

.main-content {
  display: flex;
  justify-content: center;
}
.recommand {
  width: 80vw;
  padding-top: 2%;
  background-color: #eaf2f9;
}
.recommand-content {
  border-radius: 15px;
  padding: 1%;
  display: flex;
  justify-content: space-between;
  background-color: #cbe7f0;
}
.top-title {
  width: 110px;
  height: 20px;
  display: flex;
  font-size: 20px;
}

.fadeInUp {
  animation: fadeInUp 1s ease backwards;
}
@keyframes fadeInUp {
  0% {
    transform: translate(0px, 100px);
    opacity: 0;
  }
  100% {
    transform: translate(0px, 0);
    opacity: 1;
  }
}
</style>
