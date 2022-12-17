<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container" />
    <breadcrumb />
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <img :src="avatar" class="user-avatar">
        <i class="el-icon-caret-bottom" />
      </div>
      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            Home
          </el-dropdown-item>
        </router-link>
        <el-dropdown-item divided>
          <span style="display:block;" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { getToken } from '@/utils/auth'
export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data () {
    return {
      avatar: '',
      user: ''
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ])
  },
  mounted () {
    this.parseToken()
  },
  methods: {
    toggleSideBar () {
      this.$store.dispatch('ToggleSideBar')
    },
    logout () {
      this.$store.dispatch('logOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    /* 解析token */
    parseToken () {
      const token = getToken().split('.')
      console.log(token)
      const x = (token[1]).replace(/-/g, '+').replace(/_/g, '/')
      let out, i, c
      out = ''
      for (i = 0; i < x.length; i++) {
        c = x.charCodeAt(i)
        if ((c >= 0x0001) && (c <= 0x007F)) {
          out += x.charAt(i)
        } else if (c > 0x07FF) {
          out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F))
          out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F))
          out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F))
        } else {
          out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F))
          out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F))
        }
      }
      const userInfo = window.atob(out)
      localStorage.setItem('userInfo', userInfo)
      this.user = JSON.parse(userInfo)
      console.log(this.user)
      this.avatar = this.user.avatar
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      line-height: initial;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

