import { Temporal } from '@js-temporal/polyfill';

export function getMinutesAgo(isoTimestamp: string): number {
	const timestamp = Temporal.PlainDateTime.from(isoTimestamp);
	const now = Temporal.Now.plainDateTimeISO();

	const duration = timestamp.until(now);
	return duration.round({ largestUnit: 'minutes' }).minutes;
}
