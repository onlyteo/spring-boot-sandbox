import {Action, Greeting, State} from "../types";

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
                error: 'BOOM!'
            }
        default:
            return state
    }
};