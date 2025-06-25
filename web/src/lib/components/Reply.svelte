<script lang="ts">
	import type { CommentDto } from '$lib/model/dto';

	interface Props {
		comment: CommentDto;
	}

	const { comment }: Props = $props();
</script>

<div class="reply">
	<div class="content">
		<p class="username">{comment.user}</p>
		<p>{comment.content}</p>
	</div>
</div>

<style>
	.reply {
		display: flex;
		flex-direction: row;
		gap: 10px;
	}

	.content {
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

	.username {
		font-size: 20px;
		font-weight: 600;
	}
</style>
