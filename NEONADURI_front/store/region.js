import { getSidos, getSigungus, getMyeon } from '~/api/region'

export const state = () => ({
  sidoList: [],
  sigunguList: [],
  myeonList: [],
})

export const mutations = {
  SET_SIDOLIST(state, sidoList) {
    state.sidoList = sidoList
  },
  SET_SIGUNGULIST(state, sigunguList) {
    state.sigunguList = sigunguList
  },
  SET_MYEONLIST(state, myeonList) {
    state.myeonList = myeonList
  },
  CLEAR_SIDOLIST(state) {
    state.sidoList = []
  },
  CLEAR_SIGUNGULIST(state) {
    state.sigunguList = []
  },
  CLEAR_MYEONLIST(state) {
    state.myeonList = []
  },
}

export const getters = {
  sidoSelect(state) {
    const sidoOption = []
    sidoOption.push({ value: 'all', text: '전체' })
    state.sidoList.forEach((sido) => {
      sidoOption.push({ value: sido, text: sido })
    })

    return sidoOption
  },
}

export const actions = {
  callSidos({ commit }) {
    getSidos(
      ({ data }) => {
        commit('SET_SIDOLIST', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  callSigungus({ commit }, sido) {
    getSigungus(
      sido,
      ({ data }) => {
        commit('SET_SIGUNGULIST', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  callMyeons({ commit }, location) {
    getMyeon(
      location,
      ({ data }) => {
        commit('SET_MYEONLIST', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
}
