const JSON_HEADERS: HeadersInit = {
	Accept: 'application/json',
	'Content-Type': 'application/json',
};

export class ApiClient {
	constructor(private basePath: string) {}

	async get<T = void>(path: string = '', params?: URLSearchParams): Promise<T> {
		const url = this.basePath + path + (params ? '?' + params.toString() : '');

		const response = await fetch(url, { method: 'GET' });
		return await this.getResponseJson<T>(response);
	}

	async post<T = void>(path: string = '', data?: object): Promise<T> {
		return await this.doJsonRequest<T>('POST', path, data);
	}

	async put<T = void>(path: string = '', data?: object): Promise<T> {
		return await this.doJsonRequest<T>('PUT', path, data);
	}

	async patch<T = void>(path: string = '', data?: object): Promise<T> {
		return await this.doJsonRequest<T>('PATH', path, data);
	}

	async delete<T = void>(path: string = '', data?: object): Promise<T> {
		return await this.doJsonRequest<T>('DELETE', path, data);
	}

	private async doJsonRequest<T>(method: string, path: string, data?: object) {
		const url = this.basePath + path;
		const requestInit: RequestInit = { method, headers: JSON_HEADERS };

		if (data) {
			requestInit.body = JSON.stringify(data);
		}

		const response = await fetch(url, requestInit);
		return await this.getResponseJson<T>(response);
	}

	private async getResponseJson<T>(response: Response) {
		return (await response.json()) as T;
	}
}
