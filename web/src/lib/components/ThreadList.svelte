<script lang="ts">
	import type { CommentDto } from '$lib/model/dto';
	import ThreadCard from './ThreadCard.svelte';

	interface Props {
		threads: CommentDto[];
		onThreadReply: (thread: CommentDto, comment: string) => void;
	}

	const { threads, onThreadReply }: Props = $props();
</script>

<div class="container">
	<div class="threads">
		{#each threads as thread (thread.id)}
			<ThreadCard {thread} {onThreadReply} />
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
