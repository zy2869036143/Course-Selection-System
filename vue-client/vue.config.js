module.exports = {
    devServer: {
        hot: true,
        disableHostCheck: true,
        https: false,
        proxy: { 
          '^/api': 
            {
                target: 'http://localhost:9091',
                changeOrigin: true
            },
        
      },
    }
  }