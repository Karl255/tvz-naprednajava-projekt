<script lang="ts">
	import { ButtonSize, type ButtonVariation } from '$lib/model/components';
	import type { Snippet } from 'svelte';
	import type { ClassValue } from 'svelte/elements';

	interface Props {
		variation: ButtonVariation;
		size?: ButtonSize;
		class?: ClassValue;
		children?: Snippet;
	}

	let { variation, size = ButtonSize.MEDIUM, class: className, children }: Props = $props();
</script>

<button class={['button', variation, size, className]}>{@render children?.()}</button>

<style>
	.button {
		padding: 10px 20px;
		border: none;
		cursor: pointer;

		:global {
			&.primary {
				color: white;
				background-color: var(--color-primary);
			}

			&.primary-dark {
				color: white;
				background-color: var(--color-primary-dark);
			}

			&.large {
				font-size: 32px;
				font-weight: 600;
			}
		}
	}
</style>
