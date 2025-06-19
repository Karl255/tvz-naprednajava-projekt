import { API_PATH } from '$lib/constants/api';
import type { LoginDto } from '$lib/model/auth-dto';
import { ApiClient } from './api-client';

interface UserRequest {
	username: string;
	password: string;
}

interface RefreshTokenRequest {
	token: string;
}

const LOGIN_ENDPOINT = '/login';
const REGISTER_ENDPOINT = '/register';
const REFRESH_TOKEN_ENDPOINT = '/refresh-token';

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

	async refreshToken(token: string): Promise<void> {
		const data: RefreshTokenRequest = { token };

		await this.client.post(REFRESH_TOKEN_ENDPOINT, data);
	}
}

export const authApi = new AuthApi();
