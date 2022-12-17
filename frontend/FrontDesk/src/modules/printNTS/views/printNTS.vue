<template>
  <div class="main">
    <div class="title"></div>
    <div v-if="printObj.list && printObj.list.length  > 0" class="content">
      <NTSCard v-for="(item,index) in printObj.list" :key="item.id" :details="item" :index="index+1" class="item"></NTSCard>
    </div>
    <div v-else>
      <el-empty class="enpty" description="暂无考试信息"></el-empty>
    </div>

  </div>
</template>
<script>
import { mapState } from 'vuex'
import NTSCard from './NTSCard.vue'
export default {
  filters: {
  },
  components: {
    NTSCard
  },
  data () {
    return {
      pageNum: 1,
      pageSize: 20
    }
  },
  computed: {
    ...mapState({
      printObj: state => state.printNTS.printObj
    })
  },
  mounted () {
    this.getPrintList()
  },
  methods: {
    getPrintList () {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        params: {}
      }
      console.log(params)
      this.$store.dispatch('getPrintList', params).then(res => {
        console.log(this.printObj)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  width: 1200px;
  margin: 0 auto;
  min-height: calc(100vh - 240px);

  .content {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    .item {
      margin-right: 40px;
      &:nth-child(2n) {
        margin-right: 0;
      }
    }
  }
}
</style>
