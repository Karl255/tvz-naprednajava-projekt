import type { PageLoad } from './$types';

const { commentApi } = await import('$lib/api/comment.api');

export const load = (async () => {
	const topComments = await commentApi.findAllTopComments();

	return { topComments };
}) satisfies PageLoad;
