import type { PageLoad } from './$types';

const { pinApi } = await import('$lib/api/pin.api');
const { commentApi } = await import('$lib/api/comment.api');

export const load = (async () => {
	const pins = await pinApi.findAll();
	const topComments = await commentApi.findAllTopComments();

	return { pins, topComments };
}) satisfies PageLoad;
