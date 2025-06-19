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

		color: var(--_clr);
		background-color: var(--_bgc);
		border: none;
		border-color: var(--_border);
		border-radius: var(--border-radius);

		font-size: 20px;
		font-weight: 600;

		cursor: pointer;

		transition:
			color 150ms ease-out,
			background-color 150ms ease-out,
			border-color 150ms ease-out,
			box-shadow 150ms ease-out;

		&.disabled {
			pointer-events: none;
			color: lch(from var(--_clr) l calc(c - 20) h / alpha);
			background-color: lch(from var(--_bgc) l calc(c - 20) h / alpha);
			border-color: lch(from var(--_border) l calc(c - 20) h / alpha);
		}

		&:hover {
			color: lch(from var(--_clr) calc(l + 10) c h / alpha);
			background-color: lch(from var(--_bgc) calc(l + 10) c h / alpha);
			border-color: lch(from var(--_border) calc(l + 10) c h / alpha);
			box-shadow: var(--shadow-hover);
		}

		:global {
			&.primary {
				--_clr: white;
				--_bgc: var(--color-primary);
			}

			&.primary-dark {
				--_clr: white;
				--_bgc: var(--color-primary-dark);
			}

			&.primary-light {
				--_clr: var(--color-primary-dark);
				--_bgc: var(--color-primary-light);
			}

			&.secondary {
				--_clr: var(--color-primary-light);
				--_bgc: transparent;
				--_border: var(--color-primary-light);
				border-style: solid;
				border-width: 2px;
			}

			&.large {
				font-size: 32px;
				box-shadow: var(--shadow);
				font-weight: 600;
			}
		}
	}
</style>
