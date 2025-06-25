<script lang="ts">
	import type { PinDto } from '$lib/model/dto';
	import ThreadCard from './ThreadCard.svelte';

	interface Props {
		pins: PinDto[];
	}

	const { pins }: Props = $props();
</script>

<div class="container">
	<div class="threads">
		{#each pins as pin (pin.id)}
			<ThreadCard {pin} />
		{/each}
	</div>
</div>

<style>
	.container {
		background-color: var(--color-primary);
		padding: 40px;
		color: var(--color-text-inverse);
	}

	.threads {
		display: flex;
		flex-direction: column;
		gap: 25px;
	}
</style>
