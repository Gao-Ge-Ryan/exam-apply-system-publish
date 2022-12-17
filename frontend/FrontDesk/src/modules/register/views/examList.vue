<template>
  <div class="examList">
    <el-table v-if="examList.length>0" :data="examList" style="width: 1200px; margin-top:40px">
      <el-table-column prop="title" label="考试名称" width="180">
      </el-table-column>
      <el-table-column prop="applyStartTime" label="报名开始时间">
        <template slot-scope="{row}">
          {{row.applyStartTime | parseTime}}
        </template>
      </el-table-column>
      <el-table-column prop="applyEndTime" label="报名结束时间">
        <template slot-scope="{row}">
          {{row.applyEndTime | parseTime}}
        </template>
      </el-table-column>
      <el-table-column prop="status.msg" label="状态">
      </el-table-column>
      <el-table-column prop="startTime" label="考试开始时间">
        <template slot-scope="{row}">
          {{row.applyEndTime | parseTime}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="140">
        <template slot-scope="{row}">
          <el-button v-if="row.examUserStatus === 'NoRegistered'" type="primary" size="mini" @click="apply(row)">
            报名
          </el-button>
          <el-button v-if="row.examUserStatus === 'Registered'" type="primary" disabled size="mini">
            已报名
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-empty v-else class="enpty" description="暂无考试"></el-empty>

    <!-- 考试信息 -->
    <el-dialog :visible.sync="examInfoDialog" title="考试信息" :center="true" width="800px">
      <div class="info">
        <div class="info-item">
          <div class="label">考试名称：</div>
          <div class="value">{{examInfo.title}}</div>
        </div>
        <div class="info-item">
          <div class="label">考试时间：</div>
          <div class="value">{{examInfo.startTime}} - {{examInfo.endTime}}</div>
        </div>
        <div class="info-item">
          <div class="label">考试类型：</div>
          <div class="value">{{examInfo.examType&&examInfo.examType.msg}}</div>
        </div>
        <div class="info-item">
          <div class="label">考试价格：</div>
          <div class="value">{{examInfo.price}}￥</div>
        </div>
        <div class="info-item">
          <div class="label">考试介绍：</div>
          <div class="value">{{examInfo.description}}</div>
        </div>
        <div class="info-item">
          <div class="label">注意事项：</div>
          <div class="value">{{examInfo.announcements}}</div>
        </div>
      </div>
      <div class="btn">
        <el-button type="primary" @click="toApply">点击报名</el-button>
      </div>
    </el-dialog>

    <!-- 个人信息弹窗 -->
    <el-dialog width="600px" :visible.sync="dialogFormVisible" title="填写个人信息">
      <el-form ref="dataForm" :model="registerInfo" label-position="left" label-width="130px" style="width: 400px; margin-left:50px;">
        <el-form-item label="姓名" prop="applyName">
          <el-input v-model="registerInfo.applyName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="registerInfo.idNumber" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="证件照" prop="identificationPhoto">
          <!-- TODO文件上传 -->
          <el-upload class="el-upload" action="http://82.157.42.25:5050/file/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="register">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import { parseTime } from '@/utils/time'
export default {
  data () {
    return {
      examInfo: {},
      dialogFormVisible: false,
      temp: {
        applyName: '',
        idNumber: '',
        identificationPhoto: '',

      },
      imageUrl: '',
      examInfoDialog: false,
      registerInfo: {
        applyName: '',
        examId: '',
        idNumber: '',
        identificationPhoto: ''
      }
    }
  },
  mounted () {
    this.getExamList()
  },
  filters: {
    parseTime (val) {
      return parseTime(val, '{y}-{m}-{d} {h}:{m}') || '--'
    }
  },
  computed: {
    ...mapState({
      examList: state => state.register.examList
    })
  },
  methods: {
    // 获取考试列表
    getExamList () {
      const params = {
        examType: this.$route.query.examType
      }
      this.$store.dispatch('getExamList', params).then(res => {
        this.total = res.total
      }).catch(err => {
        console.log(err)
      })
    },
    apply (row) {
      this.examInfo = row
      this.examInfoDialog = true
    },
    handleAvatarSuccess (res) {
      this.imageUrl = res.data.accessAddress
      this.registerInfo.identificationPhoto = res.data.accessAddress
    },
    beforeAvatarUpload () { },
    /* 点击报名 */
    toApply () {
      this.examInfoDialog = false
      this.dialogFormVisible = true
    },
    /* 报名 */
    register () {
      this.registerInfo.examId = this.examInfo.id
      this.$store.dispatch('register', this.registerInfo).then(res => {
        this.$message.success('报名成功')
        this.dialogFormVisible = false
        this.getExamList()
      }).catch(err => {
        this.$message.success(err)
      })
    }
  },
}
</script>
<style lang="scss" scoped>
.examList {
  width: 1200px;
  margin: 0 auto;

  .enpty {
    margin-top: 100px;
  }
}

.el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.el-upload:hover {
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

.info {
  .info-item {
    display: flex;
    margin-bottom: 10px;
    .label {
      line-height: 2rem;
      font-weight: 600;
      font-size: 16px;
      min-width: 90px;
    }
    .value {
      line-height: 2rem;
    }
  }
}

.btn {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
