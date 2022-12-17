import { login ,getAuthCode, registeredUser} from '../api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

const state = {
  token: getToken()
}
const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  }
}
const actions = {
  // 登录
  login({ commit }, params) {
    // const username = params.userName.trim()
    return login(params).then(res => {
      const data = res
      setToken(data)
      commit('SET_TOKEN', data)
      return res
    })
  },
  // 登出
  logout({ commit, state }) {
    commit('SET_TOKEN', '')
    // commit('SET_ROLES', [])
    removeToken()
    localStorage.removeItem('userInfo')
  },

  /* 获取邮箱验证码 */
  getAuthCode({commit},params){
    return getAuthCode(params)
  },

  /* 注册用户 */
  registeredUser({commit},params){
    return registeredUser(params)
  }
}
export default {
  state,
  mutations,
  actions
}
