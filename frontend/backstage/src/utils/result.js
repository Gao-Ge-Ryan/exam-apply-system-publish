import { get } from 'lodash-es'
// 处理返回结果
export function getResult(res) {
  const flag = get(res.data, 'flag')
  if (flag) {
    const data = get(res, 'data.data')
    return data
  } else {
    const message = get(res, 'data.msg')
    let data = ''
    if (res.data.data) {
      data = get(res, 'data.data')
    }

    const error = new Error(message + '  ' + (data || ''))
    throw error
  }
}
