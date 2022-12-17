import * as Api from '../api/printNTS'
const state = {
  printObj:{}
}
const mutations = {
  SET_PRINT_OBJ(state,value){
    state.printObj = value
  }
}
const actions = {
  getPrintList({commit},params){
    return Api.getPrintList(params).then(res=>{
      commit('SET_PRINT_OBJ',res)
    })
  },
  printNTS({commit},params){
    return Api.printNTS(params).then(res=>{
      return res
    })
  }
}
export default {
  state,
  mutations,
  actions
}
