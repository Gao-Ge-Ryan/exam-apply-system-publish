import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

export function login(params) {
  return request({
    url: '/user/login',
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
