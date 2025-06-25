import { i18n } from '$lib/i18n';
import { Temporal } from '@js-temporal/polyfill';

function getMinutesAgo(isoTimestamp: string): number {
	const timestamp = Temporal.PlainDateTime.from(isoTimestamp);
	const now = Temporal.Now.plainDateTimeISO();

	const duration = timestamp.until(now);
	return duration.round({ largestUnit: 'minutes' }).minutes;
}

export function getMinutesAgoText(isoTimestamp: string): string {
	const minutesAgo = getMinutesAgo(isoTimestamp);

	return minutesAgo === 0 ? 'Now' : i18n.t('minAgo', { minutes: minutesAgo });
}
