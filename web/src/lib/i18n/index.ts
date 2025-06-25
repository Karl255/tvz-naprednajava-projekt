import { I18n } from 'i18n-js';

export const LOCALES = ['en', 'hr'];
type Locale = (typeof LOCALES)[keyof typeof LOCALES];

const DEFAULT_LOCALE = 'en' satisfies Locale;

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

i18n.locale = localStorage.getItem('locale') ?? DEFAULT_LOCALE;

export function setLocale(locale: string) {
	i18n.locale = locale;
	localStorage.setItem('locale', locale);
	window.location.reload();
}

export function getLocale(): string {
	return localStorage.getItem('locale') ?? DEFAULT_LOCALE;
}
