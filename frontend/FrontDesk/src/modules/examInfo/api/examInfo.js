import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取公告列表
*/
export function getInfoList(params) {
  return request({
    url: `info/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: params.params
  }).then(res => getResult(res))
}
