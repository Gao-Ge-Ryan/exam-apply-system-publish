import { getFeedbackList, addFeedback, updateFeedback, deleteFeedback, queryFeedback } from '@/api/feedback'

const feedback = {
  state: {
    feedbackList: []
  },

  mutations: {
    SET_FEEDBACKLIST: (state, value) => {
      state.feedbackList = value
    }
  },

  actions: {
    // 获取用户列表
    getFeedbackList({ commit }, params) {
      return getFeedbackList(params).then(res => {
        commit('SET_FEEDBACKLIST', res.list)
        return res
      }
      )
    },
    // 添加用户
    addFeedback({ commit }, params) {
      return addFeedback(params).then(res => {})
    },
    // 修改用户
    updateFeedback({ commit }, params) {
      return updateFeedback(params)
    },
    // 删除用户
    deleteFeedback({ commit }, params) {
      return deleteFeedback(params)
    },
    // 获取用户列表
    queryFeedback({ commit }, params) {
      return queryFeedback(params).then(res => {
        commit('SET_FEEDBACKLIST', res.list)
        return res
      }
      )
    }
  }
}

export default feedback
