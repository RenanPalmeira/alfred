const initialState = {
    templates: []
}

export default function reducer(state = initialState, action) {
    switch (action.type) {

        case 'SET_TEMPLATES_FULFILLED':
            return {
                ...state,
                templates: action.payload.data
            }

        default:
            return state;
    }
}