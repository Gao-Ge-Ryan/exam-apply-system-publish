import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './user'
import exam from './exam'
import info from './info'
import examIntroduction from './examIntroduction'
import examUser from './examUser'
import feedback from './feedback'
import enumList from './enum'
import getters from './getters'
import dashboard from './dashboard'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    enumList,
    exam,
    examIntroduction,
    examUser,
    feedback,
    info,
    dashboard
  },
  getters
})

export default store
