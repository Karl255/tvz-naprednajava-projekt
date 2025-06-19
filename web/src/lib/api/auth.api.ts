import { API_PATH } from '$lib/constants/api';
import type { LoginDto } from '$lib/model/auth-dto';
import { ApiClient } from './api-client';

interface UserRequest {
	username: string;
	password: string;
}

const LOGIN_ENDPOINT = '/login';
const REGISTER_ENDPOINT = '/register';

class AuthApi {
	private readonly client = new ApiClient(API_PATH.AUTH);

	async login(username: string, password: string): Promise<LoginDto> {
		const data: UserRequest = { username, password };

		return await this.client.post<LoginDto>(LOGIN_ENDPOINT, data);
	}

	async register(username: string, password: string): Promise<void> {
		const data: UserRequest = { username, password };

		await this.client.post(REGISTER_ENDPOINT, data);
	}
}

export const authApi = new AuthApi();
