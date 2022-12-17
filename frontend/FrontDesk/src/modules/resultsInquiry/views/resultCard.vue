<template>
  <div class="card">
    <div class="title">成绩单</div>
    <div class="content">
      <div class="left">
        <img class="avatar" :src="details.identificationPhoto" />
        <div class="left-info">
          <div class="item">
            <div class="label">姓名：</div>
            <div class="value">{{details.applyName}}</div>
          </div>
          <div class="item">
            <div class="label">准考证号：</div>
            <div class="value">{{details.examNumber}}</div>
          </div>
          <div class="item">
            <div class="label">身份证号：</div>
            <div class="value">{{details.idNumber}}</div>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="item">
          <div class="label">分数：</div>
          <div class="value">{{details.score != '' ? details.score  : '暂无成绩'}}</div>
        </div>
        <div class="item">
          <div class="label">考试类型：</div>
          <div class="value">{{details.examModel.examType.msg}}</div>
        </div>
        <div class="item">
          <div class="label">证书编号：</div>
          <div class="value">{{details.id}}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    details: Object
  }
}
</script>
<style lang="scss" scoped>
.card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: start;
  width: 900px;
  height: 255px;
  background-color: #fff;
  // padding: 20px 30px 0px 30px;
  margin-bottom: 20px;
  .title {
    // width: 900px;
    width: 100%;
    height: 40px;
    margin-bottom: 20px;
    background-color: #e5e5e5;
  }
  .content {
    display: flex;
    .left {
      display: flex;
      .avatar {
        width: 120px;
        height: 160px;
        background-color: #fff;
      }
      .left-info {
        width: 360px;
        padding: 20px 20px 0px 20px;
        .item {
          display: flex;
          margin-bottom: 30px;
        }
      }
    }
    .right {
      padding: 20px 40px 0px 20px;
      .item {
        display: flex;
        margin-bottom: 30px;
      }
    }
  }
}
</style>
