const stores = require.context('./store', false, /\.js$/)
const module = {}
stores.keys().forEach(key => {
  if (key === './index.js') return
  const moduleName = key.replace(/(\.\/|\.js)/g, '')
  module[moduleName] = stores(key).default
})
export {
  module
}
