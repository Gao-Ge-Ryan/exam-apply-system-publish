import { login, getInfo } from '@/api/login'
import { getUserList, addUser, updateUser, deleteUser, queryUser } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
const user = {
  state: {
    token: getToken(),

    name: '',
    avatar: '',
    roles: [],
    userList: []
  },

  mutations: {
    SET_USERLIST: (state, value) => {
      state.userList = value
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 获取用户列表
    getUserList({ commit }, params) {
      return getUserList(params).then(res => {
        commit('SET_USERLIST', res.list)
        return res
      }
      )
    },
    // 添加用户
    addUser({ commit }, params) {
      return addUser(params).then(res => {})
    },
    // 修改用户
    updateUser({ commit }, params) {
      return updateUser(params)
    },
    // 删除用户
    deleteUser({ commit }, params) {
      return deleteUser(params)
    },
    // 按条件获取用户列表
    queryUser({ commit }, params) {
      return queryUser(params).then(res => {
        console.log(params)
        commit('SET_USERLIST', res.list)
        return res
      }
      )
    },
    // 登录
    login({ commit }, params) {
      // const username = params.userName.trim()
      return login(params).then(res => {
        console.log(res)
        const data = res
        setToken(data)
        commit('SET_TOKEN', data)
        return res
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const data = response.data
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    logOut({ commit, state }) {
      commit('SET_TOKEN', '')
      // commit('SET_ROLES', [])
      removeToken()
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        // removeToken()
        resolve()
      })
    }
  }
}

export default user
