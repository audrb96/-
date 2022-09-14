<template>
  <div class="search">
    <div class="main-content">
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
    <div>
      <div>검색</div>
      <div>지역</div>
      <div>표</div>
    </div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
export default {
  name: 'SearchPage',
}
</script>

<style scoped>
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
</style>
