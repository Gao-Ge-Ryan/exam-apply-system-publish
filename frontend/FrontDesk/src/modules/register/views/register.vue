<template>
  <div>
    <div class="main">
      <div class="category">
        <div class="top">
          考试类别
        </div>
        <div class="content">
          <div v-for="item in examTypeEnum" :key="item.enumCode" class="content-item" @click="changeExam(item.enumCode)" :class="typeEnum === item.enumCode ? 'active' : ''">
            <div class="name">
              {{item.msg}}
            </div>
          </div>
        </div>
      </div>
      <div class="right-content">
        <div class="content">
          <div class="top">
            {{examIntroduction.title}}介绍
          </div>
          <div class="line" />
          <div class="center">
            {{examIntroduction.description}}
          </div>
          <div class="center" style="margin-bottom:30px">
            {{examIntroduction.rule}}
          </div>
          <div class="bottom">
            <el-button type="primary" @click="toExamList">
              查看考试安排
            </el-button>
          </div>
        </div>
        <!-- <div class="examlist">
          考试列表
        </div> -->
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  mounted () {
    this.$store.dispatch('getExamTypeEnum', 'ExamTypeEnum'),
      this.changeExam(this.typeEnum)
  },
  components: {
  },
  data () {
    return {
      typeEnum: 'Mandarin'
    }
  },
  computed: {
    ...mapState({
      examTypeEnum: state => state.register.examTypeEnum,
      examIntroduction: state => state.register.examIntroduction
    })
  },
  methods: {
    getEexamIntroduction () {
      const params = {
        typeEnum: this.typeEnum
      }
      this.$store.dispatch('getExamIntroduction', params).then(res => {
        console.log(this.examIntroduction);
      })
    },
    /* 切换考试类型 */
    changeExam (typeEnum) {
      this.typeEnum = typeEnum
      this.getEexamIntroduction()
    },
    /* 跳转到考试列表 */
    toExamList () {
      this.$router.push({ path: '/register/examList', query: { examType: this.examIntroduction.examType.enumCode } })
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  display: flex;
  width: 1200px;
  margin: 0 auto;
  background-color: #fff;
  min-height: calc(100vh - 240px);
  .category {
    width: 300px;
    background-color: #f9f9f9;
    .top {
      height: 40px;
      font-size: 24px;
      font-weight: 600;
      color: #466a92;
      line-height: 40px;
    }
    .content {
      max-height: 1000px;
      .active {
        background-color: #96c2ee;
      }
      .content-item {
        cursor: pointer;
        height: 32px;
        line-height: 32px;
        &:hover {
          // background-color: #96c2ee;
          box-shadow: 0px 2px 20px rgba(30, 38, 49, 0.2);
        }
        // .name {
        // }
      }
    }
  }
  .right-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    .content {
      display: flex;
      flex-direction: column;
      align-items: center;
      .top {
        margin-top: 40px;
        font-weight: 600;
        font-size: 32px;
        color: #333;
      }
      .line {
        margin-top: 16px;
        width: 600px;
        border: 3px solid #91b8e4;
      }
      .center {
        padding: 20px;
        line-height: 2rem;
        word-wrap: break-word;
        word-break: normal;
      }
      .bottom {
        margin-bottom: 50px;
      }
    }
  }
}
</style>
