<script lang="ts">
	import { ButtonSize, type ButtonVariation } from '$lib/model/components';
	import type { Snippet } from 'svelte';
	import type { ClassValue } from 'svelte/elements';

	interface Props {
		variation: ButtonVariation;
		size?: ButtonSize;
		disabled?: boolean;
		class?: ClassValue;
		onclick?: () => void;
		children?: Snippet;
	}

	let { variation, size = ButtonSize.MEDIUM, disabled = false, class: className, onclick, children }: Props = $props();
</script>

<button {onclick} class={['button', variation, size, className, disabled && 'disabled']}>{@render children?.()}</button>

<style>
	.button {
		padding: 0.3em 0.6em;
		background-color: var(--_bgc);
		border: none;
		border-radius: var(--border-radius);
		cursor: pointer;

		font-size: 20px;
		font-weight: 600;

		transition:
			background-color 150ms ease-out,
			box-shadow 150ms ease-out;

		&.disabled {
			pointer-events: none;
			background-color: lch(from var(--_bgc) l calc(c - 20) h);
		}

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

			&.primary-light {
				color: var(--color-primary-dark);
				--_bgc: var(--color-primary-light);
			}

			&.large {
				font-size: 32px;
				box-shadow: var(--shadow);
				font-weight: 600;
			}
		}
	}
</style>
