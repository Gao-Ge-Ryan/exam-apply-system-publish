import { getInfoList, addInfo, updateInfo, deleteInfo, queryInfo } from '@/api/info'

const info = {
  state: {
    infoList: []
  },

  mutations: {
    SET_INFOLIST: (state, value) => {
      state.infoList = value
    }
  },

  actions: {
    // 获取用户列表
    getInfoList({ commit }, params) {
      return getInfoList(params).then(res => {
        commit('SET_INFOLIST', res.list)
        return res
      }
      )
    },
    // 添加用户
    addInfo({ commit }, params) {
      return addInfo(params).then(res => {})
    },
    // 修改用户
    updateInfo({ commit }, params) {
      return updateInfo(params)
    },
    // 删除用户
    deleteInfo({ commit }, params) {
      return deleteInfo(params)
    },
    // 获取用户列表
    queryInfo({ commit }, params) {
      return queryInfo(params).then(res => {
        commit('SET_INFOLIST', res.list)
        return res
      }
      )
    }
  }
}

export default info
