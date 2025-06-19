import type { LayoutLoad } from './$types';

const { authService } = await import('$lib/services/auth.service');

export const load = (async () => {
	const authenticated = await authService.refreshToken();

	if (!authenticated) {
		authService.redirectToApp();
	}
}) satisfies LayoutLoad;
