const url = new URL(window.location.href);
url.port = '8080';
const BASE_PATH = url.origin + '/di-je-zet/api';

export const API_PATH = {
	PIN: BASE_PATH + '/pins',
	COMMENT: BASE_PATH + '/comments',
	LINE: BASE_PATH + '/lines',
	STATION: BASE_PATH + '/stations',
	AUTH: BASE_PATH + '/auth',
};
