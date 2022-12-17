import { get } from 'lodash-es'
export function getResult(res) {
  const flag = get(res.data, 'flag')
  if (flag) {
    const data = get(res, 'data.data')
    return data
  }else {
    // const error = new Error(message + '  ' + (data || ''))
    const error = new Error(res.data.msg)
    console.log(error);
    throw error
  }
}
