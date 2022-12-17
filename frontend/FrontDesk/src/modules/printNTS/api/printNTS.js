import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取打印准考证列表
*/
export function getPrintList(params){
  return request({
    url: `/examUser/print/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data:params
  }).then(res => getResult(res))
}

/* 打印准考证 */
export function printNTS(params){
  return request({
    url: `/word/export-word`,
    method: 'get',
    params: params,
    responseType: 'blob'
  }).then(res => res)
}

