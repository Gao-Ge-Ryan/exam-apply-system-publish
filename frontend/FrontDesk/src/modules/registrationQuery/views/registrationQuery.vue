<template>
  <div class="main">
    <div v-if="queryList.list.length >0" class="content">
      <!-- <RegisterCard v-for="(item,index) in queryList.list" :key="item.id" :details="item" :index="index+1" @toPay="toPay"></RegisterCard> -->
      <el-table :data="queryList.list" style="width: 100%">
        <el-table-column prop="examModel.title" label="名称" width="180">
        </el-table-column>
        <el-table-column prop="examModel.examType.msg" label="类型" width="180">
        </el-table-column>
        <el-table-column prop="examModel.applyEndTime" label="缴费截止时间">
          <template slot-scope="{row}">
            {{row.examModel.applyEndTime | parseTime}}
          </template>
        </el-table-column>
        <el-table-column prop="status.enumCode" label="缴费状态">
          <template slot-scope="{row}">
            <span v-if="row.status.enumCode == 'Apply_NoPay'">未缴费</span>
            <span v-if="row.status.enumCode == 'Apply_Pay'">已缴费</span>
          </template>
        </el-table-column>
        <el-table-column prop="examModel.status.enumCode" label="操作">
          <template slot-scope="{row}">
            <el-button type="primary" v-if="dealButton(row) === 'noPay'" @click="toPay(row.examId)">缴费</el-button>
            <div v-if="dealButton(row) === 'pay'">已缴费</div>
            <div v-if="dealButton(row) === 'ban'">缴费已截止</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-empty v-else class="enpty" description="暂无报名信息"></el-empty>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import { parseTime } from '@/utils/time'
// import RegisterCard from './registerCard.vue'

export default {
  // components: { RegisterCard },
  mounted () {
    this.getQueryList()
  },
  data () {
    return {
      pageNum: 1,
      pageSize: 10
    }
  },
  filters: {
    parseTime (val) {
      return parseTime(val, '{y}-{m}-{d} {h}:{i}') || '--'
    }
  },
  computed: {
    ...mapState({
      queryList: state => state.registrationQuery.queryList,
      payDetails: state => state.registrationQuery.payDetails
    }),
    dealButton () {
      return function (row) {
        let status = ""
        if (row.examModel.status.enumCode === 'Start' && row.status.enumCode === 'Apply_NoPay') {
          status = 'noPay'
        }
        if (row.status.enumCode == 'Apply_Pay') {
          status = 'pay'
        }
        if (row.examModel.status.enumCode === 'Stop' || row.examModel.status.enumCode === 'Score_Inquiry') {
          status = 'ban'
        }
        return status
      }

    }
  },
  methods: {

    getQueryList () {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        params: {}
      }
      this.$store.dispatch('getQueryList', params).then(res => {
        console.log(this.queryList);
      })
    },
    toPay (examId) {
      console.log(examId)
      this.$store.dispatch('getPayDetails', examId).then(res => {
        console.log(this.payDetails)

        let routerData = this.$router.resolve({ path: '/registrationQuery/payPage', query: { htmlData: this.payDetails } })
        window.open(routerData.href, '_ blank')
        // this.$router.push({ path: '/registrationQuery/payPage', query: { htmlData: this.payDetails } })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  width: 1200px;
  margin: 0 auto;
  // height: 3000px;
  // min-height: 600px;
  min-height: calc(100vh - 240px);
  .content {
    padding: 20px;
  }
}
</style>
