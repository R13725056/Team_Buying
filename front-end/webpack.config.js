
const path = require('path');

module.exports = {
  // ...existing code...
  output: {
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/', // 確保這裡指向你的 public 資料夾
    filename: 'bundle.js',
  },
  // ...existing code...
  devServer: {
    contentBase: path.join(__dirname, 'public'), // 確保這裡指向你的 public 資料夾
    compress: true,
    port: 9000,
  },
  // ...existing code...
};