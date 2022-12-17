import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

export function getInfo(){
  return request({
    url:'user/info',
    method:'get'
  }).then(res=>getResult(res))
}

export function updateUserInfo(params){
  return request({
    url:'user/patch',
    method:'patch',
    data:params
  }).then(res=>getResult(res))
}
export function updatePassword(params){
  return request({
    url:'user/password',
    method:'post',
    data:params
  }).then(res=>getResult(res))
}
