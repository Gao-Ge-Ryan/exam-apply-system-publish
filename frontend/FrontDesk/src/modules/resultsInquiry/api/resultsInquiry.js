import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

export function getResultList(params){
  return request({
    url:'examUser/score',
    method:'get'
  }).then(res=>getResult(res))
}
