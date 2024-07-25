import {ClientResponse} from "../types";

export async function GET<T>(url: string): Promise<ClientResponse<T>> {
    const response = await fetch(url, {method: "GET", redirect: "manual"});
    const {status} = response;
    if (status === 200) {
        const data = await response.json();
        return {status, data}
    } else {
        return {status}
    }
}

export async function POST<T, U>(url: string, body: U): Promise<ClientResponse<T>> {
    const response = await fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json;charset=UTF-8"},
        redirect: "manual",
        body: JSON.stringify(body)
    });
    console.log("Client", response);
    const {status} = response;
    if (status === 200) {
        const data = await response.json();
        return {status, data}
    } else {
        return {status}
    }
}
