<script lang="ts">
	import Button from '$lib/components/Button.svelte';
	import PinList from '$lib/components/ThreadList.svelte';
	import PinMarker from '$lib/components/PinMarker.svelte';
	import { ButtonSize, ButtonVariation } from '$lib/model/components';
	import { type LngLat, type MapMouseEvent } from 'maplibre-gl';
	import { MapLibre } from 'svelte-maplibre';
	import type { PageProps } from './$types';
	import { IssueType, type CommentDto, type LineDto, type StationDto } from '$lib/model/dto';
	import { pinToLngLat } from '$lib/utils/model';
	import { pinApi } from '$lib/api/pin.api';
	import ReportIssueModal from '$lib/components/ReportIssueModal.svelte';
	import { commentApi } from '$lib/api/comment.api';

	const { data }: PageProps = $props();

	const threads: CommentDto[] = $state(data.topComments);

	let selectedLocation: LngLat | null = $state(null);
	let reportIssueModal: ReturnType<typeof ReportIssueModal>;

	function selectLocation(e: MapMouseEvent) {
		if (selectedLocation === null) {
			selectedLocation = e.lngLat;
		} else {
			selectedLocation = null;
		}
	}

	async function reportIssue(station: StationDto, line: LineDto, comment: string) {
		if (selectedLocation === null) {
			return;
		}

		const newPin = {
			longitude: selectedLocation.lng,
			latitude: selectedLocation.lat,
			stationId: station.id,
			lineId: line.id,
		};

		const pin = await pinApi.create(newPin);
		const thread = await commentApi.create(comment, pin.id, IssueType.LATE, undefined);
		selectedLocation = null;
		threads.push(thread);
	}

	async function openReportModal() {
		reportIssueModal.open();
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
			{#each threads as thread (thread.pin)}
				<PinMarker lngLat={pinToLngLat(thread.pin)} color="var(--color-primary)" />
			{/each}

			{#if selectedLocation !== null}
				<PinMarker lngLat={selectedLocation} />
			{/if}
		</MapLibre>

		<Button
			variation={ButtonVariation.PRIMARY_DARK}
			size={ButtonSize.LARGE}
			class="report-issue"
			onclick={openReportModal}>+ Report issue</Button
		>
	</div>

	<PinList {threads} />
</div>

<ReportIssueModal bind:this={reportIssueModal} stations={data.stations} lines={data.lines} onReport={reportIssue} />

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
