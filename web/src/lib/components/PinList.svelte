<script lang="ts">
	import { locale } from '$lib/i18n';
	import type { PinDto } from '$lib/model/dto';
	import { onDestroy } from 'svelte';
	import ThreadCard from './ThreadCard.svelte';

	interface Props {
		pins: PinDto[];
	}

	const { pins }: Props = $props();

	let currentLocale;
	const unsubscribe = locale.subscribe(value => {
		currentLocale = value;
	});

	onDestroy(unsubscribe);
</script>

<div class="container">
	<div>
		<button onclick={() => locale.set('en')}>EN</button>
		<button onclick={() => locale.set('hr')}>HR</button>
	</div>

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
