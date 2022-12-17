<template>
  <div v-loading="loading" class="main">
    <el-empty v-if="resultList.length === 0" class="enpty" description="暂无考试信息"></el-empty>
    <div v-if="resultList.length > 0 && show" class="area">
      <div v-for="item in resultList" :key="item.id" class="card-item">
        <ResultCard :details="item"></ResultCard>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import ResultCard from './resultCard'
export default {
  filters: {
  },
  components: {
    ResultCard
  },
  data () {
    return {
      show: false,
      loading: false
    }
  },
  computed: {
    ...mapState({
      resultList: state => state.resultsInquiry.resultList
    })
  },
  mounted () {
    this.getResultList()
  },
  methods: {
    getResultList () {
      this.$store.dispatch('getResultList').then(res => {
        this.show = true
        console.log(this.resultList)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  margin: 0 auto;
  width: 1200px;
  min-height: calc(100vh - 240px);
  .area {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
