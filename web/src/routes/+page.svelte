<script lang="ts">
	import PinList from '$lib/components/PinList.svelte';
	import { DefaultMarker, MapLibre } from 'svelte-maplibre';

	type Pin = [number, number];

	const pins: Pin[] = [[15.985, 45.8]];
</script>

<div class="map-container">
	<MapLibre
		class="maplibre"
		style="/map-styles/osm-liberty.json"
		standardControls="bottom-left"
		center={[15.985, 45.8]}
		zoom={12}
		dragRotate={false}
		pitchWithRotate={false}
	>
		{#each pins as pin (pin)}
			<DefaultMarker lngLat={pin} />
		{/each}
	</MapLibre>

	<PinList />
</div>

<style>
	.map-container {
		height: 100%;
		display: grid;
		grid-template-columns: 1fr min(480px, 40%);
	}

	:global(.maplibre) {
		--_: 'empty';
	}
</style>
