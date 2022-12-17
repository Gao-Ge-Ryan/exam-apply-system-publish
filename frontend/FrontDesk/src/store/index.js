import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const files = require.context('@/modules', true, /module.js$/)
let modules = {}
files.keys().forEach(key => {
  modules = { ...modules, ...files(key).module }
})
export default new Vuex.Store({
  modules: {
    ...modules
  }
})
