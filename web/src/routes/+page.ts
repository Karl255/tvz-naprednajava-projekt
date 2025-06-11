import type { PageLoad } from './$types';

const { pinApi } = await import('$lib/api/pin.api');

export const load = (async () => {
	const pins = await pinApi.findAll();

	return { pins };
}) satisfies PageLoad;
