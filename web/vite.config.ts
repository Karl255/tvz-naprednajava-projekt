import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	build: {
		target: 'esnext'
	},
	server: {
		proxy: {
			'/di-je-zet/api': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				secure: false,
			},
		},
	},
});
