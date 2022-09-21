<template>
  <div class="check-modal">
    <div class="check-modal-box">
      <div class="check-modal-head">
        <div></div>
        <div class="check-modal-head-check">
          <img class="head-logo" src="/logo/create-review-logo.png" alt="" />
        </div>
        <div class="check-modal-head-close" @click="CloseCheck">
          <v-icon large>mdi-close-circle-outline</v-icon>
        </div>
      </div>
      <div class="check-modal-body">
        <div class="check-model-body-top">
          <!-- <img
            class="check-modal-body-img"
            src="/review/deoksugung.jpg"
            alt=""
          /> -->
          <img :src="preview" class="check-modal-body-img" alt="없음" />
          <div class="check-modal-body-input">
            <input
              class="check-modal-body-input-line"
              type="text"
              placeholder="한 줄 입력하세요"
            />
            <input
              class="check-modal-body-input-tag"
              type="text"
              placeholder="태그를 입력하세요"
            />
          </div>
        </div>
        <div class="check-model-body-bot">
          <div class="check-model-body-bot-left">
            <v-file-input
              v-model="file"
              :placeholder="fileInfo?.name"
              @change="previewFile(file)"
            />
            <!-- <v-file-input
              v-model="file"
              text="fileInfo?.name"
              @change="previewFile(file)"
            /> -->
          </div>
          <div class="check-model-body-bot-right">
            <div class="check-model-body-bot-pass">
              <div>비밀번호 설정 :</div>
              <input type="password" placeholder="비밀번호" />
            </div>
            <button @click="writeReview">입력 완료</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'CheckModal',
  data() {
    return {
      preview: '/banner/no-image.png',
      // preview: '/banner/no-image1.png',
      // preview: '/banner/no-image2.png',
      fileInfo: null,
      // 리뷰 post
      review: {
        spotId: null,
        reviewImage: null,
        reviewContent: null,
        reviewPassword: null,
        tag: null,
      },
      // 임시로 넣기
    }
  },
  computed: {
    ...mapState('review', []),
  },
  methods: {
    ...mapActions('review', ['changeReview']),
    CloseCheck() {
      const modal = document.getElementsByClassName('check-modal')[0]
      const span = document.getElementsByClassName('check-modal-head-close')[0]
      span.onclick = function () {
        modal.style.display = 'none'
      }
      window.onclick = function (event) {
        if (event.target === modal) {
          modal.style.display = 'none'
        }
      }
    },

    Waiting() {
      this.postOrders(this.orders)
      this.CLEAR_ITEMS()
      this.CLEAR_CHOICE_FOODS()
      this.CLEAR_CHECK_FOODS()
      this.$router.push('/waiting')
    },
    previewFile(file) {
      if (file) {
        const fileData = (data) => {
          this.preview = data
        }
        this.fileInfo = file
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.addEventListener(
          'load',
          function () {
            fileData(reader.result)
          },
          false
        )
      } else if (file === null) {
        this.fileInfo = null
        this.preview = '/banner/no-image.png'
      } else {
        file = this.fileInfo
        const fileData = (data) => {
          this.preview = data
        }
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.addEventListener(
          'load',
          function () {
            fileData(reader.result)
          },
          false
        )
      }
    },
    writeReview() {
      this.changeReview(this.review)
    },
  },
}
</script>

<style scoped>
input {
  padding-left: 3%;
}
.check-modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: -50px;
  width: 100vw;
  height: 100vh;
  /* overflow: auto; */
  font-size: 30px;
}

/* Modal Content/Box */
.check-modal-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: white;
  /* color: white; */
  /* 15% from the top and centered */
  /* margin: 15% auto;
  padding: 15px; */
  /* width: 35%; Could be more or less, depending on screen size */
  /* height: 30%; */
  width: 70%;
  height: 90%;
  margin: 10vh auto;
}

.check-modal-head {
  width: 100%;
  height: 15%;
  background-color: #cbdcf0;
  display: flex;
  justify-content: space-between;
}
.check-modal-head-check {
  width: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.head-logo {
  width: 50%;
}
.check-modal-head-close {
  width: 5%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.check-modal-head-close img {
  width: 50%;
  height: 50%;
}

.check-modal-body {
  width: 100%;
  height: 80%;
  display: flex;
  flex-direction: column;
  background-color: white;
}

.check-modal-body-head {
  display: flex;
  width: 100%;
  height: 10%;
  border-top: solid;
  border-bottom: solid;
  background-color: #dadada;
}
.check-model-body-top {
  display: flex;
  width: 100%;
}
.check-modal-body-img {
  width: 40%;
  max-height: 400px;
  margin: 4% 4% 0 4%;
}
.check-modal-body-input {
  margin: 10% 4% 4% 0;
  display: flex;
  flex-direction: column;
}
.check-modal-body-input > input {
  margin-bottom: 10%;
  border: 2px solid #a1d6e9;
  border-radius: 10px;
}
.check-modal-body-input-tag {
  font-size: 20px;
}
.check-model-body-bot {
  display: flex;
}
.check-model-body-bot-left {
  width: 40%;
  margin: 0px 4%;
}
.check-model-body-bot-right {
  display: flex;
  justify-content: space-between;
}
.check-model-body-bot-pass {
  width: 60%;
  display: flex;
  font-size: 15px;
  margin-top: auto;
}

.check-model-body-bot-pass > input {
  width: 50%;
  border: 2px solid #a1d6e9;
  border-radius: 10px;
  margin: auto 0 5% 5%;
}
.check-model-body-bot-right > button {
  width: 150px;
  height: 75px;
  margin-left: 100px;
  margin-right: 20px;
  padding: 0 5px;
  background-color: #a1d6e9;
  border-radius: 10px;
  color: white;
  font-size: 25px;
}
</style>
