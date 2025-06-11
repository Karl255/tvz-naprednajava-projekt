import { API_PATH } from '$lib/constants/api';
import type { PinDto } from '$lib/model/dto';
import { ApiClient } from './api-client';

interface PinRequest {
	stationId?: number;
	lineId?: number;
	latitude: number;
	longitude: number;
}

class PinApi {
	private readonly client = new ApiClient(API_PATH.PIN);

	async findAll(): Promise<PinDto[]> {
		return await this.client.get<PinDto[]>();
	}

	async create(pin: PinRequest): Promise<PinDto> {
		return await this.client.post<PinDto>(undefined, pin);
	}

	async update(pin: PinDto): Promise<PinDto> {
		const pinData: PinRequest = {
			stationId: pin.station?.id,
			lineId: pin.line?.id,
			latitude: pin.latitude,
			longitude: pin.longitude,
		};

		return await this.client.put<PinDto>(`/${pin.id}`, pinData);
	}

	async delete(pinId: number): Promise<void> {
		await this.client.delete(`/${pinId}`);
	}
}

export const pinApi = new PinApi();
