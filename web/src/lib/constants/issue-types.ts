import { IssueType } from '$lib/model/dto';

export const ISSUE_TYPES: { value: IssueType; text: string }[] = [
	{ value: IssueType.DIRTY, text: 'Dirty' },
	{ value: IssueType.FULL, text: 'Full' },
	{ value: IssueType.LATE, text: 'Late' },
	{ value: IssueType.CONSTRUCTION, text: 'Construction' },
	{ value: IssueType.TRAFFIC_ACCIDENT, text: 'Traffic accident' },
];
