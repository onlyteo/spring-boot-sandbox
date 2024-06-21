export interface State<T> {
    loading: boolean,
    data?: T,
    error?: string
}

export type ActionStatus = 'LOADING' | 'SUCCESS' | 'FAILED'

export interface Action<T> {
    status: ActionStatus,
    data?: T
}