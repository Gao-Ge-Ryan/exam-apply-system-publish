<template>
  <div class="app-container">
    <div class="filter-container">
      <!-- @keyup.enter.native="handleFilter" -->
      <el-input v-model="listQuery.examName" placeholder="请输入考试名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleQuery" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleQuery">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="addExam">
        Add
      </el-button>
    </div>
    <el-table v-loading="listLoading" :data="examList" :expand-row-keys="expands" :row-key="getRowKeys" element-loading-text="Loading" border fit highlight-current-row max-height="625" @expand-change="expandChange">
      <el-table-column type="expand">
        <template slot-scope="{row}">
          <div class="open-diag">
            <!-- <div class="content-item">
              <div class="title">
                创建人：
              </div>
              <div class="words">
                {{ row.ExamId }}
              </div>
            </div> -->
            <div class="content-item">
              <div class="title">
                创建时间：
              </div>
              <div class="words">
                {{ row.createTime | parseTime }}
              </div>
            </div>
            <div class="content-item">
              <div class="title">
                更新时间：
              </div>
              <div class="words">
                {{ row.updateTime | parseTime }}
              </div>
            </div>
            <div class="content-item">
              <div class="title">
                考试描述：
              </div>
              <div class="words">
                {{ row.description }}
              </div>
            </div>
            <div class="content-item">
              <div class="title">
                注意事项：
              </div>
              <div class="words">
                {{ row.announcements }}
              </div>
            </div>
            <el-button type="primary" size="mini" @click="exportRegisterInfo(row)">
              导出报名信息
            </el-button>
            <el-upload action="http://localhost:2020/report/read" style="margin-top:20px">
              <el-button size="small" type="primary">
                导入考生成绩
              </el-button>
            </el-upload>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" type="index" />
      <el-table-column align="center" label="考试名称" prop="title" />
      <el-table-column align="center" label="考试开始时间" prop="startTime">
        <template slot-scope="{row}">
          <span>
            {{ row.startTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="考试结束时间" prop="endTime">
        <template slot-scope="{row}">
          <span>
            {{ row.endTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status.msg">
        <template slot-scope="{row}">
          <el-tag v-if="row.status && row.status.enumCode === 'NotStarted'">
            {{ row.status.msg }}
          </el-tag>
          <el-tag v-if="row.status &&row.status.enumCode === 'Start'" type="success">
            {{ row.status.msg }}
          </el-tag>
          <el-tag v-if="row.status &&row.status.enumCode === 'Stop'" type="warning">
            {{ row.status.msg }}
          </el-tag>
          <el-tag v-if="row.status &&row.status.enumCode === 'Score_Inquiry'" type="danger">
            {{ row.status.msg }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="考试费用" prop="price" />
      <el-table-column align="center" label="报名开始时间" prop="applyStartTime">
        <template slot-scope="{row}">
          <span>
            {{ row.applyStartTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="报名结束时间" prop="applyEndTime">
        <template slot-scope="{row}">
          <span>
            {{ row.applyEndTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建人" prop="userId" />
      <el-table-column align="center" label="类型" prop="examType.msg" />
      <!-- <el-table-column align="center" label="创建时间" prop="createTime">
        <template slot-scope="{row}">
          <span>
            {{ row.createTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="更新时间" prop="updateTime">
        <template slot-scope="{row}">
          <span>
            {{ row.updateTime | parseTime }}
          </span>
        </template>
      </el-table-column> -->
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

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getExamList" />

    <!-- 弹框 -->
    <el-dialog :visible.sync="dialogFormVisible" :title="type === 'add' ? '新增数据' : '修改数据'">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="130px" style="width: 400px; margin-left:50px;">
        <el-form-item label="考试名称" prop="title">
          <el-input v-model="temp.title" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="temp.description" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="报名开始时间" prop="applyStartTime">
          <el-date-picker v-model="temp.applyStartTime" type="datetime" placeholder="选择日期时间" @change="applyStartTimeChange" />
        </el-form-item>
        <el-form-item label="报名结束时间" prop="applyEndTime">
          <el-date-picker v-model="temp.applyEndTime" type="datetime" placeholder="选择日期时间" @change="applyEndTimeChange" />
        </el-form-item>
        <el-form-item label="考试费用" prop="price">
          <el-input v-model="temp.price" type="number" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="考试开始时间" prop="startTime">
          <el-date-picker v-model="temp.startTime" type="datetime" placeholder="选择日期时间" @change="startTimeChange" />
        </el-form-item>
        <el-form-item label="考试结束时间" prop="endTime">
          <el-date-picker v-model="temp.endTime" type="datetime" placeholder="选择日期时间" @change="endTimeChange" />
        </el-form-item>
        <el-form-item label="注意事项" prop="announcements">
          <el-input v-model="temp.announcements" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="考试类型" prop="examType">
          <el-select v-model="temp.examType" class="filter-item" placeholder="请选择">
            <el-option v-for="item in examTypeEnum" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="考试状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in examStatusEnum" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
          </el-select>
        </el-form-item> -->
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
    const checkApplyStartTime = (rule, value, callback) => {
      if (value <= new Date()) {
        callback(new Error('请选择大于当前的时间'))
      }
    }
    const checkApplyEndTime = (rule, value, callback) => {
      if (value <= this.temp.applyStartTime) {
        callback(new Error('请选择大于报名开始时间的时间'))
      }
    }
    const checkPrice = (rule, value, callback) => {
      if (typeof +value !== 'number') {
        callback(new Error('请输入大于0的数字'))
      } else {
        if (+value < 0) {
          callback(new Error('请输入大于0的数字'))
        }
      }
    }
    const checkStartTime = (rule, value, callback) => {
      if (value <= this.temp.applyEndTime + 172800000) {
        callback(new Error('请选择大于报名结束时间2天的时间'))
      }
    }
    const checkEndTime = (rule, value, callback) => {
      if (value <= this.temp.startTime) {
        callback(new Error('请选择大于考试开始时间的时间'))
      }
    }
    return {
      type: 'add',
      expands: [],
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        examName: ''
      },
      listLoading: true,
      dialogFormVisible: false,
      dialogStatus: '',
      temp: {
        // id: '',
        description: '',
        // status: '',
        title: '',
        examType: '',
        price: '',
        announcements: '',
        startTime: null,
        endTime: null, // 考试结束时间
        applyStartTime: null,
        applyEndTime: null
      },
      visible: false,
      total: 0,
      rules: {
        title: [
          { required: true, message: '请输入', trigger: 'blur' },
          { max: 20, message: '长度最多为20', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请输入', trigger: 'blur' },
          { max: 200, message: '长度最多为200', trigger: 'change' }
        ],
        applyStartTime: [
          { required: true, message: '请选择', trigger: 'blur' },
          { validator: checkApplyStartTime, trigger: 'change' }
        ],
        applyEndTime: [
          { required: true, message: '请选择', trigger: 'blur' },
          { validator: checkApplyEndTime, trigger: 'change' }
        ],
        price: [
          { required: true, message: '请输入', trigger: 'blur' },
          { validator: checkPrice, trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '请选择', trigger: 'blur' },
          { validator: checkStartTime, trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '请选择', trigger: 'blur' },
          { validator: checkEndTime, trigger: 'change' }
        ],
        announcements: [
          { required: true, message: '请输入', trigger: 'blur' },
          { max: 200, message: '长度最多为200', trigger: 'change' }
        ],
        examType: [
          { required: true, message: '请选择', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapState({
      examList: state => state.exam.examList,
      roleList: state => state.enumList.roleList,
      examTypeEnum: state => state.enumList.examTypeEnum,
      examStatusEnum: state => state.enumList.examStatusEnum
    })
  },
  mounted () {
    this.getExamList()
    this.$store.dispatch('getExamTypeEnum', 'ExamTypeEnum')
    this.$store.dispatch('getExamStatusEnum', 'ExamStatusEnum')
  },
  methods: {
    /** 考试开始时间切换 */
    startTimeChange (e) {
      this.temp.startTime = e.getTime()
    },
    /** 考试结束时间切换 */
    endTimeChange (e) {
      this.temp.endTime = e.getTime()
    },
    /** 报名时间切换 */
    applyStartTimeChange (e) {
      this.temp.applyStartTime = e.getTime()
    },
    /** 报名结束时间切换 */
    applyEndTimeChange (e) {
      this.temp.applyEndTime = e.getTime()
    },
    // 获取用户角色枚举
    getRoleList () {
      this.$store.dispatch('getExamRoleList', 'ExamRoleEnum')
    },

    // 获取用户列表
    getExamList () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize
      }
      this.$store.dispatch('getExamList', params).then(res => {
        this.total = res.total
        this.listLoading = false
        console.log(this.examList)
      }).catch(err => {
        console.log(err)
      })
    },

    // 清空form表单
    resetrForm () {
      this.temp = {
        // id: '',
        description: '',
        // status: '',
        title: '',
        examType: '',
        price: '',
        announcements: '',
        startTime: null,
        endTime: null, // 考试结束时间
        applyStartTime: null,
        applyEndTime: null
      }
    },

    // 添加用户弹窗
    addExam () {
      this.type = 'add'
      this.resetrForm()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
    },
    // 修改用户弹窗
    handleUpdate (row) {
      this.type = ''
      this.temp = Object.assign({}, row.row)
      console.log(row.row)
      // this.temp.id = row.row.id
      // this.temp.title = row.row.title
      // this.temp.description = row.row.description
      this.temp.examType = row.row.examType.enumCode
      this.temp.status = row.row.status.enumCode
      // this.temp.price = row.row.price
      // this.temp.announcements = row.row.announcements
      this.dialogFormVisible = true
      this.dialogStatus = 'update'
    },

    // 删除用户
    handleDelete (row) {
      this.$store.dispatch('deleteExam', row.row.id).then(res => {
        this.getExamList()
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
      this.$store.dispatch('addExam', this.temp).then(res => {
        this.getExamList()
        this.$notify({
          title: 'Success',
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
        this.dialogFormVisible = false
      }).catch(err => {
        console.log(err)
        // this.$message.error(err)
      })
    },
    // 修改用户
    updateData () {
      this.$store.dispatch('updateExam', this.temp).then(res => {
        this.getExamList()
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
        condition: { title: this.listQuery.examName }
      }
      console.log(params)
      console.log(params.condition)
      this.$store.dispatch('queryExam', params)
    },

    getRowKeys (row) {
      return row.id
    },

    expandChange (row, expandedRows) {
      const that = this
      console.log(expandedRows)
      if (expandedRows.length) { // 说明展开了
        that.expands = []
        if (row) {
          that.expands.push(row.id)// 只展开当前行id
        }
      } else { // 说明收起了
        that.expands = []
      }
    },

    /* 导出报名信息 */
    exportRegisterInfo (row) {
      console.log(row.id)
      this.$store.dispatch('exportRegisterInfo', row.id).then(res => {
        console.log(res)
        const content = res.data
        // const contentDisposition = res.headers['Content-Disposition'] // 从response的headers中获取filename, 后端response.setHeader("content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
        // console.log(contentDisposition)
        // const patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
        // const result = patt.exec(contentDisposition)
        // console.log(result)
        // const filename = decodeURI(result[1])
        const filename = `${row.title}报名信息表.xls`
        // 组装a标签
        const elink = document.createElement('a')
        // 设置下载文件名
        elink.download = filename
        elink.style.display = 'none'
        const blob = new Blob([content])
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        document.body.removeChild(elink)
      })
    }
  }

}
</script>
<style lang="scss" scoped>
.content-item {
  display: flex;
  margin-bottom: 20px;
  .title {
    margin-right: 6 px;
    font-weight: 600;
    font-size: 18px;
  }
  .words {
    display: flex;
    align-items: center;
    font-weight: 500;
    font-style: 16px;
  }
}
</style>
