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
  // 필요한 것들
  // 1. 리뷰 등록, 수정하기
  // 2. 리뷰 수정하기 전 기본정보 불러오기
}
