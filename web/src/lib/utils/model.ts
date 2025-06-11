import type { PinDto } from '$lib/model/dto';
import { LngLat } from 'maplibre-gl';

export function pinToLngLat(pin: PinDto): LngLat {
	return new LngLat(pin.longitude, pin.latitude);
}
