<script lang="ts">
	import { i18n, t } from '$lib/i18n';
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
	<p class="header">{$t('login')}</p>

	<div class="form-group">
		<label for="username">{$t('username')}</label>
		<input type="text" id="username" bind:value={username} />
	</div>

	<div class="form-group">
		<label for="password">{$t('password')}</label>
		<input type="password" id="password" bind:value={password} />
	</div>

	<Button variation={ButtonVariation.PRIMARY_LIGHT} onclick={login} disabled={!isValid}>{$t('login')}</Button>

	{#if hasError}
		<p class="form-error">{$t('failedToLogin')}</p>
	{/if}
</form>
