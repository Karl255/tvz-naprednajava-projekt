const REFRESH_TOKEN = 'REFRESH_TOKEN';

class AuthTokenStore {
	setRefreshToken(token: string) {
		localStorage.setItem(REFRESH_TOKEN, token);
	}

	getRefreshToken(): string | null {
		return localStorage.getItem(REFRESH_TOKEN);
	}
}

export const tokenStore = new AuthTokenStore();
