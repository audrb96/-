<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="review">
    <div class="main-content">
      <div>
        <div class="spot-img">
          <img src="/review/deoksugung.jpg" alt="" />
        </div>
        <div>
          <div class="spot-main">
            <div class="spot-main-title">{{ spot.title }}</div>
            <button>경유지추가</button>
          </div>
          <div class="spot-content">
            {{ spot.content }}
          </div>
          <input
            type="text"
            style="background-color: black"
            placeholder="bbbb"
          />
        </div>
      </div>
      <div class="spot-review">
        <div class="spot-review-top">
          <div class="spot-review-title">후기</div>
          <div>
            <review-modal />
            <v-icon large style="cursor: pointer" @click="MoveCheck"
              >mdi-plus-box</v-icon
            >
          </div>
        </div>
        <div class="spot-review-content">
          <link
            rel="stylesheet"
            type="text/css"
            href="https://cdn.shopify.com/s/files/1/2979/3338/files/UGC-style.css"
          />

          <script
            type="text/javascript"
            src="https://cdn.shopify.com/s/files/1/2979/3338/files/UGC_-_new_v.3.js"
          ></script>
          <!--<script type="text/javascript" src="https://cdn.shopify.com/s/files/1/2979/3338/files/UGC-js-2.0-min.js"></script> -->
          <div class="slick-wrapper home-insta">
            <div class="slider card-slider slick-slider">
              <div
                v-for="(review, idx) in reviews"
                :key="idx"
                class="card-slider-item"
              >
                <img
                  :src="
                    'https://cdn.shopify.com/s/files/1/2979/3338/files/' +
                    (idx + 1) +
                    '.jpg'
                  "
                  alt="이미지"
                />
                <div class="insta-post">
                  <p>
                    {{ review.date }}<br /><strong>{{ idx + 1 }}</strong
                    ><br />
                    {{ review.content }}
                  </p>
                </div>
                <div class="password-main">
                  <div class="password-content">
                    <div class="password-container" tabindex="1">
                      <div
                        class="search-container"
                        tabindex="1"
                        @mouseover="focusOn(idx)"
                        @mouseout="focusOut(idx)"
                      >
                        <input placeholder="password" type="password" />
                        <a class="password-button">
                          <v-icon :id="'key-icon' + idx">mdi-key</v-icon>
                          <v-icon
                            :id="'pencil-icon' + idx"
                            style="display: none"
                            >mdi-lead-pencil</v-icon
                          >
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
import NavbarComponent from '~/components/NavbarComponent.vue'
import ReviewModal from '~/components/ReviewModal.vue'

