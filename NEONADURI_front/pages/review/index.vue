<template>
  <div class="review">
    <div class="main-content">
      <div class="review-top">
        <img class="review-logo" src="/logo/review-detail-logo.png" alt="" />
      </div>
      <div class="spot-main slide-in-right">
        <div class="spot-left">
          <!-- <img
            :src="
              'https://neonaduri.s3.ap-northeast-2.amazonaws.com/' +
              spot.spotImage
            "
            alt=""
          /> -->
          <img :src="spot.spotImage" alt="" />
        </div>
        <div class="spot-right">
          <div class="spot-main-title">
            <div class="title-text">
              {{ spot.spotName }}
            </div>
            <div class="title-icon">
              <v-icon @click="modifyContent">mdi-lead-pencil</v-icon>
            </div>
          </div>
          <div class="spot-content">
            {{ spot.spotContent }}
          </div>
          <div class="content-modify" style="display: none">
            <div class="content-modify-sub">
              <textarea :value="spot.spotContent" class="modify-input" />
              <!-- <textarea value="임시 내용" class="modify-input" /> -->
              <button class="modify-button" @click="contentSave">저장</button>
            </div>
          </div>
          <div class="spot-add">
            <button class="add-btn" @click="addSpot">경유지추가</button>
          </div>
        </div>
      </div>
      <div class="spot-review">
        <div class="spot-review-top">
          <div class="spot-review-title">후기</div>
          <div>
            <review-modal
              v-if="inputToggle"
              :value="inputToggle"
              @updateStatus="changeInput"
            />
            <modify-modal
              v-if="modifyToggle"
              :value="modifyToggle"
              @updateStatus="changeModify"
            />
            <v-icon large style="cursor: pointer" @click="MoveCheck"
              >mdi-plus-box</v-icon
            >
          </div>
        </div>
        <div class="spot-review-content" style="height: 100px">
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
                v-for="(review, idx) in reviewList"
                :key="idx"
                class="card-slider-item"
                @mouseleave="focusOut(idx)"
              >
                <img
                  :src="
                    'https://neonaduri.s3.ap-northeast-2.amazonaws.com/' +
                    review.reviewImage
                  "
                  style="width: 100%; height: 45%"
                  alt="이미지"
                />
                <div class="insta-post" style="height: 55%">
                  <div>
                    <strong>
                      <div style="font-size: 20px">
                        {{ review.reviewContent }}
                      </div>
                    </strong>
                    <br />
                    <div class="tag-box">
                      <div
                        v-for="(tag, i) in reviewList[idx].tagContents"
                        :key="i"
                      >
                        #{{ tag }}
                      </div>
                    </div>
                  </div>
                </div>
                <div class="password-main">
                  <div class="password-content">
                    <div class="password-container" tabindex="1">
                      <div
                        class="search-container"
                        tabindex="1"
                        @mouseover="focusOn(idx)"
                      >
                        <input
                          :class="
                            `password-input password-input` + review.reviewId
                          "
                          placeholder="password"
                          type="password"
                        />
                        <a class="password-button">
                          <v-icon :id="'key-icon' + idx">mdi-key</v-icon>
                          <v-icon
                            :id="'pencil-icon' + idx"
                            style="display: none"
                            @click="enterPass(review, idx)"
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
    <navbar-component> </navbar-component>
  </div>
</template>
<script>
import { mapActions, mapState, mapMutations } from 'vuex'
import ModifyModal from '~/components/ModifyModal.vue'

export default {
  components: { ModifyModal },
  data() {
    return {
      inputToggle: false,
      modifyToggle: false,
    }
  },
  computed: {
    ...mapState('spot', ['spot']),
    ...mapState('review', ['reviewList']),
  },
  created() {
    // 불러올 때 review_id도 불러옴
    this.callReviews(this.spot.spotId)
  },
  mounted() {},
  methods: {
    ...mapMutations('route', ['ADD_ROUTE']),
    ...mapMutations('review', ['CLEAR_REVIEW', 'SET_REVIEW']),
    ...mapActions('spot', ['changeContent']),
    ...mapActions('review', ['callReviews', 'confirmPass']),
    MoveCheck() {
      this.inputToggle = !this.inputToggle
    },
    changeInput() {
      this.inputToggle = !this.inputToggle
    },
    changeModify() {
      this.modifyToggle = !this.modifyToggle
    },

    focusOn(idx) {
      this.$el.querySelector(`#key-icon${idx}`).style.display = 'none'
      this.$el.querySelector(`#pencil-icon${idx}`).style.display = 'block'
    },
    focusOut(idx) {
      this.$el.querySelector(`#key-icon${idx}`).style.display = 'block'
      this.$el.querySelector(`#pencil-icon${idx}`).style.display = 'none'
    },
    modifyContent() {
      this.$el.querySelector(`.content-modify`).style.display = 'block'
      this.$el.querySelector(`.spot-content`).style.display = 'none'
      this.$el.querySelector(`.modify-input`).focus()
    },
    contentSave() {
      this.$el.querySelector(`.spot-content`).innerHTML =
        this.$el.querySelector(`.modify-input`).value
      this.$el.querySelector(`.spot-content`).style.display = 'block'
      this.$el.querySelector(`.content-modify`).style.display = 'none'
      this.changeContent(this.$el.querySelector(`.modify-input`).value)
    },
    // review 불러오면 idx -> review_id로 바꾸기
    async enterPass(review, index) {
      // review_id와 review_password받아오기
      const info = {
        idx: index,
        id: review.reviewId,
        password: await this.$el.querySelector(
          `.password-input${review.reviewId}`
        ).value,
      }
      await this.confirmPass(info)
      if (this.reviewList[index].pass) {
        this.modifyToggle = !this.modifyToggle
        this.CLEAR_REVIEW()
        this.SET_REVIEW(review)
      }
    },
    addSpot() {
      const route = {
        id: this.spot.spotId,
        name: this.spot.spotName,
        lat: this.spot.lat,
        lng: this.spot.lng,
      }
      this.ADD_ROUTE(route)
    },
  },
}
</script>

