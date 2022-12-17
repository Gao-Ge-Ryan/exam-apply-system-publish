import { getInfoList } from '../api/examInfo'

const state = {
  examRegisterObj:{},
  examAdmissionTicketObj:{},
  examGradeObj:{}
}
const mutations = {
  SET_EXAMREGISTEROBJ: (state, value) => {
    state.examRegisterObj = value
  },
  SET_EXAMADMISSIONTICKETOBJ: (state, value) => {
    state.examaAmissionTicketObj = value
  },
  SET_EXAMGRADEOBJ: (state, value) => {
    state.examGradeObj = value
  }
}
const actions = {
  /* 获取报名时间公告 */
  getExamRegisterObj({commit},params){
    return getInfoList(params).then(res=>{
      console.log(res)
      commit('SET_EXAMREGISTEROBJ',res)
    })
  },

  /* 获取报名时间公告 */
  getExamAdmissionTicketrObj({commit},params){
    return getInfoList(params).then(res=>{
      console.log(res)
      commit('SET_EXAMREGISTEROBJ',res)
    })
  },

  /* 获取报名时间公告 */
  getExamGradeObj({commit},params){
    return getInfoList(params).then(res=>{
      console.log(res)
      commit('SET_EXAMREGISTEROBJ',res)
    })
  }
}
export default {
  state,
  mutations,
  actions
}
