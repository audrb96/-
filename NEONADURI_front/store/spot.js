export const state = () => ({
  spotList: [],
  spot: null,
})

export const mutations = {
  SET_SPOTLIST(state, spotList) {
    state.spotList = spotList
  },
  SET_SPOT(state, spot) {
    state.spot = spot
  },
  CLEAR_SPOTLIST(state) {
    state.spotList = []
  },
  CLEAR_SPOT(state) {
    state.spot = null
  },
}

export const getters = {}

export const actions = {
  // 필요한 것들
  // 1. 선택된 spot 불러오기
  // 2. 선택된 spot의 리뷰 불러오기
}
