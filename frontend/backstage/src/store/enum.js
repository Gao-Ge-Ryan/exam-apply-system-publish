import { getEnum } from '@/api/enum'

const enumList = {

  state: {
    roleList: [],
    examStatusEnum: [], // 考试状态 未开始
    examTypeEnum: [], // 考试类型
    examUserStatusEnum: [], // 报名 未报名 未支付
    feedBackTypeEnum: [], // bug
    feedBackStatusEnum: [], // bug
    infoTypeEnum: [] // 成绩查询 信息公告
  },

  mutations: {
    SET_USERROLELIST: (state, value) => {
      state.roleList = value
    },
    SET_EXAMSTATUS: (state, value) => {
      state.examStatusEnum = value
    },
    SET_EXAMTYPE: (state, value) => {
      state.examTypeEnum = value
    },
    SET_EXAMUSERSTATUS: (state, value) => {
      state.examUserStatusEnum = value
    },
    SET_FEEDBACKSTATUS: (state, value) => {
      state.feedBackStatusEnum = value
    },
    SET_FEEDBACKTYPE: (state, value) => {
      state.feedBackTypeEnum = value
    },
    SET_INFOTYPE: (state, value) => {
      state.infoTypeEnum = value
    }
  },

  actions: {
    // 获取用户列表
    getUserRoleList({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_USERROLELIST', res)
      }
      )
    },
    // 获取用户列表
    getExamStatusEnum({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_EXAMSTATUS', res)
      }
      )
    },
    // 获取用户列表
    getExamTypeEnum({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_EXAMTYPE', res)
      }
      )
    },
    // 获取用户列表
    getExamUserStatusEnum({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_EXAMUSERSTATUS', res)
      }
      )
    },
    // 获取用户列表
    getFeedBackStatusEnum({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_FEEDBACKSTATUS', res)
      }
      )
    },
    // 获取用户列表
    getFeedBackTypeEnum({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_FEEDBACKTYPE', res)
      }
      )
    },
    // 获取用户列表
    getInfoTypeEnum({ commit }, params) {
      return getEnum(params).then(res => {
        commit('SET_INFOTYPE', res)
      }
      )
    }
  }
}

export default enumList

