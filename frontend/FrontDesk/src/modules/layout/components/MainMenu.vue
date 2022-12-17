<template>
  <div class="main">
    <div class="content">
      <div class="top">
        <div class="logo" @click="changeTab('main')">
          <div class="icon">
            <img src="/static/images/logo.png" alt="">
          </div>
          <div class="name">
            考试报名系统
          </div>
        </div>
        <div class="user">
          <el-dropdown trigger="hover" @command="handleCommand">
            <span class="el-dropdown-link">
              {{user && user.sub}}<i class="el-icon-arrow-down el-icon--right" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-plus" command="center">
                个人中心
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-circle-plus" command="logout">
                退出
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>

      <div class="tabbar">
        <div class="left">
          <div v-for="(item) in tabbarList" :key='item.type'>
            <div class="tabbar-item" :class="activeTabbar === item.type ? 'active-tabbar-item' : ''" @click="changeTab(item.type)">
              {{item.name}}
            </div>
          </div>

          <!-- <div class="tabbar-item" @click="changeTab('register')">
            在线报名
          </div>
          <div class="tabbar-item" @click="changeTab('registrationQuery')">
            报名查询
          </div>
          <div class="tabbar-item" @click="changeTab('resultsInquiry')">
            成绩查询
          </div> -->
        </div>
        <div class="right">
          <div class="tabbar-item" @click="toConsult">
            咨询与备考
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'
import BASE64 from '@/utils/base'
export default {
  data () {
    return {
      activeTabbar: 'main',
      tabbarList: [{ name: '首页', type: 'main' }, { name: '在线报名', type: 'register' }, { name: '报名查询', type: 'registrationQuery' }, { name: '成绩查询', type: 'resultsInquiry' },
      { name: '打印准考证', type: 'print' }],
      user: null
    }
  },
  mounted () {
    this.activeTabbar = this.$route.query.type
    this.parseToken()
  },

  methods: {
    changeTab (type) {
      this.activeTabbar = type
      if (type === 'main') {
        this.$router.push({ path: '/', query: { type: 'main' } })
      }
      if (type === 'register') {
        this.$router.push({ path: '/register', query: { type: 'register' } })
      }
      if (type === 'registrationQuery') {
        this.$router.push({ path: '/registrationQuery', query: { type: 'registrationQuery' } })
      }
      if (type === 'resultsInquiry') {
        this.$router.push({ path: '/resultsInquiry', query: { type: 'resultsInquiry' } })
      }
      if (type === 'print') {
        this.$router.push({ path: '/print', query: { type: 'print' } })
      }
    },
    toConsult () {
      this.activeTabbar = 'consult'
      this.$router.push({ path: '/consult', query: { type: 'consult' } })
    },
    /* 退出 */
    handleCommand (commond) {
      if (commond === 'logout') {
        this.$store.dispatch('logout').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      }
      if (commond === 'center') {
        this.$router.push({ path: '/personalCenter' })
      }
    },
    /* 解析token */
    parseToken () {
      const token = getToken().split('.')
      console.log(token)
      let x = (token[1]).replace(/-/g, "+").replace(/_/g, "/")
      let out, i, len, c;
      out = "";
      len = x.length;
      for (i = 0; i < len; i++) {
        c = x.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
          out += x.charAt(i);
        } else if (c > 0x07FF) {
          out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
          out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
          out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
          out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
          out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
      }
      const userInfo = window.atob(out)
      localStorage.setItem("userInfo", userInfo);
      this.user = JSON.parse(userInfo)
      console.log(this.user)
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  background: url('/static/images/menu.png') no-repeat;
  background-size: 100% 100%;
  width: 100%;
  .content {
    position: relative;
    margin: 0 auto;
    width: 1200px;
    height: 200px;
    // background-color: rgb(32, 144, 228);
    .top {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-between;
      .logo {
        display: flex;
        padding-top: 30px;
        width: 400px;
        height: 100px;
        font-weight: 600;
        font-size: 40px;
        color: rgb(255, 255, 255);
        .icon {
          margin-top: 6px;
          margin-right: 16px;
          width: 75px;
          height: 40px;
          img {
            width: 100%;
            height: 100%;
          }
        }
      }
      .user {
        margin-top: 45px;
        .el-dropdown-link {
          color: #fff;
          font-weight: 500;
          font-size: 20px;
        }
      }
    }

    .tabbar {
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      width: 1200px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      background-color: rgba($color: #000000, $alpha: 0.2);
      border-radius: 8px 8px 0px 0px;
      .left {
        display: flex;
        flex-direction: row;
      }
      .tabbar-item {
        cursor: pointer;
        color: #eee;
        width: 160px;
        height: 60px;
        line-height: 60px;
      }
      .active-tabbar-item {
        background-color: #1e99ff;
        border-radius: 8px 8px 0px 0px;
      }
    }
  }
}
</style>
