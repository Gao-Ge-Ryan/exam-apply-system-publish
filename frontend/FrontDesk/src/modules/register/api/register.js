import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取考试介绍详情
*/
export function getExamIntroduction(params){
  return request({
    url: `examIntroduction/type`,
    method: 'get',
    params:params
  },params).then(res => getResult(res))
}

/* 获取考试列表 */
export function getExamList(params) {
  return request({
    url: `/exam/examType/${params.examType}`,
    method: 'get',
    params: {}
  }).then(res => getResult(res))
}

/* 考试报名 */
export function register(params) {
  return request({
    url: `/examUser`,
    method: 'post',
    data: params
  }).then(res => getResult(res))
}
