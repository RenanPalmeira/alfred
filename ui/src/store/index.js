import thunk from 'redux-thunk'
import promise from 'redux-promise-middleware'
import { createLogger } from 'redux-logger'
import { createStore, applyMiddleware } from 'redux'
import templateReducer from './template/reducer'

const middleware = applyMiddleware(promise(), thunk, createLogger())

export default createStore(templateReducer, middleware)