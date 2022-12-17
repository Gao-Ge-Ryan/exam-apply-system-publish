import { getExamUserList, addExamUser, updateExamUser, deleteExamUser, queryExamUser, getRegisterExamList } from '@/api/examUser'

const examUser = {
  state: {
    examUserList: [],
    examList: []
  },

  mutations: {
    SET_EXAMUSERLIST: (state, value) => {
      state.examUserList = value
    },
    SET_EXAMLIST: (state, value) => {
      state.examList = value
    }
  },

  actions: {
    // 获取用户列表
    getExamUserList({ commit }, params) {
      return getExamUserList(params).then(res => {
        commit('SET_EXAMUSERLIST', res.list)
        return res
      }
      )
    },
    // 添加用户
    addExamUser({ commit }, params) {
      return addExamUser(params).then(res => {})
    },
    // 修改用户
    updateExamUser({ commit }, params) {
      return updateExamUser(params)
    },
    // 删除用户
    deleteExamUser({ commit }, params) {
      return deleteExamUser(params)
    },
    // 获取用户列表
    queryExamUser({ commit }, params) {
      return queryExamUser(params).then(res => {
        commit('SET_EXAMUSERLIST', res.list)
        return res
      }
      )
    },
    // 获取考试名称列表
    getRegisterExamList({ commit }, params) {
      return getRegisterExamList(params).then(res => {
        commit('SET_EXAMLIST', res)
        return res
      }
      )
    }
  }
}

export default examUser
