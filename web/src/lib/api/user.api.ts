import { API_PATH } from '$lib/constants/api';
import type { UserDto } from '$lib/model/auth-dto';
import { ApiClient } from './api-client';

class UserApi {
	private readonly client = new ApiClient(API_PATH.USER);

	async getAll(): Promise<UserDto[]> {
		return await this.client.get<UserDto[]>();
	}

	async getCurrent(): Promise<UserDto> {
		return await this.client.get<UserDto>('/profile');
	}
}

export const userApi = new UserApi();