export default {
  components: { ReviewModal },
  component: { NavbarComponent },
  data() {
    return {
      // items: [
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      //   { isPass: true, isEnter: false },
      // ],
      dialog: false,
      spot: {
        title: '동궁과 월지',
        content:
          '신라의 문무왕이 삼국을 통일한 후 궁궐을 넓게 확장해 태자가 거처하도록 한 게 동궁! 밤에 보니깐 더 화려해보이네요.',
      },
      reviews: [
        {
          image: '',
          content: '임시로 넣은 데이터1입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터2입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터3입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터4입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터5입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터6입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터7입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터8입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터9입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
        {
          image: '',
          content: '임시로 넣은 데이터10입니다.',
          date: '2022-09-15',
          isPass: true,
          isEnter: false,
        },
      ],
    }
  },
  methods: {
    MoveCheck() {
      const modal = document.getElementsByClassName('check-modal')[0]
      modal.style.display = 'block'
    },
    focusOn(idx) {
      // eslint-disable-next-line no-console
      // console.log(idx)
      // this.reviews[idx].isPass = false
      // this.reviews[idx].isEnter = true
      this.$el.querySelector(`#key-icon${idx}`).style.display = 'none'
      this.$el.querySelector(`#pencil-icon${idx}`).style.display = 'block'
    },
    focusOut(idx) {
      // this.reviews[1].isPass = true
      // this.reviews[1].isEnter = false
      this.$el.querySelector(`#key-icon${idx}`).style.display = 'block'
      this.$el.querySelector(`#pencil-icon${idx}`).style.display = 'none'
    },
  },
}
</script>

<style scoped>
@font-face {
  font-family: 'Cafe24Ssurround';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/Cafe24Ssurround.woff')
    format('woff');
  font-weight: bold;
  font-style: normal;
}
* {
  font-family: 'Cafe24Ssurround';
}
.review {
  width: 100vw;
  height: 100%;
  display: flex;
  justify-content: space-between;
}
.main-content {
  width: 90%;
  margin-left: 20px;
}
.spot-img {
  text-align: center;
  margin: 40px 0;
}
.spot-img > img {
  width: 500px;
}
.spot-main {
  display: flex;
  justify-content: space-between;
}
.spot-main-title {
  font-size: 25px;
  font-weight: bold;
}
.spot-main > button {
  padding: 10px;
  background-color: #45a9c8;
  border-radius: 5px;
  color: white;
}
.spot-content {
  width: 70%;
  margin-bottom: 40px;
}
.spot-review-top {
  display: flex;
  align-items: center;
}
.spot-review-title {
  font-size: 25px;
  font-weight: bold;
  margin-right: 20px;
}
@media (max-width: 420px) {
  .card-slider-item {
    width: 100%;
  }
}
.password-main {
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  font-family: 'Cabin';
  height: 10%;
}

.password-content {
  height: 100%;
}

.password-container {
  display: block;
  max-width: 900px;
  margin: auto;
  padding: 5px;
  height: 100%;
  outline: none;
}

.search-container {
  overflow: hidden;
  float: right;
  height: 2.5em;
  width: 2.5em;
  border-radius: 2em;
  margin-bottom: 5px;
  box-shadow: 0 0 5px #6a5d4f;
  -moz-transition: all 0.35s;
  -webkit-transition: all 0.35s;
}
.search-container:hover,
.search-container:focus,
.search-container:focus-within {
  width: 10em;
  border-radius: 5px 2em 2em 5px;
  outline: none;
}
.search-container:hover input,
.search-container:focus input,
.search-container:focus-within input {
  display: inline-block;
  width: 7em;
}

input {
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;
  float: left;
  width: 0em;
  height: 2em;
  margin: 0.3em;
  margin-right: -4.5em;
  background: #fff;
  color: #6a5d4f;
  font-size: 1em;
  font-weight: 600;
  padding: 0px;
  border: 0;
  border-radius: 5px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2) inset;
  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
  -moz-transition: all 0.25s;
  -webkit-transition: all 0.25s;
}
input:focus {
  outline: none;
  box-shadow: 0 -1px 1px rgba(255, 255, 255, 0.25),
    0 1px 5px rgba(0, 0, 0, 0.15);
}
input[type='password'] {
  font-family: '맑은고딕', '돋움';
}
input[type='password']::placeholder {
  font-family: 'Cafe24Ssurround';
}

.password-button {
  display: flex;
  align-items: center;
  justify-content: center;
  float: right;
  width: 1em;
  height: 1em;
  margin: 0.125em;
  /* background: #6a5d4f; */
  text-align: center;
  font-size: 2em;
  color: #eaf2f9;

  border-radius: 50%;
  cursor: pointer;
  /* box-shadow: 0 -1px 1px rgba(255, 255, 255, 0.25),
    0 1px 1px rgba(0, 0, 0, 0.25); */
  /* text-shadow: 0 -2px 1px rgba(0, 0, 0, 0.3); */
}
.password-button:active {
  border: 0 !important;
  text-shadow: 0 0 0;
}
.password-button i {
  font-size: 85%;
}
.button-icon {
  display: none;
}
.Pass {
  display: block;
}
.Enter {
  display: block;
}
</style>
