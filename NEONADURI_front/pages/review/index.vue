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
            <div class="spot-main-title">동궁과 월지</div>
            <button>경유지추가</button>
          </div>
          <div class="spot-content">
            신라의 문무왕이 삼국을 통일한 후 궁궐을 넓게 확장해 태자가
            거처하도록 한 게 동궁! 밤에 보니깐 더 화려해보이네요.
          </div>
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
              <!-- 1 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/1.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Deborah.A<br /><strong>5 Star sim</strong><br />I used my
                    sim in 12 countries for 30 days. It worked perfectly and was
                    very cost effective. Thankyou.
                  </p>
                </div>
              </div>
              <!-- 2 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/2.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Anthony.P<br /><strong>Excellent service</strong
                    ><br />Using the card was seamless. I moved through three
                    countries with no problems at all - more than enough data to
                    last.
                  </p>
                </div>
              </div>
              <!-- 3 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/3.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Brian T<br /><strong>Works flawlessly!</strong><br />Easy
                    to set up and worked well during our trip! Thanks
                    SimsDirect!
                  </p>
                </div>
              </div>

              <!-- 5 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/4.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @George.B<br /><strong
                      >Great Experience with SimsDirect</strong
                    ><br />Have used SimsDirect for two multi country trips to
                    Europe. Everything worked perfectly. I would highly
                    recommend this service.
                  </p>
                </div>
              </div>
              <!-- 6 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/5.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Kieren.M<br /><strong>Couldn’t be happier</strong
                    ><br />Just put the sim and and off we went. Haven’t had any
                    troubles. Would definitely use again. Thank you.
                  </p>
                </div>
              </div>
              <!-- 7 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/6.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Leeza.B<br /><strong
                      >Best overseas data experience I've had</strong
                    ><br />I’ve always relied on my own sim on global roaming
                    and/or wifi locally to travel. after reading reviews online,
                    I ordered the sim as I was o/seas for a month and always had
                    fast speed data all day every day. Will definitely be using
                    this service again when on holidays again.
                  </p>
                </div>
              </div>
              <!--8 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/7.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Warren.H<br /><strong>So Smooth it was seemless</strong
                    ><br />SIM worked like a dream. Everything was online and
                    ready to go by the time I reached my destination. Would
                    definitely use again!
                  </p>
                </div>
              </div>
              <!-- 9 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/8.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Julie.C<br /><strong>Amazing!</strong><br />The sim worked
                    a treat - everywhere while i was in Europe - best $59 i've
                    spent! Thank you and will be using you again when l travel!
                  </p>
                </div>
              </div>
              <!-- 10 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/9.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Bao.D<br /><strong>Amazing</strong><br />Perfect,
                    recommended to everyone I was travelling with that this is
                    the website to go and buy travel a sim. Very reliable
                    compared to the sims my friends got when in Europe. They
                    said next time using this website. Cheers
                  </p>
                </div>
              </div>
              <!-- 11 -->
              <div class="card-slider-item">
                <img
                  src="https://cdn.shopify.com/s/files/1/2979/3338/files/10.jpg"
                  alt=""
                />
                <div class="insta-post">
                  <p class="">
                    @Jun.Y<br /><strong>Works just like it should</strong
                    ><br />Excellent experience overall, the activation process
                    is super easy and speed is quick, will use it on my next
                    travel!
                  </p>
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
      dialog: false,
    }
  },
  methods: {
    MoveCheck() {
      const modal = document.getElementsByClassName('check-modal')[0]
      modal.style.display = 'block'
    },
  },
}
</script>

<style scoped>
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
  font-size: 20px;
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
  font-size: 20px;
  font-weight: bold;
  margin-right: 20px;
}
@media (max-width: 420px) {
  .card-slider-item {
    width: 100%;
  }
}
</style>
