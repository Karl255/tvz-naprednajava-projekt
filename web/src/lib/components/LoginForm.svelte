<script lang="ts">
	import { i18n } from '$lib/i18n';
	import { ButtonVariation } from '$lib/model/components';
	import Button from './Button.svelte';

	interface Props {
		hasError: boolean;
		onLogin: (username: string, password: string) => void;
	}

	const { hasError, onLogin }: Props = $props();

	let username: string = $state('');
	let password: string = $state('');
	let isValid: boolean = $derived(username.length > 0 && password.length > 0);

	function login() {
		if (isValid) {
			onLogin(username, password);
		}
	}
</script>

<form class="form">
	<p class="header">{i18n.t('login')}</p>

	<div class="form-group">
		<label for="username">{i18n.t('username')}</label>
		<input type="text" id="username" bind:value={username} />
	</div>

	<div class="form-group">
		<label for="password">{i18n.t('password')}</label>
		<input type="password" id="password" bind:value={password} />
	</div>

	<Button variation={ButtonVariation.PRIMARY_LIGHT} onclick={login} disabled={!isValid}>{i18n.t('login')}</Button>

	{#if hasError}
		<p class="form-error">{i18n.t('failedToLogin')}</p>
	{/if}
</form>