<style scoped>
.slide-in-right {
  -webkit-animation: slide-in-right 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94)
    both;
  animation: slide-in-right 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
}
.slide-out-top {
  -webkit-animation: slide-out-top 0.5s cubic-bezier(0.55, 0.085, 0.68, 0.53)
    both;
  animation: slide-out-top 0.5s cubic-bezier(0.55, 0.085, 0.68, 0.53) both;
}
@-webkit-keyframes slide-in-right {
  0% {
    -webkit-transform: translateX(1000px);
    transform: translateX(1000px);
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
    -webkit-transform: translateX(1000px);
    transform: translateX(1000px);
    opacity: 0;
  }
  100% {
    -webkit-transform: translateX(0);
    transform: translateX(0);
    opacity: 1;
  }
}
@-webkit-keyframes slide-out-top {
  0% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 1;
  }
  100% {
    -webkit-transform: translateY(-1000px);
    transform: translateY(-1000px);
    opacity: 0;
  }
}
@keyframes slide-out-top {
  0% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 1;
  }
  100% {
    -webkit-transform: translateY(-1000px);
    transform: translateY(-1000px);
    opacity: 0;
  }
}
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
  height: 100vh;
  display: flex;
  justify-content: space-between;
  overflow: auto;
}
.main-content {
  width: 100%;
}
.review-top {
  width: 100%;
  height: 25%;
  display: flex;
  justify-content: center;
}
.review-logo {
  width: 40%;
  margin: 0 auto;
}
.spot-main {
  position: relative;
  height: 65%;
  background-image: url('/banner/statistics-background.png');
  background-position-y: 100%;
  background-position-x: 20px;
  background-repeat: no-repeat;
  background-size: 100% 130%;
}
.spot-left {
  position: absolute;
  /* text-align: center; */
  /* margin: 40px 0; */
  left: 10%;
  top: 13%;
  bottom: inherit;
}
.spot-left > img {
  width: 500px;
  height: 330px;
}
.spot-right {
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 40%;
  height: 70%;
  max-height: 100%;
  left: 50%;
  top: 13%;
}
.spot-main-title {
  width: 90%;
  height: 20%;
  display: flex;
  font-size: 35px;
  font-weight: bold;
  margin-bottom: 20px;
}
.title-icon {
  margin-left: 1%;
  cursor: pointer;
}
.spot-content {
  width: 70%;
  height: 60%;
  margin-bottom: 40px;
}
.content-modify {
  height: 60%;
}
.content-modify-sub {
  display: flex;
}
.modify-input {
  width: 70%;
  margin-bottom: 40px;
}
.modify-button {
  height: 5%;
  margin: 15px 0 0 15px;
  padding: 5px;
  background-color: #81c147;
  border-radius: 5px;
  color: white;
}
.spot-add {
  display: flex;
  justify-content: flex-end;
  margin-right: 10%;
}
.add-btn {
  padding: 10px;
  background-color: #81c147;
  border-radius: 5px;
  color: white;
}

/* .spot-img {
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

.content-modify-sub {
  display: flex;
}
.modify-input {
  width: 70%;
  margin-bottom: 40px;
}
.modify-button {
  height: 5%;
  margin: 15px 0 0 15px;
  padding: 5px;
  background-color: #45a9c8;
  border-radius: 5px;
  color: white;
} */
.spot-review {
  width: 85%;
  margin: auto;
  /* height: 30%; */
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
  transition: 0ms;
}
.search-container:hover,
.search-container:focus,
.search-container:focus-within {
  width: 10em;
  border-radius: 5px 2em 2em 5px;
  outline: none;
}
.search-container:hover .password-input,
.search-container:focus .password-input,
.search-container:focus-within .password-input {
  display: inline-block;
  width: 7em;
}

.insta-post {
  max-height: 230px;
}

.tag-box {
  display: flex;
}

.password-input {
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
  transition: 0ms;
}
.password-input:focus {
  outline: none;
  box-shadow: 0 -1px 1px rgba(255, 255, 255, 0.25),
    0 1px 5px rgba(0, 0, 0, 0.15);
}
.password-input[type='password'] {
  font-family: '맑은고딕', '돋움';
}
.password-input[type='password']::placeholder {
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
