<template>
  <div class="password-main">
    <div class="password-content">
      <div class="password-container" tabindex="1">
        <div class="search-container" tabindex="1">
          <input placeholder="password" type="text" />
          <a class="password-button">
            <v-icon>mdi-key</v-icon>
            <!-- <i class="fa fa-search"></i> -->
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.password-main {
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  font-family: 'Cabin';

  height: 100%;
}

.password-content {
  height: 100%;
}

.password-container {
  display: block;
  max-width: 900px;
  margin: auto;
  padding: 100px;
  height: 100%;
  outline: none;
}

.search-container {
  overflow: hidden;
  float: right;
  height: 4em;
  width: 4em;
  border-radius: 2em;
  box-shadow: 0 0 5px #6a5d4f;
  -moz-transition: all 0.35s;
  -webkit-transition: all 0.35s;
}
.search-container:hover,
.search-container:focus,
.search-container:focus-within {
  width: 25em;
  border-radius: 5px 2em 2em 5px;
  outline: none;
}
.search-container:hover input,
.search-container:focus input,
.search-container:focus-within input {
  display: inline-block;
  width: 19em;
  padding: 10px;
}

input {
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;
  float: left;
  width: 0em;
  height: 2em;
  margin: 1em;
  margin-right: -4.5em;
  background: #fff;
  color: #6a5d4f;
  font-size: 1em;
  font-weight: 600;
  padding: 0px;
  border: 0;
  border-radius: 5px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2) inset;
  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
  -moz-transition: all 0.25s;
  -webkit-transition: all 0.25s;
}
input:focus {
  outline: none;
  box-shadow: 0 -1px 1px rgba(255, 255, 255, 0.25),
    0 1px 5px rgba(0, 0, 0, 0.15);
}

.password-button {
  display: flex;
  align-items: center;
  justify-content: center;
  float: right;
  width: 1.75em;
  height: 1.75em;
  margin: 0.125em;
  background: #6a5d4f;
  text-align: center;
  font-size: 2em;
  color: #6a5d4f;
  border-radius: 50%;
  cursor: pointer;
  /* box-shadow: 0 -1px 1px rgba(255, 255, 255, 0.25),
    0 1px 1px rgba(0, 0, 0, 0.25); */
  /* text-shadow: 0 -2px 1px rgba(0, 0, 0, 0.3); */
}
.password-button:active {
  border: 0 !important;
  text-shadow: 0 0 0;
}
.password-button i {
  font-size: 85%;
}
</style>
