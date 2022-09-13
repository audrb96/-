<template>
  <div class="findRoute">
    <div class="container">
      <div class="banner">
        <img src="/banner/banner.png" style="width: 50%" />
      </div>
      <div class="search">
        <div class="input-group mb-3">
          <input
            type="text"
            class="form-control input-form"
            aria-label="Default"
            aria-describedby="inputGroup-sizing-default"
            placeholder="출발지 입력"
          />
        </div>
        <div class="input-group mb-3">
          <input
            type="text"
            class="form-control input-form"
            aria-label="Default"
            aria-describedby="inputGroup-sizing-default"
            placeholder="도착지 입력"
          />
        </div>
      </div>
      <div class="my-button">
        <div class="types reInput">
          <div class="buttons">다시입력</div>
        </div>
        <div class="types roadFinding">
          <div class="buttons">길찾기 ></div>
        </div>
      </div>
      <div class="roadMap"></div>
    </div>
  </div>
</template>

<script scoped>
export default {}
</script>

<style>
.findRoute {
  background-color: #bdd9e5;
  width: 100%;
  height: 100vh;
}

.container {
  display: inline;
}

.banner {
  display: flex;
  align-items: center;
  justify-content: center;
}

.input-form {
  width: 18vw;
  height: 6vh;
  font-size: larger;
  margin-top: 3vh;
  margin-bottom: 3vh;
  margin-left: 4vw;
  margin-right: 4vw;
}

.search {
  display: flex;
  align-items: center;
  justify-content: center;
}

.my-button {
  display: flex;
  align-items: center;
  justify-content: center;
}

.types {
  width: 18vw;
  height: 6vh;
  margin-top: 1vh;
  margin-bottom: 3vh;
  margin-left: 4vw;
  margin-right: 4vw;
}

.reInput {
  display: flex;
  justify-content: flex-start;
}

.roadFinding {
  display: flex;
  justify-content: flex-end;
}

.buttons {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 5vw;
  border-radius: 20px;
  border: 1px solid white;
  float: left;
  box-shadow: 3px 3px 3px black;

  transition-duration: 0.3s;
}

.buttons:active {
  margin-left: 5px;
  margin-top: 5px;
  box-shadow: none;
}
</style>
