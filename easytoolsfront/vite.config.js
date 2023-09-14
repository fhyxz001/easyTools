import vue from '@vitejs/plugin-vue'
export default {
    plugins: [vue()],
    resolve: {
        alias: {
            '@': '/src' // 根据你的项目目录结构进行调整
        }
    },
    server:{
        proxy:{
            '/api': {
                // target: 'http://localhost:8081',
                target: 'http://10.249.57.84:8081',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '')
            }
        }
    }
}
