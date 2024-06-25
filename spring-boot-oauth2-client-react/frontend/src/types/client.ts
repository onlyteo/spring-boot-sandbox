export interface ClientError {
    status: number,
    error: string,
    trace: string,
}

export interface ClientResponse<T> {
    status: number,
    data?: T,
    error?: ClientError
}