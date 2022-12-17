// const utils = require('./utils')
// 安装path模块
const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}
module.exports = {
  devServer: {},
  chainWebpack: (config) => {
    config.resolve.alias.set('@', resolve('src'))

    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()
  }
  // rules: [
  //   {
  //     test: /\.svg$/,
  //     loader: 'svg-sprite-loader',
  //     include: [resolve('src/icons')],
  //     options: {
  //       symbolId: 'icon-[name]'
  //     }
  //   },
  // {
  //   test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
  //   loader: 'url-loader',
  //   exclude: [resolve('src/icons')],
  //   options: {
  //     limit: 10000,
  //     name: utils.assetsPath('img/[name].[hash:7].[ext]')
  //   }
  // },
  //   {
  //     test: /\.svg$/,
  //     loader: 'svg-sprite-loader',
  //     include: [resolve('src/icons')],
  //     options: {
  //       symbolId: 'icon-[name]'
  //     }
  //   }
  // ]
}

