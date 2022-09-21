import { getSpot, modifyContent, search } from '~/api/spot'

export const state = () => ({
  keyword: [],
  spotList: [],
  spot: null,
})

export const mutations = {
  SET_KEYWORD(state, keyword) {
    state.keyword = keyword
  },

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
  MODIFY_CONTENT(state, content) {
    state.spot.content = content
  },
}

export const getters = {}

export const actions = {
  callSpot({ commit }, spotId) {
    getSpot(
      spotId,
      ({ data }) => {
        commit('SET_SPOT', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },

  changeContent({ state, commit }, content) {
    modifyContent(
      state.spot.spotId,
      content,
      ({ data }) => {
        commit('MODIFY_CONTENT', content)
      },
      (error) => {
        console.log(error)
      }
    )
  },

  searchSpot({ commit }, query) {
    search(
      query,
      ({ data }) => {
        commit('SET_SPOTLIST', data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  // 필요한 것들
  // 1. 선택된 spot 불러오기
  // 2. 선택된 spot의 리뷰 불러오기
}
