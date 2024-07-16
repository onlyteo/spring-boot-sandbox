export interface Greeting {
    message: string
}

export interface DelayedGreeting {
    message: string,
    initial: boolean,
    waiting: boolean,
    received: boolean
}

export const initialDelayedGreeting: DelayedGreeting = {
    message: "",
    initial: true,
    waiting: false,
    received: false
}

export const waitingDelayedGreeting: DelayedGreeting = {
    message: "",
    initial: false,
    waiting: true,
    received: false
}

export const receivedDelayedGreeting: DelayedGreeting = {
    message: "",
    initial: false,
    waiting: false,
    received: true
}