import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取用户列表
*/
export function getExamUserList(params) {
  return request({
    url: `examUser/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: {}
  }).then(res => getResult(res))
}

/**
* 新增用户信息
*/
export function addExamUser(params) {
  return request({
    url: `examUser`,
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改用户信息
*/
export function updateExamUser(params) {
  return request({
    url: `examUser`,
    method: 'put',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改删除信息
*/
export function deleteExamUser(id) {
  return request({
    url: `examUser/${id}`,
    method: 'delete'
  }).then(res => getResult(res))
}

/**
* 按条件信息
*/
export function queryExamUser(params) {
  return request({
    url: `examUser/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: params.condition
  }).then(res => getResult(res))
}

export function getRegisterExamList(params) {
  return request({
    url: `/examUser/examName`,
    method: 'get'
  }).then(res => getResult(res))
}

