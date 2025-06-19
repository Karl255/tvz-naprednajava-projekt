export enum UserRole {
	OPERATOR = 'OPERATOR',
	USER = 'USER',
}

export interface UserDto {
	id: number;
	username: string;
	role: UserRole;
}

export interface LoginDto {
	user: UserDto;
	refreshToken: string;
}
