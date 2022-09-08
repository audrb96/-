<template>
  <div class="search">
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
              <h2>동궁과 월지</h2>
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
          <h1>검색 :</h1>
          <input type="text" class="search-input" />
        </div>
        <div class="search-bar">
          <h1>지역 :</h1>
          <b-form-select
            v-model="sido"
            :options="sidoOptions"
            class="selected"
          ></b-form-select>
          <b-form-select
            v-model="sigungu"
            :options="sigunguOptions"
            class="selected"
          ></b-form-select>
          <b-form-select
            v-model="myeon"
            :options="myeonOptions"
            class="selected"
          ></b-form-select>
        </div>
        <div>표</div>
      </div>
    </div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
export default {
  name: 'SearchPage',
  data() {
    return {
      sido: 'all',
      sigungu: 'all',
      myeon: 'all',
      sidoOptions: [{ value: 'all', text: '전체' }],
      sigunguOptions: [{ value: 'all', text: '전체' }],
      myeonOptions: [{ value: 'all', text: '전체' }],
    }
  },
}
</script>

<style scoped>
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
  width: 80%;
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
.search {
  height: 100vh;
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
