import * as Api from '../api/personalCenter'
const state = {
userInfo:{}
}
const mutations = {
SET_USERINFO(state,value){
  state.userInfo = value
}
}
const actions = {
  getInfo({commit}){
    return Api.getInfo().then(res=>{
      commit('SET_USERINFO',res)
    })
  },
  updateUserInfo({commit},params){
    return Api.updateUserInfo(params).then(res=>{
    })
  },
  updatePassword({commit},params){
    return Api.updatePassword(params).then(res=>{
    })
  }
}
export default {
  state,
  mutations,
  actions
}
