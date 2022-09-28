import {
  getReviews,
  modifyReview,
  writeReview,
  comparePass,
} from '~/api/review'

export const state = () => ({
  reviewList: [],
  review: null,
})

export const mutations = {
  SET_REVIEWLIST(state, reviewList) {
    state.reviewList = reviewList
  },
  SET_REVIEW(state, review) {
    state.review = review
  },
  CLEAR_REVIEWLIST(state) {
    state.reviewList = []
  },
  CLEAR_REVIEW(state) {
    state.review = null
  },
}

export const getters = {}

export const actions = {
  callReviews({ commit }, spotId) {
    getReviews(
      spotId,
      ({ data }) => {
        commit('SET_REVIEWLIST', data.reviewContainsTags)
      },
      (error) => {
        console.log(error)
      }
    )
  },

  registReview({ commit }, review) {
    writeReview(
      review,
      ({ data }) => {
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  changeReview({ commit }, review) {
    modifyReview(
      review,
      ({ data }) => {
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },
  confirmPass({ commit }, info) {
    comparePass(
      info.id,
      info.password,
      ({ data }) => {
        console.log(data)
      },
      (error) => {
        console.log(error)
      }
    )
  },

  // 필요한 것들
  // 1. 리뷰 등록, 수정하기
  // 2. 리뷰 수정하기 전 기본정보 불러오기
}
