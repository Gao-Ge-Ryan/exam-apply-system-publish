<template>
  <div class="bottom-wa">
    
    <div class="bottom-t">
      <div class="zi">
        Author © 2022 Ryan Gao Ge
      </div>
      <div class="jinggon">
        <span class="zi">本项目开源免费，若有侵权信息请联系作者删除！</span>
      </div>
      <div class="jinggon" >
        <img src="" class="img">
        <span class="zi">作者QQ 3300755918</span>
      </div>
      <div class="jinggon">
        <img src="/static/images/jingying.png" class="img">
        <span class="zi">经营资质</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  filters: {
  },
  components: {
  },
  data () {
    return {
    }
  },
  computed: {
  },
  mounted () {
  },
  methods: {
    toGongan () {
      window.open()
    },
    toip () {
      window.open()
    },
    /** 切换 */
    change (route) {
      this.$router.push({ name: route.children[0].name })
    },
    businessQualification () {
      this.$router.push({ name: 'businessQualification' })
    }
  }
}
</script>
<style lang="scss" scoped>
.bottom-wa {
  width: 100%;
  .wid {
    width: 100%;
    height: 250px;
    background: #1e2021;
    display: flex;
    align-items: center;
    justify-content: center;
    .img {
      width: 200px;
    }
    .font {
      width: 800px;
    }
    .qrcode {
      width: 200px;
      .inmg {
        width: 160px;
        border-radius: 24px;
      }
      .zi {
        font-size: 12px;
        line-height: 17px;
        color: #ffffff;
        opacity: 0.7;
        margin-top: 10px;
      }
    }
  }
  .font {
    padding-left: 200px;
    .kuai {
      font-size: 14px;
      color: #ffffff;
      opacity: 0.7;
      display: flex;
      align-items: center;
      margin-bottom: 25px;
      .dian {
        cursor: pointer;
      }
      .zoi {
        font-weight: 600;
        font-size: 20px;
        color: #ffffff;
      }
    }
    .zizi {
      font-size: 18px;
      color: #ffffff;
    }
    .kuai-phone {
      color: #ffffff;
      display: flex;
      align-items: center;
      margin-bottom: 25px;
      .zoi {
        font-weight: 600;
        font-size: 20px;
        color: #ffffff;
      }
      .zi {
        font-size: 14px;
        opacity: 0.7;
        color: #ffffff;
      }
    }
  }
  .bottom-t {
    width: 100%;
    height: 48px;
    background: #8eb6df;
    display: flex;
    align-items: center;
    justify-content: center;
    .zi {
      color: #ffffff;
      opacity: 0.7;
    }
    .jinggon {
      margin-left: 20px;
      display: flex;
      align-items: center;
      cursor: pointer;
      .zi {
        margin-left: 5px;
        color: #ffffff;
        opacity: 0.7;
      }
    }
  }
}
</style>
