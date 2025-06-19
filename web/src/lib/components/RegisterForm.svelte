<script lang="ts">
	import { ButtonVariation } from '$lib/model/components';
	import Button from './Button.svelte';

	let username: string = $state('');
	let password: string = $state('');
	let repeatedPassword: string = $state('');
	let isValid: boolean = $derived(username.length > 0 && password.length > 0 && password === repeatedPassword);

	function register() {}
</script>

<form class="form">
	<p class="header">Register</p>

	<div class="form-group">
		<label for="username">Username</label>
		<input type="text" id="username" bind:value={username} />
	</div>

	<div class="form-group">
		<label for="password">Password</label>
		<input type="text" id="password" bind:value={password} />
	</div>

	<div class="form-group">
		<label for="password">Repeat password</label>
		<input type="text" id="password" bind:value={repeatedPassword} />
	</div>

	<Button variation={ButtonVariation.SECONDARY} onclick={register} disabled={!isValid}>Register</Button>
</form>
