import {Action, Greeting, State, User} from "../types";

export const initialGreetingState: State<Greeting> = {
    loading: false
}

export const greetingReducer = (state: State<Greeting>, action: Action<Greeting>): State<Greeting> => {
    const {status, data} = action;

    switch (status) {
        case 'SUCCESS':
            return {
                loading: false,
                data
            }
        case 'FAILED':
            return {
                loading: false,
                error: 'An error occurred while fetching greeting'
            }
        default:
            return state
    }
};

export const userInitialState: State<User> = {
    loading: true,
    data: {sub: 'N/A'}
}

export const userReducer = (state: State<User>, action: Action<User>): State<User> => {
    const {status, data} = action;
    switch (status) {
        case 'SUCCESS':
            return {
                loading: false,
                data
            }
        case 'FAILED':
            return {
                loading: false,
                data: {sub: 'N/A'},
                error: 'An error occurred while fetching user'
            }
        default:
            return state
    }
};