<script lang="ts">
	import Self from './Reply.svelte';
	import type { CommentDto } from '$lib/model/dto';
	import { getMinutesAgoText } from '$lib/utils/time.utils';
	import { i18n } from '$lib/i18n';

	interface Props {
		comment: CommentDto;
		onCommentReply: (thread: CommentDto, comment: string) => Promise<void>;
	}

	const { comment, onCommentReply }: Props = $props();

	let replyComment: string | null = $state(null);
	let isReplyValid = $derived(replyComment !== null && (replyComment as string).length >= 5);

	async function sendReply() {
		if (replyComment === null || replyComment.length === 0) {
			return;
		}

		await onCommentReply(comment, replyComment);
		replyComment = null;
	}
</script>

<div class="subtree">
	<div class="comment">
		<div class="line">
			<p class="username">{comment.user}</p>
			<div class="timestamp">{getMinutesAgoText(comment.createdAt)}</div>
		</div>
		<div class="line">
			<p>{comment.content}</p>

			{#if replyComment === null}
				<button class="text small" onclick={() => (replyComment = '')}>{i18n.t('reply')}</button>
			{:else}
				<button class="text small" onclick={() => (replyComment = null)}>{i18n.t('cancel')}</button>
			{/if}
		</div>
	</div>

	{#if replyComment !== null}
		<div class="comment-reply">
			<input type="text" bind:value={replyComment} />
			<button class="text small" disabled={!isReplyValid} onclick={sendReply}>{i18n.t('send')}</button>
		</div>
	{/if}

	{#each comment.replies as reply (reply.id)}
		<div class="sub-reply">
			<Self comment={reply} {onCommentReply} />
		</div>
	{/each}
</div>

<style>
	.subtree {
		display: flex;
		flex-direction: column;
		gap: 5px;
	}

	.comment {
		display: flex;
		flex-direction: column;
		gap: 5px;

		margin-left: 10px;
		padding: 5px 5px 5px 24px;

		position: relative;

		&::before {
			content: '';
			position: absolute;

			inset: 0 auto 0 0;
			width: 4px;
			border-radius: 999px;

			background-color: var(--color-background);
		}

		&::after {
			content: '';
			position: absolute;
			inset: 50% auto auto 4px;
			translate: 0 -50%;

			border-left: 4px solid var(--color-background);
			border-top: 4px solid transparent;
			border-bottom: 4px solid transparent;
		}
	}

	.line {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}

	.username {
		font-size: 20px;
		font-weight: 600;
	}

	.timestamp {
		font-style: italic;
		font-size: 14px;
	}

	.comment-reply {
		margin-inline: 34px 5px;

		display: flex;
		flex-direction: row;
		gap: 20px;

		> input {
			flex-grow: 1;
			padding: 2px;
		}
	}

	.sub-reply {
		margin-left: 24px;
	}

	button.small {
		font-size: 14px;
	}
</style>
