import { i18n } from '$lib/i18n';
import { IssueType } from '$lib/model/dto';

export const ISSUE_TYPES: { value: IssueType; text: string }[] = [
	{ value: IssueType.DIRTY, text: i18n.t('dirty') },
	{ value: IssueType.FULL, text: i18n.t('full') },
	{ value: IssueType.LATE, text: i18n.t('late') },
	{ value: IssueType.CONSTRUCTION, text: i18n.t('construction') },
	{ value: IssueType.TRAFFIC_ACCIDENT, text: i18n.t('trafficAccident') },
];
