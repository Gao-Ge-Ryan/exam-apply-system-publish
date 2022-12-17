import { getExamIntroductionList, addExamIntroduction, updateExamIntroduction, deleteExamIntroduction, queryExamIntroduction } from '@/api/examIntroduction'

const examIntroduction = {
  state: {
    examIntroductionList: []
  },

  mutations: {
    SET_EXAMINTRODUCTIONLIST: (state, value) => {
      state.examIntroductionList = value
    }
  },

  actions: {
    // 获取用户列表
    getExamIntroductionList({ commit }, params) {
      return getExamIntroductionList(params).then(res => {
        commit('SET_EXAMINTRODUCTIONLIST', res.list)
        return res
      }
      )
    },
    // 添加用户
    addExamIntroduction({ commit }, params) {
      return addExamIntroduction(params).then(res => {})
    },
    // 修改用户
    updateExamIntroduction({ commit }, params) {
      return updateExamIntroduction(params)
    },
    // 删除用户
    deleteExamIntroduction({ commit }, params) {
      return deleteExamIntroduction(params)
    },
    // 获取用户列表
    queryExamIntroduction({ commit }, params) {
      return queryExamIntroduction(params).then(res => {
        commit('SET_EXAMINTRODUCTIONLIST', res.list)
        return res
      }
      )
    }
  }
}

export default examIntroduction
