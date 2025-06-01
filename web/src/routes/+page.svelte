<script lang="ts">
	import { MapLibre } from 'svelte-maplibre';
</script>

<MapLibre
	style="/map-styles/osm-liberty.json"
	standardControls="bottom-left"
	center={[15.985, 45.8]}
	zoom={12}
	dragRotate={false}
	pitchWithRotate={false}
/>
