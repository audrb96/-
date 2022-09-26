export const state = () => ({
  routeList: [],
})

export const mutations = {
  ADD_ROUTE(state, route) {
    state.routeList.push(route)
  },
  DELETE_ROUTE(state, idx) {
    state.routeList = state.routeList.filters((route) => {
      return route.idx !== idx
    })
  },
}

export const getters = {}

export const actions = {}
