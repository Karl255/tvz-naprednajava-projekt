<script lang="ts">
	import { authApi } from '$lib/api/auth.api';
	import LoginModal from '$lib/components/LoginForm.svelte';
	import RegisterForm from '$lib/components/RegisterForm.svelte';

	let hasLoginError = $state(false);
	let hasRegisterError = $state(false);

	async function onLogin(username: string, password: string) {
		try {
			const loginDto = await authApi.login(username, password);
			console.log(loginDto);
		} catch {
			hasLoginError = true;
		}
	}

	async function onRegister(username: string, password: string) {
		try {
			await authApi.register(username, password);
		} catch {
			hasRegisterError = true;
		}
	}
</script>

<div class="container">
	<div class="logo">
		<img src="/di-je-zet-logo.svg" alt="Di je ZET" height="240" />
	</div>

	<div class="forms">
		<LoginModal {onLogin} hasError={hasLoginError} />
		<RegisterForm {onRegister} hasError={hasRegisterError} />
	</div>
</div>

<style>
	:global(html) {
		overflow: hidden;
	}

	.container {
		background-color: var(--color-primary);
		min-height: 100%;
		padding: 50px;

		display: grid;
		grid-template-rows: 1fr auto 1fr;
		justify-content: center;
		justify-items: center;
		gap: 50px;

		overflow: clip;
	}

	.logo {
		align-self: end;
	}

	.forms {
		display: flex;
		flex-direction: row;
		align-items: start;
		gap: 20px;
	}
</style>
