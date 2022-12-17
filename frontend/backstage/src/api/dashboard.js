import { request } from '@/utils/request'
import { getResult } from '@/utils/result'

/* 获取echarts数据 */
export function getEchartsData() {
  return request({
    url: `dashboard/exam`,
    method: 'get'
  }).then(res => getResult(res))
}

/* 查询考试列表 */
export function getDashExamList() {
  return request({
    url: `dashboard/scoreInquiry`,
    method: 'get'
  }).then(res => getResult(res))
}
/* 查询考试列表 */
export function getScoreData(examId) {
  return request({
    url: `/dashboard/scoreInquiry/${examId}`,
    method: 'get'
  }).then(res => getResult(res))
}
