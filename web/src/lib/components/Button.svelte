<script lang="ts">
	import { ButtonSize, type ButtonVariation } from '$lib/model/components';
	import type { Snippet } from 'svelte';
	import type { ClassValue } from 'svelte/elements';

	interface Props {
		variation: ButtonVariation;
		size?: ButtonSize;
		class?: ClassValue;
		onclick?: () => void;
		children?: Snippet;
	}

	let { variation, size = ButtonSize.MEDIUM, class: className, onclick, children }: Props = $props();
</script>

<button {onclick} class={['button', variation, size, className]}>{@render children?.()}</button>

<style>
	.button {
		padding: 10px 20px;
		background-color: var(--_bgc);
		box-shadow: var(--shadow);
		border: none;
		border-radius: var(--border-radius);
		cursor: pointer;

		transition:
			background-color 150ms ease-out,
			box-shadow 150ms ease-out;

		&:hover {
			background-color: lch(from var(--_bgc) calc(l - 10) c h);
			box-shadow: var(--shadow-hover);
		}

		:global {
			&.primary {
				color: white;
				--_bgc: var(--color-primary);
			}

			&.primary-dark {
				color: white;
				--_bgc: var(--color-primary-dark);
			}

			&.large {
				font-size: 32px;
				font-weight: 600;
			}
		}
	}
</style>
