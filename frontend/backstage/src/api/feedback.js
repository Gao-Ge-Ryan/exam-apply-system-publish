import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取用户列表
*/
export function getFeedbackList(params) {
  return request({
    url: `feedback/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: {}
  }).then(res => getResult(res))
}

/**
* 新增用户信息
*/
export function addFeedback(params) {
  return request({
    url: `feedback`,
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改用户信息
*/
export function updateFeedback(params) {
  return request({
    url: `feedback`,
    method: 'put',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改删除信息
*/
export function deleteFeedback(id) {
  return request({
    url: `feedback/${id}`,
    method: 'delete'
  }).then(res => getResult(res))
}

/**
* 按条件信息
*/
export function queryFeedback(params) {
  return request({
    url: `feedback/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: params.condition
  }).then(res => getResult(res))
}

