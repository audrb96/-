export const state = () => ({
  routeList: [],
})

export const mutations = {
  ADD_ROUTE(state, route) {
    state.routeList.push(route)
  },
  DELETE_ROUTE(state, idx) {
    state.routeList.splice(idx, 1)
  },
}

export const getters = {}

export const actions = {}
