<script lang="ts">
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
	<p class="header">Login</p>

	<div class="form-group">
		<label for="username">Username</label>
		<input type="text" id="username" bind:value={username} />
	</div>

	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" id="password" bind:value={password} />
	</div>

	<Button variation={ButtonVariation.PRIMARY_LIGHT} onclick={login} disabled={!isValid}>Login</Button>

	{#if hasError}
		<p class="form-error">Failed to login</p>
	{/if}
</form>
