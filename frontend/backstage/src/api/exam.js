import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取用户列表
*/
export function getExamList(params) {
  return request({
    url: `exam/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: {}
  }).then(res => {
    console.log(res)
    return getResult(res)
  })
}

/**
* 新增用户信息
*/
export function addExam(params) {
  return request({
    url: `exam`,
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改用户信息
*/
export function updateExam(params) {
  return request({
    url: `exam`,
    method: 'put',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改删除信息
*/
export function deleteExam(id) {
  return request({
    url: `exam/${id}`,
    method: 'delete'
  }).then(res => getResult(res))
}

/**
* 按条件信息
*/
export function queryExam(params) {
  console.log(params)
  return request({
    url: `exam/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: params.condition
  }).then(res => getResult(res))
}

/**
* 导出报名信息
*/
export function exportRegisterInfo(examId) {
  return request({
    url: `/report/export/${examId}`,
    method: 'get',
    responseType: 'blob'
  }).then(res => res)
}

