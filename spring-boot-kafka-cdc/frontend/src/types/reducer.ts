export interface State<T> {
    loading: boolean,
    data?: T,
    error?: string
}

export interface Action<T> {
    status: 'LOADING' | 'SUCCESS' | 'FAILED',
    data?: T
}