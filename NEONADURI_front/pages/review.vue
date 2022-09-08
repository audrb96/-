<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="review">
    <div></div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
import NavbarComponent from '~/components/NavbarComponent.vue'

export default {
  component: { NavbarComponent },
}
</script>

<style scoped>
.review {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: space-between;
}
</style>
