import type { LayoutLoad } from './$types';

const { authService } = await import('$lib/services/auth.service');

export const load = (async () => {
	try {
		const authenticated = await authService.refreshToken();

		if (!authenticated) {
			authService.redirectToApp();
		}
	} catch {
		//
	}
}) satisfies LayoutLoad;
