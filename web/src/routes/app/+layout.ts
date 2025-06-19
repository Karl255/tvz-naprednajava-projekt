import type { UserDto } from '$lib/model/auth-dto';
import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';

const { userApi } = await import('$lib/api/user.api');
const { lineApi } = await import('$lib/api/line.api');
const { stationApi } = await import('$lib/api/station.api');

export const load = (async () => {
	let user: UserDto | null = null;

	try {
		user = await userApi.getCurrent();
	} catch {
		redirect(302, '/');
	}

	const lines = await lineApi.findAll();
	const stations = await stationApi.findAll();

	return { user, lines, stations };
}) satisfies LayoutLoad;
