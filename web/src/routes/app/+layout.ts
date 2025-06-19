import type { LayoutLoad } from './$types';

const { lineApi } = await import('$lib/api/line.api');
const { stationApi } = await import('$lib/api/station.api');

export const load = (async () => {
	const lines = await lineApi.findAll();
	const stations = await stationApi.findAll();

	return { lines, stations };
}) satisfies LayoutLoad;
