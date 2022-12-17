<template>
  <div class="card">
    <div>{{index}}</div>
    <div class="block">名称：{{details.examModel.title}}</div>
    <div class="block">类型：{{details.examModel.examType.msg}}</div>
    <div class="block">缴费截止时间：{{details.examModel.applyEndTime | parseTime}}</div>
    <div v-if="details.status.enumCode == 'Apply_NoPay'" class="block">缴费状态：未缴费</div>
    <div v-if="details.status.enumCode == 'Apply_Pay'" class="block">缴费状态：已缴费</div>
    <div v-if="dealButton === 'noPay'" class="block" @click="toPay">缴费</div>
    <div v-if="dealButton === 'pay'" class="block">已缴费</div>
    <div v-if="dealButton === 'ban'" class="block">缴费已截止</div>
  </div>
</template>
<script>
import { parseTime } from '@/utils/time'
export default {
  props: {
    details: Object,
    index: Number
  },
  filters: {
    parseTime (val) {
      return parseTime(val, '{y}-{m}-{d} {h}:{i}') || '--'
    }
  },
  computed: {
    dealButton () {
      let status = ""
      if (this.details.examModel.status.enumCode === 'Start' && this.details.status.enumCode === 'Apply_NoPay') {
        status = 'noPay'
      }
      if (this.details.status.enumCode == 'Apply_Pay') {
        status = 'pay'
      }
      if (this.details.examModel.status.enumCode === 'Stop' || this.details.examModel.status.enumCode === 'Score_Inquiry') {
        status = 'ban'
      }
      return status
    }
  },
  methods: {
    toPay () {
      this.$emit('toPay', this.details.examId)
    }
  },
}
</script>
<style lang="scss">
.card {
  height: 40px;
  display: flex;
  justify-content: space-between;
}
</style>
