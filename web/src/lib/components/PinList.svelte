<script lang="ts">
	import type { Snippet } from 'svelte';

	interface Props {
		children?: Snippet;
	}

	const { children }: Props = $props();
</script>

<div class="container">{@render children?.()}</div>

<style>
	.container {
		background-color: var(--color-primary);
	}
</style>
