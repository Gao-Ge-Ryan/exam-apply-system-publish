import * as Api from '../api/registrationQuery'

const state = {
  queryList:{},
  payDetails:''
}
const mutations = {
  SET_QUERYLIST(state,value){
    state.queryList = value
  },
  SET_PAY_DETAILS(state,value){
    state.payDetails = value
  }
}
const actions = {
  /* 查询报名列表 */
  getQueryList({commit},params){
    return Api.getQueryList(params).then(res=>{
      commit('SET_QUERYLIST',res)
    })
  },
  getPayDetails({commit},examId){
    return Api.getPay(examId).then(res=>{
      commit('SET_PAY_DETAILS',res)
    })
  }
}
export default {
  state,
  mutations,
  actions
}
