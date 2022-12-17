import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取考试介绍详情
*/
export function getQueryList(params){
  return request({
    url: `/examUser/user/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data:params
  }).then(res => getResult(res))
}

/* 缴费 */
export function getPay(examId){
  return request({
    url: `/alipay/${examId}`,
    method: 'post'
  }).then(res => res.data)
}

