<script lang="ts">
	import { ButtonVariation } from '$lib/model/components';
	import Button from './Button.svelte';

	interface Props {
		hasError: boolean;
		onRegister: (username: string, password: string) => void;
	}

	const { hasError, onRegister }: Props = $props();

	let username: string = $state('');
	let password: string = $state('');
	let repeatedPassword: string = $state('');
	let isValid: boolean = $derived(username.length >= 8 && password.length >= 8 && password === repeatedPassword);

	function register() {
		onRegister(username, password);
	}
</script>

<form class="form">
	<p class="header">Register</p>

	<div class="form-group">
		<label for="username">Username</label>
		<input type="text" id="username" bind:value={username} />
	</div>

	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" id="password" bind:value={password} />
	</div>

	<div class="form-group">
		<label for="password">Repeat password</label>
		<input type="password" id="password" bind:value={repeatedPassword} />
	</div>

	<Button variation={ButtonVariation.SECONDARY} onclick={register} disabled={!isValid}>Register</Button>

	{#if hasError}
		<p class="form-error">Failed to register</p>
	{/if}
</form>
