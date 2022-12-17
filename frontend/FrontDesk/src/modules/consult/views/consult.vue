<template>
  <div class="main">
    <Chat></Chat>
  </div>
</template>
<script>
import Chat from './chat.vue'
export default {
  components: { Chat },

}
</script>
<style lang="scss" scoped>
.main {
  width: 1200px;
  margin: 0 auto;
}
</style>
