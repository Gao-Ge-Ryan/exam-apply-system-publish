import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取角色枚举
*/
export function getEnum(className) {
  return request({
    url: `enum/${className}`,
    method: 'get',
    data: {}
  }).then(res => getResult(res))
}
