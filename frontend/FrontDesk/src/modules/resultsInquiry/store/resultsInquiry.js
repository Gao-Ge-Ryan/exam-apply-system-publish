import * as Api from '../api/resultsInquiry'
const state = {
  resultList:[]
}
const mutations = {
  SET_RESULT_LIST(state,value){
    state.resultList = value
  }
}
const actions = {
  getResultList({commit},params){
    return Api.getResultList(params).then(res=>{
      commit('SET_RESULT_LIST',res)
    })
  }
}
export default {
  state,
  mutations,
  actions
}
