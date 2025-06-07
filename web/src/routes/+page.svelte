<script lang="ts">
	import PinList from '$lib/components/PinList.svelte';
	import { LngLat, MapMouseEvent, type LngLatLike } from 'maplibre-gl';
	import { DefaultMarker, MapLibre } from 'svelte-maplibre';

	const pins: LngLatLike[] = [[15.985, 45.8]];

	let selectedLocation: LngLat | null = $state(null);

	function selectLocation(e: MapMouseEvent) {
		selectedLocation = e.lngLat;
	}
</script>

<div class="container">
	<MapLibre
		class="maplibre"
		style="/map-styles/osm-liberty.json"
		standardControls="bottom-left"
		center={[15.985, 45.8]}
		zoom={12}
		dragRotate={false}
		pitchWithRotate={false}
		onclick={selectLocation}
	>
		{#each pins as pin (pin)}
			<DefaultMarker lngLat={pin} />
		{/each}
		{#if selectedLocation !== null}
			<DefaultMarker lngLat={selectedLocation} />
		{/if}
	</MapLibre>

	<PinList />
</div>

<style>
	.container {
		height: 100%;
		display: grid;
		grid-template-columns: 1fr min(480px, 40%);
	}

	:global(.maplibre) {
		--_: 'empty';
	}
</style>
