<script lang="ts">
	import Button from '$lib/components/Button.svelte';
	import PinList from '$lib/components/PinList.svelte';
	import { ButtonSize, ButtonVariation } from '$lib/model/components';
	import { type LngLat, type MapMouseEvent, type LngLatLike } from 'maplibre-gl';
	import { DefaultMarker, MapLibre } from 'svelte-maplibre';

	const pins: LngLatLike[] = [[15.985, 45.8]];

	let selectedLocation: LngLat | null = $state(null);

	function selectLocation(e: MapMouseEvent) {
		selectedLocation = e.lngLat;
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
			onclick={selectLocation}
		>
			{#each pins as pin (pin)}
				<DefaultMarker lngLat={pin} />
			{/each}
			{#if selectedLocation !== null}
				<DefaultMarker lngLat={selectedLocation} />
			{/if}
		</MapLibre>

		<Button variation={ButtonVariation.PRIMARY_DARK} size={ButtonSize.LARGE} class="report-issue">+ Report issue</Button
		>
	</div>

	<PinList />
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
