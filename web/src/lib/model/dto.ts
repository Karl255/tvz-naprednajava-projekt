export enum IssueType {
	DIRTY = 'DIRTY',
	FULL = 'FULL',
	LATE = 'LATE',
	CONSTRUCTION = 'CONSTRUCTION',
	TRAFFIC_ACCIDENT = 'TRAFFIC_ACCIDENT',
}

export interface StationDto {
	id: number;
	name: string;
}

export interface LineDto {
	id: number;
	name: string;
}

export interface PinDto {
	id: number;
	station?: StationDto;
	line?: LineDto;
	latitude: number;
	longitude: number;
	user: string;
}

export interface CommentDto {
	id: number;
	content: string;
	user: string;
	pin: PinDto;
	issueType: IssueType;
	/** datetime */
	createdAt: string;
	replies: CommentDto[];
}
