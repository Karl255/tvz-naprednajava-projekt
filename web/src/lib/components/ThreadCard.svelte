<script lang="ts">
	import type { PinDto } from '$lib/model/dto';

	interface Props {
		pin: PinDto;
	}

	const { pin }: Props = $props();
</script>

<div class="thread">
	<div class="header">
		<p class="username space-right">username {pin.user}</p>
		{@render chip(`Line ${pin.line?.name}`)}
		{@render chip(`Type`)}
	</div>

	<div class="content">
		<p class="space-right">Comment text.</p>
		<p class="timestamp">2 min ago</p>
	</div>

	<div class="actions">
		<p class="space-right">Uncollapse</p>
		<p>Reply</p>
	</div>
</div>

{#snippet chip(text: string)}
	<p class="chip">{text}</p>
{/snippet}

<style>
	.thread {
		background-color: var(--color-primary-light);
		padding: 15px;
		border-radius: var(--border-radius);

		display: flex;
		flex-direction: column;
		gap: 12px;
	}

	.header,
	.content,
	.actions {
		display: flex;
		flex-direction: row;
		align-items: baseline;
		gap: 5px;
	}

	.username {
		font-size: 20px;
		font-weight: 600;
	}

	.chip {
		padding: 4px 10px;
		background-color: var(--color-primary-dark);
		border-radius: var(--border-radius);
	}

	.timestamp {
		font-style: italic;
		font-size: 14px;
	}

	.space-right {
		margin-right: auto;
	}
</style>
