<template>
  <div class="navbar">
    <div class="navbar-btn">
      <v-icon large @click="toggle">mdi-menu</v-icon>
    </div>
    <div
      class="navbar-content"
      :class="{ 'go-bottom': isExpanded, 'go-top': !isExpanded }"
    >
      <div></div>
      <div class="navbar-content-icon">
        <v-icon large>mdi-magnify</v-icon>

        <div>여행지 찾기</div>
      </div>
      <div class="navbar-content-icon" @click="mvStatistics">
        <v-icon large>mdi-alpha-i-circle-outline</v-icon>
        <div>여행 정보</div>
      </div>
      <div class="navbar-content-icon" @click="mvFindRoute">
        <v-icon large> mdi-call-split </v-icon>
        <div>여행 경로찾기</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isExpanded: false,
    }
  },
  methods: {
    toggle() {
      this.isExpanded = !this.isExpanded
    },
    mvFindRoute() {
      this.$router.push('/findRoute')
    },
    mvStatistics() {
      this.$router.push('/statistics')
    },
  },
}
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  right: 0;
  display: flex;
  justify-content: center;
}
.navbar-btn {
  position: fixed;
  top: 0;
  right: 1;
  z-index: 100;
  margin: 25px 20px 0 0;
}

.navbar-content {
  height: 400px;
  margin: 10px 20px 0 0;
  background-color: #fffff0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  font-size: 12px;
  border-radius: 20px;
}
.go-bottom {
  max-height: 500px;
  transition: max-height 0.35s ease-in;
}

.go-top {
  max-height: 0;
  transition: max-height 0.35s ease-out;
  overflow: hidden;
}
.navbar-content-icon {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
