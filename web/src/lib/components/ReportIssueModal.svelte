<script lang="ts">
	import { ISSUE_TYPES } from '$lib/constants/issue-types';
	import { ButtonVariation } from '$lib/model/components';
	import { IssueType, type LineDto, type StationDto } from '$lib/model/dto';
	import Button from './Button.svelte';

	interface Props {
		onReport: (issueType: IssueType, station: StationDto, line: LineDto, comment: string) => void;
		stations: StationDto[];
		lines: LineDto[];
	}

	const { onReport, stations, lines }: Props = $props();

	let dialog: HTMLDialogElement;

	let issue: IssueType | null = $state(null);
	let station: StationDto | null = $state(null);
	let line: LineDto | null = $state(null);
	let comment: string = $state('');

	let isValid = $derived(issue !== null && station !== null && line !== null && comment.length > 0);

	export function open() {
		dialog.showModal();
	}

	function report() {
		if (issue !== null && station !== null && line !== null) {
			onReport(issue, station, line, comment);
			dialog.close();
		}
	}
</script>

<dialog bind:this={dialog} class="form">
	<form onsubmit={report}>
		<p class="header">Report issue</p>

		<div class="form-group">
			<label for="station">Issue</label>
			<select id="station" bind:value={issue}>
				{#each ISSUE_TYPES as option (option.value)}
					<option value={option.value}>{option.text}</option>
				{/each}
			</select>
		</div>

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
