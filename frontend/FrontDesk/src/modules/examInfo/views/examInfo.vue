<template>
  <div>
    <div class="main">
      <div class="top">{{bulletinTitle}}</div>
      <div v-if="examRegisterObj.list.length > 0" class="center">
        <div class="affiche" v-for="item in examRegisterObj.list" :key="item.id">
          <div class="title">{{item.title}}</div>
          <div class="content">{{item.content}}</div>
          <div class="time">{{item.examModel.applyStartTime | parseTime}} -- {{item.examModel.applyEndTime | parseTime}}</div>
        </div>
      </div>
      <el-empty v-else class="enpty" :description="'暂无'+ this.bulletinTitle"></el-empty>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import { parseTime } from '@/utils/time'
export default {
  data () {
    return {
      listQuery: {
        pageNum: 1,
        pageSize: 8,
        status: ''
      },
      bulletinTitle: '报名时间公告'
    }
  },
  filters: {
    parseTime (val) {
      return parseTime(val, '{y}-{m}-{d} {h}:{i}') || '--'
    }
  },
  mounted () {
    this.listQuery.type = this.$route.query.bulletinType
    if (this.$route.query.bulletinType === 'register') {
      this.bulletinTitle = '报名时间公告'
    }
    if (this.$route.query.bulletinType === 'admissionTicket') {
      this.bulletinTitle = '打印准考证公告'
    }
    if (this.$route.query.bulletinType === 'grade') {
      this.bulletinTitle = '成绩查询公告'
    }
    this.getBulletinDetails()
  },

  computed: {
    ...mapState({
      examRegisterObj: state => state.examInfo.examRegisterObj,
      examAdmissionTicketObj: state => state.examInfo.examAdmissionTicketObj,
      examGradeObj: state => state.examInfo.examGradeObj
    })
  },

  methods: {
    getBulletinDetails () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize,
        params: {
          type: this.listQuery.type
        }
      }
      if (this.listQuery.type === 'register') {
        params.params.type = 'Registration_Time_Announcement'
        this.$store.dispatch('getExamRegisterObj', params).then(res => {
          console.log(this.examRegisterObj)
        })
      }
      if (this.listQuery.type === 'admissionTicket') {
        params.params.type = 'Notice_of_Examination_Time'
        this.$store.dispatch('getExamAdmissionTicketrObj', params)
      }
      if (this.listQuery.type === 'grade') {
        params.params.type = 'Examination_Announcement'
        this.$store.dispatch('getExamGradeObj', params)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  margin: 0 auto;
  width: 1200px;
  padding: 20px;
  min-height: calc(100vh - 248px);
  .top {
    font-weight: 600;
    font-size: 38px;
    padding-bottom: 6px;
    border-bottom: 6px solid rgb(73, 144, 238);
  }
  .center {
    text-align: left;
    .affiche {
      margin: 10px 0px;
      padding: 15px;
      border-radius: 4px;
      background-color: #fff;
      &:hover {
        box-shadow: 3px 3px 8px #ccc;
      }
      .title {
        font-weight: 700;
        font-size: 24px;
        margin-bottom: 6px;
        color: rgb(10, 11, 12);
      }
      .content {
        font-size: 18px;
        line-height: 2rem;
        color: rgb(87, 84, 84);
      }
      .time {
        display: flex;
        flex-direction: row-reverse;
      }
    }
  }
}
</style>
