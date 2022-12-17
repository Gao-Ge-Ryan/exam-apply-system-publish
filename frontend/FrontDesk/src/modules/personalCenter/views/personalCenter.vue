<template>
  <div class="main">
    <div class="left">
      <div class="area">
        <div class="label word">头像：</div>
        <!--TODO 文件上传地址 -->
        <el-upload class="avatar-uploader" action="http://82.157.42.25:5050/file/upload" :show-file-list="false" :on-success="handleAvatarSuccess">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <div class="area word">
        <div class="label word">用户名：</div>
        <div>{{info.userName}}</div>
      </div>
      <div class="area">
        <div class="label word">昵称：</div>
        <el-input v-model="info.nickName"></el-input>
      </div>
      <div class="area word">
        <div class="label word">手机号：</div>
        <el-input v-model="info.phone"></el-input>
      </div>
      <div class="area word">
        <div class="label word">身份证号：</div>
        <el-input v-model="info.identityNum"></el-input>
      </div>
      <div class="area word">
        <div class="label word">地址：</div>
        <el-input v-model="info.address"></el-input>
      </div>
      <el-button type="primary" @click="updateInfo">确认修改</el-button>
    </div>
    <div class="right">
      <div class="inputBox">
        <el-input type="text" v-model="password" placeholder="新密码" style="width:300px;margin-bottom:30px" />
        <el-input type="text" v-model="authCode" placeholder="验证码" style="width:300px;margin-bottom:30px" />
      </div>
      <div v-if=" isCheck" class="check" @click="getAuthCode">点击获取验证码
      </div>
      <div v-else class="check-false">重新获取({{time}})</div>
      <el-button type="primary" @click="updatePassword">修改密码</el-button>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  mounted () {
    this.getInfo()
  },
  data () {
    return {
      info: {},
      password: '',
      authCode: '',
      isCheck: true, //显示获取验证码
      time: 60,
      imageUrl: ''
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.personalCenter.userInfo
    })
  },
  methods: {
    handleAvatarSuccess (el) {
      console.log(el)
      this.imageUrl = el.data.accessAddress
      this.$message.success('头像上传成功，点击修改保存')
    },
    getInfo () {
      this.$store.dispatch('getInfo').then(res => {
        console.log(this.userInfo)
        this.info = this.userInfo
        this.imageUrl = this.userInfo.avatar
      })
    },
    updateInfo () {
      let params = {
        nickName: this.info.nickName,
        phone: this.info.phone,
        identityNum: this.info.identityNum,
        address: this.info.address,
        avatar: this.imageUrl
      }
      this.$store.dispatch('updateUserInfo', params).then(res => {
        this.$message.success('修改成功')
        this.getInfo()
      })
    },

    /* 获取验证码 */
    getAuthCode () {
      /* 获取验证码 */
      this.isCheck = false
      let timer = setInterval(() => {
        if (this.time > 0) {
          this.time--
        }
        if (this.time === 0) {
          this.isCheck = true
          this.time = 60
          clearInterval(timer);
        }
      }, 1000)
      const params = {
        receiver: JSON.parse(localStorage.getItem('userInfo')).sub
      }
      this.$store.dispatch('getAuthCode', params)
    },
    updatePassword () {
      this.$store.dispatch('updatePassword', { password: this.password, inputCode: this.authCode }).then(res => {
        this.$message.success('密码修改成功')
      }).catch(err => {
        console.log(err)
        this.$message.error(err)
      })
    }
  },
}
</script>
<style lang="scss" scoped>
.word {
  font-weight: 600;
  font-size: 18px;
}
.main {
  margin: 0 auto;
  width: 1200px;
  min-height: calc(100vh - 240px);
  background-color: #fff;
  padding: 60px 30px 30px 100px;
  text-align: left;
  display: flex;
  .left {
    flex: 1;
    border-right: 1px solid #ccc;
    .area {
      display: flex;
      margin-bottom: 40px;
      .avatar-uploader {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
      }
      .avatar-uploader:hover {
        border-color: #409eff;
      }
      .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
      }
      .avatar {
        width: 178px;
        height: 178px;
        display: block;
      }
      .label {
        margin-right: 20px;
      }
      .avator {
        width: 160px;
        height: 160px;
      }
      .el-input {
        width: 300px;
      }
    }
    .username {
      margin-top: 20px;
    }
  }
  .right {
    flex: 1;
    padding-left: 50px;
    .check-false {
      margin-bottom: 30px;
    }
    .check {
      margin-bottom: 30px;
    }
  }
}
</style>
