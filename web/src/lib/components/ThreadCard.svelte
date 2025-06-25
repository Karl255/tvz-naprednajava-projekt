<script lang="ts">
	import { ISSUE_TYPES } from '$lib/constants/issue-types';
	import type { CommentDto } from '$lib/model/dto';
	import { getReplyCount } from '$lib/utils/comment.utils';
	import { getMinutesAgoText } from '$lib/utils/time.utils';
	import Reply from './Reply.svelte';
	import { i18n } from '$lib/i18n';

	interface Props {
		thread: CommentDto;
		onThreadReply: (thread: CommentDto, comment: string) => Promise<void>;
	}

	const { thread, onThreadReply: onSendReply }: Props = $props();
	const type = ISSUE_TYPES.find(type => type.value === thread.issueType);

	let isExpanded = $state(false);
	let replyComment: string | null = $state(null);
	let isReplyValid = $derived(replyComment !== null && (replyComment as string).length >= 5);

	const replyCount = $derived(getReplyCount(thread));

	async function sendReply() {
		if (replyComment === null || replyComment.length === 0) {
			return;
		}

		await onSendReply(thread, replyComment);
		replyComment = null;
	}
</script>

<div class="thread">
	<div class="card pin">
		<div class="header">
			<p class="username space-right">{thread.user}</p>
			{@render chip(i18n.t('lineNumber', { lineNumber: thread.pin.line?.name }))}
			{@render chip(type?.text ?? '')}
		</div>

		<div class="content">
			<p class="space-right">{thread.content}</p>
			<p class="timestamp">{getMinutesAgoText(thread.createdAt)}</p>
		</div>

		<div class="actions">
			<p class="space-right">
				{#if replyCount > 0}
					{@const buttonText = isExpanded ? i18n.t('collapse') : i18n.t('expand')}

					<button class="text" onclick={() => (isExpanded = !isExpanded)}>
						{buttonText}
						{replyCount > 0 ? `(${replyCount})` : ''}
					</button>
				{/if}
			</p>
			<p>
				{#if replyComment === null}
					<button class="text" onclick={() => (replyComment = '')}>{i18n.t('reply')}</button>
				{:else}
					<button class="text" onclick={() => (replyComment = null)}>{i18n.t('cancel')}</button>
				{/if}
			</p>
		</div>
	</div>

	{#if replyComment !== null}
		<div class="card reply">
			<input type="text" bind:value={replyComment} />
			<button class="text" disabled={!isReplyValid} onclick={sendReply}>{i18n.t('send')}</button>
		</div>
	{/if}

	{#if isExpanded}
		<div class="card replies">
			{#each thread.replies as reply (reply.id)}
				<Reply comment={reply} onCommentReply={onSendReply} />
			{/each}
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

	.replies {
		display: flex;
		flex-direction: column;
		gap: 7px;
	}
</style>
