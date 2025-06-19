import { tokenStore } from '$lib/stores/token.store';
import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';

const { authApi } = await import('$lib/api/auth.api');

export const load = (async () => {
	const token = tokenStore.getRefreshToken();

	if (token === null) {
		return;
	}

	try {
		await authApi.refreshToken(token);
	} catch {
		return;
	}

	console.log('refreshed token, should redirect to app');
	redirect(302, '/app');
}) satisfies LayoutLoad;
