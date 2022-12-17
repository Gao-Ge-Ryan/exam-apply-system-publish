<template>
  <div class="main">
    <div class="top">
      <div class="left">
        <el-select v-model="examId" filterable placeholder="请选择" @change="changeExam">
          <el-option v-for="item in examList" :key="item.id" :label="item.title" :value="item.id" />
        </el-select>
        <div v-if="examList.length > 0" ref="score" class="bar" style="width:900px; height:400px" />
        <div v-else style="width:900px; height:400px;" class="no">
          暂无数据
        </div>
        <!-- <div ref="score" class="bar" style="width:900px; height:400px" /> -->
      </div>

      <div ref="bar" class="bar" style="width:600px; height:400px" />
    </div>

    <div ref="type" class="bar" style="width:1000px; height:400px" />
    <div class="circle" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: 'Main',
  data () {
    return {
      status: [],
      type: [],
      examList: [],
      examId: '',
      scoreData: [],
      exam: ''
    }
  },
  computed: {

  },
  mounted () {
    this.getEchartsData()
    this.getDashExamList()
    this.getScoreChart()
  },
  methods: {
    /* 获取echarts数据 */
    getEchartsData () {
      this.$store.dispatch('getEchartsData').then(res => {
        console.log(res.type)
        this.status = Object.values(res.status)
        this.type = Object.values(res.type)
        this.getBar()
        this.getTypeChart()
      })
    },
    getDashExamList () {
      this.$store.dispatch('getDashExamList').then(res => {
        console.log(res)
        this.examList = res
        if (this.examList.length > 0) {
          this.examId = this.examList[0].examId
          this.exam = this.examList[0].title + '分数饼状图'
          this.getScoreData('first')
        }
      })
    },
    getScoreData (flag) {
      this.$store.dispatch('getScoreData', this.examId).then(res => {
        console.log(res)
        this.scoreData = res
        // if (flag === 'first') {
        this.getScoreChart()
        // }
      })
    },
    getBar () {
      // 基于准备好的dom，初始化echarts实例
      var bar = echarts.init(this.$refs.bar)
      // 绘制图表
      bar.setOption({
        title: {
          text: '考试状态柱状图'
        },
        tooltip: {},
        xAxis: {
          data: ['报名', '打印准考证', '进行中', '成绩查询']
        },
        yAxis: {},
        series: [
          {
            name: '人数',
            type: 'bar',
            data: this.status
          }
        ]
      })
    },
    getTypeChart () {
      const typeChart = echarts.init(this.$refs.type)

      var option

      option = {
        title: {
          text: '考试类别折线图'
        },
        xAxis: {
          type: 'category',
          data: ['普通话', '英语四级', '英语六级', '会计', '计算机等级', '教师资格证', '英语口语']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.type,
            type: 'line'
          }
        ]
      }

      option && typeChart.setOption(option)
    },
    getScoreChart () {
      const myChart = echarts.init(this.$refs.score)

      const option = {
        title: {
          text: this.exam,
          // subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '人数',
            type: 'pie',
            radius: '50%',
            data: this.scoreData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }

      option && myChart.setOption(option)
    },
    changeExam (el) {
      console.log(el)
      this.examList.forEach(item => {
        if (item.id === el) {
          this.exam = item.title + '分数饼状图'
          console.log(this.exam)
        }
      })
      this.examId = el
      this.getScoreData()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.main {
  padding-bottom: 100px;
  .top {
    display: flex;
    .no {
      display: flex;
      justify-content: center;
      align-items: center;
      font-weight: 700;
      font-size: 30px;
    }
  }
}
</style>
