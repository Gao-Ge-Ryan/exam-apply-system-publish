<template>
  <div class="main-chat">
    <div class="chat">
      <div class="chat-content" ref="chat">
        <div v-for="(item,index) in recordContent" :key="index">
          <!-- 我的 -->
          <div class="my-word" v-if="item.userAccount === userId">
            <div class="my-info">
              <p class="my-time">{{item.nickName}} {{item.currentTime | parseTime}}</p>
              <div class="my-info-content">{{item.message}}</div>
            </div>
            <img class="my-avator" src="../../../../public/images/man.png">
          </div>
          <!-- 对方 -->
          <div v-else class="word">
            <img class="avator" src="../../../../public/images/man.png">
            <div class="info">
              <p class="time">{{item.nickName}} {{item.currentTime | parseTime}}</p>
              <div class="info-content">{{item.message}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="tools">
      <img v-show="!showIcon" @mouseenter="iconEnter" class="icon" src="../../../../public/images/weixiao.svg" alt="">
      <img v-show="showIcon" @mouseout="iconOut" @click="openIcon" class="icon" src="../../../../public/images/weixiao-dark.svg" alt="">
      <Picker v-show="showEmojiArea" :i18n="I18N" @select="showEmoji" style="position:absolute;bottom:0;left:0"></Picker>
    </div>

    <div class="input-area">
      <el-input ref="input" type="textarea" :rows="2" placeholder="请输入内容" v-model="message" resize="none">
      </el-input>
      <el-button @click="send">发送</el-button>
    </div>

  </div>
</template>
<script>
import { parseTime } from '@/utils/time'
import { Picker } from 'emoji-mart-vue'
export default {
  components: { Picker },
  data () {
    return {
      showEmojiArea: false,
      ws: null,
      message: '',
      recordContent: [],
      userId: JSON.parse(localStorage.getItem('userInfo')).sub,
      showIcon: false,
      I18N: {
        search: '搜索',
        notfound: 'No Emoji Found',
        categories: {
          search: '搜索结果',
          recent: '经常使用',
          smileys: '表情与情感',
          people: '人物与身体',
          nature: '动物与自然',
          foods: '食物与饮料',
          activity: '活动',
          places: '旅行与地理',
          objects: '物品',
          symbols: '符号标志',
          flags: '旗帜',
          custom: 'Custom',
        }

      },
      word: ''
    }
  },
  mounted () {
    this.recordContent.push({ nickName: '官方提醒', currentTime: new Date(), message: '欢迎前来咨询交流' })
    this.buildChat()
    this.focus()
  },
  destroyed () {
    this.ws.close()
    console.log('close')
  },
  filters: {
    parseTime (val) {
      return parseTime(val, '{y}-{m}-{d} {h}:{i}') || '--'
    }
  },
  methods: {
    iconEnter () {
      this.showIcon = true
    },
    iconOut () {
      this.showIcon = false
    },
    openIcon () {
      this.showEmojiArea = true
    },

    showEmoji (el) {
      this.word = el.native
      this.message += el.native
      this.showEmojiArea = false
    },

    moveBottom () {
      let chat = this.$refs.chat
      chat.scrollTop = chat.scrollHeight - 400
      console.log(chat.scrollTop)
    },
    buildChat () {
      console.log(this.ws)
      if (!this.ws) {
        try {
          let userId = JSON.parse(localStorage.getItem('userInfo')).sub
          // this.ws = new WebSocket("ws://192.168.0.103:2020/websocket/" + userId);//连接服务器
          // TODO
          // this.ws = new WebSocket("ws://192.168.6.100:2020/websocket/" + userId);//连接服务器
          this.ws = new WebSocket("ws://82.157.42.25:2020/websocket/" + userId);//连接服务器
          // this.ws = new WebSocket("ws://192.168.0.106:2020/websocket/" + userId);//连接服务器
          this.ws.onopen = function (event) {
            console.log("已经与服务器建立了连接...");
          };
          let that = this
          this.ws.onmessage = function (event) {
            // console.log(event)
            // console.log("接收到服务器发送的数据..." + event.data);
            let message = event.data.replace(/\//g, "")
            that.recordContent.push(JSON.parse(message))
            // console.log(that.recordContent)
          };
          this.ws.onclose = function (event) {
            console.log("已经与服务器断开连接...");
          };
          this.ws.onerror = function (event) {
            console.log("WebSocket异常！");
          };
        } catch (ex) {
          alert(ex.message);
        }
      } else {
        this.ws.close();
        this.ws = null;
      }
    },
    send () {
      try {
        let regu = "^[ ]+$";
        let reg = new RegExp(regu);

        console.log(reg.test(this.message))
        if (!reg.test(this.message) && this.message != '') {
          this.ws.send(this.message);
        }
        this.message = ''
      } catch (ex) {
        alert(ex.message);
      }
      let input = this.$refs.input
      input.focus()
      setTimeout(() => {
        this.$nextTick(() => {
          this.moveBottom()
        })
      }, 100)
    },
    focus () {
      let input = this.$refs.input
      input.focus()
    }
  },
}
</script>
<style lang="scss" scoped>
.main-chat {
  margin: 0 auto;
  width: 800px;
  .chat {
    margin: 0 auto;
    margin-top: 20px;
    padding: 0px 2px 0px 0px;
    width: 800px;
    // height: 600px;
    // background-color: #f5f5f5;
    background-color: #eee;
    border-bottom: 1px solid #ccc;
    // border-radius: 8px;
    .chat-content {
      background-color: #eee;
      width: 100%;
      padding: 20px;
      overflow-y: scroll;
      max-height: 400px;
      min-height: 400px;

      .word {
        display: flex;
        margin-bottom: 20px;
        img {
          width: 40px;
          height: 40px;
          border-radius: 50%;
        }
        .avator {
          margin-top: 20px;
          margin-left: 6px;
        }
        .info {
          width: 90%;
          margin-left: 10px;
          text-align: left;
          .time {
            width: 100%;
            text-align: left;
            font-size: 12px;
            color: rgba(51, 51, 51, 0.8);
            margin: 0;
            height: 20px;
            line-height: 20px;
            margin-top: -5px;
            margin-left: -44px;
          }
          .info-content {
            float: left;
            max-width: 400px;
            padding: 10px;
            font-size: 14px;
            background: #fff;
            position: relative;
            margin-top: 8px;
            border-radius: 6px;
            text-align: left;
            line-height: 1.5rem;
            word-wrap: break-word;
            min-height: 38.5px;
          }
          //小三角形
          .info-content::before {
            position: absolute;
            left: -8px;
            top: 8px;
            content: '';
            border-right: 10px solid #fff;
            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
          }
        }
      }

      .my-word {
        display: flex;
        justify-content: flex-end;
        margin-bottom: 20px;
        img {
          width: 40px;
          height: 40px;
          border-radius: 50%;
        }
        .my-avator {
          margin-top: 20px;
          margin-left: 6px;
        }
        .my-info {
          width: 90%;
          margin-left: 10px;
          text-align: right;
          .my-time {
            font-size: 12px;
            color: rgba(51, 51, 51, 0.8);
            margin: 0;
            height: 20px;
            line-height: 20px;
            margin-top: -5px;
            margin-right: -44px;
          }
          .my-info-content {
            text-align: left;
            max-width: 60%;
            padding: 10px;
            font-size: 14px;
            float: right;
            margin-right: 10px;
            position: relative;
            margin-top: 8px;
            background: #a3c3f6;
            text-align: left;
            border-radius: 6px;
            word-wrap: break-word;
            min-height: 38.5px;
          }
          //小三角形
          .my-info-content::after {
            position: absolute;
            right: -8px;
            top: 8px;
            content: '';
            border-left: 10px solid #a3c3f6;
            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
          }
        }
      }
    }
  }
  .tools {
    position: relative;
    display: flex;
    align-items: center;
    padding-left: 8px;
    width: 100%;
    height: 30px;
    background-color: #ececec;
    text-align: left;
    .icon {
      cursor: pointer;
      width: 26px;
      height: 26px;
      color: aquamarine;
    }
  }
  /deep/ .el-textarea__inner {
    border: none;
    border-radius: 0;
    background-color: #ececec;
  }

  .input-area {
    display: flex;
    flex-direction: column;
    background-color: #ececec;
    align-items: flex-end;
    .el-button {
      background-color: #ddd;
      border: 0;
      width: 70px;
      height: 30px;
      padding: 0;
      color: #127ed8;
      margin: 10px;
      &:hover {
        background-color: #ccc;
      }
    }
  }
}
</style>
