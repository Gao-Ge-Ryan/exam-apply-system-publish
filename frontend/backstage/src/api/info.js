import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/**
* 获取用户列表
*/
export function getInfoList(params) {
  return request({
    url: `info/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: {}
  }).then(res => getResult(res))
}

/**
* 新增用户信息
*/
export function addInfo(params) {
  return request({
    url: `info`,
    method: 'post',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改用户信息
*/
export function updateInfo(params) {
  return request({
    url: `info`,
    method: 'put',
    data: params
  }).then(res => getResult(res))
}

/**
* 修改删除信息
*/
export function deleteInfo(id) {
  return request({
    url: `info/${id}`,
    method: 'delete'
  }).then(res => getResult(res))
}

/**
* 按条件信息
*/
export function queryInfo(params) {
  console.log(params)
  return request({
    url: `info/${params.pageNum}/${params.pageSize}`,
    method: 'post',
    data: params.condition
  }).then(res => getResult(res))
}

