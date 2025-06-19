<script lang="ts">
	import LoginModal from '$lib/components/LoginForm.svelte';
</script>

<div class="container">
	<div class="logo">
		<img src="/di-je-zet-logo.svg" alt="Di je ZET" height="240" />
	</div>

	<div class="modals">
		<LoginModal />
	</div>

	<div>extra</div>
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
</style>
