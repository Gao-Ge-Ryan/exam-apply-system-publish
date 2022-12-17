<template>
  <div class="app-container">
    <div class="filter-container">
      <!-- @keyup.enter.native="handleFilter" -->
      <el-input v-model="listQuery.applyName" placeholder="请输入考生姓名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleQuery" />
      <!-- <el-select v-model="listQuery.importance" placeholder="Imp" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select> -->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleQuery">
        查询
      </el-button>
      <!-- <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="addExam">
        Add
      </el-button> -->
      <el-select v-model="examId" clearable filterable placeholder="请选择" style="margin-left:30px" @change="changeExam">
        <el-option v-for="item in examList" :key="item.id" :label="item.title" :value="item.id" />
      </el-select>
    </div>
    <el-table v-loading="listLoading" :data="examUserList" max-height="625" element-loading-text="Loading" border fit highlight-current-row>
      <!-- <el-table-column align="center" label="ID" prop="id" /> -->
      <el-table-column align="center" type="index" />
      <el-table-column align="center" label="考试名称" prop="examModel.title" />
      <el-table-column align="center" label="报名时间" prop="createTime">
        <template slot-scope="{row}">
          {{ row.createTime | parseTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户id" prop="userId" />
      <el-table-column align="center" label="分数" prop="score" />
      <el-table-column align="center" label="状态" prop="status.msg" />
      <el-table-column align="center" label="姓名" prop="applyName" />
      <el-table-column align="center" label="身份证号" prop="idNumber" />
      <el-table-column align="center" label="考场" prop="examRoom" />
      <el-table-column align="center" label="准考证号" prop="examNumber" />
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

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getExamUserList" />

    <!-- 弹框 -->
    <el-dialog :visible.sync="dialogFormVisible" :title="type === 'add' ? '新增数据' : '修改数据'">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="分数" prop="score">
          <el-input v-model="temp.score" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in examUserStatusEnum" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="角色">
          <el-select v-model="temp.role" class="filter-item" placeholder="请选择角色">
            <el-option v-for="item in roleList" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="姓名" prop="applyName">
          <el-input v-model="temp.applyName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="考场" prop="examRoom">
          <el-input v-model="temp.examRoom" placeholder="请输入" />
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
      type: 'add',
      listQuery: {
        pageNum: 1,
        pageSize: 12,
        applyName: ''
      },
      listLoading: true,
      ExamName: '',
      dialogFormVisible: false,
      dialogStatus: '',
      temp: {
        score: '',
        status: '',
        applyName: '',
        examRoom: ''
      },
      visible: false,
      total: 0,
      examId: ''
    }
  },
  computed: {
    ...mapState({
      examUserList: state => state.examUser.examUserList,
      examUserStatusEnum: state => state.enumList.examUserStatusEnum,
      examList: state => state.examUser.examList
    })
  },
  mounted () {
    this.getExamUserList()
    // this.getRoleList()
    this.$store.dispatch('getExamUserStatusEnum', 'ExamUserStatusEnum')
    this.getRegisterExamList()
  },
  methods: {
    getRegisterExamList () {
      this.$store.dispatch('getRegisterExamList').then(res => {
        console.log(this.examList)
      }).catch(err => {
        console.log(err)
      })
    },
    changeExam (el) {
      this.examId = el
      this.handleQuery()
    },
    // 获取用户角色枚举
    // getRoleList() {
    //   this.$store.dispatch('getExamRoleList', 'ExamRoleEnum')
    // },

    // 获取用户列表
    getExamUserList () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize
      }
      this.$store.dispatch('getExamUserList', params).then(res => {
        this.total = res.total
        console.log(this.examUserList)
        this.listLoading = false
      }).catch(err => {
        console.log(err)
      })
    },

    // 清空form表单
    resetrForm () {
      this.temp = {
        score: '',
        status: '',
        applyName: '',
        examRoom: ''
      }
    },

    // 添加用户弹窗
    addExam () {
      this.type = 'add'
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改用户弹窗
    handleUpdate (row) {
      this.type = ''
      console.log(row)
      this.temp = Object.assign({}, row.row)
      this.dialogFormVisible = true
      this.dialogStatus = 'update'
      this.temp.status = row.row.status.enumCode
    },

    // 删除用户
    handleDelete (row) {
      this.$store.dispatch('deleteExamUser', row.row.id).then(res => {
        this.getExamUserList()
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
      this.$store.dispatch('addExamUser', this.temp).then(res => {
        this.dialogFormVisible = false
        this.resetrForm()
        this.getExamUserList()
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
      this.$store.dispatch('updateExamUser', this.temp).then(res => {
        this.getExamUserList()
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
      this.listQuery.pageNum = 1
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize,
        condition: { examId: this.examId, applyName: this.listQuery.applyName }
      }
      this.$store.dispatch('queryExamUser', params)
    }
  }

}
</script>
