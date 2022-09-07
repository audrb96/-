<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="search">
    <div class="main-content">
      <div class="recommand">
        <div class="recommand-content">
          <img
            src="@/static/logo/logo-black.png"
            alt="임시"
            style="width: 200px"
          />
          <div>
            <div>동궁과 월지</div>
            <div>어쩌고 저쩌고</div>
            <div>#데이트 #경주</div>
          </div>
          <div>
            <div class="top-title">추천장소</div>
          </div>
        </div>
      </div>
      <div>검색</div>
      <div>지역</div>
      <div>표</div>
    </div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
import NavbarComponent from '~/components/NavbarComponent.vue'

export default {
  components: { NavbarComponent },
}
</script>

<style scoped>
.search {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: space-between;
}
.main-content {
  width: 85vw;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.recommand {
  width: 70vw;
  background-color: #eaf2f9;
}
.recommand-content {
  display: flex;
  justify-content: space-between;
  background-color: #cbe7f0;
}
.top-title {
  background-color: #cbdcf0;
}
</style>
