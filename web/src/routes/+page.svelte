<script lang="ts">
	import Button from '$lib/components/Button.svelte';
	import PinList from '$lib/components/PinList.svelte';
	import PinMarker from '$lib/components/PinMarker.svelte';
	import { ButtonSize, ButtonVariation } from '$lib/model/components';
	import { type LngLat, type MapMouseEvent } from 'maplibre-gl';
	import { MapLibre } from 'svelte-maplibre';
	import type { PageProps } from './$types';
	import type { PinDto } from '$lib/model/dto';
	import { pinToLngLat } from '$lib/utils/model';
	import { pinApi } from '$lib/api/pin.api';

	const { data }: PageProps = $props();

	const pins: PinDto[] = $state(data.pins);

	let selectedLocation: LngLat | null = $state(null);

	function selectLocation(e: MapMouseEvent) {
		if (selectedLocation === null) {
			selectedLocation = e.lngLat;
		} else {
			selectedLocation = null;
		}
	}

	async function reportIssue() {
		if (selectedLocation === null) {
			return;
		}

		const newPin = {
			longitude: selectedLocation.lng,
			latitude: selectedLocation.lat,
		};

		const pin = await pinApi.create(newPin);
		selectedLocation = null;
		pins.push(pin);
	}
</script>

<div class="container">
	<div class="stack">
		<MapLibre
			class="maplibre"
			style="/map-styles/osm-liberty.json"
			standardControls="bottom-left"
			center={[15.985, 45.8]}
			zoom={12}
			dragRotate={false}
			pitchWithRotate={false}
			attributionControl={false}
			zoomOnDoubleClick={false}
			onclick={selectLocation}
		>
			{#each pins as pin (pin)}
				<PinMarker lngLat={pinToLngLat(pin)} color="var(--color-primary)" />
			{/each}

			{#if selectedLocation !== null}
				<PinMarker lngLat={selectedLocation} />
			{/if}
		</MapLibre>

		<Button variation={ButtonVariation.PRIMARY_DARK} size={ButtonSize.LARGE} class="report-issue" onclick={reportIssue}
			>+ Report issue</Button
		>
	</div>

	<PinList></PinList>
</div>

<style>
	.container {
		height: 100%;
		display: grid;
		grid-template-columns: 1fr min(480px, 40%);
	}

	.stack {
		position: relative;

		display: grid;

		> :global(*) {
			grid-area: 1 / 1;
		}
	}

	:global(.maplibre) {
		--_: 'empty';
	}

	:global(.report-issue) {
		position: absolute;
		inset: auto 2rem 2rem auto;
	}
</style>
