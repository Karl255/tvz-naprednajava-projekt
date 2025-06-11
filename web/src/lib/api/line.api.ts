import { API_PATH } from '$lib/constants/api';
import type { LineDto } from '$lib/model/dto';
import { ApiClient } from './api-client';

class LineApi {
	private readonly client = new ApiClient(API_PATH.LINE);

	async findAll(): Promise<LineDto[]> {
		return await this.client.get<LineDto[]>();
	}

	async create(name: string): Promise<LineDto> {
		return await this.client.post<LineDto>(undefined, { name });
	}

	async update(line: LineDto): Promise<LineDto> {
		return await this.client.put<LineDto>(`/${line.id}`, { name: line.name });
	}

	async delete(lineId: number): Promise<void> {
		await this.client.delete(`/${lineId}`);
	}
}

export const lineApi = new LineApi();
