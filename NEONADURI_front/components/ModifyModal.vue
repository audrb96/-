<template>
  <div class="check-modal">
    <div class="check-modal-box">
      <div class="check-modal-head">
        <div></div>
        <div class="check-modal-head-check">
          <img class="head-logo" src="/logo/modify-review-logo.png" alt="" />
        </div>
        <div class="check-modal-head-close" @click="CloseCheck">
          <v-icon large>mdi-close-circle-outline</v-icon>
        </div>
      </div>
      <div class="check-modal-body">
        <div class="check-model-body-top">
          <img
            v-if="flag"
            :src="preview"
            class="check-modal-body-img"
            alt="없음"
          />
          <img
            v-else
            :src="
              'https://neonaduri.s3.ap-northeast-2.amazonaws.com/' +
              review.reviewImage
            "
            alt=""
            class="head-logo"
          />
          <div class="check-modal-body-input">
            <input
              v-model="reviewForm.reviewContent"
              class="check-modal-body-input-line"
              type="text"
              placeholder="한 줄 입력하세요"
            />
            <div class="check-modal-body-input-tag">
              <!-- <tagify-component></tagify-component> -->
              <Tags
                id="tag-input"
                ref="myRef"
                :settings="tagifyStuff.tagifySettings"
                :suggestions="tagifyStuff.suggestions"
                :value="tagifyStuff.value"
                :on-change="onTagsChange"
              />
            </div>
          </div>
        </div>
        <div class="check-model-body-bot">
          <div class="check-model-body-bot-left">
            <v-file-input
              v-if="flag"
              v-model="reviewForm.reviewImage"
              :placeholder="fileInfo?.name"
              @change="previewFile(reviewForm.reviewImage)"
            />
            <div v-else class="modify-box">
              <button class="modify-img" @click="modifyImg">사진 수정</button>
            </div>
          </div>
          <div class="check-model-body-bot-right">
            <button @click="modifyReview">수정 완료</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { ref, reactive } from 'vue'
// import Tags from "@yaireo/tagify/dist/tagify.vue"
import Tags from '@/components/TagifyInput'

export default {
  name: 'CheckModal',
  components: { Tags },
  props: {
    value: Boolean,
  },
  setup(props) {
    const myRef = ref(null)

    const tagifyStuff = reactive({
      value: '',

      tagifySettings: {
        whitelist: [],
        dropdown: {
          enabled: 0,
        },
        callbacks: {
          add(e) {
            // console.log("tag added:", e.detail);
          },
        },
      },

      suggestions: [],
    })

    function onTagsChange(e) {
      //   console.log('tags changed:', e.target.value)
    }

    return {
      onTagsChange,
      myRef,
      tagifyStuff,
    }
  },
  data() {
    return {
      pvalue: this.value,
      preview: '/banner/no-img.png',
      // preview: '/banner/no-image1.png',
      // preview: '/banner/no-image2.png',
      fileInfo: null,
      // 리뷰 post
      reviewForm: {
        spotId: null,
        reviewImage: null,
        reviewContent: null,
        reviewPassword: null,
        tags: '',
      },
      flag: false,
    }
  },

  computed: {
    ...mapState('spot', ['spot']),
    ...mapState('review', ['review']),
  },
  // watch: {
  //   reviewForm(newReview) {
  //     console.log(111111111111)
  //     console.log(newReview)
  //     console.log(11111111111)
  //   },
  // },

  created() {
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.callReview(this.review.reviewId)
      // this.reviewForm.reviewImage = this.review.reviewImage
      this.reviewForm.reviewContent = this.review.reviewContent
      const arr = []
      this.review.tagContents.forEach((element) => {
        arr.push(element)
      })
      this.tagifyStuff.value = arr
    })
  },
  methods: {
    ...mapActions('review', ['changeReview', 'callReviews', 'callReview']),
    CloseCheck() {
      this.$emit('updateStatus', !this.pvalue)
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
    async modifyReview() {
      const reviewData = new FormData()
      this.reviewForm.tags = ''
      const arr = this.$el
        .querySelector(`#tag-input`)
        .value.split('"},{"value":"')
      arr[0] = arr[0].substr(11, arr[0].length - 11)
      arr[arr.length - 1] = arr[arr.length - 1].substr(
        0,
        arr[arr.length - 1].length - 3
      )
      arr.forEach((element) => {
        const word = element.replace('#', '')
        if (word !== '') {
          this.reviewForm.tags += word
          this.reviewForm.tags += ', '
        }
      })
      this.reviewForm.tags = this.reviewForm.tags.substr(
        0,
        this.reviewForm.tags.length - 2
      )
      reviewData.append('reviewId', this.review.reviewId)
      reviewData.append('reviewImage', this.reviewForm.reviewImage)
      reviewData.append('reviewContent', this.reviewForm.reviewContent)
      reviewData.append('tags', this.reviewForm.tags)
      await this.changeReview(reviewData)
      await this.callReviews(this.spot.spotId)
      this.$emit('updateStatus', !this.pvalue)
    },
    modifyImg() {
      this.flag = true
    },
  },
}
</script>

<style scoped>
input {
  padding-left: 3%;
}
.check-modal {
  /* display: none; */
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
  border-radius: 15px;
  border: 2px solid black;
}

.check-modal-head {
  width: 100%;
  height: 15%;
  background-color: #cbdcf0;
  display: flex;
  border-radius: 15px;
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
.modify-box {
  display: flex;
  justify-content: flex-end;
}
.modify-img {
  margin-top: 20px;
  font-size: 15px;
  background-color: #a1d6e9;
  padding: 10px;
  border-radius: 5px;
  color: white;
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
  margin-left: 25%;
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
.password-input[type='password'] {
  font-family: '맑은고딕', '돋움';
}
.password-input[type='password']::placeholder {
  font-family: 'Cafe24Ssurround';
}
.check-model-body-bot-right > button {
  width: 150px;
  height: 75px;
  padding: 0 5px;
  background-color: #a1d6e9;
  border-radius: 10px;
  color: white;
  font-size: 25px;
}
</style>
