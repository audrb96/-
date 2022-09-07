<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="splash-background">
    <div></div>
    <navbar-component></navbar-component>
  </div>
</template>

<script>
import NavbarComponent from '@/components/NavbarComponent.vue'
export default {
  components: { NavbarComponent },
}
</script>

<style scoped>
.splash-background {
  width: 100%;
  height: 100vh;
  background-color: #bdd9e5;
  display: flex;
  justify-content: space-between;
}
</style>
