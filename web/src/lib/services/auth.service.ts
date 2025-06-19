import { goto } from '$app/navigation';
import { authApi } from '$lib/api/auth.api';
import { userApi } from '$lib/api/user.api';
import type { UserDto } from '$lib/model/auth-dto';
import { tokenStore } from '$lib/stores/token.store';
import { redirect } from '@sveltejs/kit';

class AuthService {
	async refreshToken(): Promise<boolean> {
		const token = tokenStore.getRefreshToken();

		if (token === null) {
			return false;
		}

		try {
			await authApi.refreshToken(token);
		} catch {
			return false;
		}

		return true;
	}

	async getCurrentUser(): Promise<UserDto | null> {
		try {
			return await userApi.getCurrent();
		} catch {
			return null;
		}
	}

	redirectToLogin(): never {
		redirect(302, '/');
	}

	redirectToApp() {
		redirect(302, '/app');
	}

	gotoApp() {
		goto('/app');
	}
}

export const authService = new AuthService();
