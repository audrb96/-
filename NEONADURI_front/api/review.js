import { apiInstance } from '.'

const api = apiInstance()

async function getReviews(spotId, success, fail) {
  await api.get(`/api/spot/${spotId}/review`).then(success).catch(fail)
}

async function writeReview(review, success, fail) {
  await api
    .post(`/api/review`, JSON.stringify(review))
    .then(success)
    .catch(fail)
}

async function modifyReview(review, success, fail) {
  await api.put(`/api/review`, JSON.stringify(review)).then(success).catch(fail)
}

async function comparePass(id, pass, success, fail) {
  await api.get(`/api/review/pass/${id}/${pass}`).then(success).catch(fail)
}

export { writeReview, getReviews, modifyReview, comparePass }
