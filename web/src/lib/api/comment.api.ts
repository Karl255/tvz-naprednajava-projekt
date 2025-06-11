import { API_PATH } from '$lib/constants/api';
import type { CommentDto, IssueType } from '$lib/model/dto';
import { ApiClient } from './api-client';

interface CommentRequest {
	content: string;
	pinId: number;
	parentId?: number;
	issueType: IssueType;
}

class CommentApi {
	private readonly client = new ApiClient(API_PATH.COMMENT);

	async findAll(): Promise<CommentDto[]> {
		return await this.client.get<CommentDto[]>();
	}

	async create(content: string, pinId: number, issueType: IssueType, parentId?: number): Promise<CommentDto> {
		const commentRequest: CommentRequest = {
			content,
			pinId,
			issueType,
			parentId,
		};

		return await this.client.post<CommentDto>(undefined, commentRequest);
	}

	async update(comment: CommentDto): Promise<CommentDto> {
		const commentRequest: CommentRequest = {
			content: comment.content,
			pinId: comment.pin.id,
			issueType: comment.issueType,
			//parentId: comment.parentId, // TODO: fix
		};

		return await this.client.put<CommentDto>(`/${comment.id}`, commentRequest);
	}

	async delete(commentId: number): Promise<void> {
		await this.client.delete(`/${commentId}`);
	}
}

export const commentApi = new CommentApi();
