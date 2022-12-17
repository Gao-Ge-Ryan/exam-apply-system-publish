<template>
  <div>
    <div>
      <div class="main">
        <div class="contenter">
          <div class="menu">
            <main-menu />
          </div>
          <app-main />
        </div>
        <div class="bottom">
          <bottom />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import AppMain from './components/AppMain.vue'
import MainMenu from './components/MainMenu.vue'
import Bottom from './components/Bottom.vue'
export default {
  components: { AppMain, MainMenu, Bottom }
}
</script>
<style lang="scss" scoped>
.main {
  .contener {
  }
  .bottom {
    position: fixed;
    bottom: 0;
    width: 100%;
  }
}
</style>
