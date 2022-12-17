import { getEchartsData, getDashExamList, getScoreData } from '@/api/dashboard'

const exam = {
  state: {

  },

  mutations: {

  },

  actions: {
    getEchartsData({ commit }) {
      return getEchartsData().then(res => res)
    },
    getDashExamList({ commit }) {
      return getDashExamList().then(res => res)
    },
    getScoreData({ commit }, id) {
      return getScoreData(id).then(res => res)
    }
  }
}

export default exam
