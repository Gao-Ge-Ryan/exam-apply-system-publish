import Cookies from 'js-cookie'

const TokenKey = 'user-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  Cookies.set(TokenKey, token)
  Cookies.get(TokenKey)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
