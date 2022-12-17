<template>
  <div class="app-container">
    <div class="filter-container">
      <!-- @keyup.enter.native="handleFilter" -->
      <el-input v-model="listQuery.title" placeholder="请输入标题" style="width: 200px;" class="filter-item" @keyup.enter.native="handleQuery" />
      <!-- <el-select v-model="listQuery.importance" placeholder="Imp" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select> -->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleQuery">
        查询
      </el-button>
      <!-- <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="addExam">
        Add
      </el-button> -->
    </div>
    <el-table v-loading="listLoading" :data="feedbackList" max-height="625" element-loading-text="Loading" border fit highlight-current-row>
      <!-- <el-table-column align="center" label="ID" prop="id" /> -->
      <el-table-column align="center" type="index" />
      <el-table-column align="center" label="标题" prop="title" />
      <el-table-column align="center" label="内容" max-width="200" show-overflow-tooltip prop="content">
        <!-- <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.content }}</p>
            <div slot="reference" class="name-wrapper">
              {{ scope.row.content }}
            </div>
          </el-popover>
        </template> -->
      </el-table-column>
      <el-table-column align="center" label="用户id" prop="userId" />
      <el-table-column align="center" label="类型" prop="type.msg" />
      <el-table-column align="center" label="创建时间" prop="createTime">
        <template slot-scope="{row}">
          {{ row.createTime | parseTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="回复内容" max-width="200" show-overflow-tooltip prop="replyContent" />
      <el-table-column align="center" label="回复人id" prop="replyUserId" />
      <el-table-column align="center" label="回复时间" prop="replyTime">
        <template slot-scope="{row}">
          {{ row.replyTime | parseTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status.msg" />
      <el-table-column align="center" label="操作" width="140">
        <template slot-scope="row">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-popconfirm title="确定要删除此条数据吗？" @confirm="handleDelete(row)">
            <el-button slot="reference" size="mini" type="danger">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getFeedbackList" />

    <!-- 弹框 -->
    <el-dialog :visible.sync="dialogFormVisible" title="新增数据">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="标题" prop="title">
          <el-input v-model="temp.title" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="temp.content" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="temp.type" class="filter-item" placeholder="请选择">
            <el-option v-for="item in feedBackTypeEnum" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
          </el-select>
        </el-form-item>
        <el-form-item label="回复内容" prop="replyContent">
          <el-input v-model="temp.replyContent" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in feedBackStatusEnum" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?addData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils/time'

export default {
  components: { Pagination },
  filters: {
    parseTime (val) {
      return parseTime(val, '{y}-{m}-{d} {h}:{i}') || '--'
    }
  },
  data () {
    return {
      listQuery: {
        pageNum: 1,
        pageSize: 12,
        title: ''
      },
      listLoading: true,
      ExamName: '',
      dialogFormVisible: false,
      dialogStatus: '',
      temp: {
        title: '',
        content: '',
        type: '',
        replyContent: '',
        status: ''
      },
      visible: false,
      total: 0
    }
  },
  computed: {
    ...mapState({
      feedbackList: state => state.feedback.feedbackList,
      feedBackStatusEnum: state => state.enumList.feedBackStatusEnum,
      feedBackTypeEnum: state => state.enumList.feedBackTypeEnum
    })
  },
  mounted () {
    this.$store.dispatch('getFeedBackTypeEnum', 'FeedBackTypeEnum')
    this.$store.dispatch('getFeedBackStatusEnum', 'FeedBackStatusEnum')
    this.getFeedbackList()
    // this.getRoleList()
  },
  methods: {
    // 获取用户角色枚举
    // getRoleList() {
    //   this.$store.dispatch('getExamRoleList', 'ExamRoleEnum')
    // },

    // 获取用户列表
    getFeedbackList () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize
      }
      this.$store.dispatch('getFeedbackList', params).then(res => {
        this.listLoading = false
        this.total = res.total
      }).catch(err => {
        console.log(err)
      })
    },

    // 清空form表单
    resetrForm () {
      this.temp = {
        title: '',
        content: '',
        type: '',
        replyContent: '',
        status: ''
      }
    },

    // 添加用户弹窗
    addExam () {
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改用户弹窗
    handleUpdate (row) {
      console.log(row.row)
      this.temp = Object.assign({}, row.row)
      this.dialogFormVisible = true
      this.dialogStatus = 'update'
      this.temp.type = row.row.type.enumCode
      this.temp.status = row.row.status.enumCode
    },

    // 删除用户
    handleDelete (row) {
      this.$store.dispatch('deleteFeedback', row.row.id).then(res => {
        this.getFeedbackList()
        this.$notify({
          title: 'Success',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      }).catch(err => {
        this.$message.error(err)
      })
    },

    // 添加用户
    addData () {
      this.$store.dispatch('addFeedback', this.temp).then(res => {
        this.dialogFormVisible = false
        this.resetrForm()
        this.getfeedbackList()
        this.$notify({
          title: 'Success',
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
      }).catch(err => {
        this.$message.error(err)
        console.log(123)
      })
    },
    // 修改用户
    updateData () {
      this.$store.dispatch('updateFeedback', this.temp).then(res => {
        this.getFeedbackList()
        this.$notify({
          title: 'Success',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
        this.dialogFormVisible = false
      }).catch(err => {
        this.$message.error(err)
      })
    },

    // 按条件查询用户
    handleQuery () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize,
        condition: { title: this.listQuery.title }
      }
      this.$store.dispatch('queryFeedback', params)
    }
  }

}
</script>

<style lang="scss" scoped>
/deep/ .el-table-column {
  height: 30px;
  overflow: hidden; //超出的文本隐藏
  text-overflow: ellipsis; //溢出用省略号显示
  white-space: nowrap; //溢出不换行
}
</style>
