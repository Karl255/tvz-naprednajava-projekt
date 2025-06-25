import type { LayoutLoad } from './$types';

const { authService } = await import('$lib/services/auth.service');
const { lineApi } = await import('$lib/api/line.api');
const { stationApi } = await import('$lib/api/station.api');

export const load = (async () => {
	const user = await authService.getCurrentUser();

	if (user === null) {
		authService.redirectToLogin();
	}

	const lines = await lineApi.findAll();
	const stations = await stationApi.findAll();

	return { user, lines, stations };
}) satisfies LayoutLoad;
