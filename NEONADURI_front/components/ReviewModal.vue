<template>
  <div class="check-modal">
    <div class="check-modal-box">
      <div class="check-modal-head">
        <div class="check-modal-head-check">리뷰 작성</div>
        <div class="check-modal-head-close" @click="CloseCheck">
          <v-icon large>mdi-close-circle-outline</v-icon>
        </div>
      </div>
      <div class="check-modal-body">
        <div class="check-model-body-top">
          <img
            class="check-modal-body-img"
            src="/review/deoksugung.jpg"
            alt=""
          />
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
            <v-icon>mdi-plus-box-multiple</v-icon>
            <!-- <v-icon>mdi-delete-forever</v-icon> -->
          </div>
          <div class="check-model-body-bot-right">
            <div class="check-model-body-bot-pass">
              <div>비밀번호 설정 :</div>
              <input type="password" placeholder="비밀번호" />
            </div>
            <button>입력 완료</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CheckModal',
  computed: {},
  methods: {
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
  },
}
</script>

<style scoped>
.check-modal {
  /* Hidden by default */
  display: none;
  /* Stay in place */
  position: fixed;
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100vw; /* Full width */
  height: 80vh; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  /* Fallback color */
  font-size: 30px;
}

/* Modal Content/Box */
.check-modal-box {
  display: flex;
  flex-direction: column;
  background-color: white;
  /* color: white; */
  /* 15% from the top and centered */
  /* margin: 15% auto;
  padding: 15px; */
  /* width: 35%; Could be more or less, depending on screen size */
  /* height: 30%; */
  width: 70vw;
  height: 60vh;
  margin: 10vh auto;
}

.check-modal-head {
  width: 100%;
  height: 10%;
  display: flex;
}
.check-modal-head-check {
  width: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.check-modal-head-close {
  width: 10%;
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
  width: 400px;
  margin: 40px 40px 0 40px;
}
.check-modal-body-input {
  margin: 100px 40px 40px 0;
  display: flex;
  flex-direction: column;
}
.check-modal-body-input > input {
  margin-bottom: 30px;
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
  width: 400px;
  margin: 0px 40px;
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
  margin: auto 0 0 10px;
}
.check-model-body-bot-right > button {
  width: 150px;
  height: 75px;
  margin-right: 20px;
  background-color: #a1d6e9;
  border-radius: 10px;
  color: white;
}
</style>
