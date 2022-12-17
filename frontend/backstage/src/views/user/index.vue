<template>
  <div class="app-container">
    <div class="filter-container">
      <!-- @keyup.enter.native="handleFilter" -->
      <el-input v-model="listQuery.userName" placeholder="请输入用户名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleQuery" />
      <!-- <el-select v-model="listQuery.importance" placeholder="Imp" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select> -->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleQuery">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="addUser">
        Add
      </el-button>
    </div>
    <el-table v-loading="listLoading" max-height="540" :data="userList" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column align="center" type="index" />
      <el-table-column align="center" label="头像" prop="avatar">
        <!-- <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" /> -->
        <template slot-scope="{row}">
          <img style="width:40px;heigth:40px" :src="row.avatar ? row.avatar :'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" alt="">
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名" prop="userName" />
      <!-- <el-table-column align="center" label="密码" prop="password" /> -->
      <el-table-column align="center" label="昵称" prop="nickName" />
      <el-table-column align="center" label="手机号" prop="phone" />
      <el-table-column align="center" label="身份证号" prop="identityNum" />
      <el-table-column align="center" label="居住地址" prop="address" />
      <el-table-column align="center" label="角色" prop="role.msg">
        <template slot-scope="{row}">
          <el-tag v-if="row.role.enumCode === 'User'">
            {{ row.role.msg }}
          </el-tag>
          <el-tag v-if="row.role.enumCode === 'Student'" type="success">
            {{ row.role.msg }}
          </el-tag>
          <el-tag v-if="row.role.enumCode === 'Teacher'" type="warning">
            {{ row.role.msg }}
          </el-tag>
          <el-tag v-if="row.role.enumCode === 'Admin'" type="danger">
            {{ row.role.msg }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime">
        <template slot-scope="{row}">
          <span>
            {{ row.createTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="修改时间" prop="updateTime">
        <template slot-scope="{row}">
          <span>
            {{ row.updateTime | parseTime }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="操作" width="140">
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

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getUserList" />

    <!-- 弹框 -->
    <el-dialog :visible.sync="dialogFormVisible" :title="type === 'add' ? '新增数据' : '修改数据'">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;" :rules="rules">
        <el-form-item label="用户名" prop="userName">
          <el-input v-if="type === 'add'" v-model="temp.userName" placeholder="请输入邮箱" />
          <el-input v-else v-model="temp.userName" disabled placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item v-if="type === 'add'" label="密码" prop="password">
          <el-input v-model="temp.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="temp.role" class="filter-item" placeholder="请选择角色" @change="changeRole">
            <el-option v-for="item in roleList" :key="item.enumCode" :label="item.msg" :value="item.enumCode" />
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
      rules: {
        userName: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          {
            type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { max: 16, message: '长度最多为16', trigger: 'change' }
        ],
        role: [
          { required: true, message: '请选择', trigger: 'blur' }
        ]
      },
      type: 'add',
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        userName: ''
      },
      listLoading: true,
      userName: '',
      dialogFormVisible: false,
      dialogStatus: '',
      temp: {
        password: '',
        role: '',
        userName: ''
      },
      visible: false,
      total: 0
    }
  },
  computed: {
    ...mapState({
      userList: state => state.user.userList,
      roleList: state => state.enumList.roleList
    })
  },
  mounted () {
    this.getUserList()
    this.getRoleList()
  },
  methods: {
    changeRole (el) {
      this.temp.role = el
    },
    // 获取用户角色枚举
    getRoleList () {
      this.$store.dispatch('getUserRoleList', 'UserRoleEnum')
    },

    // 获取用户列表
    getUserList () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize
      }
      this.$store.dispatch('getUserList', params).then(res => {
        this.total = res.total
        this.listLoading = false
      }).catch(err => {
        console.log(err.msg)
      })
    },

    // 清空form表单
    resetrForm () {
      this.temp = {
        password: '',
        role: '',
        userName: '',
        id: ''
      }
    },

    // 添加用户弹窗
    addUser () {
      this.resetrForm()
      this.type = 'add'
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
    },
    // 修改用户弹窗
    handleUpdate (row) {
      console.log(row.row)
      this.type = ''
      // this.temp = Object.assign({}, row.row)
      this.temp.password = row.row.password
      this.temp.role = row.row.role.enumCode
      this.temp.userName = row.row.userName
      this.temp.id = row.row.id
      this.dialogFormVisible = true
      this.dialogStatus = 'update'
    },

    // 删除用户
    handleDelete (row) {
      this.$store.dispatch('deleteUser', row.row.id).then(res => {
        this.getUserList()
        this.$notify({
          title: 'Success',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      }).catch(err => {
        this.$message.error(err.msg)
      })
    },

    // 添加用户
    addData () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$store.dispatch('addUser', this.temp).then(res => {
            this.dialogFormVisible = false
            this.getUserList()
            this.$notify({
              title: 'Success',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
            this.resetrForm()
          }).catch(err => {
            console.log(err)
            this.$message.error(err)
          })
        } else {
          return false
        }
      })
    },
    // 修改用户
    updateData () {
      console.log(this.temp)
      this.$store.dispatch('updateUser', this.temp).then(res => {
        this.dialogFormVisible = false
        this.getUserList()
        this.$notify({
          title: 'Success',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
        this.resetrForm()
      }).catch(err => {
        this.$message.error(err)
      })
    },

    // 按条件查询用户
    handleQuery () {
      const params = {
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize,
        condition: { userName: this.listQuery.userName }
      }
      console.log(params)
      this.$store.dispatch('queryUser', params).then(res => {
        this.total = res.total
      })
    }
  }

}
</script>
