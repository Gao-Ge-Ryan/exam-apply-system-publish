import {getEnum} from '@/api/common.js'
import {getExamIntroduction,getExamList,register} from '../api/register.js'

const state = {
  examTypeEnum: [], // 考试类型
  examIntroduction:{}, //考试介绍
  examList: []
}
const mutations = {
  SET_EXAMTYPE: (state, value) => {
    state.examTypeEnum = value
  },
  SET_EXAMINTRODUCTION:(state,value)=>{
    state.examIntroduction = value
  },
  SET_EXAMLIST: (state, value) => {
    state.examList = value
  }
}
const actions = {
   // 获取考试类型列表
   getExamTypeEnum({ commit }, params) {
    return getEnum(params).then(res => {
      commit('SET_EXAMTYPE', res)
    }
    )
  },

  /* 获取考试介绍 */
  getExamIntroduction({ commit }, params) {
    return getExamIntroduction(params).then(res => {
      commit('SET_EXAMINTRODUCTION', res)
    }
    )
  },
  // 获取用户列表
  getExamList({ commit }, params) {
    return getExamList(params).then(res => {
      commit('SET_EXAMLIST', res)
      return res
    }
    )
  },
  /* 考试报名 */
  register({commit},params){
    return register(params).then(res=>{

    })
  }
}
export default {
  state,
  mutations,
  actions
}
