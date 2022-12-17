import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取用户列表
*/
export function getExamIntroductionList(params) {
  return request({
    url: `examIntroduction/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: {}
  }).then(res => getResult(res))
}

/**
* 新增用户信息
*/
export function addExamIntroduction(params) {
  return request({
    url: `examIntroduction`,
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改用户信息
*/
export function updateExamIntroduction(params) {
  return request({
    url: `examIntroduction`,
    method: 'put',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改删除信息
*/
export function deleteExamIntroduction(id) {
  return request({
    url: `examIntroduction/${id}`,
    method: 'delete'
  }).then(res => getResult(res))
}

/**
* 按条件信息
*/
export function queryExamIntroduction(params) {
  return request({
    url: `examIntroduction/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: params.condition
  }).then(res => getResult(res))
}

