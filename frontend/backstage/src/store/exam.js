import { getExamList, addExam, updateExam, deleteExam, queryExam, exportRegisterInfo } from '@/api/exam'

const exam = {
  state: {
    examList: []
  },

  mutations: {
    SET_EXAMLIST: (state, value) => {
      state.examList = value
    }
  },

  actions: {
    // 获取用户列表
    getExamList({ commit }, params) {
      return getExamList(params).then(res => {
        commit('SET_EXAMLIST', res.list)
        return res
      }
      )
    },
    // 添加用户
    addExam({ commit }, params) {
      return addExam(params).then(res => {})
    },
    // 修改用户
    updateExam({ commit }, params) {
      return updateExam(params)
    },
    // 删除用户
    deleteExam({ commit }, params) {
      return deleteExam(params)
    },
    // 获取用户列表
    queryExam({ commit }, params) {
      return queryExam(params).then(res => {
        console.log(params)
        commit('SET_EXAMLIST', res.list)
        return res
      }
      )
    },
    exportRegisterInfo({ commit }, examId) {
      return exportRegisterInfo(examId).then(res => {
        return res
      }
      )
    }
  }
}

export default exam
