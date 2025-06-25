import type { CommentDto } from '$lib/model/dto';

export function getReplyCount(comment: CommentDto): number {
	return flattenComments(comment).length - 1;
}

function flattenComments(comment: CommentDto): CommentDto[] {
	return [comment, ...comment.replies.flatMap(flattenComments)];
}
