<script lang="ts">
	import Button from '$lib/components/Button.svelte';
	import PinList from '$lib/components/PinList.svelte';
	import PinMarker from '$lib/components/PinMarker.svelte';
	import { ButtonSize, ButtonVariation } from '$lib/model/components';
	import { type LngLatLike, type MapMouseEvent } from 'maplibre-gl';
	import { MapLibre } from 'svelte-maplibre';

	const pins: [number, number][] = $state([[15.985, 45.8]]);

	let selectedLocation: LngLatLike | null = $state(null);

	function selectLocation(e: MapMouseEvent) {
		if (selectedLocation === null) {
			selectedLocation = e.lngLat;

			pins.push(e.lngLat.toArray());
		} else {
			selectedLocation = null;
		}
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
				<PinMarker lngLat={pin} color="var(--color-primary)" />
			{/each}
			{#if selectedLocation !== null}
				<PinMarker lngLat={selectedLocation} />
			{/if}
		</MapLibre>

		<Button variation={ButtonVariation.PRIMARY_DARK} size={ButtonSize.LARGE} class="report-issue">+ Report issue</Button
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
