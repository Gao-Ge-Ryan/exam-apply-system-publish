<template>
  <div class="nts-card">
    <div class="mask">
      <div class="download" @click="printNTS">
        点击下载准考证
      </div>

    </div>
    <!-- <div>{{index}}</div>
    <div class="block">名称：{{details.examModel.title}}</div>
    <div class="block">类型：{{details.examModel.examType.msg}}</div>
    <div class="block">缴费截止时间：{{details.examModel.startTime | parseTime}}</div>
    <div class="block" @click="printNTS">打印准考证</div> -->

    <div class="title">{{details.examModel.title}}准考证</div>
    <img :src="details.identificationPhoto" class="avatar" />
    <div class="table">
      <table>
        <tr>
          <td style="width:90px">姓名</td>
          <td style="width:200px">{{details.applyName}}</td>
          <td style="width:90px">准考证号</td>
          <td style="width:200px">{{details.examNumber}}</td>
        </tr>
        <tr>
          <td>考场</td>
          <td>{{details.examRoom}}</td>
          <td>身份证号</td>
          <td>{{details.idNumber}}</td>
        </tr>
        <tr>
          <td>开始时间</td>
          <td>{{details.examModel.startTime | parseTime}}</td>
          <td>结束时间</td>
          <td>{{details.examModel.endTime | parseTime}}</td>
        </tr>
      </table>
    </div>
    <div class="tip">
      考试须知：
    </div>
    <p class="word">1．考前三十分钟，考生需持符合报考规定的并与准考证显示信息一致的有效证件，进入规定的考场。</p>
    <p class="word">2．考生入场后，按号入座，将本人《准考证》放在课桌上，以便核验。 </p>
    <p class="word">3．考场内不得相互借用文具。严禁在考场内饮食。 </p>
    <p class="word">4．考生离开考场时必须交卷，不准携带试卷离开考场。离开考场后不准在考场附近逗留和交谈。 </p>
    <p class="word">5．考生领到试卷后，必须先在指定位置准确，清楚地填写姓名，准考证号，座位号等栏目。 </p>
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

  },
  methods: {
    printNTS () {
      // let link = document.createElement('a')
      let userId = JSON.parse(localStorage.getItem('userInfo')).sub
      // link.href = `http://localhost:2020/word/export-word?examId=${this.details.examId}&userId=${userId}`
      // // link.download = '123.doc'
      // link.click()
      let params = {
        examId: this.details.examId,
        userId: userId
      }
      this.$store.dispatch('printNTS', params).then(res => {
        let content = res.data
        // 组装a标签
        let elink = document.createElement("a");
        // 设置下载文件名
        elink.style.display = "none";
        let blob = new Blob([content]);
        elink.href = URL.createObjectURL(blob);
        elink.download = this.details.examModel.title + '准考证.doc'
        document.body.appendChild(elink);
        elink.click();
        document.body.removeChild(elink);

      })
    }
  },
}
</script>

<style lang="scss" scoped>
.nts-card {
  position: relative;
  padding: 20px;
  // height: 40px;
  // justify-content: space-between;
  width: 580px;
  // height: 682px;
  background-color: #fff;
  .mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    display: none;
    color: rgb(55, 102, 230);
    .download {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      // color: rgb(190, 22, 36);
      color: #fff;
      font-weight: 600;
      font-size: 20px;
      cursor: pointer;
    }
  }

  &:hover .mask {
    display: block;
  }
  .avatar {
    margin: 20px auto;
    width: 120px;
    height: 160px;
    background-color: bisque;
  }
  .table {
    table,
    td,
    th {
      border: 1px solid black;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      tr {
        .lebel {
          width: 40px;
        }
      }
    }
  }

  .word {
    text-align: left;
    line-height: 1.8rem;
  }
}
</style>
