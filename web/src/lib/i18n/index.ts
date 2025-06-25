import { I18n, type Scope } from 'i18n-js';
import { derived, writable } from 'svelte/store';

export const i18n = new I18n({
	en: {
		username: 'Username',
		password: 'Password',
		confirmPassword: 'Confirm Password',
		login: 'Login',
		failedToLogin: 'Failed to login',
		register: 'Register',
		failedToRegister: 'Failed to register',
		logout: 'Logout',
		reportIssue: 'Report Issue',
		station: 'Station',
		line: 'Line',
		comment: 'Comment',
		report: 'Report',
		lineNumber: 'Line %{lineNumber}',
		issueType: 'Issue Type',
		collapse: 'Collapse',
		uncollapse: 'Uncollapse',
		minAgo: '%{minutes} min ago',
		reply: 'Reply',
	},
	hr: {
		username: 'Korisničko ime',
		password: 'Lozinka',
		confirmPassword: 'Potvrdi lozinku',
		login: 'Prijava',
		failedToLogin: 'Prijava nije uspjela',
		register: 'Registracija',
		failedToRegister: 'Registracija nije uspjela',
		logout: 'Odjavi se',
		reportIssue: 'Prijavi problem',
		station: 'Stanica',
		line: 'Linija',
		comment: 'Komentar',
		report: 'Prijava',
		lineNumber: 'Linija %{lineNumber}',
		issueType: 'Vrsta problema',
		collapse: 'Sakrij',
		uncollapse: 'Proširi',
		minAgo: 'prije %{minutes} min',
		reply: 'Odgovori',
	},
});

export const locale = writable<'en' | 'hr'>('en');

locale.subscribe(value => {
	i18n.locale = value;
});

export const t = derived(locale, () => {
	return (key: Scope, options = {}) => i18n.t(key, options);
});
