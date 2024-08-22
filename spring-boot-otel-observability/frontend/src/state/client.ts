import {ClientResponse} from "../types";

export async function GET<T>(url: string): Promise<ClientResponse<T>> {
    const response = await fetch(url, {method: "GET", redirect: "manual"});
    return handleResponse<T>(response)
}

export async function POST<T, U>(url: string, body: U): Promise<ClientResponse<T>> {
    const response = await fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json;charset=UTF-8"},
        redirect: "manual",
        body: JSON.stringify(body)
    });
    return handleResponse<T>(response)
}

async function handleResponse<T>(response: Response): Promise<ClientResponse<T>> {
    const {status} = response;
    if (status === 401) {
        window.location.pathname = response.headers.get("Location");
        window.location.hash = '';
        return Promise.resolve({status})
    } else if (status >= 400) {
        const error = await response.json();
        return Promise.reject({status, error})
    } else if (status === 200) {
        const data = await response.json();
        return Promise.resolve({status, data})
    } else {
        return Promise.resolve({status})
    }
}
