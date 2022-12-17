import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

export function login(params) {
  return request({
    url: '/user/login',
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

/* 获取验证码 */
export function getAuthCode(params) {
  return request({
    url: '/email/getAuthCode',
    method: 'get',
    params: params
  })
}

/* 注册用户 */
export function registeredUser(params) {
  return request({
    url: '/user/registeredUser',
    method: 'post',
    data: params
  }).then(res => getResult(res))
}


