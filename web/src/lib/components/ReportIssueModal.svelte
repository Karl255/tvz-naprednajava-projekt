<script lang="ts">
	import { ButtonVariation } from '$lib/model/components';
	import type { LineDto, StationDto } from '$lib/model/dto';
	import Button from './Button.svelte';

	interface Props {
		onReport: (station: StationDto, line: LineDto, comment: string) => void;
		stations: StationDto[];
		lines: LineDto[];
	}

	const { onReport, stations, lines }: Props = $props();

	let dialog: HTMLDialogElement;

	let station: StationDto | null = $state(null);
	let line: LineDto | null = $state(null);
	let comment: string = $state('');

	let isValid = $derived(station !== null && line !== null && comment.length > 0);

	export function open() {
		dialog.showModal();
	}

	function report() {
		if (station !== null && line !== null) {
			dialog.close();
			onReport(station, line, comment);
		}
	}
</script>

<dialog bind:this={dialog} class="form">
	<form onsubmit={report}>
		<p class="header">Report issue</p>

		<div class="form-group">
			<label for="station">Station</label>
			<select id="station" bind:value={station}>
				{#each stations as station (station.id)}
					<option value={station}>{station.name}</option>
				{/each}
			</select>
		</div>

		<div class="form-group">
			<label for="line">Line</label>
			<select id="line" bind:value={line}>
				{#each lines as line (line.id)}
					<option value={line}>{line.name}</option>
				{/each}
			</select>
		</div>

		<div class="form-group">
			<label for="comment">Comment</label>
			<textarea id="comment" bind:value={comment}></textarea>
		</div>

		<Button variation={ButtonVariation.PRIMARY_LIGHT} onclick={report} disabled={!isValid}>Report</Button>
	</form>
</dialog>

<style>
	form {
		display: contents;
	}
</style>
