<script lang="ts">
	import { getLocale, LOCALES, setLocale } from '$lib/i18n';
	import { ButtonSize, ButtonVariation } from '$lib/model/components';
	import Button from './Button.svelte';

	interface Props {
		buttonVariation?: ButtonVariation;
	}

	const { buttonVariation = ButtonVariation.PRIMARY_LIGHT }: Props = $props();

	const currentLocale = getLocale();
</script>

<div class="locales">
	{#each LOCALES as locale (locale)}
		<Button
			variation={buttonVariation}
			size={ButtonSize.SMALL}
			disabled={locale === currentLocale}
			onclick={() => setLocale(locale)}
		>
			{locale.toUpperCase()}
		</Button>
	{/each}
</div>

<style>
	.locales {
		display: flex;
		flex-direction: row;
		gap: 5px;
	}
</style>
