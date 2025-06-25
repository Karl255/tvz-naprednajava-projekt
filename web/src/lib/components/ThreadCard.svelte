<script lang="ts">
	import type { CommentDto } from '$lib/model/dto';
	import { getReplyCount } from '$lib/utils/comment.utils';

	interface Props {
		thread: CommentDto;
		onThreadReply: (thread: CommentDto, comment: string) => void;
	}

	const { thread, onThreadReply: onSendReply }: Props = $props();

	let replyComment: string | null = $state(null);

	const replyCount = $derived(getReplyCount(thread));

	function sendReply() {
		if (replyComment === null || replyComment.length === 0) {
			return;
		}

		onSendReply(thread, replyComment);
	}
</script>

<div class="thread">
	<div class="card pin">
		<div class="header">
			<p class="username space-right">{thread.user}</p>
			{@render chip(`Line ${thread.pin.line?.name}`)}
			{@render chip(`Type`)}
		</div>

		<div class="content">
			<p class="space-right">{thread.content}</p>
			<p class="timestamp">{thread.createdAt}</p>
		</div>

		<div class="actions">
			<p class="space-right">
				{#if replyCount > 0}
					<button class="text">Expand {replyCount > 0 ? `(${replyCount})` : ''}</button>
				{/if}
			</p>
			<p>
				{#if replyComment === null}
					<button class="text" onclick={() => (replyComment = '')}>Reply</button>
				{:else}
					<button class="text" onclick={() => (replyComment = null)}>Cancel</button>
				{/if}
			</p>
		</div>
	</div>

	{#if replyComment !== null}
		<div class="card reply">
			<input type="text" bind:value={replyComment} />
			<button class="text" onclick={sendReply}>Send</button>
		</div>
	{/if}
</div>

{#snippet chip(text: string)}
	<p class="chip">{text}</p>
{/snippet}

<style>
	.thread {
		display: flex;
		flex-direction: column;
		gap: 5px;
	}

	.card {
		background-color: var(--color-primary-light);
		padding: 10px;
		border-radius: var(--border-radius);
	}

	.pin {
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

	.reply {
		display: flex;
		flex-direction: row;
		gap: 20px;

		> input {
			flex-grow: 1;

			padding: 2px;
		}
	}
</style>
