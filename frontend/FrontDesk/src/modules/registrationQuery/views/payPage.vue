<template>
  <div>
    即将跳转到支付页面
    <div v-html="payGateWay">{{payGateWay}}</div>

  </div>

</template>
<script>
export default {
  data () {
    return {
      payGateWay: ''
    }
  },
  mounted () {
    this.payGateWay = this.$route.query.htmlData
    this.$nextTick(() => {
      document.forms[0].submit();
    })
  },
}
</script>
<style lang="scss" scoped>
</style>
