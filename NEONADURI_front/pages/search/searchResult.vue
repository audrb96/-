<template>
  <div class="result-page">
    <div class="result-title mb-1 pt-1">
      <div class="title-text">'경상도'의 검색 결과입니다.</div>
    </div>
    <div id="main" class="search-container">
      <div class="search-result" @wheel="handleScroll">
        <div class="container">
          <div class="slider">
            <div class="slide" style="background-color: green"></div>
            <div class="slide" style="background-color: red"></div>
            <div class="slide" style="background-color: orange"></div>
            <div
              class="slide slide--active"
              style="background-color: blue"
            ></div>
            <div class="slide" style="background-color: violet"></div>
            <div class="slide" style="background-color: black"></div>
            <div class="slide" style="background-color: pink"></div>
            <div class="slide" style="background-color: teal"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      timer: 0,
    }
  },
  mounted() {
    this.renderSlides()
  },
  methods: {
    handleScroll(e) {
      if (this.timer) {
        clearTimeout(this.timer)
      }

      this.timer = setTimeout(() => {
        if (e.wheelDeltaY > 0) {
          console.log('333333')
          this.prevSlide()
        } else if (e.wheelDeltaY < 0) {
          console.log('444444')
          this.nextSlide()
        }
      }, 50)
    },
    nextSlide() {
      const activeSlide = this.$el.querySelector('.slide--active')
      const nextSlide = activeSlide.nextElementSibling
      if (nextSlide) {
        activeSlide.classList.remove('slide--active')
        nextSlide.classList.remove('next')
        nextSlide.classList.add('slide--active')
        this.renderSlides()
      }
    },
    prevSlide() {
      const activeSlide = this.$el.querySelector('.slide--active')
      const prevSlide = activeSlide.previousElementSibling
      if (prevSlide) {
        activeSlide.classList.remove('slide--active')
        prevSlide.classList.remove('prev')
        prevSlide.classList.add('slide--active')
        this.renderSlides()
      }
    },
    renderSlides() {
      const slides = this.$el.querySelectorAll('.slide')
      if (!slides) {
        return
      }
      let activeSlide = this.$el.querySelector('.slide--active')
      if (!activeSlide) {
        activeSlide = slides.item(0)
        activeSlide.classList.add('slide--active')
      }
      ;[].forEach.call(slides, function (slide) {
        slide.classList.remove('prev', 'next')
      })

      const prevSlide = activeSlide.previousElementSibling
      prevSlide && prevSlide.classList.add('prev')

      const nextSlide = activeSlide.nextElementSibling
      nextSlide && nextSlide.classList.add('next')
    },
  },
}
</script>

<style>
.container {
  display: flex;
  flex-flow: column;
  justify-content: center;
  width: 100%;
  height: 600px;
}

.controls {
  display: flex;
  justify-content: center;
}

.controls div {
  padding: 12px 14px;
  cursor: pointer;
  border: 1px solid #000000;
  color: #000000;
  font-size: 16px;
  font-weight: bold;
  margin: 0 5px;
  border-radius: 10px;
}

.controls div.disabled {
  opacity: 0;
  cursor: default;
}

.slider {
  position: relative;
  width: 100%;
  height: 100%;
}

.slide {
  position: absolute;
  top: 20%;
  /* left: 1%; */
  width: 100%;
  height: 60%;
  background-color: red;
  border: 1px solid #fff;
  border-radius: 15px;
  opacity: 0;
  transition: all 0.7s ease-out;
}

.slide--active {
  opacity: 1;
  z-index: 9;
}

.prev,
.next {
  opacity: 0.3;
}

.prev {
  transform: translate(0, -30%) scale(0.8);
}

.next {
  transform: translate(0, 30%) scale(0.8);
}

.search-container {
  width: 100%;
  display: flex;
  justify-content: center;
}
.search-result {
  background-color: white;
  border-radius: 15px;
  width: 80%;
  padding: 2%;
}
.title-text {
  font-size: 40px;
}

.result-page {
  width: 100%;
  height: 100vh;
}

.result-title {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
