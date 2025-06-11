import { API_PATH } from '$lib/constants/api';
import type { StationDto } from '$lib/model/dto';
import { ApiClient } from './api-client';

class StationApi {
	private readonly client = new ApiClient(API_PATH.STATION);

	async findAll(): Promise<StationDto[]> {
		return await this.client.get<StationDto[]>();
	}

	async create(name: string): Promise<StationDto> {
		return await this.client.post<StationDto>(undefined, { name });
	}

	async update(station: StationDto): Promise<StationDto> {
		return await this.client.put<StationDto>(`/${station.id}`, { name: station.name });
	}

	async delete(stationId: number): Promise<void> {
		await this.client.delete(`/${stationId}`);
	}
}

export const stationApi = new StationApi();
